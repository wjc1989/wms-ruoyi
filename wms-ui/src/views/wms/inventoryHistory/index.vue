<template>
  <div class="app-container">
    <el-form class="ry_form" v-show="showSearch" :inline="true" label-width="120px" :model="queryParams" ref="queryForm"
             size="medium">
      <el-form-item label="Type" prop="formType">
        <in-out-type-select v-model="queryParams.formType" size="small"></in-out-type-select>
      </el-form-item>
      <el-form-item label="Item" prop="itemId">
        <item-select v-model="queryParams.itemId" clearable="clearable" placeholder="Please Input ItemID"
                     size="small"></item-select>
      </el-form-item>
      <el-form-item label="Warehouse" prop="rackId">
        <wms-warehouse-cascader v-model="queryParams.warehouseArr" size="small"></wms-warehouse-cascader>
      </el-form-item>
      <el-form-item class="flex_one tr">
        <el-button icon="el-icon-search" size="mini" type="primary" @click="handleQuery">Search</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">Reset</el-button>
      </el-form-item>
    </el-form>
    <el-row class="mb8" :gutter="10">
      <el-col :span="1.5">
        <el-button v-hasPermi="['wms:inventoryHistory:export']" icon="el-icon-download" :loading="exportLoading"
                   plain="plain" size="mini" type="warning" @click="handleExport">Export
        </el-button>
      </el-col>
      <right-toolbar  :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <WmsTable v-loading="loading" :data="wmsInventoryHistoryList" @selection-change="handleSelectionChange">
      <el-table-column  align="center" label="Type" prop="formType">
        <template v-slot="{ row }"><span>{{ row.formType ? opTypeMap[row.formType] : row.formType }}</span></template>
      </el-table-column>
      <el-table-column  align="center" label="Goods Name" prop="itemName"></el-table-column>
      <el-table-column align="center" label="Goods No." prop="itemNo"></el-table-column>
      <el-table-column  align="center" label="Warehouse" prop="rackId">
        <template v-slot="{ row }"><span>{{ row.warehouseName }}</span><span
          v-if="row.areaName">/{{ row.areaName }}</span></template>
      </el-table-column>
      <el-table-column  align="center" label="Inventory Change" prop="quantity"></el-table-column>
      <el-table-column  align="center" label="Date" prop="createTime"></el-table-column>
      <el-table-column  align="center" label="Remark" prop="remark"></el-table-column>
    </WmsTable>
    <pagination v-show="total&gt;0" :limit.sync="queryParams.pageSize" :page.sync="queryParams.pageNum" :total="total"
                @pagination="getList"></pagination>
    <!-- Add OrModifyInventory Record对话框-->
    <el-dialog append-to-body="append-to-body" :title="title" :visible.sync="open" width="50%">
      <el-form class="dialog-form-two" inline="inline" label-width="108px" :model="form" ref="form" :rules="rules">
        <el-form-item label="Operateid" prop="formId">
          <el-input v-model="form.formId" placeholder="Please Input Operateid"></el-input>
        </el-form-item>
        <el-form-item label="Type" prop="formType">
          <el-select v-model="form.formType" placeholder="Please select Type">
            <el-option label="Please select Type " value=""></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="ItemID" prop="itemId">
          <el-input v-model="form.itemId" placeholder="Please Input ItemID"></el-input>
        </el-form-item>
        <el-form-item label=" Rackid" prop="rackId">
          <el-input v-model="form.rackId" placeholder="Please Input  Rackid"></el-input>
        </el-form-item>
        <el-form-item label="InventoryChange" prop="quantity">
          <el-input v-model="form.quantity" placeholder="Please Input InventoryChange"></el-input>
        </el-form-item>
        <el-form-item label="Remark" prop="remark">
          <el-input v-model="form.remark" placeholder="Please Input Remark"></el-input>
        </el-form-item>
      </el-form>
      <div class="dialog-footer" slot="footer">
        <el-button type="primary" @click="submitForm">OK</el-button>
        <el-button @click="cancel">Cancel</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listWmsInventoryHistory, getWmsInventoryHistory, delWmsInventoryHistory, addWmsInventoryHistory, updateWmsInventoryHistory, exportWmsInventoryHistory } from "@/api/wms/inventoryHistory";
import ItemSelect from '@/components/ItemSelect'
import InOutTypeSelect from '@/components/InOutTypeSelect'
import { mapGetters } from 'vuex'

export default {
  name: "WmsInventoryHistory",
  components: { InOutTypeSelect, ItemSelect },
  data() {
    return {
      // 遮罩层
      loading: true,
      // Export遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      // 非个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // ShowSearch
      showSearch: true,
      // 总条数
      total: 0,
      // Inventory Record表格Data
      wmsInventoryHistoryList: [],
      // 弹出层标题
      title: "",
      // Show弹出层
      open: false,
      // SearchParams
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        formId: null,
        formType: null,
        itemId: null,
        warehouseArr: null,
        quantity: null,
      },
      // 表Params
      form: {},
      // 表校验
      rules: {
      },
      columns: [
            { key: 1, label: "Operateid（Outbound 、Inbound、Inventory移动表id）", visible:  true  },
            { key: 2, label: "Type", visible:  true  },
            { key: 3, label: "ItemID", visible:  true  },
            { key: 4, label: " Rackid", visible:  true  },
            { key: 5, label: "InventoryChange", visible:  true  },
            { key: 6, label: "Remark", visible:  true  },
                             ],
    };
  },
  computed: {
    ...mapGetters(['opTypeMap'])
  },
  created() {
    this.getList();
  },
  methods: {
    /** SearchInventory Record列表 */
    getList() {
      this.loading = true;
      const {pageNum, pageSize, warehouseArr} = this.queryParams;
      const [warehouseId, areaId, rackId] = warehouseArr || [];
      const query = {...this.queryParams, pageNum: undefined, pageSize: undefined, warehouseId, areaId, rackId};
      const pageReq = {page: pageNum - 1, size: pageSize};
      listWmsInventoryHistory(query, pageReq).then(response => {
        const { content, totalElements } = response
        this.wmsInventoryHistoryList = content;
        this.total = totalElements;
        this.loading = false;
      });
    },
    // CancelButton
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表Reset
    reset() {
      this.form = {
        id: null,
        formId: null,
        formType: null,
        itemId: null,
        rackId: null,
        quantity: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    /** SearchButtonOperate */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** ResetButtonOperate */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中Data
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** AddButtonOperate */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "New";
    },
    /** ModifyButtonOperate */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getWmsInventoryHistory(id).then(response => {
        this.form = response;
        this.open = true;
        this.title = "Modify";
      });
    },
    /** 提交Button */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateWmsInventoryHistory(this.form).then(response => {
              this.$modal.msgSuccess("Modify Successful");
              this.open = false;
              this.getList();
            });
          } else {
            addWmsInventoryHistory(this.form).then(response => {
              this.$modal.msgSuccess("Add Successful");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** DeleteButtonOperate */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm(' Do you want delete Inventory RecordNo."' + ids + '"？').then(function() {
        return delWmsInventoryHistory(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Delete Successful");
      }).catch(() => {});
    },
    /** ExportButtonOperate */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal.confirm('Export All？').then(() => {
        this.exportLoading = true;
        return exportWmsInventoryHistory(queryParams);
      }).then(response => {
        this.$download.download(response);
        this.exportLoading = false;
      }).catch(() => {});
    }
  }
};
</script>
