<template>
  <div class="register-container">
    <img src="../assets/background.jpg">
    <div class="register-box">
      <div class="title-container" style="">
        <img src="../assets/logo_web.png">
        <!--          <h3 class="title">水尺识别系统</h3>-->
      </div>
      <div>
        <el-form class="register-form" ref="registerForm" :model="registerForm" :rules="registerRules" auto-complete="on" label-position="left">
          <el-form-item prop="mail">
            <el-input v-model="email" prefix-icon="iconfont icon-mail_fill" placeholder="邮箱" />
          </el-form-item>
          <el-form-item prop="username">
            <el-input v-model="registerForm.username" prefix-icon="iconfont icon-people_fill" placeholder="用户名"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input
              v-model="registerForm.password"
              prefix-icon="iconfont icon-lock_fill"
              placeholder="密码"
              type="password"
              show-password
              @keyup.enter.native="handleregister"
            />
          </el-form-item>
          <el-form-item  prop="verifyCode">
            <el-input v-model="registerForm.Code" placeholder="验证码">
            </el-input>
          </el-form-item>
          <el-button style="width:100%;margin-bottom:30px;border-radius: 20px;" type="primary" @click.native.prevent="sendCode">发送验证码</el-button>
          <div style="height: 30px">
            <el-button type="primary" style="width:100%;margin-bottom:30px;border-radius: 20px;" @click.native.prevent="handleRegister">注册</el-button>
          </div>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Register',
  data () {
    return {
      // 登录表单数据对象
     registerForm: {
          mail: '1163107972@qq.com',
          username: '傅杰青',
          password: '123456',
          Code: '',
          verifyCode_status: false,
          isRemember: '0'
        },
      email: "1163107972@qq.com",
        serverCode: "",
        registerRules: {
          mail: [
            { required: true, message: '请输入邮箱地址', trigger: 'blur' },
            { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change']}
          ],
          username: [
            { required: true, message: '请输入用户名!', trigger: 'blur' },
            { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请输入密码!', trigger: 'blur' },
            { min:6, message: '至少6个字符以上', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      handleRegister () {
        this.$refs.registerForm.validate(async valid => {
          if (!valid) return
          const response = await this.$http.post('user/register', this.registerForm)
          const { data: result } = response
          if (result.code === 200) {
            const { data: loginRes }=await this.$http.post('user/login', this.loginForm)
            if(loginRes.code === 200){
              window.sessionStorage.setItem('head',loginRes.head)
              window.sessionStorage.setItem('token', loginRes.token_id)
              window.sessionStorage.setItem('permission',loginRes.permission)
              window.sessionStorage.setItem('username',loginRes.username)
              this.$message.success('注册成功')
              this.$router.push({ path: '/index' })
            }else {
              this.$message.error('登录失败')
              this.$router.push('/login')
            }
          } else if (result.code === 500) return this.$message.error(result.msg)
          else { return this.$message.info('服务器异常') }
        })
      },
      sendCode () {
        this.$refs.registerForm.validate(async valid => {
          if (!valid) return
          const response = await this.$http.post('user/verifyCode', this.registerForm)
          const { data: res}=response
          console.log(res)
          if( res.code == 200)this.$message.success(res.msg)
          else if(res.code) this.$message.error(res.msg)
          else this.$message.info(res.msg)
        })
      }
    }
  }
</script>

<style lang="less" scoped>

  .register-container {
    /*min-height: 100%;*/
    width: 100%;
    height: 100%;
    background-color: #fff;
    img {
      height: 100%;
      width: 100%;
      border: 0px;
      vertical-align: middle;
      /*overflow: hidden;*/
    }
    /*overflow: hidden;*/
  }
  .register-box {
    /*position: relative;*/
    width: 400px;
    height: 60%;
    /*background-color: #fff;*/
    border-radius: 3px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    /*padding: 160px 35px 0;*/
    /*margin: 0 auto;*/
    /*overflow: hidden;*/
  }
  .tips {
    font-size: 26px;
    color: #f00;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }
  .title-container {
    height: 130px;
    width: 130px;
    border-radius: 50%;
    /*border: 1px solid #eee;*/
    padding: 30px;
    /*box-shadow: 0 0 10px #ddd;*/
    /*position: relative;*/
    position: absolute;
    left: 50%;
    transform: translate(-50%, -50%);
    /*background-color: #fff;*/
    img{
      width: 100%;
      height: 100%;
      border-radius: 50%;
      /*background-color: #eeeeee;*/
    }

    /*.title {*/
    /*  font-size: 23px;*/
    /*  color: #409EFF;*/
    /*  margin: 0px auto 10px auto;*/
    /*  text-align: center;*/
    /*  font-weight: bold;*/
    /*}*/
  }
  .register-form{
    /*position: absolute;*/
    bottom: 0;
    width: 100%;
    padding: 30px 20px 0 20px;
    box-sizing: border-box;
    margin-top: 50px;
  }

  /*.tips {*/
  /*  font-size: 14px;*/
  /*  color: #fff;*/
  /*  margin-bottom: 10px;*/

  /*  span {*/
  /*    &:first-of-type {*/
  /*      margin-right: 16px;*/
  /*    }*/
  /*  }*/
  /*}*/
  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: #889aa4;
    cursor: pointer;
    user-select: none;
  }
</style>
<!--<style lang="css" scoped>-->
<!--  .register-el-input >>> el-input.test-el-input{-->
<!--    display: inline-block;-->
<!--    border: 2px solid;-->
<!--    height: 47px;-->
<!--    width: 85%;-->
<!--    border-radius: 50px;-->
<!--  }-->
<!--  .register-el-form-item /deep/ .el-form-item{-->
<!--    border: 1px solid rgba(0, 0, 0, 1);-->
<!--    background: rgba(255, 255, 255, 0.5);-->
<!--    border-radius: 50px;-->
<!--  }-->
<!--</style>-->
<style lang="scss" scoped>
  /* 修复input 背景不协调 和光标变色 */
  /* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

  $bg:#fff;
  $light_gray:#050505;
  $cursor: #000;

  @supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
    .register-container .el-input input {
      color: $cursor;
    }
  }

  /* reset element-ui css */
  /*.register-container {*/
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;
    /*background-color: rgba(0,0,0,0.5);*/

    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 20px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid;
    background: rgba(255, 255, 255, 0.8);
    border-radius: 50px;
    /*color: #11ff22;*/
  }
  .el-form-item__error{
    padding-left: 30px;
    color: orangered!important;
  }
  /*}*/
</style>
