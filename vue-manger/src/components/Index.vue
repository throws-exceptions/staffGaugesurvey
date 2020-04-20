<template>
  <el-container class="home-el-container">
    <el-header>
      <el-menu
        background-color="#fff"
        text-color="#000"
        :default-active="activePath"
        active-text-color="blue"
        :collapse="isCollapse"
        :collapse-transition="false"
        router
        mode="horizontal">
        <!--                  一级菜单-->
        <el-menu-item :index="item.path + ''" v-for="item in menulist" :key="item.id"
                      @click="saveNavStatus(item.path)">
          <!--                  一级菜单模板区域-->
          <i :class="icon[item.id]"></i>
          <template slot="title">
            <!--                    一级菜单图标-->
            <!--                    一级菜单文本-->
            <span>{{item.authName}}</span>
          </template>
          <!--                  二级菜单-->
          <!--                  <el-menu-item :index="subItem.id + ''" v-for="subItem in item.children"-->
          <!--                  :key="subItem.id">-->
          <!--                    <template slot="title">-->
          <!--                      <span>{{subItem.authName}}</span>-->
          <!--                    </template>-->
          <!--                  </el-menu-item>-->
        </el-menu-item>
      </el-menu>
      <el-dropdown>
            <span class="el-dropdown-link">
              <i><img :src='this.headUrl'></i>
            </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native.prevent="toUserInfo">个人中心</el-dropdown-item>
          <el-dropdown-item @click.native.prevent="loginOut">退出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </el-header>
    <el-container>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>

  export default {
    name: 'Index',
    data() {
      return {
        menulist: '',
        headUrl: require('@/assets/avator/' + sessionStorage.getItem('head')),
        Form: {
          permission: '',
        },
        tagsList: [],
        dialogUserVisible: false,
        icon: {
          1: 'iconfont icon-group_fill',
          2: 'iconfont icon-group_fill',
          3: 'iconfont icon-group_fill',
          4: 'iconfont icon-group_fill',
          5: 'iconfont icon-group_fill',
        },
        isCollapse: false,
        activePath: ''
      }
    },
    created() {
      this.getMenuList()
      this.activePath = window.sessionStorage.getItem("activePath")
    },
    methods: {
      loginOut() {
        window.sessionStorage.clear()
        this.$router.push('/login')
      },
      toUserInfo() {
        this.$router.push('user/info')
      },
      async getMenuList() {
        this.Form.permission = window.sessionStorage.getItem('permission')
        const {data: menusRes} = await this.$http.post('user/menus', this.Form)
        console.log(menusRes)
        console.log(this.listObj)
        if (menusRes.code === 500) return this.$message.error(menusRes.msg)
        else {
          this.menulist = menusRes
          console.log(this.menulist)
        }
      },
      toggleCollapse() {
        console.log(this.isCollapse)
        this.isCollapse = !this.isCollapse
      },
      saveNavStatus(activePath) {
        window.sessionStorage.setItem("activePath", activePath)
        this.activePath = activePath
      }

    }
  }
</script>

<style lang="scss" scoped>
  .el-dropdown-link {
    cursor: pointer;
    color: #000;
    align-items: center;
    padding-bottom: 0;

    img {
      height: 45px;
      width: 45px;
      border-radius: 50%;
      border: 2px solid #97a8be;
    }
  }

  .el-icon-arrow-down {
    font-size: 12px;
  }

  .el-header {
    background-color: #fff;
    color: #97a8be;
    font-size: 26px;
    display: flex;
    justify-content: space-between;
    padding-left: 0;
    padding-right: 0;
    align-items: center;
  }

  .el-aside {
    background-color: #fff;
    color: #fff;
    text-align: left;
    line-height: 200px;

    .el-menu {
      border-right: none;
    }

    .el-menu-item {
      margin-bottom: 10px;
    }
  }

  .el-main {
    background-color: #eee;
    text-align: center;
    /*line-height: 160px;*/
  }

  .home-el-container {
    height: 100%;
  }

  .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }

  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
  }

  .iconfont {
    margin-right: 10px;
  }

  .toggle-button {
    background-color: #97a8be;
    font-size: 10px;
    line-height: 24px;
    color: #fff;
    text-align: center;
    letter-spacing: 0.2em;
    cursor: pointer;
  }
</style>
