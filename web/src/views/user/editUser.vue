<template>
  <el-dialog :title="title" :visible.sync="visible" :close-on-click-modal="false" @opened="openFun">
    <el-form :model="userForm" :rules="rules" ref="userRef" label-width="120px">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="userForm.username" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item label="昵称" prop="nickName">
        <el-input v-model="userForm.nickName" placeholder="请输入昵称"></el-input>
      </el-form-item>
      <el-form-item label="用户角色" prop="roleIds">
        <el-select v-model="userForm.roleIds" multiple placeholder="请选择角色">
          <el-option v-for="(item, index) in roleList" :key="index" :label="item.roleName" :value="parseInt(item.id)"></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <span slot="footer">
      <el-button @click="resetForm('userRef')">重置</el-button>
      <el-button type="primary" :loading="isLoading" @click="submitUser('userRef')">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import {editUser} from "../../api/user/sysUser";
import {getRoleList} from "../../api/role/sysRole";
import {errorMsg, successMsg} from "../../utils/message";
import {resetForm} from "../../utils/common";
export default {
  name: "editUser",
  props: {
    dialogVisible: {
      type: Boolean,
      require: true,
      default: false
    },
    userObj: Object
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
    return{
      title: '新增',
      isLoading: false,
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
    }
  },
  methods: {
    resetForm,
    openFun(){
      if (this.userObj.id){
        this.getRoleList()
        this.title = '编辑'
        this.userForm = this.userObj
        this.userForm.roleIds = this.userForm.roleIds[0].split(',')
        //  将roleIds中的字符串数字转化为数字
        this.userForm.roleIds = this.userForm.roleIds.map(Number)
      }
    },
    //  获取角色列表
    getRoleList() {
      getRoleList({}).then(res => {
        if (res.success){
          this.roleList = res.data
        } else {
          errorMsg(res.msg)
        }
      })
    },
    //  提交
    submitUser(formName){
      this.$refs[formName].validate((valid) => {
        if (valid){
          this.isLoading = true
          editUser(this.userForm).then(res => {
            if (res.success){
              successMsg(res.data)
              this.visible = false
              this.$emit('get-list')
            } else {
              errorMsg(res.msg)
            }
            this.isLoading = false
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