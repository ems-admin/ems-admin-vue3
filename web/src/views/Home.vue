<template>
  <div class="home">
    <img alt="EMS logo" src="../assets/image/ems.png">
    <HelloWorld :msg="msg"/>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '../components/HelloWorld.vue'

export default {
  name: 'Home',
  components: {
    HelloWorld
  },
  data(){
    return{
      msg: 'welcome to ems-admin'
    }
  }
}
</script>
<style scoped>
  .home{
    text-align: center;
  }
</style>