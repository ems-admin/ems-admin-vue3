<template>
  <div>
    <div class="searchDiv">
      <el-input class="searchInput" v-model="state.blurry" placeholder="请输入操作人或说明" clearable></el-input>
      <el-select class="searchInput" v-model="state.logType" placeholder="请选择日志类型" clearable>
        <el-option value="1" label="成功"></el-option>
        <el-option value="2" label="失败"></el-option>
      </el-select>
      <el-button type="primary" @click="getLogs">查询</el-button>
    </div>
    <el-table :data="state.tableData" row-key="id" border>
      <el-table-column label="序号" type="index" width="60">
        <template #default="scope">
          <span>{{(state.current - 1) * state.size + 1 + scope.$index}}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作人" prop="username" width="100"></el-table-column>
      <el-table-column label="操作说明" prop="description" show-overflow-tooltip width="150"></el-table-column>
      <el-table-column label="请求方法" prop="method" show-overflow-tooltip></el-table-column>
      <el-table-column label="请求参数" prop="params" show-overflow-tooltip></el-table-column>
      <el-table-column label="IP" prop="ip" width="120"></el-table-column>
      <el-table-column label="日志类型" prop="logType" width="100">
        <template #default="scope">
          <span>{{scope.row.logType === '1' ? '成功' : '失败'}}</span>
        </template>
      </el-table-column>
      <el-table-column label="错误详情" prop="exceptionDetail" show-overflow-tooltip>
        <!--将错误的长度显示限制在100，防止内容过长，引起由于show-tooltip-when-overflow自带BUG产生页面的抖动-->
        <template #default="scope">
          <span>{{scope.row.exceptionDetail ? scope.row.exceptionDetail.substring(0, 100) : ''}}</span>
        </template>
      </el-table-column>
      <el-table-column label="请求耗时" prop="time" width="100"></el-table-column>
      <el-table-column label="操作时间" prop="createTime" width="150"></el-table-column>
      <el-table-column label="操作" prop="option" width="120" align="center">
        <template #default="scope">
          <el-button type="primary" :disabled="scope.row.logType === '1'" @click="showErrorDetails(scope.row.exceptionDetail)">错误详情</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--    分页-->
    <pagination v-model:current="state.current" v-model:size="state.size" v-model:total="state.total" @get-list="getLogs"></pagination>
    <error-detail v-model:dialog-visible="dialogVisible" :msg="state.msg"></error-detail>
  </div>
</template>

<script setup>
import {getLogList} from "../../api/log/sysLog";
import {errorMsg} from "../../utils/message";
import Pagination from "../../components/Pagination";
import ErrorDetail from "./ErrorDetail";
import {onMounted, reactive, ref} from "vue";

const state = reactive({
  blurry: '',
  msg: null,
  tableData: [],
  logType: '',
  current: 1,
  size: 10,
  total: 0
})

onMounted(() => {
  getLogs()
})

const dialogVisible = ref(false)

const getLogs = () => {
  const params = {
    blurry: state.blurry,
    currentPage: state.current,
    size: state.size,
    logType: state.logType
  }
  getLogList(params).then(res => {
    if (res.success){
      state.tableData = res.data.records
      state.total = res.data.total
    } else {
      errorMsg(res.msg)
    }
  })
}
//  显示错误信息详情
const showErrorDetails = (msg) => {
  dialogVisible.value = true
  state.msg = msg
}
</script>

<style scoped>
.el-tooltip__popper{
  margin-left: 20px;
}
</style>