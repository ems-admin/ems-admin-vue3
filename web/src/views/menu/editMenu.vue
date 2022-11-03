<template>
  <el-dialog :title="title" :visible.sync="visible" :close-on-click-modal="false" @opened="openFun">
    <el-form :model="menuForm" :rules="rules" ref="menuRef" label-width="120px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="上级目录" prop="parentId">
            <treeselect v-model="menuForm.parentId" :options="options" :clearable="false" :normalizer="normalizer"></treeselect>
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
        <el-input v-model="menuForm.name" placeholder="请输入菜单名称"></el-input>
      </el-form-item>
      <el-form-item label="选择图标" prop="icon">
            <el-popover placement="bottom" trigger="click">
              <el-row class="icon-row">
                <el-col v-for="(item, index) in iconList" :key="index" :span="8">
                  <i :class="item.font_class" style="font-size: 40px;" @click="checkIcon(item.font_class)"></i>
                  <i style="display: flow-root;">{{item.name}}</i>
                </el-col>
              </el-row>
              <el-input v-model="menuForm.icon" prefix-icon="请选择菜单图标" slot="reference"></el-input>
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
    <span slot="footer">
      <el-button @click="resetForm('menuRef')">重置</el-button>
      <el-button type="primary" :loading="isLoading" @click="submitMenu('menuRef')">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import {getMenuTree, editMenu} from "../../api/menu/sysMenu";
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import {errorMsg, successMsg} from "../../utils/message";
import {resetForm} from "../../utils/common";
export default {
  name: "editMenu",
  components: {
    Treeselect
  },
  props: {
    dialogVisible: {
      type: Boolean,
      require: true,
      default: false
    },
    menuObj: Object
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
      menuForm: {
        id: null,
        parentId: 0,
        name: '',
        path: '',
        component: '',
        permission: '',
        type: '1',
        icon: ''
      },
      rules: {
        parentId: [{required: true, message: '上级菜单不能为空', trigger: 'change'}],
        sort: [{required: true, message: '排序不能为空', trigger: 'blur'}],
        name: [{required: true, message: '菜单名称不能为空', trigger: 'blur'}],
        path: [{required: true, message: '菜单路径不能为空', trigger: 'blur'}],
        component: [{required: true, message: '组件路径不能为空', trigger: 'blur'}],
        permission: [{required: true, message: '权限不能为空', trigger: 'blur'}],
      },
      options: [
        {
          id: 0,
          name: '顶级目录',
          children: []
        }
      ],
      normalizer(node) {
        return{
          id: node.id,
          label: node.name,
          children: node.children
        }
      },
      iconList: []
    }
  },
  methods: {
    resetForm,
    openFun(){
      if (this.menuObj.id){
        this.title = '编辑'
        this.menuForm = this.menuObj
      }
      this.getMenuTree()
      this.getIconList()
    },
    //  获取下拉菜单树
    getMenuTree() {
      getMenuTree().then(res => {
        if (res.success){
          this.options[0].children = res.data
        } else {
          errorMsg(res.msg)
        }
      })
    },
    //  提交
    submitMenu(formName){
      this.$refs[formName].validate((valid) => {
        if (valid){
          this.isLoading = true
          editMenu(this.menuForm).then(res => {
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
    },
    getIconList(){
      const iconJson = require('../../assets/iconfont/iconfont.json')
      const iconClassList = JSON.parse(JSON.stringify(iconJson.glyphs))
      this.iconList = iconClassList.map(item => {
        item.font_class = 'iconfont icon-' + item.font_class
        return item
      })
      console.info(this.iconList)
    },
    checkIcon(value){
      this.menuForm.icon = value
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
 ::v-deep .el-popover{
   width: 50% !important;
 }
 .icon-row{
   text-align: center;
   height: 300px;
   overflow-y: auto;
 }
</style>