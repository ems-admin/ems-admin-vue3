<template>
  <div>
    <div class="searchDiv">
      <el-input class="searchInput" v-model="state.blurry" placeholder="请输入用户名或昵称" clearable></el-input>
      <el-button type="primary" @click="getUserListFun">查询</el-button>
      <el-button v-if="hasPer('user:add')" @click="editUserFun" style="float: right;">新增</el-button>
    </div>
    <el-table :data="state.tableData" row-key="id" border>
      <el-table-column label="用户名" prop="username"></el-table-column>
      <el-table-column label="昵称" prop="nickName"></el-table-column>
      <el-table-column label="角色" prop="roles"></el-table-column>
      <el-table-column label="状态" prop="enabled" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.enabled" type="success" size="mini">启用</el-tag>
          <el-tag v-else type="danger" size="mini">停用</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" prop="option" width="220px" align="center">
        <template v-if="scope.row.username !== state.username" slot-scope="scope">
          <el-button v-if="hasPer('user:enabled')" :type="buttonType(scope.row.enabled)"
                     @click="enabledUserFun(JSON.parse(JSON.stringify(scope.row)))">
            {{scope.row.enabled ? '停用' : '启用'}}</el-button>
          <el-button v-if="hasPer('user:edit')" type="primary" @click="editUserFun(JSON.parse(JSON.stringify(scope.row)))">编辑</el-button>
          <el-button v-if="hasPer('user:del')" type="danger" @click="deleteUser(scope.row.id, scope.row.username)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--分页-->
    <pagination :current.sync="current" :size.sync="size" :total="total" @get-list="getUserList"></pagination>
    <!--编辑-->
    <edit-user :dialog-visible.sync="dialogVisible" :user-obj="userObj" @get-list="getUserList"></edit-user>
  </div>
</template>

<script setup>
import editUser from "./editUser";
import Pagination from "../../components/Pagination";
import {getUserList, delUser, enabledUser} from "../../api/user/sysUser";
import {errorMsg, infoMsg, successMsg} from "../../utils/message";
import {hasPer} from "../../utils/common";
import {useStore} from "../../store";
import {onMounted, reactive, ref} from "vue";
import {ElMessageBox} from "element-plus";

const store = useStore()

const state = reactive({
  blurry: '',
  username: store.userInfo.username,
  tableData: [],
  userObj: {},
  current: 1,
  size: 10,
  total: 0
})

const dialogVisible = ref(false)

onMounted(() => {
  getUserListFun()
})

const getUserListFun = () => {
  getUserList({blurry: state.blurry}).then(res => {
    if (res.success){
      state.tableData = res.data.records
      state.total = res.data.total
    }
  })
}

const editUserFun = (row) => {
  dialogVisible.value = true
  state.userObj = row.id ? row : {}
}

const deleteUser = (id, username) => {
  this.$confirm('确定删除用户【' + username + '】？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    delUser({id: id}).then(res => {
      if (res.success){
        successMsg(res.data)
        getUserListFun()
      } else {
        errorMsg(res.msg)
      }
    })
  }).catch(() => {
    infoMsg('操作已取消')
  })
}
//  启用/停用用户
const enabledUserFun = (row) => {
  row.enabled = !row.enabled
  const str = row.enabled ? '启用' : '停用'
  const color = row.enabled ? '#67C23A' : '#F56C6C'
  ElMessageBox.confirm('确定<span style="color: '+color+'">' + str + '</span>用户【' + row.nickName + '】？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
    dangerouslyUseHTMLString: true
  }).then(() => {
    enabledUser(row).then(res => {
      if (res.success){
        successMsg(res.data)
        getUserListFun()
      } else {
        errorMsg(res.msg)
      }
    })
  }).catch(() => {
    infoMsg('操作已取消')
  })
}

const buttonType = (type) => {
  if (type){
    return 'warning'
  } else {
    return 'success'
  }
}

// export default {
//   name: "index",
//   components: {
//     Pagination,
//     editUser
//   },
//   data(){
//     return{
//       blurry: '',
//       username: store.state.userInfo.username,
//       tableData: [],
//       dialogVisible: false,
//       userObj: {},
//       current: 1,
//       size: 10,
//       total: 0
//     }
//   },
//   mounted() {
//     this.getUserList()
//   },
//   methods: {
//     hasPer,
//     getUserList(){
//       getUserList({blurry: this.blurry}).then(res => {
//         if (res.success){
//           this.tableData = res.data.records
//           this.total = res.data.total
//         }
//       })
//     },
//     editUser(row){
//       this.dialogVisible = true
//       this.userObj = row.id ? row : {}
//     },
//     delUser(id, username){
//       this.$confirm('确定删除用户【' + username + '】？', '提示', {
//         confirmButtonText: '确定',
//         cancelButtonText: '取消',
//         type: 'warning'
//       }).then(() => {
//         delUser({id: id}).then(res => {
//           if (res.success){
//             successMsg(res.data)
//             this.getUserList()
//           } else {
//             errorMsg(res.msg)
//           }
//         })
//       }).catch(() => {
//         infoMsg('操作已取消')
//       })
//     },
//     //  启用/停用用户
//     enabledUser(row){
//       row.enabled = !row.enabled
//       const str = row.enabled ? '启用' : '停用'
//       const color = row.enabled ? '#67C23A' : '#F56C6C'
//       this.$confirm('确定<span style="color: '+color+'">' + str + '</span>用户【' + row.nickName + '】？', '提示', {
//         confirmButtonText: '确定',
//         cancelButtonText: '取消',
//         type: 'warning',
//         dangerouslyUseHTMLString: true
//       }).then(() => {
//         enabledUser(row).then(res => {
//           if (res.success){
//             successMsg(res.data)
//             this.getUserList()
//           } else {
//             errorMsg(res.msg)
//           }
//         })
//       }).catch(() => {
//         infoMsg('操作已取消')
//       })
//     },
//     buttonType(type){
//       if (type){
//         return 'warning'
//       } else {
//         return 'success'
//       }
//     }
//   }
// }
</script>

<style scoped>

</style>