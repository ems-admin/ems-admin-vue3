<template>
  <div class="wrap9">
    <div class="container9">
      <h1>401 UNAUTHORIZED</h1>
      <div class="txt">
        <p>The access is not authorized, please contact the administrator to make sure you have the permission to access the url. If it is a newly authorized URL, please log in again and try again.</p>
        <button type="button" class="layui-btn layui-btn-lg layui-btn-normal" @click="back">返回</button>
      </div>
    </div>
  </div>
</template>

<script>
import routers from "../../router/routers";

export default {
  name: "401",
  methods: {
    back(){
      routers.back()
    }
  }
}
</script>

<style scoped>
.wrap9{
  height: calc(100vh);
  background-color: #000;
  display: flex;
  justify-content: center;
  align-items: center;
}
.container9{
  width: 1300px;
  margin: auto;
  text-align: center;
}
.container9 h1{
  width:50%;
  display: inline-block;
  margin-top: 50px;
  box-sizing: border-box;
  font-size: 60px;
  background-color: #a00;
  color: #fff;
  margin-bottom: 0.5em;
}
.container9 .txt{
  flex-shrink: 0;
  box-sizing: border-box;
  color: #fff;
}
.container9 .txt P{
  display: inline-block;
  margin-bottom: 30px;
  font-size: 26px;
  padding: 0 100px;
}
.container9 .txt p:first-child:first-letter{
  font-size: 40px;
}

.layui-btn-normal{
  background-color: rgba(170,0,0,1);
  font-weight: bold;
}

</style>