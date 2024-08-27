<template>
  <div class="app-container">

    <Dashboard  ></Dashboard>
  <!--  <wave v-if="activeName === 'second'" ></wave>-->
  </div>
</template>

<script>
  import Dashboard from '@/views/wms/receiptOrder/dashboard.vue'

  export default {
  components: { Dashboard},
  data() {
    return {
      activeName: 'first',
    }
  },
}
</script>
