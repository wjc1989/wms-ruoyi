<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="120px" size="medium"
      class="ry_form">
      <el-form-item label="Trade No." prop="transactionCode">
        <el-input v-model.trim="queryParams.transactionCode" placeholder="Please Input Trade No." clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="Trade Type" prop="transactionType">
        <DictRadio v-model="queryParams.transactionType" @change="handleQuery" size="small"
          :radioData="dict.type.wms_customer_transaction_type" :showAll="'all'" />
      </el-form-item>
      <el-form-item label="Create Time" prop="Time">
        <el-date-picker v-model="queryParams.Time" type="datetimerange" :picker-options="pickerOptions"
          range-separator=" To " size="small" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss"
          start-placeholder="Start time" end-placeholder="End time" :default-time="['00:00:00', '23:59:59']" align="right"
          @change="handleChange">
        </el-date-picker>
      </el-form-item>
      <el-form-item class="flex_one tr">
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">Search</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">Reset</el-button>
      </el-form-item>
    </el-form>

    <WmsTable v-loading="loading" :data="wmsCustomerTransactionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="Trade No." align="center" prop="transactionCode"  />
      <el-table-column label="User " align="center" prop="customerId" >
        <template slot-scope="scope">
          {{ getCustomerName(scope.row) }}
        </template>
      </el-table-column>
      <el-table-column label="Trade Type" align="center" prop="transactionType" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.wms_customer_transaction_type" :value="scope.row.transactionType" />
        </template>
      </el-table-column>
      <el-table-column label="Trade Amount" align="center" prop="transactionAmount"  >
        <template slot-scope="scope">
          <span style="color:red">{{scope.row.transactionType==11 ? "-":"+"}} {{scope.row.transactionAmount}}</span>
        </template>
      </el-table-column>
      <el-table-column label="Last balance" align="center" prop="previousBalance"  />
      <el-table-column label="Current balance" align="center" prop="currentBalance"  />
      <el-table-column label="Outbound No." align="center" prop="shipmentOrderId"  />
      <el-table-column label="Remark" align="center" prop="remark"  />
      <el-table-column label="Create Time" align="center" prop="createTime"  />
      <el-table-column label="Operate" align="center" class-name="small-padding fixed-width" >
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['wms:wmsCustomerTransaction:edit']">Modify</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['wms:wmsCustomerTransaction:remove']">Delete</el-button>
        </template>
      </el-table-column>
    </WmsTable>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- Add OrModifyCustom flow对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="50%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="108px" inline class="dialog-form-two">
        <el-form-item label="Trade No." prop="transactionCode">
          <el-input v-model="form.transactionCode" placeholder="Please Input Trade No." />
        </el-form-item>
        <el-form-item label="User No." prop="customerId">
          <el-input v-model="form.customerId" placeholder="Please Input User No." />
        </el-form-item>
        <el-form-item label="Trade Type" prop="transactionType">
          <el-select v-model="form.transactionType" placeholder="Please select Trade Type">
            <el-option label="Please select Trade Type" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="Trade Amount" prop="transactionAmount">
          <el-input v-model="form.transactionAmount" placeholder="Please Input Trade Amount" />
        </el-form-item>
        <el-form-item label="Last balance" prop="previousBalance">
          <el-input v-model="form.previousBalance" placeholder="Please Input Last balance" />
        </el-form-item>
        <el-form-item label="Current balance" prop="currentBalance">
          <el-input v-model="form.currentBalance" placeholder="Please Input Current balance" />
        </el-form-item>
        <el-form-item label="Outbound No." prop="shipmentOrderId">
          <el-input v-model="form.shipmentOrderId" placeholder="Please Input Outbound No." />
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
import { listWmsCustomerTransaction, getWmsCustomerTransaction, delWmsCustomerTransaction, addWmsCustomerTransaction, updateWmsCustomerTransaction, exportWmsCustomerTransaction } from "@/api/wms/customerTransaction";
import { mapGetters } from "vuex";

export default {
  name: "WmsCustomerTransaction",
  dicts: ["wms_customer_transaction_type"],
  computed: {
    ...mapGetters(['customerMap']),
  },
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
      // Custom flow表格Data
      wmsCustomerTransactionList: [],
      // 弹出层标题
      title: "",
      // Show弹出层
      open: false,
      // SearchParams
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        transactionCode: null,
        customerId: null,
        transactionType: null,
        transactionAmount: null,
        previousBalance: null,
        currentBalance: null,
        shipmentOrderId: null,
      },
      // 表Params
      form: {},
      // 表校验
      rules: {
        transactionCode: [
          { required: true, message: "Trade No. is required", trigger: "blur" }
        ],
        customerId: [
          { required: true, message: "User No. is required", trigger: "blur" }
        ],
        transactionType: [
          { required: true, message: "Trade Type  1：Paid  2：Payment  is required", trigger: "change" }
        ],
        transactionAmount: [
          { required: true, message: "Trade Amount is required", trigger: "blur" }
        ],
        previousBalance: [
          { required: true, message: "Last balance is required", trigger: "blur" }
        ],
        currentBalance: [
          { required: true, message: "Current balance is required", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "Create Time is required", trigger: "blur" }
        ],
        updateTime: [
          { required: true, message: "更新 Time is required", trigger: "blur" }
        ]
      },
      columns: [
        { key: 1, label: "Trade No.", visible: true },
        { key: 2, label: "User No.", visible: true },
        { key: 3, label: "Trade Type", visible: true },
        { key: 4, label: "Trade Amount", visible: true },
        { key: 5, label: "Last balance", visible: true },
        { key: 6, label: "Current balance", visible: true },
        { key: 7, label: "Outbound No.", visible: false },
        { key: 8, label: "Remark", visible: true },
        { key: 9, label: "Create Time", visible: true },
        { key: 10, label: "Operate", visible: false },
      ],
      pickerOptions: {
        shortcuts: [{
          text: 'Last week',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: 'Last month',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: 'Last three month',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
      customerId: null
    };
  },
  created() {
    const {id} = this.$route.query
    if (id) {
      this.customerId = id
      this.getList(id)
    }
  },
  methods: {
    getCustomerName(row) {
      return this.customerMap.get(Number(row.customerId))
    },
    /** SearchCustom flow列表 */
    getList() {
      if (this.queryParams.Time){
        this.queryParams.startTime = this.queryParams.Time[0]
        this.queryParams.endTime = this.queryParams.Time[1]
      }
      this.loading = true;
      const { pageNum, pageSize } = this.queryParams;
      const query = { ...this.queryParams, pageNum: undefined, pageSize: undefined,customerId:this.customerId };
      const pageReq = { page: pageNum - 1, size: pageSize };
      listWmsCustomerTransaction(query, pageReq).then(response => {
        const { content, totalElements } = response
        this.wmsCustomerTransactionList = content;
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
        transactionCode: null,
        customerId: null,
        transactionType: null,
        transactionAmount: null,
        previousBalance: null,
        currentBalance: null,
        shipmentOrderId: null,
        remark: null,
        createTime: null,
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
      getWmsCustomerTransaction(id).then(response => {
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
            updateWmsCustomerTransaction(this.form).then(response => {
              this.$modal.msgSuccess("Modify Successful");
              this.open = false;
              this.getList();
            });
          } else {
            addWmsCustomerTransaction(this.form).then(response => {
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
      this.$modal.confirm(' Do you want delete Custom flowNo."' + ids + '"？').then(function () {
        return delWmsCustomerTransaction(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Delete Successful");
      }).catch(() => { });
    },
    /** ExportButtonOperate */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal.confirm('Export All Custom flow？').then(() => {
        this.exportLoading = true;
        return exportWmsCustomerTransaction(queryParams);
      }).then(response => {
        this.$download.download(response);
        this.exportLoading = false;
      }).catch(() => { });
    },
    handleChange(value) {
      if (!value) {
        this.queryParams.startTime = null;
        this.queryParams.endTime = null;
      }
    }
  }
};
</script>
