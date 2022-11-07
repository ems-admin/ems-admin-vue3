<template>
  <div>
    <div class="searchDiv">
      <el-input class="searchInput" v-model="state.blurry" placeholder="请输入角色名称或代码" clearable></el-input>
      <el-button type="primary" @click="getRoleListFun">查询</el-button>
      <el-button v-if="hasPer('role:add')" @click="editRoleFun" style="float: right;">新增</el-button>
    </div>
    <el-table :data="state.tableData" row-key="id" border height="calc(100vh - 180px)" max-height="calc(100vh - 180px)">
      <el-table-column label="序号" type="index" width="60"></el-table-column>
      <el-table-column label="角色名称" prop="roleName"></el-table-column>
      <el-table-column label="角色代码" prop="roleCode"></el-table-column>
      <el-table-column label="角色说明" prop="description"></el-table-column>
      <el-table-column label="操作" prop="option" width="220px" align="center">
        <template #default="scope">
          <el-button v-if="hasPer('role:authorize')" type="success" @click="authorizeRoleFun(scope.row.id)">授权</el-button>
          <el-button v-if="hasPer('role:edit')" type="primary" @click="editRoleFun(JSON.parse(JSON.stringify(scope.row)))">编辑</el-button>
          <el-button v-if="hasPer('role:del')" type="danger" @click="delRoleFun(scope.row.id, scope.row.roleName)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--编辑-->
    <edit-role v-model:dialog-visible="dialogVisible" :role-obj="roleObj" @get-list="getRoleListFun"></edit-role>
    <!--授权-->
    <authorize-role v-model:auth-visible="authVisible" :role-id="roleId"></authorize-role>
  </div>
</template>

<script setup>
import editRole from "./editRole";
import authorizeRole from "./authorizeRole";
import {getRoleList, delRole} from "../../api/role/sysRole";
import {errorMsg, infoMsg, successMsg} from "../../utils/message";
import {hasPer} from "../../utils/common";
import {onMounted, reactive, ref} from "vue";
import {ElMessageBox} from "element-plus";

const state = reactive({
  blurry: '',
  tableData: [],
  roleId: null,
  roleObj: {},
})

const dialogVisible = ref(false)

const authVisible = ref(false)

onMounted(() => {
  getRoleListFun()
})

//  获取角色列表
const getRoleListFun = () => {
  getRoleList({blurry: state.blurry}).then(res => {
    if (res.success){
      state.tableData = res.data
    } else {
      errorMsg(res.msg)
    }
  })
}
//  编辑角色
const editRoleFun = (row) => {
  dialogVisible.value = true
  state.roleObj = row.id ? row : {}
}
//  角色授权
const authorizeRoleFun = (id) => {
  authVisible.value = true
  state.roleId = id
}
//  删除角色
const delRoleFun = (id, name) => {
  ElMessageBox.confirm('确定删除角色【'+name+'】？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    delRole({id: id}).then(res => {
      if (res.success){
        successMsg(res.data)
        getRoleListFun()
      } else {
        errorMsg(res.msg)
      }
    })
  }).catch(() => {
    infoMsg('操作已取消')
  })
}
</script>

<style scoped>

</style>