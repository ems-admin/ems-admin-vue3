<template>
  <div class="login">
    <el-form class="form" :model="loginForm" :rules="rules" ref="loginRef">
      <h1>用 户 登 录</h1>
      <el-form-item prop="username">
        <el-input type="text" v-model="loginForm.username" prefix-icon="iconfont icon-username" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input type="password" v-model="loginForm.password" prefix-icon="iconfont icon-password" placeholder="请输入密码"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button class="button" type="primary" :loading="isLoading" @click="submitLogin(loginRef)">登  录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import './assets/css/login.css'
import {useStore} from "./store";
import routers from "./router/routers";
import {login} from "./api/login/login";
import {errorMsg} from "./utils/message";
import {ref, reactive} from 'vue'

const store = useStore()

const loginRef = ref(null)

const isLoading = ref(false)

const loginForm = reactive({
  username: '',
  password: ''
})

const rules = reactive({
  username: [{ required: true, message: '用户名不能为空', trigger: 'blur' }],
  password: [{ required: true, message: '密码不能为空', trigger: 'blur' }]
})

const submitLogin = (loginRef) => {
  loginRef.validate((valid) => {
    if (valid){
      isLoading.value = true
      login(loginForm).then(res => {
        if (res.success){
          //  缓存token
          store.$patch(res.data.token)
          //  缓存刷新token
          store.$patch(res.data.refreshToken)
          //  缓存当前登录用户信息
          store.$patch(res.data.userDto)
          //  切换到首页
          routers.push({path: '/Layout'})
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

</style>