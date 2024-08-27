<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="120px" size="medium"
             class="ry_form">
      <el-form-item label="Outbound No." prop="shipmentOrderId">
        <el-input
          v-model.trim="queryParams.shipmentOrderId"
          placeholder="Please Input Outbound No."
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label=" Carrier" prop="carrierId">

        <WmsCarrierSelect v-model="queryParams.carrierId"></WmsCarrierSelect>

      </el-form-item>
      <el-form-item label="Outbound Date" prop="deliveryDate">
        <el-date-picker
          clearable
          size="small"
          v-model="queryParams.deliveryDate"
          type="datetime"
          value-format="yyyy-MM-ddTHH:mm:ss"
          placeholder="Select Outbound Date">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="Tracking No." prop="trackingNo">
        <el-input
          v-model.trim="queryParams.trackingNo"
          placeholder="Please Input Tracking No."
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
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" ></right-toolbar>
    </el-row>

    <WmsTable v-loading="loading" :data="wmsDeliveryList" @selection-change="handleSelectionChange">
      <el-table-column label="Outbound " align="center" prop="shipmentOrderId" >
        <template slot-scope="scope">
          <el-button size="mini"
                     type="text"
                     @click.stop="handleView(scope.row)">{{ 'ViewOutbound ' }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label=" Carrier" align="center" prop="carrierId" :formatter="getCarrier"
                       />
      <el-table-column label="Outbound Date" align="center" prop="deliveryDate" width="180" >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.deliveryDate, '') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Tracking No." align="center" prop="trackingNo" >
        <!--        https://www.kuaidi100.com/chaxun?com=[]&nu=[]-->
        <template slot-scope="scope">
          <a
            target="_blank"
            :href=" 'https://www.kuaidi100.com/chaxun?com='+getCarrier(scope.row)+'&nu='+scope.row.trackingNo">{{
              scope.row.trackingNo
            }}
          </a>
        </template>
      </el-table-column>
      <el-table-column label="Remark" align="center" prop="remark" />
    </WmsTable>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- Add OrModifyOutbound Record对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="50%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="108px" inline class="dialog-form-two">
        <el-form-item label="Outbound No." prop="shipmentOrderId">
          <el-input v-model="form.shipmentOrderId" placeholder="Please Input Outbound No."/>
        </el-form-item>
        <el-form-item label=" CarrierId" prop="carrierId">
          <el-input v-model="form.carrierId" placeholder="Please Input  CarrierId"/>
        </el-form-item>
        <el-form-item label="Outbound Date" prop="deliveryDate">
          <el-date-picker clearable size="small"
                          v-model="form.deliveryDate"
                          type="datetime"
                          value-format="yyyy-MM-ddTHH:mm:ss"
                          placeholder="Select Outbound Date">
          </el-date-picker>
        </el-form-item>
        1
        <el-form-item label="Tracking No." prop="trackingNo">
          <el-input v-model="form.trackingNo" placeholder="Please Input Tracking No."/>
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
  addWmsDelivery,
  delWmsDelivery,
  exportWmsDelivery,
  getWmsDelivery,
  listWmsDelivery,
  updateWmsDelivery
} from "@/api/wms/delivery";
import {mapGetters} from "vuex";

export default {
  name: "WmsDelivery",
  computed: {
    ...mapGetters(['carrierMap']),
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
      // Outbound Record表格Data
      wmsDeliveryList: [],
      // 弹出层标题
      title: "",
      // Show弹出层
      open: false,
      // SearchParams
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        shipmentOrderId: null,
        carrierId: null,
        deliveryDate: null,
        trackingNo: null,
      },
      // 表Params
      form: {},
      // 表校验
      rules: {},
      columns: [
        {key: 1, label: "Outbound No.", visible: true},
        {key: 2, label: "CarrierId", visible: true},
        {key: 3, label: "Outbound Date", visible: true},
        {key: 4, label: "Tracking No.", visible: true},
        {key: 5, label: "Remark", visible: true},
      ],
    };
  },
  created() {
    this.getList();
    // this.$message.warning('该功能正在开发中...')
  },
  methods: {
    //ViewOutbound
    handleView(row) {
      const id = row.shipmentOrderId
      this.$router.push({path: '/wms/shipmentOrder/status', query: {id}})
    },
    // 格式化 Carrier
    getCarrier(row, column) {
      return this.carrierMap.get(row.carrierId)
    },
    /** SearchOutbound Record列表 */
    getList() {
      this.loading = true;
      const {pageNum, pageSize} = this.queryParams;
      const query = {...this.queryParams, pageNum: undefined, pageSize: undefined};
      const pageReq = {page: pageNum - 1, size: pageSize};
      listWmsDelivery(query, pageReq).then(response => {
        const {content, totalElements} = response
        this.wmsDeliveryList = content;
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
        shipmentOrderId: null,
        carrierId: null,
        deliveryDate: null,
        trackingNo: null,
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
      getWmsDelivery(id).then(response => {
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
            updateWmsDelivery(this.form).then(response => {
              this.$modal.msgSuccess("Modify Successful");
              this.open = false;
              this.getList();
            });
          } else {
            addWmsDelivery(this.form).then(response => {
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
      this.$modal.confirm(' Do you want delete Outbound RecordNo."' + ids + '"？').then(function () {
        return delWmsDelivery(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Delete Successful");
      }).catch(() => {
      });
    },
    /** ExportButtonOperate */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal.confirm('Export AllOutbound Record？').then(() => {
        this.exportLoading = true;
        return exportWmsDelivery(queryParams);
      }).then(response => {
        this.$download.download(response);
        this.exportLoading = false;
      }).catch(() => {
      });
    }
  }
};
</script>
