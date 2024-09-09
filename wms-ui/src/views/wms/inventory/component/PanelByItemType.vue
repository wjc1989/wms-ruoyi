<template>
  <merge-table :table-data="tableData" :merge-arr="mergeArr">
    <el-table-column
      prop="itemTypeName"
      label="Type"
    >
    </el-table-column>
    <el-table-column
      prop="itemNo"
      label="Goods No."
    ></el-table-column>
    <el-table-column
      prop="itemName"
      label="Goods Name"
    >
      <template v-slot="{ row }">


        <span>{{ row.itemName }}</span>

        <el-popconfirm
          v-if="row.itemDelFlag"
          placement="top-start"
          icon="el-icon-info"
          icon-color="red"
          width="200"
          class="ml10"
          trigger="hover"
          @confirm="deleteItem(row)"
          title="该Item已经被逻辑Delete，清除Inventory Record？">
          <a slot="reference" class="el-icon-question red">Delete</a>
        </el-popconfirm>

      </template>
    </el-table-column>
    <el-table-column
      prop="warehouseName"
      label="Warehouse"
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
import {delWmsInventoryByItem} from "@/api/wms/inventory";

export default {
  name: 'PanelByItemType',
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
      mergeArr: ['itemTypeName', 'itemNo', 'itemName'], // 表格中的列名
    }
  }, methods: {
    deleteItem(row) {
      delWmsInventoryByItem(row.itemId).then(res => {
        this.$message({
          message: 'Delete Successful',
          type: 'success'
        });
        this.$emit('update', row)
      })
    }
  }
}
</script>
