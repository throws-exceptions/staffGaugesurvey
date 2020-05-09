<template>
  <div>
    <el-form>
      <el-form-item label="艏左">
        <el-input v-model="Form.Fp"></el-input>
      </el-form-item>
      <el-form-item label="艏右">
        <el-input v-model="Form.Fs"></el-input>
      </el-form-item>
      <el-form-item label="艏平均">
        <el-input v-model="this.Form.Fps"></el-input>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  export default {
    name: "Distinguish",
    data() {
      return {
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
          _2: 0,
          _3: 0,
          p: 0,
          p1: 0,
          _4: 0,
          W: 0,
        }
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

        },
        deep: true
      }
    }
  }
</script>

<style scoped>

</style>
