<template>
  <el-dialog title="错误详情" :visible.sync="visible">
    <pre>{{msg}}</pre>
  </el-dialog>
</template>

<script>
export default {
  name: "ErrorDetail",
  props: {
    dialogVisible: {
      type: Boolean,
      require: true,
      default: false
    },
    msg: String
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
  }
}
</script>

<style scoped>

</style>