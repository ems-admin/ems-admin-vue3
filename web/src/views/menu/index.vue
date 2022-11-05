<template>
  <div>
    <div class="searchDiv">
      <el-input class="searchInput" v-model="blurry" placeholder="请输入菜单名称或路径" clearable></el-input>
      <el-button type="primary" @click="getMenuList">查询</el-button>
      <el-button v-if="hasPer('menu:add')" @click="editMenuFun" style="float: right;">新增</el-button>
    </div>
    <el-table :data="tableData" row-key="id" border height="calc(100vh - 180px)" max-height="calc(100vh - 180px)">
      <el-table-column label="菜单名称" prop="name"></el-table-column>
      <el-table-column label="菜单路径" prop="path"></el-table-column>
      <el-table-column label="component" prop="component"></el-table-column>
      <el-table-column label="权限" prop="permission"></el-table-column>
      <el-table-column label="类型" prop="type">
        <template slot-scope="scope">
          <span v-if="scope.row.type === '1'">菜单</span>
          <span v-else-if="scope.row.type === '2'">页面</span>
          <span v-else-if="scope.row.type === '3'">按钮</span>
        </template>
      </el-table-column>
      <el-table-column label="排序" prop="sort"></el-table-column>
      <el-table-column label="操作" prop="option" width="150px" align="center">
        <template slot-scope="scope">
          <el-button v-if="hasPer('menu:edit')" type="primary" @click="editMenuFun(JSON.parse(JSON.stringify(scope.row)))">编辑</el-button>
          <el-button v-if="hasPer('menu:del')" type="danger" @click="delMenuFun(scope.row.id, scope.row.name)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <edit-menu v-model:dialog-visible="dialogVisible" :menu-obj="menuObj" @get-list="getMenuList"></edit-menu>
  </div>
</template>

<script setup>
import {getMenuTable, delMenu} from "../../api/menu/sysMenu";
import {errorMsg, infoMsg, successMsg} from "../../utils/message";
import editMenu from "./editMenu";
import {onMounted, reactive, ref} from "vue";
import {ElMessageBox} from "element-plus";

const dialogVisible = ref(false)

const state = reactive({
  blurry: '',
  menuObj: {},
  tableData: []
})

onMounted(() => {
  getMenuList()
})

//  获取菜单列表
const getMenuList = () => {
  getMenuTable({blurry: state.blurry}).then(res => {
    if (res.success){
      state.tableData = res.data
    } else {
      errorMsg(res.msg)
    }
  })
}
//  编辑菜单
const editMenuFun = (row) => {
  dialogVisible.value = true
  state.menuObj = row.id ? row : {}
}
//  删除菜单
const delMenuFun = (id, name) => {
  ElMessageBox.confirm('确定删除菜单【'+ name + '】？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    delMenu({id: id}).then(res => {
      if (res.success){
        successMsg(res.data)
        getMenuList()
      } else {
        errorMsg(res.msg)
      }
    })
  }).catch(() => {
    infoMsg('操作已取消')
  })
}
</script>

<style scoped>

</style>