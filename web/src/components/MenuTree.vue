<template>
  <div>
    <template v-for="(menu, menuIndex) in props.menuData">
      <el-sub-menu :key="menuIndex" :index="menu.name" v-if="menu.children">
        <template #title>
          <i :class="menu.icon"></i>
          <span slot="title">{{menu.name}}</span>
        </template>
        <menu-tree :menuData="menu.children"></menu-tree>
      </el-sub-menu>
      <el-menu-item v-else :key="menu.name + menuIndex" :index="menu.name"
                    :route="menu.path"
                    @click="openTab(menu.name, menu.path)">
        <i :class="menu.icon"></i>
        {{menu.name}}
      </el-menu-item>
    </template>
  </div>
</template>

<script setup>
import {useStore} from "../store";

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
/*由于 element-ui 的<el-menu>标签本身希望里面嵌套的是<el-menu-item>,<el-submenu>,
<el-menu-item-group>之一，但是却嵌套了<div>,而导致收折就隐藏不了文字*/
/*隐藏文字*/
.el-menu--collapse  .el-submenu__title span{
  display: none;
}
/*隐藏 > */
.el-menu--collapse  .el-submenu__title .el-submenu__icon-arrow{
  display: none;
}
</style>