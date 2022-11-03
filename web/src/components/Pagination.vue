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

<script>
export default {
  name: "Pagination",
  props: {
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
  },
  computed: {
    currentPage: {
      get: function (){
        return this.current
      },
      set: function (val){
        this.$emit('update:current', val)
      }
    },
    pageSize: {
      get: function (){
        return this.size
      },
      set: function (val) {
        this.$emit('update:size', val)
      }
    },
    totalSize: {
      get: function () {
        return this.total
      }
    }
  },
  data(){
    return{

    }
  },
  methods: {
    //  修改每页条数
    handleSizeChange(val){
      this.size = val
      this.$emit('get-list')
    },
    //  修改当前页码
    handleCurrentChange(val){
      this.currentPage = val
      this.$emit('get-list')
    }
  }
}
</script>

<style scoped>

</style>