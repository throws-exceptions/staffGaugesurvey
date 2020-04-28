<template>
  <div>
    <el-card class="user-card">
      <el-row :gutter="5" class="user-row">
        <el-col :span="7">
          <el-input
            size="mini"
            v-model="search"
            placeholder="输入用户名关键字搜索"/>
        </el-col>
      </el-row>

      <el-table
        :data="userList.filter(data => !search || data.userName.toLowerCase().includes(search.toLowerCase()))"
        border
        :default-sort="{prop: 'userName', order: 'Ascending'}">
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column
          prop="userName"
          label="姓名">
        </el-table-column>
        <el-table-column
          prop="mail"
          label="邮箱">
        </el-table-column>
        <el-table-column
          prop="phoneNumber"
          label="电话">
        </el-table-column>
        <el-table-column
          prop="permissionName"
          label="角色"
          :formatter="isManager"
          sortable
          :sort-method="sortManager"
        >
        </el-table-column>
        <el-table-column
          label="操作">
          <template slot-scope="scope">
            <el-button type="success" icon="el-icon-top" size="small" @click="addManger(scope.row.userName)">升级权限
            </el-button>
            <el-button type="danger" size="small" icon="el-icon-bottom" @click="deleteManger(scope.row.userName)">降级权限
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.pageNum"
        :page-sizes="[1, 2, 5, 10]"
        :page-size="queryInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </el-card>
  </div>
</template>

<script>
  export default {
    name: "UserManager",
    data() {
      return {
        username: sessionStorage.getItem("username"),
        queryInfo: {
          //查询数据
          query: '',
          //当前页数
          pageNum: 1,
          pageSize: 5
        },
        userList: [],
        total: 0,
        permissionName: '',
        search: ''
      }
    },
    created() {
      this.getUserList()
    },
    methods: {
      async getUserList() {
        const {data: res} = await this.$http.get('user/getUserList', {params: this.queryInfo})
        this.userList = res.list
        this.total = res.total
      },
      //监听pagesize改变的事件
      handleSizeChange(newSize) {
        this.queryInfo.pageSize = newSize
        this.getUserList()
      },
      //监听page改变的事件
      handleCurrentChange(newPage) {
        this.queryInfo.pageNum = newPage
        this.getUserList()
      },
      isManager(row, column) {
        return row.permission === 'A' ? '管理员' : '普通用户'
      },
      sortManager(a, b) {
        return a.permission === 'A' ? 1 : -1
      },
      async addManger(row_name) {
        var {data: res} = await this.$http.post('user/addManager', {"admin": this.username, "username": row_name})
        if (res.code === 200) this.getUserList()
        else this.$message.error(res.msg)
      },
      async deleteManger(row_name) {
        var {data: res} = await this.$http.post('user/deleteManager', {"admin": this.username, "username": row_name})
        if (res.code === 200) this.getUserList()
        else this.$message.error(res.msg)
      }
    }
  }
</script>

<style scoped>
  .user-card /deep/ .user-row {
    margin-bottom: 10px;
  }
</style>
