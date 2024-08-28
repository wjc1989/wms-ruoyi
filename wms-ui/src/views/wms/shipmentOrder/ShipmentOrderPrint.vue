<template>
  <div class="receipt-order-print" ref="receiptOrderPrintRef" hidden="hidden">
    <div class="title">{{ row.shipmentOrderNo }}</div>
    <div class="summary">
      <div class="col1">Project: {{ row.orderNo }}</div>
      <div class="col1">Outbound Type: {{ row.shipmentType }}</div>
      <div class="col1">Date: {{ row.createTime }}</div>
    </div>
    <table class="common-table">
      <tr>
        <th>Goods Name</th>
        <th>Goods No.</th>
        <th>Category</th>
        <th>Count</th>
        <th>Warehouse</th>
      </tr>
      <tr v-for="it in row.details">
        <td>{{ it.itemName || '' }}</td>
        <td>{{ it.itemNo || '' }}</td>
        <td>{{ it.itemTypeName || '' }}</td>
        <td>{{ it.planQuantity }}</td>
        <td>{{ it.place }}</td>
      </tr>
      <tr>
        <td>Total </td>
        <td></td>
        <td></td>
        <td>{{ row.totalCount }}</td>
        <td></td>
      </tr>
    </table>
    <div class="foot">
      <div class="col2">Remark: {{ row.remark }}</div>
    </div>
  </div>
</template>

<script>

export default {
  props: ['row'],
  methods: {
    start() {
      this.$print(this.$refs.receiptOrderPrintRef, {}, 'A4')
    },
  }
}
</script>

<style lang="stylus" media="print">
@page {
  size: auto;
  margin: 0;
}

@media print {
  * {
    color: #000 !important;
  }

  table {
    width 100%
    table-layout: fixed;
    border-collapse: collapse;
    border-spacing: 0;
  }

  table, tbody, thead {
    width: 100% !important;
  }

  .receipt-order-print {
    width: 100% !important;
    font-size: 14px;
  }

  table, table tr th, table tr td {
    border: 0.05rem solid #000;
    font-size: 12px;
  }

}

.receipt-order-print
  padding 12px
  line-height 1.8

  .summary
    display flex
    flex-wrap wrap

    .col1
      width 50%

  .title
    font-size 18px
    text-align center

  .common-table
    td, th
      border-color black
</style>
