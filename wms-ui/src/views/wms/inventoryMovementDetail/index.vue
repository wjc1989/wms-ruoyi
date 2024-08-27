<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="120px" size="medium" class="ry_form">
      <el-form-item label="No." prop="inventoryMovementId">
        <el-input
          v-model="queryParams.inventoryMovementId"
          placeholder="Please Input No."
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Item" prop="itemId">
        <el-input
          v-model="queryParams.itemId"
          placeholder="Please Input Item"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Plan Count" prop="planQuantity">
        <el-input
          v-model="queryParams.planQuantity"
          placeholder="Please Input Plan Count"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Real Count" prop="realQuantity">
        <el-input
          v-model="queryParams.realQuantity"
          placeholder="Please Input Real Count"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item class="flex_one tr">
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">Search</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">Reset</el-button>
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
          v-hasPermi="['wms:inventoryMovementDetail:add']"
        >New</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['wms:inventoryMovementDetail:edit']"
        >Modify</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['wms:inventoryMovementDetail:remove']"
        >Delete</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['wms:inventoryMovementDetail:export']"
        >Export</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" ></right-toolbar>
    </el-row>

    <WmsTable v-loading="loading" :data="wmsInventoryMovementDetailList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="No." align="center" prop="inventoryMovementId" />
      <el-table-column label="Item" align="center" prop="itemId" />
      <el-table-column label="Plan Count" align="center" prop="planQuantity" />
      <el-table-column label="Real Count" align="center" prop="realQuantity" />
      <el-table-column label="Remark" align="center" prop="remark" />
      <el-table-column label="Operate" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['wms:inventoryMovementDetail:edit']"
          >Modify</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['wms:inventoryMovementDetail:remove']"
          >Delete</el-button>
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

    <!-- Add OrModifyInventory移动 Detail对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="50%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="108px" inline class="dialog-form-two">
        <el-form-item label="No." prop="inventoryMovementId">
          <el-input v-model="form.inventoryMovementId" placeholder="Please Input No." />
        </el-form-item>
        <el-form-item label="Item" prop="itemId">
          <el-input v-model="form.itemId" placeholder="Please Input Item" />
        </el-form-item>
        <el-form-item label="Plan Count" prop="planQuantity">
          <el-input v-model="form.planQuantity" placeholder="Please Input Plan Count" />
        </el-form-item>
        <el-form-item label="Real Count" prop="realQuantity">
          <el-input v-model="form.realQuantity" placeholder="Please Input Real Count" />
        </el-form-item>
        <el-form-item label="Remark" prop="remark">
          <el-input v-model="form.remark" placeholder="Please Input Remark" />
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
import { listWmsInventoryMovementDetail, getWmsInventoryMovementDetail, delWmsInventoryMovementDetail, addWmsInventoryMovementDetail, updateWmsInventoryMovementDetail, exportWmsInventoryMovementDetail } from "@/api/wms/inventoryMovementDetail";

export default {
  name: "WmsInventoryMovementDetail",
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
      // Inventory移动 Detail表格Data
      wmsInventoryMovementDetailList: [],
      // 弹出层标题
      title: "",
      // Show弹出层
      open: false,
      // SearchParams
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        inventoryMovementId: null,
        itemId: null,
        planQuantity: null,
        realQuantity: null,
      },
      // 表Params
      form: {},
      // 表校验
      rules: {
      },
      columns: [
            { key: 1, label: "Inventory移动Id", visible:  true  },
            { key: 2, label: "Item", visible:  true  },
            { key: 3, label: "Plan Count", visible:  true  },
            { key: 4, label: "Real Count", visible:  true  },
                { key: 6, label: "Remark", visible:  true  },
                         ],
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** SearchInventory移动 Detail列表 */
    getList() {
      this.loading = true;
      const {pageNum, pageSize} = this.queryParams;
      const query = {...this.queryParams, pageNum: undefined, pageSize: undefined};
      const pageReq = {page: pageNum - 1, size: pageSize};
      listWmsInventoryMovementDetail(query, pageReq).then(response => {
        const { content, totalElements } = response
        this.wmsInventoryMovementDetailList = content;
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
        inventoryMovementId: null,
        itemId: null,
        planQuantity: null,
        realQuantity: null,
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
      getWmsInventoryMovementDetail(id).then(response => {
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
            updateWmsInventoryMovementDetail(this.form).then(response => {
              this.$modal.msgSuccess("Modify Successful");
              this.open = false;
              this.getList();
            });
          } else {
            addWmsInventoryMovementDetail(this.form).then(response => {
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
      this.$modal.confirm(' Do you want delete Inventory移动 DetailNo."' + ids + '"？').then(function() {
        return delWmsInventoryMovementDetail(ids);
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
        return exportWmsInventoryMovementDetail(queryParams);
      }).then(response => {
        this.$download.download(response);
        this.exportLoading = false;
      }).catch(() => {});
    }
  }
};
</script>
