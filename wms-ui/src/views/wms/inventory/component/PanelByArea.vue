<template>
  <merge-table :table-data="tableData" :merge-arr="mergeArr">
    <el-table-column
      prop="warehouseName"
      label="Warehouse" width="160"
    ></el-table-column>
    <el-table-column
      prop="areaName"  width="160"
      label="Area"
    ></el-table-column>

    <el-table-column
      prop="itemTypeName"  width="160"
      label="Type"
    >
    </el-table-column>
    <el-table-column
      prop="itemNo"
      label="Goods No." width="125"
    ></el-table-column>
    <el-table-column
      prop="itemName"
      label="Goods Name"
    ></el-table-column>

    <el-table-column
      prop="quantity"
      label="Inventory" width="100"
    ></el-table-column>
    <el-table-column
      prop="remark"
      label="Remark"
    >
      <template slot-scope="scope">
        <div :title="scope.row.remark" v-html="scope.row.remark?scope.row.remark.replaceAll('\n','<br/>'):''"></div>
      </template>
    </el-table-column>
  </merge-table>
</template>

<script>
import MergeTable from "@/views/wms/inventory/component/MergeTable.vue";

export default {
  name: 'PanelByArea',
  components: {MergeTable},
  props: {
    tableData: {
      type: Array,
      default: () => []
    },
    // loading: {
    //   type: Boolean,
    //   default: false
    // }
  },
  data() {
    return {
      mergeArr: ['warehouseName', 'areaName','itemTypeName'], // 表格中的列名
    }
  }
}
</script>
