<template>

    <template v-for="(menu, menuIndex) in props.menuData">
      <el-sub-menu :key="menuIndex" :index="menu.name" v-if="menu.children">
        <template #title>
          <i :class="menu.icon"></i>
          <span>{{menu.name}}</span>
        </template>
        <menu-tree :menuData="menu.children"></menu-tree>
      </el-sub-menu>
      <el-menu-item v-else :key="menu.name + menuIndex" :index="menu.name"
                    :route="menu.path"
                    @click="openTab(menu.name, menu.path)">
        <i :class="menu.icon"></i>
        <template #title>
          {{menu.name}}
        </template>
      </el-menu-item>
    </template>
</template>

<script setup>
import {useStore} from "@/store/index.js";

const props = defineProps({
  menuData: {
    type: Array
  }
})

const store = useStore()

const openTab = (name, path) => {
  //  将当前打开的菜单添加到已打开列表中
  store.addTabAction({name: name, path: path})
  //  将激活菜单改成选中的菜单
  store.activeIndexAction(name)
}
</script>

<style scoped>
.el-menu{
  height: 100%;
}
</style>