<template>
  <el-dialog :title="state.title" v-model="visible" :close-on-click-modal="false" @opened="openFun">
    <el-form :model="state.userForm" :rules="state.rules" ref="userRef" label-width="120px">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="state.userForm.username" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item label="昵称" prop="nickName">
        <el-input v-model="state.userForm.nickName" placeholder="请输入昵称"></el-input>
      </el-form-item>
      <el-form-item label="用户角色" prop="roleIds">
        <el-select v-model="state.userForm.roleIds" multiple placeholder="请选择角色">
          <el-option v-for="(item, index) in state.roleList" :key="index" :label="item.roleName" :value="parseInt(item.id)"></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <span>
      <el-button @click="resetForm(userRef)">重置</el-button>
      <el-button type="primary" :loading="isLoading" @click="submitUser">确定</el-button>
    </span>
    </template>
  </el-dialog>
</template>

<script setup>
import {editUser} from "../../api/user/sysUser";
import {getRoleList} from "../../api/role/sysRole";
import {errorMsg, successMsg} from "../../utils/message";
import {resetForm} from "../../utils/common";
import {computed, reactive, ref} from "vue";

const props = defineProps({
  dialogVisible: {
    type: Boolean,
    required: true,
    default: false
  },
  userObj: Object
})

const emit = defineEmits(['update:dialogVisible', 'get-list'])

const visible = computed({
  get: () => props.dialogVisible,
  set: (val) => emit('update:dialogVisible', val)
})

const isLoading = ref(false)

const userRef = ref()

const state = reactive({
  title: '新增',
  userForm: {
    id: null,
    username: '',
    nickName: '',
    roleIds: []
  },
  rules: {
    username: [{required: true, message: '用户名不能为空', trigger: 'blur'}],
    nickName: [{required: true, message: '用户昵称不能为空', trigger: 'blur'}],
    roleIds: [{required: true, message: '用户角色不能为空', trigger: 'change'}]
  },
  roleList: []
})

const openFun = () => {
  resetForm(userRef.value)
  state.title = '新增'
  getRoleListFun()
  isLoading.value = false
  if (props.userObj.id){
    state.title = '编辑'
    state.userForm = props.userObj
    state.userForm.roleIds = state.userForm.roleIds[0].split(',')
    //  将roleIds中的字符串数字转化为数字
    state.userForm.roleIds = state.userForm.roleIds.map(Number)
  }
}
//  获取角色列表
const getRoleListFun = () => {
  getRoleList({}).then(res => {
    if (res.success){
      state.roleList = res.data
    } else {
      errorMsg(res.msg)
    }
  })
}
//  提交
const submitUser = () => {
  userRef.value.validate((valid) => {
    if (valid){
      isLoading.value = true
      editUser(state.userForm).then(res => {
        if (res.success){
          successMsg(res.data)
          visible.value = false
          emit('get-list')
        } else {
          errorMsg(res.msg)
        }
        isLoading.value = false
      })
    }
  })
}
</script>

<style scoped>
 :deep(.vue-treeselect__control){
  height: 28px;
}
 :deep(.el-form-item__content){
   line-height: 28px;
   font-size: 12px;
 }
</style>