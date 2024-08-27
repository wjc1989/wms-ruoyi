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
      <el-form-item label="No." prop="areaNo">
        <el-input
          v-model.trim="queryParams.areaNo"
          placeholder="Please Input No."
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Area Name" prop="areaName">
        <el-input
          v-model.trim="queryParams.areaName"
          placeholder="Please Input Area Name"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Warehouse" prop="warehouseId">
        <el-input
          v-model.trim="queryParams.warehouseId"
          placeholder="Please Input Warehouse"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item class="flex_one tr">
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >Search</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >Reset</el-button
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
          v-hasPermi="['wms:area:add']"
          >New</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['wms:area:edit']"
          >Modify</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['wms:area:remove']"
          >Delete</el-button
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
          v-hasPermi="['wms:area:export']"
          >Export</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"

      ></right-toolbar>
    </el-row>

    <WmsTable
      v-loading="loading"
      :data="wmsAreaList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column
        label="No."
        align="center"
        prop="areaNo"

      />
      <el-table-column
        label="Area Name"
        align="center"
        prop="areaName"

      />
      <el-table-column
        label="Warehouse"
        align="center"
        prop="warehouseName"

      />
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
            @click.stop="handleUpdate(scope.row)"
            v-hasPermi="['wms:area:edit']"
            >Modify</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['wms:area:remove']"
            >Delete</el-button
          >
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

    <!-- Add OrModifyArea对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="50%" append-to-body>
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="108px"
        inline
        class="dialog-form-two"
      >
        <el-form-item label="No." prop="areaNo">
          <el-input v-model="form.areaNo" placeholder="Please Input No." />
        </el-form-item>
        <el-form-item label="Area Name" prop="areaName">
          <el-input v-model="form.areaName" placeholder="Please Input Area Name" />
        </el-form-item>
        <el-form-item label="Warehouse" prop="warehouseId">
          <el-select
            v-model="form.warehouseId"
            placeholder="Please select warehouse"
            clearable
            size="small"
          >
            <el-option
              v-for="item in warehouseList"
              :key="item.id"
              :label="item.warehouseName"
              :value="item.id"
            >
            </el-option>
          </el-select>
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
import {
  listWmsArea,
  getWmsArea,
  delWmsArea,
  addWmsArea,
  updateWmsArea,
  exportWmsArea,
} from "@/api/wms/area";
import { mapGetters } from 'vuex';
export default {
  name: "WmsArea",
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
      // Area表格Data
      wmsAreaList: [],
      // 弹出层标题
      title: "",
      // Show弹出层
      open: false,
      // SearchParams
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        areaNo: null,
        areaName: null,
        warehouseId: null,
      },
      // 表Params
      form: {},
      // 表校验
      rules: {
        areaNo: [{ required: true, message: "No. is required", trigger: "blur" }],
        areaName: [
          { required: true, message: "Area Name is required", trigger: "blur" },
        ],
        warehouseId: [
          { required: true, message: "Warehouse is required", trigger: "blur" },
        ],
      },
      columns: [
        { key: 1, label: "No.", visible: true },
        { key: 2, label: "Item", visible: true },
        { key: 3, label: "Warehouse", visible: true },
        { key: 4, label: "Remark", visible: true },
      ],
    };
  },
  computed:{
    ...mapGetters(['warehouseMap','warehouseList'])
  },
  created() {
    this.getList();
  },
  methods: {
    /** SearchArea列表 */
    getList() {
      this.loading = true;
      const { pageNum, pageSize } = this.queryParams;
      const query = {
        ...this.queryParams,
        pageNum: undefined,
        pageSize: undefined,
      };
      const pageReq = { page: pageNum - 1, size: pageSize };
      listWmsArea(query, pageReq).then((response) => {
        const { content, totalElements } = response;
        content.forEach((item) => {
          item.warehouseName = this.warehouseMap.get(item.warehouseId);
        });
        this.wmsAreaList = content;
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
        areaNo: null,
        areaName: null,
        warehouseId: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
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
    /** AddButtonOperate */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "New Area";
    },
    /** ModifyButtonOperate */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getWmsArea(id).then((response) => {
        this.form = response;
        this.open = true;
        this.title = "Modify Area";
      });
    },
    /** 提交Button */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateWmsArea(this.form).then((response) => {
              this.$store.dispatch("wms/getAreaList");
              this.$modal.msgSuccess("Modify Successful");
              this.open = false;
              this.getList();
            });
          } else {
            addWmsArea(this.form).then((response) => {
              this.$store.dispatch("wms/getAreaList");
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
      this.$modal
        .confirm(' Do you want delete AreaNo."' + ids + '"？')
        .then(function () {
          return delWmsArea(ids);
        })
        .then(() => {
          this.getList();
          this.$store.dispatch("wms/getAreaList");
          this.$modal.msgSuccess("Delete Successful");
        })
        .catch(() => {});
    },
    /** ExportButtonOperate */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal
        .confirm("Export AllArea？")
        .then(() => {
          this.exportLoading = true;
          return exportWmsArea(queryParams);
        })
        .then((response) => {
          this.$download.download(response);
          this.exportLoading = false;
        })
        .catch(() => {});
    },
  },
};
</script>
