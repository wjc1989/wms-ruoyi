<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="120px"
      size="medium"
      class="ry_form"
    >
      <el-form-item label="SettlementNo." prop="inventorySettlementNo">
        <el-input
          v-model="queryParams.inventorySettlementNo"
          placeholder="Please Input SettlementNo."
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="SettlementStatus" prop="inventorySettlementStatus">
        <DictRadio
          v-model="queryParams.inventorySettlementStatus"
          @change="handleQuery"
          size="small"
          :radioData="dict.type.wms_settlement_status"
          :showAll="'all'"
        />
      </el-form-item>
      <!--      <el-form-item label="SettlementType" prop="settlementType">
              <DictRadio
                v-model="queryParams.settlementType"
                @change="handleQuery"
                size="small"
                :radioData="dict.type.wms_settlement_type"
                :showAll="'all'"
              />
            </el-form-item>-->

      <el-form-item class="flex_one tr">
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
        >Search
        </el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
        >Reset
        </el-button
        >
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
          v-hasPermi="['wms:wmsInventorySettlement:add']"
        >New
        </el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['wms:wmsInventorySettlement:export']"
        >Export
        </el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"

      ></right-toolbar>
    </el-row>

    <WmsTable
      v-loading="loading"
      :data="wmsInventorySettlementList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column
        label="SettlementNo."
        align="center"
        prop="inventorySettlementNo"

      />
      <el-table-column
        label="Settlement Cycle"
        align="center"

      >
        <template slot-scope="scope">
          <span>{{ formatInventorySettlementPeriod(scope.row) }}</span>
        </template>

      </el-table-column>
      <el-table-column label="SettlementStatus" align="center" prop="inventorySettlementStatus" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.wms_settlement_status" :value="scope.row.inventorySettlementStatus"/>
        </template>
      </el-table-column>
      <!--      <el-table-column
              label="SettlementType"
              align="center"
              prop="settlementType"

            />-->
      <el-table-column
        label="Remark"
        align="center"
        prop="remark"

      />
      <el-table-column
        label="Operate"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleView(scope.row)"
            v-hasPermi="['wms:wmsInventorySettlement:edit']"
          >View
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-if="11 === scope.row.inventorySettlementStatus"
            v-hasPermi="['wms:wmsInventorySettlement:edit']"
          >继续Settlement
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            v-if="11 === scope.row.inventorySettlementStatus"
            @click="handleDelete(scope.row)"
            v-hasPermi="['wms:wmsInventorySettlement:remove']"
          >Delete
          </el-button>
        </template>
      </el-table-column>
    </WmsTable>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

  </div>
</template>

<script>
import {
  listWmsInventorySettlement,
  delWmsInventorySettlement,
  exportWmsInventorySettlement,
} from "@/api/wms/inventorySettlement";
import moment from "moment";

export default {
  name: "WmsInventorySettlement",
  dicts: ["wms_settlement_status", "wms_settlement_type"],
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
      // InventorySettlement表格Data
      wmsInventorySettlementList: [],
      // SearchParams
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        inventorySettlementStatus: null,
        settlementType: null,
        inventorySettlementNo: null,
      },
      // 表Params
      form: {},
      columns: [
        {key: 9, label: "SettlementNo.", visible: true},
        {key: 1, label: "SettlementStatus", visible: true},
        {key: 2, label: "SettlementType", visible: true},
        {key: 4, label: "Remark", visible: true},
      ],
    };
  },
  created() {
    const {settlementType} = this.$route.query;
    if (Number(settlementType) === 1 || Number(settlementType) === 2) {
      this.queryParams.settlementType = settlementType
      this.getList();
    } else {
      this.$router.push({path: this.redirect || "/"}).catch(() => {
      });
    }
  },
  methods: {
    formatInventorySettlementPeriod(row) {
      if (row.inventorySettlementStartTime) {
        return moment(row.inventorySettlementStartTime).format('YYYY-MM-DD') + "-" +
          moment(row.inventorySettlementEndTime).format('YYYY-MM-DD')
      }
      return ''
    },
    /** SearchInventorySettlement列表 */
    getList() {
      this.loading = true;
      const {pageNum, pageSize} = this.queryParams;
      const query = {
        ...this.queryParams,
        pageNum: undefined,
        pageSize: undefined,
      };
      const pageReq = {page: pageNum - 1, size: pageSize};
      listWmsInventorySettlement(query, pageReq).then((response) => {
        const {content, totalElements} = response;
        this.wmsInventorySettlementList = content;
        this.total = totalElements;
        this.loading = false;
      });
    },
    // 表Reset
    reset() {
      this.form = {
        id: null,
        inventorySettlementStatus: 0,
        settlementType: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        inventorySettlementNo: null,
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
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** ViewButtonOperate */
    handleView(row) {
      const id = row.id || this.ids
      this.$router.push({path: 'inventorySettlement/status', query: {id}})
    },
    /** AddButtonOperate */
    handleAdd() {
      const settlementType = this.queryParams.settlementType
      this.$router.push({path: "inventorySettlement/edit", query: {settlementType}});
    },
    /** ModifyButtonOperate */
    handleUpdate(row) {
      const id = row.id || this.ids
      this.$router.push({path: 'inventorySettlement/edit', query: {id}})
    },
    /** DeleteButtonOperate */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm(' Do you want delete InventorySettlementNo."' + ids + '"？')
        .then(function () {
          return delWmsInventorySettlement(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("Delete Successful");
        })
        .catch(() => {
        });
    },
    /** ExportButtonOperate */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal
        .confirm("Export All？")
        .then(() => {
          this.exportLoading = true;
          return exportWmsInventorySettlement(queryParams);
        })
        .then((response) => {
          this.$download.download(response);
          this.exportLoading = false;
        })
        .catch(() => {
        });
    },
  },
};
</script>
