<template>
  <div>
    <el-upload
      class="upload-demo"
      action="http://localhost:8081/user/up"
      accept="image/png,image/gif,image/jpg,image/jpeg"
      :auto-upload="true"
      :limit="1">
      <el-button size="small" type="primary">点击上传</el-button>
      <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过10MB</div>

    </el-upload>
    <el-button type="primaty" @click="hidden=!hidden">隐藏</el-button>
    <div v-show="hidden">
      sdasd
    </div>
  </div>
</template>

<script>
  export default {
    name: "Distinguish",
    data() {
      return {
        active: 1,
        hidden: true
      };
    }
  }
</script>

<style scoped>

</style>
