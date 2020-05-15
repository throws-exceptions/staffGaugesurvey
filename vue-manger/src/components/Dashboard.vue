<template>
  <div>
    <el-card class="mgb20" shadow="never" style="height:252px;">
      <div class="user-info-list">
        <img :src="this.userInfoForm.head" class="user-avator" alt/>
        <div class="user-info-list">
          <div class="user-info-name">{{this.userInfoForm.name}}</div>
          <div>{{this.userInfoForm.permission}}</div>
          <div>{{this.userInfoForm.mail}}</div>
          <el-button type="text" @click="dialogUserVisible=true">编辑资料</el-button>
          <el-dialog class="dialog" title="编辑个人资料" :visible.sync="dialogUserVisible">
            <el-form ref="userInfoForm" :model="userInfoForm" :rules="userInfoFormRules">
              <img :src="this.userInfoForm.head" class="user-avator" alt/>
              <el-upload
                class="upload-demo"
                action="http://localhost:8081/user/up"
                :data="{username: this.userInfoForm.name}"
                accept="image/png,image/gif,image/jpg,image/jpeg"
                :auto-upload="true"
                :limit="1"
              >
                <el-button size="small" type="primary">点击上传</el-button>
                <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过2MB</div>
              </el-upload>
              <el-form-item label="用户名" label-width="100px">
                <el-input v-model="userInfoForm.newname"></el-input>
              </el-form-item>
              <el-form-item label="邮箱" label-width="100px">
                <el-row>
                  <el-col :span="15">
                    <el-input v-model="userInfoForm.newmail"></el-input>
                  </el-col>
                  <el-col :span="5">
                    <el-button type="text" @click="hidden=!hidden">发送验证码</el-button>
                  </el-col>
                </el-row>
              </el-form-item>
              <el-form-item v-show="hidden" label="验证码" label-width="100px">
                <el-row>
                  <el-col :span="15">
                    <el-input v-model="Code"></el-input>
                  </el-col>
                  <el-col :span="5">
                    <el-button type="text" @click="updateCode">验证</el-button>
                  </el-col>
                </el-row>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogUserVisible = false">取 消</el-button>
              <el-button type="primary" @click="updateUserInfo">确 定</el-button>
            </div>
          </el-dialog>
        </div>
      </div>
    </el-card>
    <el-card shadow="never">
      <vue-event-calendar :events="todoList" @day-changed="handleDayChanged" @month-changed="handleMonthChanged"
                          class="events-calendar">
        <template scope="props">
          <div v-for="(event, index) in props.showEvents" :key="index" class="event-item">
            <!-- In here do whatever you want, make you owner event template -->
            <div class="wrapper">
              <h3 class="title">{{index+1}}.{{event.title}}</h3>
              <p class="time">{{event.date}}</p>
              <p class="desc">{{event.desc}}</p>
              <el-button type="text" icon="el-icon-edit" circle
                         @click="Event=event,dialogVisible=true"></el-button>

              <el-button type="text" icon="el-icon-success" circle @click="deleteToList(event)"></el-button>
            </div>
          </div>
          <el-button type="text" @click="dialogFormVisible = true"
                     icon="el-icon-circle-plus"></el-button>
        </template>
      </vue-event-calendar>
      <el-dialog class="dialog2" center title="编辑待办事项" :visible.sync="dialogVisible">
        <el-form :model="Event">
          <el-form-item label="日期" label-width="120px">
            <el-date-picker type="date" placeholder="选择日期" v-model="Event.date"
                            style="width: 100%;" value-format="yyyy/MM/dd"
                            change="yyyy/MM/dd"></el-date-picker>
          </el-form-item>
          <el-form-item label="标题" label-width="120px">
            <el-input v-model="Event.title"></el-input>
          </el-form-item>
          <el-form-item label="内容" label-width="120px">
            <el-input type="textarea" v-model="Event.desc"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="updateToDo">确 定</el-button>
        </div>
      </el-dialog>
      <el-dialog class="dialog2" title="添加待办事项" :visible.sync="dialogFormVisible" custom-class="insert">
        <el-form :model="Event">
          <el-form-item label="日期" label-width="120px">
            <el-date-picker type="date" placeholder="选择日期" v-model="Event.date"
                            style="width: 100%;" value-format="yyyy/MM/dd" change="yyyy/MM/dd"></el-date-picker>
          </el-form-item>
          <el-form-item label="标题" label-width="120px">
            <el-input v-model="Event.title"></el-input>
          </el-form-item>
          <el-form-item label="内容" label-width="120px">
            <el-input type="textarea" v-model="Event.desc"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="addToList">确 定</el-button>
        </div>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>

  export default {
    name: 'dashboard',
    data() {
      return {

        todoList: [{date: '2020/04/14', title: '测试', desc: '测试日历功能'},
          {date: '2020/04/15', title: '测试', desc: '测试日历功能'}],
        Event: {date: '', title: '', desc: ''},
        dialogFormVisible: false,
        dialogVisible: false,
        dialogUserVisible: false,
        dialogCode: false,
        Code: '',
        userInfoForm: {
          name: sessionStorage.getItem('username'),
          newname: '',
          // head: require('@/assets/avator/' + sessionStorage.getItem('head')),
          head: sessionStorage.getItem('head').startsWith('http') ? sessionStorage.getItem('head') : require('@/assets/avator/' + sessionStorage.getItem('head')),
          permission: sessionStorage.getItem('permission') === 'A' ? '管理员' : '普通用户',
          mail: '',
          newmail: ''
        },
        userInfoFormRules: {
          mail: [
            {required: true, message: '请输入邮箱地址', trigger: 'blur'},
            {type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change']}
          ],
          name: [
            {required: true, message: '请输入用户名!', trigger: 'blur'},
            {min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur'}
          ]
        },
        hidden: false
      };
    },
    created() {
      this.getUserInfo();
      this.getToList();
    },
    // activated() {
    //     this.handleListener();
    // },
    // deactivated() {
    //     window.removeEventListener('resize', this.renderChart);
    //     bus.$off('collapse', this.handleBus);
    // },
    methods: {
      async getUserInfo() {
        const {data: res} = await this.$http.get('user/info', {params: {"username": sessionStorage.getItem('username')}})
        window.sessionStorage.setItem('head', res.head)
        window.sessionStorage.setItem('permission', res.permission)
        window.sessionStorage.setItem('username', res.username)
      },
      getToList() {
        this.$http.get('user/getToDoList', {params: {username: this.userInfoForm.name}}).then(response => {
          console.log(response.data.list)
          this.todoList = response.data.list
          console.log(this.todoList)
        })
      },
      async addToList() {
        const {data: res} = await this.$http.post('user/addToDoList', this.twoJsonMerge({username: this.userInfoForm.name}, this.Event))
        if (res.code === 200) {
          this.getToList()
        } else {
          this.$message.error(res.msg)
        }
        this.dialogFormVisible = false;
      },
      updateToDo() {
        this.$http.post('user/updateToDoList', this.twoJsonMerge({username: this.userInfoForm.name}, this.Event)).then(
          response => {
            if (response.data.code === 200) {
              this.getToList()
            } else {
              this.$message.error(res.msg)
            }
          }
        )

        this.dialogVisible = false;
      },
      async deleteToList(event) {
        var resEvent = this.twoJsonMerge({username: this.userInfoForm.name}, event)
        const {data: res} = await this.$http.post('user/deleteToDoList', resEvent)
        if (res.code === 200) {
          this.getToList()
        } else {
          this.$message.error(res.msg)
        }
      },
      twoJsonMerge(json1, json2) {
        console.log(json1)
        console.log(json2)
        var jsonStr = JSON.parse((JSON.stringify(json1) + JSON.stringify(json2)).replace(/}{/, ','));
        console.log(jsonStr)
        return jsonStr
      },
      handleMonthChanged(data) {
        console.log(data);
      },
      handleDayChanged(data) {
      },
      sendCode() {
        this.$refs.userInfoForm.validate(async valid => {
          if (!valid) return
          this.hidden = true
          const response = await this.$http.post('user/verifyCode', this.userInfoForm)
          const {data: res} = response
          console.log(res)
          if (res.code == 200) {
            this.$message.success(res.msg)
          } else if (res.code) this.$message.error(res.msg)
          else this.$message.info(res.msg)
        })
      },
      updateUserInfo() {
        this.$refs.userInfoForm.validate(async valid => {
          if (!valid) {
            this.$message.error("用户信息更新失败")
            return
          }
          if (this.userInfoForm.mail === this.userInfoForm.newmail || this.flag) {
            const response = await this.$http.post('user/updateUserInfo', this.userInfoForm)
            if (response.data.code === 200) {
              this.getUserInfo()
            }
          } else this.$message.error("用户信息更新失败")

        })
        this.dialogUserVisible = false
      },
      async updateCode() {
        var {data: res} = await this.$http.post('user/updateCode', {
          'name': this.userInfoForm.name,
          'Code': this.Code
        })
        if (res.code === 200) {
          this.flag = true
        } else {
          this.flag = false
        }
        this.dialogCode = false
      }
    }
  };
</script>


<style scoped>
  .el-row {
    margin-bottom: 20px;
  }

  .el-card {
    border: 0px;

  }

  .user-info {
    display: flex;
    align-items: center;
    padding-bottom: 0px;
    border-bottom: 2px solid #ccc;
    margin-bottom: 20px;
  }

  .user-avator {
    height: 60px;
    width: 60px;
    border-radius: 50%;
  }

  .dialog {
    width: 850px;
    height: 100%;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
  }

  .dialog2 {
    width: 850px;
    height: 100%;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -30%);
  }

  .user-info-cont {
    padding-left: 10px;
    flex: 1;
    font-size: 14px;
    color: #999;
  }

  .user-info-cont div:first-child {
    font-size: 30px;
    color: #222;
  }

  .user-info-list {
    font-size: 14px;
    color: #999;
    line-height: 25px;
  }

  .user-info-list span {
    margin-left: 70px;
  }

  .mgb20 {
    margin-bottom: 10px;
    height: 130px;
  }


</style>
