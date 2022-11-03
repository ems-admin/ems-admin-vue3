<template>
  <el-dialog title="授权" :visible.sync="visible" :close-on-click-modal="false" @opened="openFun">
    <el-tree
        :data="treeData"
        ref="tree"
        show-checkbox
        node-key="id"
        :props="defaultProps"
        default-expand-all
        :default-checked-keys="authorizeData">
    </el-tree>
    <span slot="footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" :loading="isLoading" @click="submitAuthorize">授权</el-button>
    </span>
  </el-dialog>
</template>

<script>
import {getMenuTable} from "../../api/menu/sysMenu";
import {getMenusByRoleId, authorizeRole} from "../../api/role/sysRole";
import {errorMsg, successMsg} from "../../utils/message";
export default {
  name: "authorizeRole",
  props: {
    authVisible: {
      type: Boolean,
      require: true,
      default: false
    },
    roleId: Number
  },
  computed: {
    visible: {
      get: function (){
        return this.authVisible
      },
      set: function (val) {
        this.$emit('update:authVisible', val)
      }
    }
  },
  data(){
    return{
      isLoading: false,
      treeData: [],
      authorizeData: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      }
    }
  },
  methods: {
    openFun(){
      this.authorizeData = []
      this.getMenusByRoleId()
      // this.getMenuTree()
    },
    //  获取当前角色的所有菜单树
    getMenuTree(){
      getMenuTable({}).then(res => {
        if (res.success){
          this.treeData = res.data
        } else {
          errorMsg(res.msg)
        }
      })
    },
    //  获取当前角色已授权的菜单列表
    getMenusByRoleId(){
      getMenusByRoleId({roleId: this.roleId}).then(res => {
        if (res.success){
          if (res.data && res.data.length > 0){
            res.data.forEach(item => {
              this.authorizeData.push(item.menuId)
            })
          }
          //  延时器，主要是为了渲染已授权菜单，可根据具体情况调整延时时间
          setTimeout(() => {
            this.getMenuTree()
          }, 10)
        } else {
          errorMsg(res.msg)
        }
      })
    },
    //  提交
    submitAuthorize(){
      this.isLoading = true
      authorizeRole({roleId: this.roleId, menuIds: this.$refs.tree.getCheckedKeys()}).then(res => {
        if (res.success){
          successMsg(res.data)
          this.visible = false
        } else {
          errorMsg(res.msg)
        }
        this.isLoading = false
      })
    }
  }

}
</script>

<style scoped>

</style>