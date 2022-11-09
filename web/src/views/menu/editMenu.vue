<template>
  <el-dialog :title="state.title" v-model="visible" draggable :close-on-click-modal="false" @opened="openFun">
    <el-form :model="menuForm" :rules="rules" ref="menuRef" label-width="120px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="上级目录" prop="parentId">
            <el-tree-select key-node="value" v-model="menuForm.parentId" :data="options" check-strictly :render-after-expand="false"></el-tree-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="排序" prop="sort">
            <el-input v-model="menuForm.sort" placeholder="请输入排序" clearable></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="菜单类型" prop="type">
        <el-radio-group v-model="menuForm.type">
          <el-radio :label="'1'">菜单</el-radio>
          <el-radio :label="'2'">页面</el-radio>
          <el-radio :label="'3'">按钮</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="菜单名称" prop="name">
        <el-input v-model="menuForm.name" placeholder="请输入菜单名称" clearable></el-input>
      </el-form-item>
      <el-form-item label="选择图标" prop="icon">
            <el-popover placement="bottom" trigger="click" width="300px;">
              <template #reference>
                <el-input v-model="menuForm.icon" prefix-icon="请选择菜单图标" clearable></el-input>
              </template>
              <el-row class="icon-row">
                <el-col v-for="(item, index) in iconList" :key="index" :span="8">
                  <i :class="item.font_class" style="font-size: 40px;" @click="checkIcon(item.font_class)"></i>
                  <i style="display: flow-root;">{{item.name}}</i>
                </el-col>
              </el-row>
            </el-popover>
      </el-form-item>
      <el-form-item v-if="menuForm.type === '2' || menuForm.type === '3'" label="访问路径" prop="path">
        <el-input v-model="menuForm.path" placeholder="请输入菜单路径"></el-input>
      </el-form-item>
      <el-form-item v-if="menuForm.type === '2'" label="组件路径" prop="component">
        <el-input v-model="menuForm.component" placeholder="请输入页面component"></el-input>
      </el-form-item>
      <el-form-item v-if="menuForm.type === '3'" label="按钮权限" prop="permission">
        <el-input v-model="menuForm.permission" placeholder="请输入权限"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span>
        <el-button @click="resetForm(menuRef)">重置</el-button>
        <el-button type="primary" :loading="isLoading" @click="submitMenu">确定</el-button>
    </span>
    </template>
  </el-dialog>
</template>

<script setup>
import {getMenuTreeSelect, editMenu} from "../../api/menu/sysMenu";
import {errorMsg, successMsg} from "../../utils/message";
import {resetForm} from "../../utils/common";
import {computed, reactive, ref} from "vue";

const props = defineProps({
  dialogVisible: {
    type: Boolean,
    require: true,
    default: false
  },
  menuObj: Object
})

const emit = defineEmits(['update:dialogVisible', 'get-list'])

const visible = computed({
  get: () => props.dialogVisible,
  set: (val) => emit('update:dialogVisible', val)
})

const menuForm = ref({
  id: null,
  parentId: 0,
  name: '',
  path: '',
  component: '',
  permission: '',
  type: '1',
  icon: ''
})

const rules = reactive({
  parentId: [{required: true, message: '上级菜单不能为空', trigger: 'change'}],
  sort: [{required: true, message: '排序不能为空', trigger: 'blur'}],
  name: [{required: true, message: '菜单名称不能为空', trigger: 'blur'}],
  path: [{required: true, message: '菜单路径不能为空', trigger: 'blur'}],
  component: [{required: true, message: '组件路径不能为空', trigger: 'blur'}],
  permission: [{required: true, message: '权限不能为空', trigger: 'blur'}],
})

const state = {
  title: '新增',
  iconList: []
}

const iconList = ref([])

const options = ref([
  {
    value: 0,
    label: '顶级目录',
    children: []
  }
])

const menuRef = ref()

const isLoading = ref(false)

const openFun = () => {
  state.title = '新增'
  resetForm(menuRef.value)
  isLoading.value = false
  getMenuTreeFun()
  if (props.menuObj.id){
    state.title = '编辑'
    menuForm.value = props.menuObj
  }
  getIconList()
}
//  获取下拉菜单树
const getMenuTreeFun = () => {
  getMenuTreeSelect().then(res => {
    if (res.success){
      options.value[0].children = res.data
    } else {
      errorMsg(res.msg)
    }
  })
}
//  提交
const submitMenu = () => {
  menuRef.value.validate((valid) => {
    if (valid){
      isLoading.value = true
      editMenu(menuForm).then(res => {
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

const getIconList = () => {
  const iconJson = require('../../assets/iconfont/iconfont.json')
  const iconClassList = JSON.parse(JSON.stringify(iconJson.glyphs))
  iconList.value = iconClassList.map(item => {
    item.font_class = 'iconfont icon-' + item.font_class
    return item
  })
}

const checkIcon = (value) => {
  menuForm.value.icon = value
}
</script>

<style scoped>
 .icon-row{
   text-align: center;
   height: 300px;
   overflow-y: auto;
 }
</style>