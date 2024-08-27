<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="120px" size="medium"
             class="ry_form">
      <el-form-item label="Settlementid" prop="settlementId">
        <el-input
          v-model="queryParams.settlementId"
          placeholder="Please Input Settlementid"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="SettlementType,1:Monthly，2:Year-end" prop="settlementType">
        <el-select v-model="queryParams.settlementType" placeholder="Please select SettlementType,1:Monthly，2:Year-end" clearable
                   size="small">
          <el-option label="Please select Type" value=""/>
        </el-select>
      </el-form-item>
      <el-form-item label="Itemid" prop="itemId">
        <el-input
          v-model="queryParams.itemId"
          placeholder="Please Input Itemid"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="No." prop="itemNo">
        <el-input
          v-model="queryParams.itemNo"
          placeholder="Please Input No."
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Item" prop="itemName">
        <el-input
          v-model="queryParams.itemName"
          placeholder="Please Input Item"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Warehouseid" prop="warehouseId">
        <el-input
          v-model="queryParams.warehouseId"
          placeholder="Please Input Warehouseid"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="WarehouseNo." prop="warehouseNo">
        <el-input
          v-model="queryParams.warehouseNo"
          placeholder="Please Input WarehouseNo."
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <template v-if="showMoreCondition">
        <el-form-item label="WarehouseItem" prop="warehouseName">
          <el-input
            v-model="queryParams.warehouseName"
            placeholder="Please Input WarehouseItem"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="Areaid" prop="areaId">
          <el-input
            v-model="queryParams.areaId"
            placeholder="Please Input Areaid"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="AreaNo." prop="areaNo">
          <el-input
            v-model="queryParams.areaNo"
            placeholder="Please Input AreaNo."
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="AreaItem" prop="areaName">
          <el-input
            v-model="queryParams.areaName"
            placeholder="Please Input AreaItem"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label=" Last inventory" prop="previousBalance">
          <el-input
            v-model="queryParams.previousBalance"
            placeholder="Please Input  Last inventory"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="Current Inbound" prop="currentEnter">
          <el-input
            v-model="queryParams.currentEnter"
            placeholder="Please Input Current Inbound"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="Current Outbound " prop="currentOut">
          <el-input
            v-model="queryParams.currentOut"
            placeholder="Please Input Current Outbound "
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="Current Count" prop="currentCheck">
          <el-input
            v-model="queryParams.currentCheck"
            placeholder="Please Input Current Count"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="Current Inventory" prop="currentBalance">
          <el-input
            v-model="queryParams.currentBalance"
            placeholder="Please Input Current Inventory"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
      </template>
      <el-form-item class="flex_one tr">
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">Search</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">Reset</el-button>
        <el-button :icon="showMoreCondition ? 'el-icon-arrow-up' : 'el-icon-arrow-down'" size="mini"
                   @click="showMoreCondition = !showMoreCondition">{{ showMoreCondition ? 'Collapse' : 'Expand' }}
        </el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['wms:wmsInventorySettlementDetail:add']"
        >New
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['wms:wmsInventorySettlementDetail:edit']"
        >Modify
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['wms:wmsInventorySettlementDetail:remove']"
        >Delete
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['wms:wmsInventorySettlementDetail:export']"
        >Export
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <WmsTable v-loading="loading" :data="wmsInventorySettlementDetailList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="Settlementid" align="center" prop="settlementId" v-if="columns[0].visible"/>
      <el-table-column label="SettlementType,1:Monthly，2:Year-end" align="center" prop="settlementType" v-if="columns[1].visible"/>
      <el-table-column label="Itemid" align="center" prop="itemId" v-if="columns[2].visible"/>
      <el-table-column label="No." align="center" prop="itemNo" v-if="columns[3].visible"/>
      <el-table-column label="Item" align="center" prop="itemName" v-if="columns[4].visible"/>
      <el-table-column label="Warehouseid" align="center" prop="warehouseId" v-if="columns[5].visible"/>
      <el-table-column label="WarehouseNo." align="center" prop="warehouseNo" v-if="columns[6].visible"/>
      <el-table-column label="WarehouseItem" align="center" prop="warehouseName" v-if="columns[7].visible"/>
      <el-table-column label="Areaid" align="center" prop="areaId" v-if="columns[8].visible"/>
      <el-table-column label="AreaNo." align="center" prop="areaNo" v-if="columns[9].visible"/>
      <el-table-column label="AreaItem" align="center" prop="areaName" v-if="columns[10].visible"/>
      <el-table-column label=" Last inventory" align="center" prop="previousBalance" v-if="columns[11].visible"/>
      <el-table-column label="Current Inbound" align="center" prop="currentEnter" v-if="columns[12].visible"/>
      <el-table-column label="Current Outbound " align="center" prop="currentOut" v-if="columns[13].visible"/>
      <el-table-column label="Current Count" align="center" prop="currentCheck" v-if="columns[14].visible"/>
      <el-table-column label="Current Inventory" align="center" prop="currentBalance" v-if="columns[15].visible"/>
      <el-table-column label="Remark" align="center" prop="remark" v-if="columns[16].visible"/>
      <el-table-column label="Operate" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['wms:wmsInventorySettlementDetail:edit']"
          >Modify
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['wms:wmsInventorySettlementDetail:remove']"
          >Delete
          </el-button>
        </template>
      </el-table-column>
    </WmsTable>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- Add OrModifyInventorySettlement Detail对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="50%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="108px" inline class="dialog-form-two">
        <el-form-item label="Settlementid" prop="settlementId">
          <el-input v-model="form.settlementId" placeholder="Please Input Settlementid"/>
        </el-form-item>
        <el-form-item label="SettlementType,1:Monthly，2:Year-end" prop="settlementType">
          <el-select v-model="form.settlementType" placeholder="Please select SettlementType,1:Monthly，2:Year-end">
            <el-option label="Please select Type" value=""/>
          </el-select>
        </el-form-item>
        <el-form-item label="Itemid" prop="itemId">
          <el-input v-model="form.itemId" placeholder="Please Input Itemid"/>
        </el-form-item>
        <el-form-item label="No." prop="itemNo">
          <el-input v-model="form.itemNo" placeholder="Please Input No."/>
        </el-form-item>
        <el-form-item label="Item" prop="itemName">
          <el-input v-model="form.itemName" placeholder="Please Input Item"/>
        </el-form-item>
        <el-form-item label="Warehouseid" prop="warehouseId">
          <el-input v-model="form.warehouseId" placeholder="Please Input Warehouseid"/>
        </el-form-item>
        <el-form-item label="WarehouseNo." prop="warehouseNo">
          <el-input v-model="form.warehouseNo" placeholder="Please Input WarehouseNo."/>
        </el-form-item>
        <el-form-item label="WarehouseItem" prop="warehouseName">
          <el-input v-model="form.warehouseName" placeholder="Please Input WarehouseItem"/>
        </el-form-item>
        <el-form-item label="Areaid" prop="areaId">
          <el-input v-model="form.areaId" placeholder="Please Input Areaid"/>
        </el-form-item>
        <el-form-item label="AreaNo." prop="areaNo">
          <el-input v-model="form.areaNo" placeholder="Please Input AreaNo."/>
        </el-form-item>
        <el-form-item label="AreaItem" prop="areaName">
          <el-input v-model="form.areaName" placeholder="Please Input AreaItem"/>
        </el-form-item>
        <el-form-item label=" Last inventory" prop="previousBalance">
          <el-input v-model="form.previousBalance" placeholder="Please Input  Last inventory"/>
        </el-form-item>
        <el-form-item label="Current Inbound" prop="currentEnter">
          <el-input v-model="form.currentEnter" placeholder="Please Input Current Inbound"/>
        </el-form-item>
        <el-form-item label="Current Outbound " prop="currentOut">
          <el-input v-model="form.currentOut" placeholder="Please Input Current Outbound "/>
        </el-form-item>
        <el-form-item label="Current Count" prop="currentCheck">
          <el-input v-model="form.currentCheck" placeholder="Please Input Current Count"/>
        </el-form-item>
        <el-form-item label="Current Inventory" prop="currentBalance">
          <el-input v-model="form.currentBalance" placeholder="Please Input Current Inventory"/>
        </el-form-item>
        <el-form-item label="Remark" prop="remark">
          <el-input v-model="form.remark" placeholder="Please Input Remark"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">OK</el-button>
        <el-button @click="cancel">Cancel</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listWmsInventorySettlementDetail,
  getWmsInventorySettlementDetail,
  delWmsInventorySettlementDetail,
  addWmsInventorySettlementDetail,
  updateWmsInventorySettlementDetail,
  exportWmsInventorySettlementDetail
} from "@/api/wms/inventorySettlementDetail";

export default {
  name: "WmsInventorySettlementDetail",
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
      // InventorySettlement Detail表格Data
      wmsInventorySettlementDetailList: [],
      // 弹出层标题
      title: "",
      // Show弹出层
      open: false,
      // SearchParams
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        settlementId: null,
        settlementType: null,
        itemId: null,
        itemNo: null,
        itemName: null,
        warehouseId: null,
        warehouseNo: null,
        warehouseName: null,
        areaId: null,
        areaNo: null,
        areaName: null,
        previousBalance: null,
        currentEnter: null,
        currentOut: null,
        currentCheck: null,
        currentBalance: null,
      },
      // 表Params
      form: {},
      // 表校验
      rules: {
        settlementId: [
          {required: true, message: "Settlementid is required", trigger: "blur"}
        ],
        itemId: [
          {required: true, message: "Itemid is required", trigger: "blur"}
        ],
        itemNo: [
          {required: true, message: "No. is required", trigger: "blur"}
        ],
        itemName: [
          {required: true, message: "Item is required", trigger: "blur"}
        ],
        warehouseId: [
          {required: true, message: "Warehouseid is required", trigger: "blur"}
        ],
        warehouseNo: [
          {required: true, message: "WarehouseNo. is required", trigger: "blur"}
        ],
        warehouseName: [
          {required: true, message: "WarehouseItem is required", trigger: "blur"}
        ],
        areaId: [
          {required: true, message: "Areaid is required", trigger: "blur"}
        ],
        areaNo: [
          {required: true, message: "AreaNo. is required", trigger: "blur"}
        ],
        areaName: [
          {required: true, message: "AreaItem is required", trigger: "blur"}
        ],
      },
      columns: [
        {key: 1, label: "Settlementid", visible: true},
        {key: 2, label: "SettlementType,1:Monthly，2:Year-end", visible: true},
        {key: 3, label: "Itemid", visible: true},
        {key: 4, label: "No.", visible: true},
        {key: 5, label: "Item", visible: true},
        {key: 6, label: "Warehouseid", visible: true},
        {key: 7, label: "WarehouseNo.", visible: true},
        {key: 8, label: "WarehouseItem", visible: true},
        {key: 9, label: "Areaid", visible: true},
        {key: 10, label: "AreaNo.", visible: false},
        {key: 11, label: "AreaItem", visible: false},
        {key: 12, label: " Last inventory", visible: false},
        {key: 13, label: "Current Inbound", visible: false},
        {key: 14, label: "Current Outbound ", visible: false},
        {key: 15, label: "Current Count", visible: false},
        {key: 16, label: "Current Inventory", visible: false},
        {key: 18, label: "Remark", visible: false},
      ],
      showMoreCondition: false
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** SearchInventorySettlement Detail列表 */
    getList() {
      this.loading = true;
      const {pageNum, pageSize} = this.queryParams;
      const query = {...this.queryParams, pageNum: undefined, pageSize: undefined};
      const pageReq = {page: pageNum - 1, size: pageSize};
      listWmsInventorySettlementDetail(query, pageReq).then(response => {
        const {content, totalElements} = response
        this.wmsInventorySettlementDetailList = content;
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
        settlementId: null,
        settlementType: null,
        itemId: null,
        itemNo: null,
        itemName: null,
        warehouseId: null,
        warehouseNo: null,
        warehouseName: null,
        areaId: null,
        areaNo: null,
        areaName: null,
        previousBalance: null,
        currentEnter: null,
        currentOut: null,
        currentCheck: null,
        currentBalance: null,
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
      this.single = selection.length !== 1
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
      getWmsInventorySettlementDetail(id).then(response => {
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
            updateWmsInventorySettlementDetail(this.form).then(response => {
              this.$modal.msgSuccess("Modify Successful");
              this.open = false;
              this.getList();
            });
          } else {
            addWmsInventorySettlementDetail(this.form).then(response => {
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
      this.$modal.confirm(' Do you want delete InventorySettlement DetailNo."' + ids + '"？').then(function () {
        return delWmsInventorySettlementDetail(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Delete Successful");
      }).catch(() => {
      });
    },
    /** ExportButtonOperate */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal.confirm('Export All？').then(() => {
        this.exportLoading = true;
        return exportWmsInventorySettlementDetail(queryParams);
      }).then(response => {
        this.$download.download(response);
        this.exportLoading = false;
      }).catch(() => {
      });
    }
  }
};
</script>
