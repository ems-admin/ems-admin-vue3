<template>
  <el-dialog title="修改密码" v-model="visible" :close-on-click-modal="false">
    <el-form :model="passwordForm" :rules="rules" ref="passwordRef" label-width="120px">
      <el-form-item label="原密码" prop="password">
        <el-input type="password" v-model="passwordForm.password" placeholder="请输入原密码" clearable></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="newPassword">
        <el-input type="password" v-model="passwordForm.newPassword" placeholder="请输入新密码" clearable></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="confirmPassword">
        <el-input type="password" v-model="passwordForm.confirmPassword" placeholder="请输入确认密码" clearable></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span>
      <el-button @click="resetForm(passwordRef)">重置</el-button>
      <el-button type="primary" @click="submitPassword">确定</el-button>
    </span>
    </template>
  </el-dialog>
</template>

<script setup>
import {updatePwd} from "../../api/user/sysUser";
import {errorMsg, successMsg} from "../../utils/message";
import {computed, ref, reactive} from "vue";
import {resetForm} from "../../utils/common";
const props = defineProps({
  dialogVisible: {
    type: Boolean,
    require: true,
    default: false
  }
})

const emit = defineEmits(['update:dialog-visible'])

const visible = computed({
  get: () => props.dialogVisible,
  set: (value) => emit('update:dialog-visible', value)
})

const passwordForm = reactive({
  password: '',
  newPassword: '',
  confirmPassword: '',
})

const passwordRef = ref()

const validateNew = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入密码'));
  } else {
    if (passwordForm.confirmPassword !== '') {
      passwordRef.value.validateField('confirmPassword');
    }
    callback();
  }
}

const validateConfirm = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入确认密码'));
  } else if (value !== passwordForm.newPassword) {
    callback(new Error('两次输入密码不一致!'));
  } else {
    callback();
  }
}

const rules = reactive( {
  password: [{required: true, message: '原密码不能为空', trigger: 'blur'}],
  newPassword: [
      {required: true, message: '新密码不能为空', trigger: 'blur'},
      {validator: validateNew, trigger: 'blur'}
  ],
  confirmPassword: [
      {required: true, message: '确认密码不能为空', trigger: 'blur'},
      {validator: validateConfirm, trigger: 'blur'}
  ]
})
//  提交
const submitPassword = () => {
  passwordRef.value.validate((valid) => {
    if (valid){
      updatePwd(passwordForm).then(res => {
        if (res.success){
          successMsg(res.data)
          visible.value = false
          emit('logout')
        } else {
          errorMsg(res.msg)
        }
      })
    }
  })
}
</script>

<style scoped>
 ::v-deep .vue-treeselect__control{
  height: 28px;
}
 ::v-deep .el-form-item__content{
   line-height: 28px;
   font-size: 12px;
 }
</style>