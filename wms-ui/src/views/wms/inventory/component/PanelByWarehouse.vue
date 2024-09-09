<template>
  <merge-table :table-data="tableData" :merge-arr="mergeArr">
    <el-table-column
      prop="warehouseName"
      label="Warehouse"
    >
      <template v-slot="{ row }">

        <span>{{ row.warehouseName }}</span>

        <el-popover
          v-if="!row.warehouseId"
          placement="top-start"
          title="Info"
          width="200"
          trigger="hover"
          content="Not Distribute Warehouse。">
          <a slot="reference" class="el-icon-question blue" @click="allocationItem(row)"> Distribute </a>
        </el-popover>

        <el-popconfirm
          v-if="row.warehouseDelFlag == 1"
          placement="top-start"
          icon="el-icon-info"
          icon-color="red"
          width="200"
          trigger="hover"
          @confirm="deleteItem(row)"
          title="This Warehouse has been deleted,Is delete this inventory record？">
          <a slot="reference" class="el-icon-question red">Delete</a>
        </el-popconfirm>
      </template>


    </el-table-column>

    <el-table-column
      prop="itemTypeName"
      label="Category"
    >
    </el-table-column>
    <el-table-column
      prop="itemNo"
      label="Goods No."
    ></el-table-column>
    <el-table-column
      prop="itemName"
      label="Goods Name"
    ></el-table-column>

    <el-table-column
      prop="quantity"
      label="Inventory"
    ></el-table-column>
    <el-table-column
      prop="remark"
      label="Remark"
    >
      <template slot-scope="scope">
        <div :title="scope.row.remark" v-html="scope.row.remark.replaceAll('\n','<br/>')"></div>
      </template>
    </el-table-column>
  </merge-table>
</template>

<script>
import MergeTable from "@/views/wms/inventory/component/MergeTable.vue";
import {delWmsInventoryByWarehouse} from "@/api/wms/inventory";

export default {
  name: 'PanelByWarehouse',
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
      mergeArr: ['warehouseName','itemTypeName'], // 表格中的列名
    }
  },
  methods: {
    deleteItem(row) {
      delWmsInventoryByWarehouse(row.warehouseId).then(res => {
        this.$message({
          message: 'Delete Successful',
          type: 'success'
        });
        this.$emit('update', row)
      })
    },
    allocationItem(row) {
      console.log('该项目未 Distribute Warehouse。', row)
      // this.$message({
      //   message: '该功能No开放',
      //   type: 'warning'
      // });
    }
  }
}
</script>
