<template>
      <el-container class="home-el-container">
        <el-header>
          <div>
            <span>水尺识别系统</span>
          </div>
          <el-dropdown>
            <span class="el-dropdown-link">
              <i><img src="../assets/logo.png"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native.prevent="loginOut">退出</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-header>
        <el-container>
          <el-aside width="200px">
            <el-col :span="12">
              <el-menu
                background-color="DimGray"
                text-color="#fff"
                active-text-color="#ffd04b">
<!--                  一级菜单-->
                <el-submenu :index="item.id + ''" v-for="item in menulist" :key="item.id">
<!--                  一级菜单模板区域-->
                  <template slot="title">
<!--                    一级菜单图标-->
                    <i class="el-icon-location"></i>
<!--                    一级菜单文本-->
                    <span>{{item.authName}}</span>
                  </template>
<!--                  二级菜单-->
                  <el-menu-item :index="subItem.id + ''" v-for="subItem in item.children"
                  :key="subItem.id">
                    <template slot="title">
                      <span>{{subItem.authName}}</span>
                    </template>
                  </el-menu-item>
                </el-submenu>
              </el-menu>
            </el-col>
          </el-aside>
          <el-main>Main</el-main>
        </el-container>
      </el-container>
</template>

<script>
export default {
  name: 'Index',
  data () {
    return {
      menulist: []
    }
  },
  created () {
    this.getMenuList()
  },
  methods: {
    loginOut () {
      window.sessionStorage.clear()
      this.$router.push('/login')
    },
    getMenuList () {
      const { data: res } = this.$http.get('user/menus')
      console.log(res)
      if (res.status === 500) return this.$message.error('用户权限获取失败！')
      else if (res.status === 200) this.menulist = res.list
      else this.$message.info('网络异常')
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
  .el-header{
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
    background-color: DimGray;
    color: #fff;
    text-align: center;
    line-height: 200px;
  }

  .el-main {
    background-color: #E9EEF3;
    color: #333;
    text-align: center;
    line-height: 160px;
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
</style>
