<template>
  <div id="index">
    <h1>{{ message }}</h1>
    <input type="text" v-model="message"><!--v-model是双向环境-->
    <pre>
    {{$data|json}}
  </pre>
  </div>
    
</template>
<!--v-show可以进行判断，真就显示，假就隐藏,v-if也可-->
<script>
    export default {
        name: "index",
        el:"#index",
        data(){
          return{
            message:"hello world!"
          }

        },
        methods:{
          submi

    }

    }

</script>

<!-- built files will be auto injected -->

