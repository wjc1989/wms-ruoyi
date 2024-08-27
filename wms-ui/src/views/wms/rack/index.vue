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
      <el-form-item label="No." prop="rackNo">
        <el-input
          v-model="queryParams.rackNo"
          placeholder="Please Input Shelf No."
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Shelf Name" prop="rackName">
        <el-input
          v-model="queryParams.rackName"
          placeholder="Please Input Shelf Name"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Area" prop="areaId">
        <el-input
          v-model="queryParams.areaId"
          placeholder="Please Input Area"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Warehouse" prop="warehouseId">
        <el-input
          v-model="queryParams.warehouseId"
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
          v-hasPermi="['wms:rack:add']"
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
          v-hasPermi="['wms:rack:edit']"
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
          v-hasPermi="['wms:rack:remove']"
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
          v-hasPermi="['wms:rack:export']"
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
      :data="wmsRackList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column
        label="No."
        align="center"
        prop="rackNo"

      />
      <el-table-column
        label="Shelf Name"
        align="center"
        prop="rackName"

      />
      <el-table-column
        label="Area"
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
            v-hasPermi="['wms:rack:edit']"
            >Modify</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['wms:rack:remove']"
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

    <!-- Add OrModify Rack对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="50%" append-to-body>
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="108px"
        inline
        class="dialog-form-two"
      >
        <el-form-item label="No." prop="rackNo">
          <el-input v-model="form.rackNo" placeholder="Please Input No." />
        </el-form-item>

        <el-form-item label="Shelf Name" prop="rackName">
          <el-input v-model="form.rackName" placeholder="Please Input Shelf Name" />
        </el-form-item>

        <el-form-item label="Warehouse" prop="place">
          <WmsAreaCascader v-model="form.place" size="small"></WmsAreaCascader>
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
import WmsAreaCascader from "@/components/WmsAreaCascader";
import {
  listWmsRack,
  getWmsRack,
  delWmsRack,
  addWmsRack,
  updateWmsRack,
  exportWmsRack,
} from "@/api/wms/rack";
import { mapGetters } from "vuex";
export default {
  name: "WmsRack",
  components: {
    WmsAreaCascader,
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
      //  Rack表格Data
      wmsRackList: [],
      // 弹出层标题
      title: "",
      // Show弹出层
      open: false,
      // SearchParams
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        rackNo: null,
        rackName: null,
        areaId: null,
        warehouseId: null,
      },
      // 表Params
      form: {},
      // 表校验
      rules: {
        rackNo: [{ required: true, message: "No. is required", trigger: "blur" }],
        place: [
          {
            validator: (rule, value, callback) => {
              console.log("validator",this.form.place)
              let v = this.form.place;
              if (v && v.length == 2) {
                callback();
              } else {
                callback(new Error("Please select Warehouse And Area"));
              }
            },
            trigger: "change",
          },
        ],
      },
      columns: [
        { key: 1, label: "No.", visible: true },
        { key: 2, label: "Shelf Name", visible: true },
        { key: 3, label: "Area", visible: true },
        { key: 4, label: "Warehouse", visible: true },
        { key: 5, label: "Remark", visible: true },
      ],
    };
  },
  computed: {
    ...mapGetters(["warehouseMap", "warehouseList", "areaList", "areaMap"]),
  },
  created() {
    this.getList();
  },
  methods: {
    /** Search Rack列表 */
    getList() {
      this.loading = true;
      const { pageNum, pageSize } = this.queryParams;
      const query = {
        ...this.queryParams,
        pageNum: undefined,
        pageSize: undefined,
      };
      const pageReq = { page: pageNum - 1, size: pageSize };

      listWmsRack(query, pageReq).then((response) => {
        const { content, totalElements } = response;
        content.forEach((item) => {
          item.warehouseName = this.warehouseMap.get(item.warehouseId);
        });
        content.forEach((item) => {
          item.areaName = this.areaMap.get(item.areaId);
        });
        this.wmsRackList = content;
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
        rackNo: null,
        rackName: null,
        areaId: null,
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
      this.title = "New Shelf";
    },
    /** ModifyButtonOperate */
    async handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      await getWmsRack(id).then((response) => {
        response.place = [response.warehouseId,response.areaId];
        this.form = response;
        this.open = true;
        this.title = "Modify Shelf";
      });
    },
    /** 提交Button */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.place) {
            console.log(this.form.place);
            this.form.warehouseId = this.form.place[0];
            this.form.areaId = this.form.place[1];
          }
          if (this.form.id != null) {
            updateWmsRack(this.form).then((response) => {
              this.$store.dispatch("wms/getRackList");
              this.$modal.msgSuccess("Modify Successful");
              this.open = false;
              this.getList();
            });
          } else {
            addWmsRack(this.form).then((response) => {
              this.$store.dispatch("wms/getRackList");
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
        .confirm(' Do you want delete  RackNo."' + ids + '"？')
        .then(function () {
          return delWmsRack(ids);
        })
        .then(() => {
          this.$store.dispatch("wms/getRackList");
          this.getList();
          this.$modal.msgSuccess("Delete Successful");
        })
        .catch(() => {});
    },
    /** ExportButtonOperate */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal
        .confirm("Export All Rack？")
        .then(() => {
          this.exportLoading = true;
          return exportWmsRack(queryParams);
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
