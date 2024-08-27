<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="120px" size="medium"
      class="ry_form">
      <el-form-item label="No." prop="supplierNo">
        <el-input v-model="queryParams.supplierNo" placeholder="Please Input No." clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="Supplier Name" prop="supplierName">
        <el-input v-model="queryParams.supplierName" placeholder="Please Input Supplier Name" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="Address" prop="address">
        <el-input v-model="queryParams.address" placeholder="Please Input Address" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="Phone" prop="mobileNo">
        <el-input v-model="queryParams.mobileNo" placeholder="Please Input Phone" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>

      <el-form-item label="Contacts" prop="contact">
        <el-input v-model="queryParams.contact" placeholder="Please Input Contacts" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="Leave" prop="level">
        <el-select v-model="queryParams.level" placeholder="Please select Leave" clearable size="small" @change="handleQuery">
          <el-option v-for="dict in dict.type.wms_supplier_level" :key="dict.value" :label="dict.label"
            :value="dict.value"></el-option>
        </el-select>
      </el-form-item>
      <template v-if="showMoreCondition">
        <el-form-item label="Email" prop="email">
          <el-input v-model="queryParams.email" placeholder="Please Input Email" clearable size="small"
            @keyup.enter.native="handleQuery" />
        </el-form-item>
      </template>
      <el-form-item class="flex_one tr">
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">Search</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">Reset</el-button>
        <el-button :icon="showMoreCondition ? 'el-icon-arrow-up' : 'el-icon-arrow-down'" size="mini"
          @click="showMoreCondition = !showMoreCondition">{{ showMoreCondition ? 'Collapse' : 'Expand' }}</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['wms:supplier:add']">New</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['wms:supplier:edit']">Modify</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['wms:supplier:remove']">Delete</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" :loading="exportLoading" @click="handleExport"
          v-hasPermi="['wms:supplier:export']">Export</el-button>
      </el-col> -->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <WmsTable v-loading="loading" :data="wmsSupplierList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="No." align="center" prop="supplierNo" v-if="columns[0].visible" />
      <el-table-column label="Supplier Name" align="center" prop="supplierName" v-if="columns[1].visible" />
      <el-table-column label="Payment" align="center" class-name="small-padding fixed-width" v-if="columns[2].visible">

        <template slot-scope="scope">
          <p> {{ scope.row.payableAmount }}</p>
          <el-button size="mini" type="text" @click="handleEnter(scope.row)">+Payment
          </el-button>
          <el-button size="mini" type="text" @click="handleExit(scope.row)">-Paid
          </el-button>
          <el-button size="mini" type="text" @click="handleDetail(scope.row)">Transcation
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="Address" align="center" prop="address" v-if="columns[3].visible" />
      <el-table-column label="Phone" align="center" prop="mobileNo" v-if="columns[4].visible" />
       <el-table-column label="Contacts" align="center" prop="contact" v-if="columns[5].visible" />
      <el-table-column label="Leave" align="center" prop="level" v-if="columns[6].visible" />
      <el-table-column label="Email" align="center" prop="email" v-if="columns[7].visible" />
      <el-table-column label="Remark" align="center" prop="remark" v-if="columns[8].visible" />
      <el-table-column label="Operate" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['wms:supplier:edit']">Modify</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['wms:supplier:remove']">Delete</el-button>
        </template>
      </el-table-column>
    </WmsTable>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- Add OrModifySupplier对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="50%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="128px" inline class="dialog-form-two">
        <el-form-item label="No." prop="supplierNo">
          <el-input v-model="form.supplierNo" placeholder="Please Input No." />
        </el-form-item>
        <el-form-item label="Supplier Name" prop="supplierName">
          <el-input v-model="form.supplierName" placeholder="Please Input Supplier Name" />
        </el-form-item>
        <el-form-item label="Address" prop="address">
          <el-input v-model="form.address" placeholder="Please Input Address" />
        </el-form-item>
        <el-form-item label="Phone" prop="mobileNo">
          <el-input v-model="form.mobileNo" placeholder="Please Input Phone" />
        </el-form-item>
        <el-form-item label="Bank Name." prop="bankName">
          <el-input v-model="form.bankName" placeholder="Please Input Bank Name." />
        </el-form-item>
        <el-form-item label="Bank No." prop="bankAccount">
          <el-input v-model="form.bankAccount" placeholder="Please Input Bank No." />
        </el-form-item>

        <el-form-item label="Contacts" prop="contact">
          <el-input v-model="form.contact" placeholder="Please Input Contacts" />
        </el-form-item>
        <el-form-item label="Leave" prop="level">
          <el-select v-model="form.level" placeholder="Please select Leave">
            <el-option v-for="dict in dict.type.wms_supplier_level" :key="dict.value" :label="dict.label"
              :value="dict.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="Email" prop="email">
          <el-input v-model="form.email" placeholder="Please Input Email" />
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

    <!-- 应付款 Add/结账 对话框 -->
    <el-dialog :title="duePayForm.title" :visible.sync="duePayForm.open" width="600px" append-to-body>
      <el-form ref="duePayForm" :model="duePayForm.form" :rules="duePayForm.rules" label-width="108px">

        <el-form-item label="Amount" prop="transactionAmount">
          <el-input-number v-model="duePayForm.form.transactionAmount" :precision="2" :min="0"
            label="Please Input Amount"></el-input-number>
        </el-form-item>
        <el-form-item label="Remark" prop="remark">
          <el-input v-model="duePayForm.form.remark" placeholder="Please Input Remark" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitDuePayForm">OK</el-button>
        <el-button @click="cancelDuePayForm">Cancel</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listWmsSupplier, getWmsSupplier, delWmsSupplier, addWmsSupplier, updateWmsSupplier, exportWmsSupplier } from "@/api/wms/supplier";
import {randomId} from "@/utils/RandomUtils";
import {addWmsSupplierTransaction} from "@/api/wms/supplierTransaction";

/* 银行卡校验 */
var isbankAccount = (rule, value, callback) => {
  const strBin =
    "10,18,30,35,37,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,58,60,62,65,68,69,84,87,88,94,95,98,99";
  if (!value) {
    return callback(new Error("Accounts is required"));
  } else if (!Number.isInteger(+value)) {
    callback(new Error("Bank No. is Invalid."));
  } else{
    callback();
  }
};

export default {
  name: "WmsSupplier",
  dicts: ['wms_supplier_level'],
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
      // Supplier表格Data
      wmsSupplierList: [],
      // 弹出层标题
      title: "",
      // Show弹出层
      open: false,
      // SearchParams
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        supplierNo: null,
        supplierName: null,
        address: null,
        mobileNo: null,
        telNo: null,
        contact: null,
        level: null,
        email: null,
      },
      // 表Params
      form: {},
      // 表校验
      rules: {
        supplierNo: [
          { required: true, message: "No. is required", trigger: "blur" }
        ],
        supplierName: [
          { required: true, message: "Supplier Name is required", trigger: "blur" }
        ],
        bankAccount: [{ validator: isbankAccount, trigger: "blur" },]
      },
      columns: [
        { key: 1, label: "No.", visible: true },
        { key: 2, label: "Supplier Name", visible: true },
        { key: 3, label: "Payment", visible: true },
        { key: 4, label: "Address", visible: false },
        { key: 5, label: "Phone", visible: false },
         { key: 7, label: "Contacts", visible: true },
        { key: 8, label: "Leave", visible: true },
        { key: 9, label: "Email", visible: false },
        { key: 10, label: "Remark", visible: true },
      ],
      showMoreCondition: false,
      duePayForm: {
        // 弹出层标题
        title: "",
        // Show弹出层
        open: false,
        form: {}
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** SearchSupplier列表 */
    getList() {
      this.loading = true;
      const { pageNum, pageSize } = this.queryParams;
      const query = { ...this.queryParams, pageNum: undefined, pageSize: undefined };
      const pageReq = { page: pageNum - 1, size: pageSize };
      listWmsSupplier(query, pageReq).then(response => {
        const { content, totalElements } = response
        this.wmsSupplierList = content;
        this.total = totalElements;
        this.loading = false;
      });
    },
    /** 应付款 CancelButton */
    cancelDuePayForm() {
      this.duePayForm.open = false
    },
    /** 应付款 提交Button */
    submitDuePayForm() {
      this.$refs["duePayForm"].validate(valid => {
        if (valid) {
          addWmsSupplierTransaction(this.duePayForm.form).then(res => {
            this.duePayForm.open = false
            this.$store.dispatch('wms/getSuppliers')
            this.$modal.msgSuccess("Modify Successful");
            this.getList();

          })

        }
      })

    },
    /** Transcation */
    handleDetail(row) {
      const id = row.id || this.ids
      this.$router.push({ path: '/relation/supplierTransaction', query: { id } })
    },
    /* Add 应付款 */
    handleEnter(row) {
      this.duePayForm.title = 'Add应付款'
      this.duePayForm.open = true

      this.resetDueForm();
      this.duePayForm.form.supplierId = row.id
      this.duePayForm.form.transactionType = '22'

    },
    resetDueForm() {
      this.duePayForm.form = {
        transactionCode: 'TS-' + randomId(),
        transactionAmount: 0.00,
        remark: null
      }
    },
    /* Paid */
    handleExit(row) {
      this.duePayForm.title = 'Paid'
      this.duePayForm.open = true

      this.resetDueForm();
      this.duePayForm.form.transactionType = '11'
      this.duePayForm.form.supplierId = row.id

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
        supplierNo: null,
        supplierName: null,
        address: null,
        mobileNo: null,
        telNo: null,
        contact: null,
        level: null,
        email: null,
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
      getWmsSupplier(id).then(response => {
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
            updateWmsSupplier(this.form).then(response => {
              this.$store.dispatch('wms/getSuppliers')
              this.$modal.msgSuccess("Modify Successful");
              this.open = false;
              this.getList();
            });
          } else {
            addWmsSupplier(this.form).then(response => {
              this.$store.dispatch('wms/getSuppliers')
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
      this.$modal.confirm(' Do you want delete SupplierNo."' + ids + '"？').then(function () {
        return delWmsSupplier(ids);
      }).then(() => {
        this.$store.dispatch('wms/getSuppliers')
        this.getList();
        this.$modal.msgSuccess("Delete Successful");
      }).catch(() => { });
    },
    /** ExportButtonOperate */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal.confirm('Export All Supplier？').then(() => {
        this.exportLoading = true;
        return exportWmsSupplier(queryParams);
      }).then(response => {
        this.$download.download(response);
        this.exportLoading = false;
      }).catch(() => { });
    }
  }
};
</script>
