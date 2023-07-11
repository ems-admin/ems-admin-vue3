<template>
  <div class="login">
    <el-form class="form" :model="loginForm" :rules="rules" ref="loginRef">
      <h1>用 户 登 录</h1>
      <el-form-item prop="username">
        <el-input type="text" v-model="loginForm.username" placeholder="请输入用户名">
          <template #prefix>
            <i class="iconfont icon-username"></i>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input type="password" v-model="loginForm.password" placeholder="请输入密码">
          <template #prefix>
            <i class="iconfont icon-password"></i>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="code">
        <div style="display: inline-flex;justify-content: space-between;width: 100%;">
          <el-input type="text" v-model="loginForm.code" style="width: 170px;" placeholder="请输入验证码">
            <template #prefix>
              <i class="iconfont icon-captcha"></i>
            </template>
          </el-input>
          <img :src="state.image" @click="getCode">
        </div>
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
import {login, getVerifyCode} from "./api/login/login";
import {errorMsg} from "./utils/message";
import {ref, reactive, onMounted} from 'vue'

  const store = useStore()

  const loginRef = ref(null)

  const isLoading = ref(false)

  const state = reactive({
    image: ''
  })

  const loginForm = reactive({
    username: '',
    password: '',
    code: '',
    uuid: ''
  })

  const rules = reactive({
    username: [{ required: true, message: '用户名不能为空', trigger: 'blur' }],
    password: [{ required: true, message: '密码不能为空', trigger: 'blur' }],
    code: [{ required: true, message: '验证码不能为空', trigger: 'blur' }]
  })

  onMounted(() => {
    getCode()
  })

  //  获取验证码
  const getCode = () =>{
    getVerifyCode().then(res => {
      state.image = res.img
      loginForm.uuid = res.uuid
    })
  }

  const submitLogin = (loginRef) => {
    loginRef.validate((valid) => {
      if (valid){
        isLoading.value = true
        login(loginForm).then(res => {
          if (res.success){
            //  缓存token
            store.token = res.data.token
            //  缓存刷新token
            store.refreshToken = res.data.refreshToken
            //  缓存当前登录用户信息
            store.userInfo = res.data.userDto
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