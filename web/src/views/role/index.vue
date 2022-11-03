<template>
  <div>
    <div class="searchDiv">
      <el-input class="searchInput" v-model="blurry" placeholder="请输入角色名称或代码" clearable></el-input>
      <el-button type="primary" @click="getRoleList">查询</el-button>
      <el-button v-if="hasPer('role:add')" @click="editRole" style="float: right;">新增</el-button>
    </div>
    <el-table :data="tableData" row-key="id" border height="calc(100vh - 180px)" max-height="calc(100vh - 180px)">
      <el-table-column label="序号" type="index" width="60"></el-table-column>
      <el-table-column label="角色名称" prop="roleName"></el-table-column>
      <el-table-column label="角色代码" prop="roleCode"></el-table-column>
      <el-table-column label="角色说明" prop="description"></el-table-column>
      <el-table-column label="操作" prop="option" width="220px" align="center">
        <template slot-scope="scope">
          <el-button v-if="hasPer('role:authorize')" type="success" @click="authorizeRole(scope.row.id)">授权</el-button>
          <el-button v-if="hasPer('role:edit')" type="primary" @click="editRole(JSON.parse(JSON.stringify(scope.row)))">编辑</el-button>
          <el-button v-if="hasPer('role:del')" type="danger" @click="delRole(scope.row.id, scope.row.roleName)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--编辑-->
    <edit-role :dialog-visible.sync="dialogVisible" :role-obj="roleObj" @get-list="getRoleList"></edit-role>
    <!--授权-->
    <authorize-role :auth-visible.sync="authVisible" :role-id="roleId"></authorize-role>
  </div>
</template>

<script>
import editRole from "./editRole";
import authorizeRole from "./authorizeRole";
import {getRoleList, delRole} from "../../api/role/sysRole";
import {errorMsg, infoMsg, successMsg} from "../../utils/message";
import {hasPer} from "../../utils/common";
export default {
  name: "index",
  components: {
    editRole,
    authorizeRole
  },
  data(){
    return{
      blurry: '',
      tableData: [],
      dialogVisible: false,
      authVisible: false,
      roleId: null,
      roleObj: {},
    }
  },
  mounted() {
    this.getRoleList()
  },
  methods: {
    hasPer,
    //  获取角色列表
    getRoleList(){
      getRoleList({blurry: this.blurry}).then(res => {
        if (res.success){
          this.tableData = res.data
        } else {
          errorMsg(res.msg)
        }
      })
    },
    //  编辑角色
    editRole(row){
      this.dialogVisible = true
      this.roleObj = row.id ? row : {}
    },
    //  角色授权
    authorizeRole(id){
      this.authVisible = true
      this.roleId = id
    },
    //  删除角色
    delRole(id, name){
      this.$confirm('确定删除角色【'+name+'】？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delRole({id: id}).then(res => {
          if (res.success){
            successMsg(res.data)
            this.getRoleList()
          } else {
            errorMsg(res.msg)
          }
        })
      }).catch(() => {
        infoMsg('操作已取消')
      })
    }
  }
}
</script>

<style scoped>

</style>