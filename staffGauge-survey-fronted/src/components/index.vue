<template>
    
</template>

<script>
    export default {
        name: "index"
    }
</script>
<div id="app">
  <h1>{{ message }}</h1>
  <input type="text" v-model="message"><!--v-model是双向环境-->
  <pre>
    {{$data|json}}
  </pre>
</div>
<!-- built files will be auto injected -->
<script>
  new Vue({
    el:'#app',
    data:{
        message: "hello world!"
    }
  });
</script>
<style scoped>

</style>
