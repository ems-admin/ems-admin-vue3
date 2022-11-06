<template>
  <div style="width: 100%;">
    <el-row>
      <el-col :span="12">
        <span>EMS后台管理系统</span>
      </el-col>
      <el-col :span="12">
        <el-dropdown @command="handleCommand">
          <span class="el-dropdown-link">{{username}}<i class="el-icon-arrow-down el-icon--right"></i></span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="pwd">修改密码</el-dropdown-item>
              <el-dropdown-item command="logout">退出</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-col>
    </el-row>
    <update-password v-model:dialog-visible="dialogVisible" @logout="clearToken"></update-password>
  </div>
</template>

<script setup>
import updatePassword from "../../views/user/updatePassword";
import {useStore} from "../../store";
import routers from "../../router/routers";
import {infoMsg} from "../../utils/message";
import {ref} from "vue";
import {ElMessageBox} from "element-plus";

const store = useStore()

const username = ref(store.userInfo.nickName)

const dialogVisible = ref(false)

const handleCommand = (command) => {
  if (command === 'logout'){
    logout()
  } else if (command === 'pwd'){
    dialogVisible.value = true
  }
}
//  退出
const logout = () => {
  ElMessageBox.confirm('确定退出当前登录？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    clearToken()
  }).catch(() => {
    infoMsg('操作已取消')
  })
}
//  清空token
const clearToken = () => {
  //  清空token
  store.token = null
  //  清空refreshToken
  store.refreshToken = null
  //  跳转到登录页面
  routers.push({path: '/login'})
}
</script>

<style scoped>
  .el-row{
    width: 100%;
  }
  .el-dropdown{
    float: right;
  }
  .el-dropdown-link {
    cursor: pointer;
    color: #409EFF;
  }
  .el-icon-arrow-down {
    font-size: 12px;
  }
</style>