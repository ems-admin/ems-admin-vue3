<template>
  <el-dialog :title="state.title" v-model="visible" draggable :close-on-click-modal="false" @opened="openFun">
    <el-form :model="state.roleForm" :rules="rules" ref="roleRef" label-width="120px">
      <el-form-item label="角色名称" prop="roleName">
        <el-input v-model="state.roleForm.roleName" placeholder="请输入角色名称"></el-input>
      </el-form-item>
      <el-form-item label="角色代码" prop="roleCode">
        <el-input v-model="state.roleForm.roleCode" placeholder="请输入角色代码"></el-input>
      </el-form-item>
      <el-form-item label="角色说明" prop="roleIds">
        <el-input type="textarea" v-model="state.roleForm.description" aria-placeholder="请输入角色说明"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span>
      <el-button @click="resetForm(roleRef)">重置</el-button>
      <el-button type="primary" :loading="isLoading" @click="submitRole">确定</el-button>
    </span>
    </template>
  </el-dialog>
</template>

<script setup>
import {editRole} from "../../api/role/sysRole";
import {errorMsg, successMsg} from "../../utils/message";
import {computed, reactive, ref} from "vue";
import {resetForm} from "../../utils/common";

const props = defineProps({
  dialogVisible: {
    type: Boolean,
    require: true,
    default: false
  },
  roleObj: Object
})

const emit = defineEmits(['update:dialogVisible', 'get-list'])

const visible = computed({
  get: () => props.dialogVisible,
  set: (val) => emit('update:dialogVisible', val)
})

const isLoading = ref(false)

const state = reactive({
  title: '新增',
  roleForm: {
    id: null,
    roleName: '',
    roleCode: '',
    description: ''
  },
  rules: {
    roleName: [{required: true, message: '角色名称不能为空', trigger: 'blur'}],
    roleCode: [{required: true, message: '角色代码不能为空', trigger: 'blur'}]
  }
})

const roleRef = ref()

const openFun = () => {
  resetForm(roleRef.value)
  state.title = '新增'
  isLoading.value = false
  if (props.roleObj.id){
    state.title = '编辑'
    state.roleForm = props.roleObj
  }
}
//  提交
const submitRole = () => {
  roleRef.value.validate((valid) => {
    if (valid){
      isLoading.value = true
      editRole(state.roleForm).then(res => {
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

// export default {
//   name: "editRole",
//   props: {
//     dialogVisible: {
//       type: Boolean,
//       require: true,
//       default: false
//     },
//     roleObj: Object
//   },
//   computed: {
//     visible: {
//       get: function () {
//         return this.dialogVisible
//       },
//       set: function (val) {
//         this.$emit('update:dialogVisible', val)
//       }
//     }
//   },
//   data(){
//     return{
//       title: '新增',
//       isLoading: false,
//       roleForm: {
//         id: null,
//         roleName: '',
//         roleCode: '',
//         description: ''
//       },
//       rules: {
//         roleName: [{required: true, message: '角色名称不能为空', trigger: 'blur'}],
//         roleCode: [{required: true, message: '角色代码不能为空', trigger: 'blur'}]
//       }
//     }
//   },
//   methods: {
//     resetForm,
//     openFun(){
//       if (this.roleObj.id){
//         this.title = '编辑'
//         this.roleForm = this.roleObj
//       }
//     },
//     //  提交
//     submitRole(formName){
//       this.$refs[formName].validate((valid) => {
//         if (valid){
//           this.isLoading = true
//           editRole(this.roleForm).then(res => {
//             if (res.success){
//               successMsg(res.data)
//               this.visible = false
//               this.$emit('get-list')
//             } else {
//               errorMsg(res.msg)
//             }
//             this.isLoading = false
//           })
//         }
//       })
//     }
//   }
// }
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