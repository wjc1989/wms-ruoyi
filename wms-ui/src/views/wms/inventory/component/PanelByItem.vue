<template>
  <merge-table :table-data="tableData" :merge-arr="['itemNo']">
    <el-table-column
      prop="itemNo"
      label="Item Info"
    >
      <template v-slot="{ row }">
        <el-row>
          <el-col :span="12">
            <span>Item：{{ row.itemName }}</span>
          </el-col>
          <el-col :span="12">
            <span>No.：{{ row.itemNo }}</span>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <span>Type：{{ row.itemTypeName }}</span>
          </el-col>
          <el-col :span="12" v-if="row.itemDelFlag">
            <el-popconfirm
              placement="top-start"
              icon="el-icon-info"
              icon-color="red"
              width="200"
              trigger="hover"
              @confirm="deleteItem(row)"
              title="This item has already deleted，Is delete this inventory record？">
              <a slot="reference" class="el-icon-question red" >Delete</a>
            </el-popconfirm>
          </el-col>
        </el-row>

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
        <div :title="scope.row.remark" v-html="scope.row.remark?scope.row.remark.replaceAll('\n','<br/>'):''"></div>
      </template>
    </el-table-column>
  </merge-table>
</template>

<script>
import MergeTable from "@/views/wms/inventory/component/MergeTable.vue";
import {delWmsInventoryByItem} from "@/api/wms/inventory";

export default {
  name: 'PanelByItem',
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
  methods: {
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
