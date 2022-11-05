<template>
  <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-sizes="[10, 20, 50, 100, 200, 500, 1000]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="totalSize">
  </el-pagination>
</template>

<script setup>
import {computed} from "vue";

const props = defineProps({
  current: {
    type: Number,
    default: 1
  },
  size: {
    type: Number,
    default: 10
  },
  total: {
    type: Number,
    default: 0
  }
})

const emit = defineEmits(['update:current', 'update:size', 'get-list'])

let currentPage = computed({
  get: () => props.current,
  set: (val) => emit('update:current', val)
})

let pageSize = computed({
  get: () => props.size,
  set: (val) => emit('update:size', val)
})

const totalSize = computed(() => {
  return props.total
})

//  修改每页条数
const handleSizeChange = (val) => {
  pageSize.value = val
  emit('get-list')
}
//  修改当前页码
const handleCurrentChange = (val) => {
  currentPage.value = val
  emit('get-list')
}
</script>

<style scoped>

</style>