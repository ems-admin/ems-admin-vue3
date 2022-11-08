<template>
  <el-dialog title="授权" v-model="visible" draggable :close-on-click-modal="false" @opened="openFun">
    <el-tree
        :data="state.treeData"
        ref="tree"
        show-checkbox
        node-key="id"
        :props="state.defaultProps"
        default-expand-all
        :default-checked-keys="state.authorizeData">
    </el-tree>
    <template #footer>
      <span>
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" :loading="isLoading" @click="submitAuthorize">授权</el-button>
    </span>
    </template>
  </el-dialog>
</template>

<script setup>
import {getMenuTable} from "../../api/menu/sysMenu";
import {getMenusByRoleId, authorizeRole} from "../../api/role/sysRole";
import {errorMsg, successMsg} from "../../utils/message";
import {computed, reactive, ref} from "vue";

const props = defineProps({
  authVisible: {
    type: Boolean,
    require: true,
    default: false
  },
  roleId: Number
})

const emit = defineEmits(['update:authVisible'])

const visible = computed({
  get: () => props.authVisible,
  set: (val) => emit('update:authVisible', val)
})

const isLoading = ref(false)

const tree = ref()

const state = reactive({
  treeData: [],
  authorizeData: [],
  defaultProps: {
    children: 'children',
    label: 'name'
  }
})

const openFun = () => {
  isLoading.value = false
  state.authorizeData = []
  getMenusByRoleIdFun()
}
//  获取当前角色的所有菜单树
const getMenuTree = () => {
  getMenuTable({}).then(res => {
    if (res.success){
      state.treeData = res.data
    } else {
      errorMsg(res.msg)
    }
  })
}
//  获取当前角色已授权的菜单列表
const getMenusByRoleIdFun = () => {
  getMenusByRoleId({roleId: props.roleId}).then(res => {
    if (res.success){
      if (res.data && res.data.length > 0){
        res.data.forEach(item => {
          state.authorizeData.push(item.menuId)
        })
      }
      //  延时器，主要是为了渲染已授权菜单，可根据具体情况调整延时时间
      setTimeout(() => {
        getMenuTree()
      }, 10)
    } else {
      errorMsg(res.msg)
    }
  })
}
//  提交
const submitAuthorize = () => {
  isLoading.value = true
  authorizeRole({roleId: props.roleId, menuIds: tree.value.getCheckedKeys()}).then(res => {
    if (res.success){
      successMsg(res.data)
      visible.value = false
    } else {
      errorMsg(res.msg)
    }
    isLoading.value = false
  })
}
</script>

<style scoped>

</style>