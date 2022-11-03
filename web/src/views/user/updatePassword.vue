<template>
  <el-dialog title="修改密码" :visible.sync="visible" :close-on-click-modal="false">
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
    <span slot="footer">
      <el-button @click="resetForm('passwordRef')">重置</el-button>
      <el-button type="primary" @click="submitPassword('passwordRef')">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import {updatePwd} from "../../api/user/sysUser";
import {errorMsg, successMsg} from "../../utils/message";
import {resetForm} from "../../utils/common";
export default {
  name: "editUser",
  props: {
    dialogVisible: {
      type: Boolean,
      require: true,
      default: false
    }
  },
  computed: {
    visible: {
      get: function () {
        return this.dialogVisible
      },
      set: function (val) {
        this.$emit('update:dialogVisible', val)
      }
    }
  },
  data(){
    const validateNew = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.passwordForm.confirmPassword !== '') {
          this.$refs.passwordRef.validateField('confirmPassword');
        }
        callback();
      }
    };
    const validateConfirm = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入确认密码'));
      } else if (value !== this.passwordForm.newPassword) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return{
      isLoading: false,
      passwordForm: {
        password: '',
        newPassword: '',
        confirmPassword: '',
      },
      rules: {
        password: [{required: true, message: '原密码不能为空', trigger: 'blur'}],
        newPassword: [
            {required: true, message: '新密码不能为空', trigger: 'blur'},
            {validator: validateNew, trigger: 'blur'}
        ],
        confirmPassword: [
            {required: true, message: '确认密码不能为空', trigger: 'blur'},
            {validator: validateConfirm, trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    resetForm,
    //  提交
    submitPassword(formName){
      this.$refs[formName].validate((valid) => {
        if (valid){
          updatePwd(this.passwordForm).then(res => {
            if (res.success){
              successMsg(res.data)
              this.visible = false
              this.$emit('logout')
            } else {
              errorMsg(res.msg)
            }
          })
        }
      })
    }
  }
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