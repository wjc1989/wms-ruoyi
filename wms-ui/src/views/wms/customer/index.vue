<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="120px" size="medium"
      class="ry_form">
      <el-form-item label="No." prop="customerNo">
        <el-input v-model.trim="queryParams.customerNo" placeholder="Please Input No." clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="Name" prop="customerName">
        <el-input v-model.trim="queryParams.customerName" placeholder="Please Input Name" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="Address" prop="address">
        <el-input v-model.trim="queryParams.address" placeholder="Please Input Address" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="Phone" prop="mobile">
        <el-input v-model.trim="queryParams.mobile" placeholder="Please Input Phone" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>

      <el-form-item label="Contacts" prop="customerPerson">
        <el-input v-model.trim="queryParams.customerPerson" placeholder="Please Input Contacts" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="Leave" prop="customerLevel">
        <el-input v-model.trim="queryParams.customerLevel" placeholder="Please Input Leave" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <template v-if="showMoreCondition">
        <el-form-item label="Email" prop="email">
          <el-input v-model.trim="queryParams.email" placeholder="Please Input Email" clearable size="small"
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
          v-hasPermi="['wms:customer:add']">New</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" ></right-toolbar>
    </el-row>

    <WmsTable v-loading="loading" :data="wmsCustomerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="No." align="center" prop="customerNo"  />
      <el-table-column label="Name" align="center" prop="customerName"  />
      <el-table-column label="Receivable" align="center" class-name="small-padding fixed-width" >
        <template slot-scope="scope">
          <p> {{ scope.row.receivableAmount }}</p>
          <el-button size="mini" type="text" @click="handleEnter(scope.row)">+应收
          </el-button>
          <el-button size="mini" type="text" @click="handleExit(scope.row)">-Paid
          </el-button>
          <el-button size="mini" type="text" @click="handleDetail(scope.row)">Transcation
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="Address" align="center" prop="address"  />
      <el-table-column label="Phone" align="center" prop="mobile"  />
       <el-table-column label="Contacts" align="center" prop="customerPerson"  />
      <el-table-column label="Leave" align="center" prop="customerLevel"  />
      <el-table-column label="Email" align="center" prop="email"  />
      <el-table-column label="Remark" align="center" prop="remark"  />
      <el-table-column label="Operate" align="center" class-name="small-padding fixed-width" >
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['wms:customer:edit']">Modify</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['wms:customer:remove']">Delete</el-button>
        </template>
      </el-table-column>
    </WmsTable>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- Add OrModifyCustom对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="50%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="108px" inline class="dialog-form-two">
        <el-form-item label="No." prop="customerNo">
          <el-input v-model="form.customerNo" placeholder="Please Input No." />
        </el-form-item>
        <el-form-item label="Name" prop="customerName">
          <el-input v-model="form.customerName" placeholder="Please Input Name" />
        </el-form-item>
        <el-form-item label="Address" prop="address">
          <el-input v-model="form.address" placeholder="Please Input Address" />
        </el-form-item>
        <el-form-item label="Phone" prop="mobile">
          <el-input v-model="form.mobile" placeholder="Please Input Phone" />
        </el-form-item>
        <el-form-item label="Bank Name." prop="bankName">
          <el-input v-model="form.bankName" placeholder="Please Input Bank Name." />
        </el-form-item>
        <el-form-item label="Bank No." prop="bankAccount">
          <el-input v-model="form.bankAccount" placeholder="Please Input Bank No." />
        </el-form-item>

        <el-form-item label="Contacts" prop="customerPerson">
          <el-input v-model="form.customerPerson" placeholder="Please Input Contacts" />
        </el-form-item>
        <el-form-item label="Leave" prop="customerLevel">
          <el-input v-model="form.customerLevel" placeholder="Please Input Leave" />
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
import { listWmsCustomer, getWmsCustomer, delWmsCustomer, addWmsCustomer, updateWmsCustomer, exportWmsCustomer } from "@/api/wms/customer";
import {randomId} from "@/utils/RandomUtils";
import {addWmsCustomerTransaction} from "@/api/wms/customerTransaction";

/* 银行卡校验 */
var isbankAccount = (rule, value, callback) => {
  const strBin =
    "10,18,30,35,37,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,58,60,62,65,68,69,84,87,88,94,95,98,99";
  if (!value) {
    return callback(new Error("Accounts is required"));
  } else if (!Number.isInteger(+value)) {
    callback(new Error("Bank No. Incorrect format"));
  } else if (value.trim().length < 12 || value.trim().length > 19) {
    callback(new Error("Bank No. Length must be between 12-19."));
  } else {
    callback();
  }
};

export default {
  name: "WmsCustomer",
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
      // Custom表格Data
      wmsCustomerList: [],
      // 弹出层标题
      title: "",
      // Show弹出层
      open: false,
      // SearchParams
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        customerNo: null,
        customerName: null,
        address: null,
        mobile: null,
        tel: null,
        customerPerson: null,
        customerLevel: null,
        email: null,
      },
      // 表Params
      form: {},
      // 表校验
      rules: {
        customerNo: [
          { required: true, message: "No. is required", trigger: "blur" }
        ],
        customerName: [
          { required: true, message: "Name is required", trigger: "blur" }
        ],
        bankAccount: [{ validator: isbankAccount, trigger: "blur" },]
      },
      columns: [
        { key: 1, label: "No.", visible: true },
        { key: 2, label: "Name", visible: true },
        { key: 3, label: "Receivable", visible: true },
        { key: 4, label: "Address", visible: false },
        { key: 5, label: "Phone", visible: false },
         { key: 7, label: "Contacts", visible: true },
        { key: 8, label: "Leave", visible: true },
        { key: 9, label: "Email", visible: false },
        { key: 10, label: "Remark", visible: true },
        { key: 11, label: "Operate", visible: true },
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
    /** SearchCustom列表 */
    getList() {
      this.loading = true;
      const { pageNum, pageSize } = this.queryParams;
      const query = { ...this.queryParams, pageNum: undefined, pageSize: undefined };
      const pageReq = { page: pageNum - 1, size: pageSize };
      listWmsCustomer(query, pageReq).then(response => {
        const { content, totalElements } = response
        this.wmsCustomerList = content;
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
          addWmsCustomerTransaction(this.duePayForm.form).then(res => {
            this.duePayForm.open = false
            this.$store.dispatch('wms/getSuppliers')
            this.$modal.msgSuccess("Modify Successful");
            this.getList();

          })

        }
      })

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
        customerNo: null,
        customerName: null,
        address: null,
        mobile: null,
        tel: null,
        customerPerson: null,
        customerLevel: null,
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
      getWmsCustomer(id).then(response => {
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
            updateWmsCustomer(this.form).then(response => {
              this.$modal.msgSuccess("Modify Successful");
              this.open = false;
              this.getList();
              this.$store.dispatch('wms/getCustomer')
            });
          } else {
            addWmsCustomer(this.form).then(response => {
              this.$modal.msgSuccess("Add Successful");
              this.open = false;
              this.getList();
              this.$store.dispatch('wms/getCustomer')
            });
          }
        }
      });
    },
    /** DeleteButtonOperate */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm(' Do you want delete CustomNo."' + ids + '"？').then(function () {
        return delWmsCustomer(ids);
      }).then(() => {
        this.getList();
        this.$store.dispatch('wms/getCustomer')
        this.$modal.msgSuccess("Delete Successful");
      }).catch(() => { });
    },
    /** ExportButtonOperate */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal.confirm('Export AllCustom？').then(() => {
        this.exportLoading = true;
        return exportWmsCustomer(queryParams);
      }).then(response => {
        this.$download.download(response);
        this.exportLoading = false;
      }).catch(() => { });
    },
    /** Transcation */
    handleDetail(row) {
      const id = row.id || this.ids
      this.$router.push({ path: '/relation/customerTransaction', query: { id } })
    },
    /* Add 应付款 */
    handleEnter(row) {
      this.duePayForm.title = 'Add应收款'
      this.duePayForm.open = true

      this.resetDueForm();
      this.duePayForm.form.customerId = row.id
      this.duePayForm.form.transactionType = '22'

    }, resetDueForm() {
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
      this.duePayForm.form.customerId = row.id

    },
  }
};
</script>
