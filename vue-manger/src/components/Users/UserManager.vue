<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card class="box-card">
      <el-row :gutter="30">
        <el-col :span="30">
              <el-input placeholder="请输入内容" style="border: 1px;border-radius: 50px!important;"></el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" round>搜索用户</el-button>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" round>添加用户</el-button>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
    export default {
        name: "UserManager"
    }
</script>

<style lang="less" scoped>

</style>
