<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
  </div>
</template>
<script setup>
import {computed} from "vue";

const props = defineProps({
  msg: String
})

const msg = computed(() => {
  return props.msg
})
</script>
<style scoped>
  .hello{
    font-size: 40px;
  }
</style>
