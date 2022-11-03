<template>
  <el-menu :default-active="defaultActive" :unique-opened="true" router :collapse="isCollapse"
           background-color="#545c64" text-color="#fff">
    <div class="logo" @click="changeCollapse">
      <el-image :src="require('../../assets/image/ems.png')" style="width: 40px;"></el-image>
<!--      <span v-if="!isCollapse">EMS-ADMIN</span>-->
    </div>
    <!--默认将首页放在第一位-->
    <el-menu-item route="/home" index="首页"><i class="iconfont icon-home"></i>首页</el-menu-item>
    <el-submenu v-for="(menu, menuIndex) in menuList" :key="menuIndex" :index="menu.name">
      <template slot="title">
        <i :class="menu.icon"></i>
        <span>{{menu.name}}</span>
      </template>
      <el-submenu v-if="menu.children.children && menu.children.children.length > 0" index="">

      </el-submenu>
      <el-menu-item
          v-else
          v-for="(item, itemIndex) in menu.children"
          :key="itemIndex"
          :index="item.name"
          :route="item.path" @click="openTab(item.name, item.path)">
        <i :class="item.icon"></i>
        {{item.name}}
      </el-menu-item>
    </el-submenu>
  </el-menu>
</template>

<script>
import store from "../../store";
import {getMenuTree, getPermission} from "../../api/menu/sysMenu";
import {errorMsg} from "../../utils/message";
export default {
  name: "Aside",
  data(){
    return {
      menuList: [],
      isCollapse: false
    }
  },
  computed: {
    defaultActive: {
      get: function () {
        return store.state.activeIndex
      }
    }
  },
  mounted() {
    this.getMenuTree()
    this.getPermission()
  },
  methods: {
    //  获取当前用户菜单树
    getMenuTree(){
      getMenuTree().then(res => {
        if (res.success){
          this.menuList = res.data
        } else {
          errorMsg(res.msg)
        }
      })
    },
    //  获取当前用户的按钮权限列表
    getPermission(){
      getPermission().then(res => {
        if (res.success){
          store.dispatch('permissionAction', res.data)
        } else {
          errorMsg(res.msg)
        }
      })
    },
    //  打开页面
    openTab(name, path){
      //  将当前打开的菜单添加到已打开列表中
      store.dispatch('addTabAction', {name: name, path: path})
      //  将激活菜单改成选中的菜单
      store.dispatch('activeIndexAction', name)
    },
    changeCollapse(){
      this.isCollapse = !this.isCollapse
      if (this.isCollapse){
        this.$emit('update:width', '64px')
      } else {
        this.$emit('update:width', '230px')
      }
    }
  }
}
</script>

<style scoped>
  .el-menu{
    height: 100%;
  }
  .logo{
    display: flex;
    justify-content: center;
    align-items: center;
    color: white;
    font-size: 20px;
    height: 60px;
    cursor: pointer;
  }
</style>