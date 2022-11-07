<template>
  <el-tabs v-model="activeIndex" type="card" @tab-click="clickTab" @tab-remove="removeTab">
    <el-tab-pane
        v-for="(item, index) in tabs"
        :closable="item.isClose"
        :key="index"
        :label="item.name"
        :name="item.name">
      <router-view></router-view>
    </el-tab-pane>
  </el-tabs>
</template>

<script setup>
import {storeToRefs} from 'pinia'
import {useStore} from "../../store";
import {useRouter} from 'vue-router'
// import routers from "../../router/routers";
import {computed} from "vue";

const router = useRouter()

const store = useStore()

const { activeIndex } = storeToRefs(store)

const tabs = computed(() => {
  return store.openTabs
})
  //  点击tab
const clickTab = (tab) => {
  store.activeIndex = tab.paneName
  //  跳转到对应的tab
  router.push({name: tab.paneName})
}
  //  移除tab
const removeTab = (name) => {
    //  遍历当前已打开的tabs
    tabs.value.forEach((tab, index) => {
      //  如果关闭的是当前激活状态的tab
      if (tab.name === name){
        //  则将下一个tab设置为激活状态
        //  如果当前激活状态为最后一个,则将上一个tab设置为激活状态
        const nextTab = tabs.value[index + 1] || tabs.value[index - 1]
        if (nextTab){
          store.activeIndex = nextTab.name
          //  跳转至当前页面
          router.push({path: nextTab.path})
        }
      }
    })
    //  在已打开tabs的缓存中删除当前删除的tab
    store.removeTabAction(name)
}
</script>

<style scoped>
::v-deep .el-tabs__content{
  height: calc(100vh - 120px);
  padding-left: 30px;
  padding-right: 30px;
}
::v-deep  .el-tabs__nav-wrap{
  height: 40px;
  border-top: 1px solid #d8dce5;
  border-bottom: 1px solid #d8dce5;
  background-color: rgb(247, 247, 247);
}

::v-deep  .el-tabs__nav{
  border: none!important;
  height: 40px;
  display: flex;
  align-items: center;
}

::v-deep .el-tabs__item.is-active {
  height: 30px;
  line-height: 30px;
  background-color: #42b983;
  border-color: #42b983;
  color: white;
}

::v-deep  .el-tabs__item.is-active::before {
  content: "";
  background-color: #fff;
  display: inline-block;
  width: 8px;
  height: 8px;
  border-radius: 50%;
  margin-right: 4px;
}
</style>