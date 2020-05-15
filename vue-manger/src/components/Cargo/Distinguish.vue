<template>
  <div class="Cargo-box">
    <el-steps :active="active" finish-status="success">
      <el-step title="上传照片"></el-step>
      <el-step title="计算水尺"></el-step>
      <el-step title="矫正排水量"></el-step>
      <el-step title="货物重量"></el-step>
    </el-steps>

    <el-form label-position="right">
      <div class="active1" v-if="active==1">
        <el-row type="flex">
          <el-col>
            <el-form-item label="艏左">
              <el-row type="flex">
                <el-col>
                  <el-input v-model="Form.Fp"></el-input>
                </el-col>
                <el-col>
                  <el-upload
                    class="upload-demo"
                    action="http://localhost:8085/cargo/upimg"
                    accept="image/png,image/gif,image/jpg,image/jpeg"
                    :auto-upload="true"
                    :limit="1"
                    :on-success="hanldeFp">
                    <el-button type="primary">上传<i class="el-icon-upload el-icon--right"></i></el-button>
                  </el-upload>
                </el-col>
              </el-row>
            </el-form-item>
          </el-col>
          <el-col>
            <el-form-item label="艏右">
              <el-row type="flex">
                <el-col>
                  <el-input v-model="Form.Fs"></el-input>
                </el-col>
                <el-col>
                  <el-upload
                    class="upload-demo"
                    action="http://localhost:8085/cargo/upimg"
                    accept="image/png,image/gif,image/jpg,image/jpeg"
                    :auto-upload="true"
                    :limit="1">
                    <el-button type="primary">上传<i class="el-icon-upload el-icon--right"></i></el-button>
                  </el-upload>
                </el-col>
              </el-row>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row type="flex">
          <el-col>
            <el-form-item label="艉左">
              <el-row type="flex">
                <el-col>
                  <el-input v-model="Form.Ap"></el-input>
                </el-col>
                <el-col>
                  <el-upload
                    class="upload-demo"
                    action="http://localhost:8085/cargo/upimg"
                    accept="image/png,image/gif,image/jpg,image/jpeg"
                    :auto-upload="true"
                    :limit="1">
                    <el-button type="primary">上传<i class="el-icon-upload el-icon--right"></i></el-button>
                  </el-upload>
                </el-col>
              </el-row>
            </el-form-item>
          </el-col>
          <el-col>
            <el-form-item label="艉右">
              <el-row type="flex">
                <el-col>
                  <el-input v-model="Form.As"></el-input>
                </el-col>
                <el-col>
                  <el-upload
                    class="upload-demo"
                    action="http://localhost:8085/cargo/upimg"
                    accept="image/png,image/gif,image/jpg,image/jpeg"
                    :auto-upload="true"
                    :limit="1">
                    <el-button type="primary">上传<i class="el-icon-upload el-icon--right"></i></el-button>
                  </el-upload>
                </el-col>
              </el-row>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row type="flex">
          <el-col>
            <el-form-item label="舯左">
              <el-row type="flex">
                <el-col>
                  <el-input v-model="Form.Mp"></el-input>
                </el-col>
                <el-col>
                  <el-upload
                    class="upload-demo"
                    action="http://localhost:8085/cargo/upimg"
                    accept="image/png,image/gif,image/jpg,image/jpeg"
                    :auto-upload="true"
                    :limit="1">
                    <el-button type="primary">上传<i class="el-icon-upload el-icon--right"></i></el-button>
                  </el-upload>
                </el-col>
              </el-row>
            </el-form-item>
          </el-col>
          <el-col>
            <el-form-item label="舯右">
              <el-row type="flex">
                <el-col>
                  <el-input v-model="Form.Ms"></el-input>
                </el-col>
                <el-col>
                  <el-upload
                    class="upload-demo"
                    action="http://localhost:8085/cargo/upimg"
                    accept="image/png,image/gif,image/jpg,image/jpeg"
                    :auto-upload="true"
                    :limit="1">
                    <el-button type="primary">上传<i class="el-icon-upload el-icon--right"></i></el-button>
                  </el-upload>
                </el-col>
              </el-row>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="active2" v-if="active==2">
        <div style="margin-bottom: 20px">实际水尺：{{Form.D_M}}</div>
        <el-form-item label-width="230px" label="根据实际水尺查表所得最近水尺">
          <el-input v-model="Form.tableD_M"></el-input>
        </el-form-item>

        <el-form-item label-width="230px" label="根据实际水尺查表所得排水量">
          <el-input v-model="Form.W1"></el-input>
        </el-form-item>

        <el-form-item label-width="230px" label="查表所得每厘米吃水吨数">
          <el-input v-model="Form.TPC"></el-input>
        </el-form-item>
      </div>
      <div class="active3" v-if="active==3">
        <div style="margin-bottom: 20px">实际排水量：{{Form._2}}</div>
        <el-form-item label-width="150px" label="纵倾校正后排水量">
          <el-input v-model="Form._3"></el-input>
        </el-form-item>
        <el-form-item label-width="150px" label="表载密度">
          <el-input v-model="Form.p"></el-input>
        </el-form-item>
        <el-form-item label-width="150px" label="实测密度">
          <el-input v-model="Form.p1"></el-input>
        </el-form-item>
      </div>
      <div class="active3" v-if="active==4">
        <el-row>
          <el-col>
            <div style="margin-bottom: 20px">密度校正后排水量：{{Form._4}}</div>
          </el-col>
          <el-col>
            <el-form-item label="船用物料+轻载+定量备料">
              <el-input v-model="Form.W2"></el-input>
            </el-form-item>
          </el-col>
          <el-col>
            <template>货物总量：{{Form.W}}</template>
          </el-col>
          <el-col>
            <el-button type="primary" @click="isvisiable=true">提交数据</el-button>
          </el-col>
        </el-row>
      </div>
      <div>
        <el-button style="margin-top: 12px;" @click="active=active-1" v-if="active>1">上一步</el-button>
        <el-button style="margin-top: 12px;" @click="active=active+1" v-if="active<4">下一步</el-button>
      </div>
    </el-form>
    <el-dialog title="提交表单" :modal-append-to-body="false" :visible.sync="isvisiable" center>
      <el-form :model="Cargo">
        <el-form-item label="轮船型号" label-width="120px">
          <el-input v-model="Cargo.freightersNum"></el-input>
        </el-form-item>
        <el-form-item label="初始重量" label-width="120px">
          <el-input v-model="Cargo.startWeight"></el-input>
        </el-form-item>
        <el-form-item label="最终重量" label-width="120px">
          <el-input v-model="Cargo.endWeight"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="isvisiable = false">取 消</el-button>
        <el-button type="primary" @click.prevent.native="insertCargo">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "Distinguish",
    data() {
      return {
        active: 1,
        Cargo: {
          person: sessionStorage.getItem("username"),
          freightersNum: '',
          startWeight: 0,
          endWeight: 0,
          goodsWeight: 0
        },
        isvisiable: false,
        Form: {
          Fp: 0,
          Fs: 0,
          Fps: 0,
          Ap: 0,
          As: 0,
          Aps: 0,
          Mp: 0,
          Ms: 0,
          Mps: 0,
          T: 0,
          Fc: 0,
          Ac: 0,
          Mc: 0,
          dF: 0,
          dA: 0,
          dM: 0,
          Fc: 0,
          Ac: 0,
          Mc: 0,
          Fm: 0,
          Am: 0,
          Mm: 0,
          Lbp: 0,
          Tc: 0,
          MFA: 0,
          D_M: 0,
          TPC: 0,
          tableD_M: 0,
          W1: 0,
          _2: 0,
          _3: 0,
          p: 0,
          p1: 0,
          _4: 0,
          W2: 0,
          W: 0,
        },
      };
    },
    watch: {
      Form: {
        handler(val) {
          this.Form.Fps = (Number(val.Fp) + Number(val.Fs)) / 2
          this.Form.Aps = (Number(val.Ap) + Number(val.As)) / 2
          this.Form.Mps = (Number(val.Mp) + Number(val.Ms)) / 2
          this.Form.T = (Number(val.Fps) + Number(val.Aps)) / 2
          var temp = (Number(val.Lbp) + Number(val.dF) - Number(val.dA))
          if (temp === 0) temp = 1
          this.Form.Fc = (Number(val.T) * Number(val.dF)) / temp
          this.Form.Ac = (Number(val.T) * Number(val.dA)) / temp
          this.Form.Mc = (Number(val.T) * Number(val.dM)) / temp
          this.Form.Fm = (Number(val.Fps) + Number(val.Fc))
          this.Form.Am = (Number(val.Aps) + Number(val.Ac))
          this.Form.Mm = (Number(val.Mps) + Number(val.Mc))
          this.Form.Tc = (Number(val.Am) - Number(val.Fm))
          this.Form.MFA = (Number(val.Fm) + Number(val.Am)) / 2
          this.Form.D_M = (Number(val.Fm) + Number(val.Am) + 6 * Number(val.Mm)) / 8
          this.Form._2 = Number(val.W1) - ((Number(val.tableD_M) - Number(val.D_M)) * Number(val.TPC))
          if (val.p === 0) val.p = 1
          this.Form._4 = Number(val._3) * Number(val.p1) / Number(val.p)
          this.Form.W = Number(val._4) - Number(val.W2)
          console.log(this.Form.W)
        },
        deep: true
      }
    },
    methods: {
      async insertCargo() {
        this.Cargo.goodsWeight = String(Number(this.Cargo.startWeight) - Number(this.Cargo.endWeight))
        console.log(this.Cargo.goodsWeight)
        const {data: res} = await this.$http.post('http://localhost:8085/cargo/insertCargo', this.Cargo)
        if (res.code === 200) {
          this.$message.success(res.msg)
        } else {
          this.$message.error(res.msg)
        }
      },
      hanldeFp(response) {
        console.log(response)
        this.Form.Fp = response.msg
      },
      hanldeFs(response) {
        console.log(response)
        this.Form.Fs = response.msg
      },
      hanldeAp(response) {
        console.log(response)
        this.Form.Ap = response.msg
      },
      hanldeAs(response) {
        console.log(response)
        this.Form.As = response.msg
      },
      hanldeMp(response) {
        console.log(response)
        this.Form.Mp = response.msg
      },
      hanldeMs(response) {
        console.log(response)
        this.Form.Ms = response.msg
      },
    }
  }
</script>

<style scoped>
  .el-form /deep/ .el-input {
    width: 120px;
    margin-left: 0px;
  }

  .active1 {
    width: 600px;
    height: 80%;
    position: absolute;
    left: 50%;
    top: 20%;
    transform: translate(-50%, 10%);
  }

  .active2 {
    width: 600px;
    height: 80%;
    position: absolute;
    left: 50%;
    top: 20%;
    transform: translate(-50%, 10%);
  }

  .active3 {
    width: 400px;
    height: 80%;
    position: absolute;
    left: 50%;
    top: 20%;
    transform: translate(-50%, 10%);
  }

  .active4 {
    width: 600px;
    height: 80%;
    position: absolute;
    left: 50%;
    top: 20%;
    transform: translate(-50%, 10%);
  }

  .upload-demo {
    width: 0;
  }

  .active2 {
    width: 60%;
  }

  .Cargo-box {
    /*position: relative;*/
    width: 700px;
    height: 600px;
    background-color: #fff;
    border-radius: 3px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    /*padding: 160px 35px 0;*/
    /*margin: 0 auto;*/
    /*overflow: hidden;*/
  }
</style>
