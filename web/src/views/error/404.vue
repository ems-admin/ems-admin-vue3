<template>
<div class="system">
  <div class="title">
    <h2>页面走丢了...</h2>
    <button type="button" class="layui-btn layui-btn-lg layui-btn-normal" @click="back">返回</button>
  </div>
</div>
</template>
<script setup>
import routers from "../../router/routers";
import '../../assets/css/404.css'

const back = () => {
  routers.back()
}

</script>

<style scoped>
  .layui-btn-normal{
    background-color: rgba(30,159,255,0.5);
    font-size: 18px;
    font-weight: bold;
  }
</style>