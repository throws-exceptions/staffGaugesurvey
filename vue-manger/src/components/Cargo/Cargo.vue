<template>
  <div>
    <el-card class="cargo-card">
      <el-row :gutter="5" class="user-row">
        <el-col :span="7">
          <el-input
            size="mini"
            v-model="search"
            placeholder="输入用户名关键字搜索"/>
        </el-col>
      </el-row>

      <el-table
        :data="cargoList.filter(data => !search || data.userName.toLowerCase().includes(search.toLowerCase()))"
        border
        :default-sort="{prop: 'person', order: 'Ascending'}">
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column
          prop="person"
          label="处理人">
        </el-table-column>
        <el-table-column
          prop="freightersNum"
          label="轮船型号">
        </el-table-column>
        <el-table-column
          prop="startWeight"
          label="初始载重量">
        </el-table-column>
        <el-table-column
          prop="endWeight"
          label="最终载重量"
        >
        </el-table-column>
        <el-table-column
          prop="goodsWeight"
          label="货物载重量"
        >
        </el-table-column>
        <el-table-column
          prop="time"
          label="时间"
        >
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
        person: sessionStorage.getItem("username"),
        queryInfo: {
          //查询数据
          query: '',
          //当前页数
          username: sessionStorage.getItem("username"),
          pageNum: 1,
          pageSize: 5
        },
        cargoList: [],
        total: 0,
        permissionName: '',
        search: ''
      }
    },
    created() {
      this.getCargoList()
    },
    methods: {
      async getCargoList() {
        const {data: res} = await this.$http.get('http://localhost:8085/cargo/getCargoList', {params: this.queryInfo,})
        this.cargoList = res.list
        this.total = res.total
      },
      //监听pagesize改变的事件
      handleSizeChange(newSize) {
        this.queryInfo.pageSize = newSize
        this.getCargoList()
      },
      //监听page改变的事件
      handleCurrentChange(newPage) {
        this.queryInfo.pageNum = newPage
        this.getCargoList()
      }
    }
  }
</script>

<style scoped>
  .cargo-card /deep/ .user-row {
    margin-bottom: 10px;
  }
</style>
