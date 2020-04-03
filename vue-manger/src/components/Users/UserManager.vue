<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card class="box-card">
      <el-row :gutter="30">
        <el-col :span="30">
          <el-form>
          <el-form-item>
              <el-input placeholder="请输入内容" style="border: 1px;border-radius: 50px!important;"></el-input>
          </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" round>搜索用户</el-button>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" round>添加用户</el-button>
        </el-col>
      </el-row>
      <el-table :data="userList">
        <el-table-column label="姓名" prop="userName"></el-table-column>
        <el-table-column label="邮箱" prop="mail"></el-table-column>
        <el-table-column label="电话" prop="phoneNumber"></el-table-column>
        <el-table-column label="角色" prop="permission"></el-table-column>
        <el-table-column label="操作"></el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
    export default {
        name: "UserManager",
        data() {
          return {
            queryInfo: {
              pageNum: 1,
              pageSize: 10
            },
            userList: [],
            total: 0
          }
        },
      created() {
          this.getUserList()
      },
      methods: {
        async getUserList(){
          const{data: res}=await this.$http.get('user/getUserList',{params:this.queryInfo})
          console.log(res)
          this.userList = res.list
          console.log("liebiao")
          console.log(this.userList)
          this.total = res.total
          console.log(this.total)
        }
      }
    }
</script>

<style lang="less" scoped>

</style>
