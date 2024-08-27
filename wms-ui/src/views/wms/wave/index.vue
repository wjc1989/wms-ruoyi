<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="120px" size="medium" class="ry_form">
      <el-form-item label="Wave No." prop="waveNo">
        <el-input
          v-model.trim="queryParams.waveNo"
          placeholder="Please Input Wave No."
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Status" prop="status">
        <DictRadio
          v-model="queryParams.status"
          @change="handleQuery"
          size="small"
          :radioData="dict.type.wms_wave_status"
          :showAll="'all'"
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

    <WmsTable v-loading="loading" :data="WaveList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="Wave No." align="center" prop="waveNo" />
      <el-table-column label="Status" align="center" prop="status" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.wms_wave_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="Create Time" align="center" prop="createTime" />
      <el-table-column label="Remark" align="center" prop="remark" />
      <el-table-column label="Operate" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            @click="handleUpdate(scope.row)"-->
<!--            v-hasPermi="['wms:wave:edit']"-->
<!--          >Modify</el-button>-->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleAllocation(scope.row)"
            v-hasPermi="['wms:wave:edit']"
          >Allocate inventory</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            v-if="scope.row.status == 1"
            @click="handleDelete(scope.row)"
            v-hasPermi="['wms:wave:remove']"
          >CancelWave </el-button>
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
  </div>
</template>

<script>
import { listWave, getWave, delWave, addWave, updateWave, exportWave } from "@/api/wms/wave";

export default {
  name: "Wave",
  dicts: ["wms_wave_status"],
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
      // Wave 表格Data
      WaveList: [],
      // 弹出层标题
      title: "",
      // Show弹出层
      open: false,
      // SearchParams
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        waveNo: null,
        status: null,
      },
      // 表Params
      form: {},
      // 表校验
      rules: {
        waveNo: [
          { required: true, message: "Wave No. is required", trigger: "blur" }
        ],
      },
      columns: [
            { key: 1, label: "Wave No.", visible:  true  },
            { key: 2, label: "Status", visible:  true  },
            { key: 3, label: "Remark", visible:  true  },
                             ],
    };
  },
  created() {
    this.getList();
  },
  methods: {
    handleAllocation(row){
      this.$router.push({
        path: "/wms/wave/status",
        query: {
          id: row.id
        }
      });
    },
    /** SearchWave 列表 */
    getList() {
      this.loading = true;
      const {pageNum, pageSize} = this.queryParams;
      const query = {...this.queryParams, pageNum: undefined, pageSize: undefined, type: 2};
      const pageReq = {page: pageNum - 1, size: pageSize};
      listWave(query, pageReq).then(response => {
        const { content, totalElements } = response
        this.WaveList = content;
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
        waveNo: null,
        status: "0",
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
      getWave(id).then(response => {
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
            updateWave(this.form).then(response => {
              this.$modal.msgSuccess("Modify Successful");
              this.open = false;
              this.getList();
            });
          } else {
            addWave(this.form).then(response => {
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
      const waveNo = row.waveNo;
      this.$modal.confirm(' Do you want delete Wave No."' + waveNo + '"？').then(function() {
        return delWave(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Delete Successful");
      }).catch(() => {});
    },
    /** ExportButtonOperate */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal.confirm('Export AllWave ？').then(() => {
        this.exportLoading = true;
        return exportWave(queryParams);
      }).then(response => {
        this.$download.download(response);
        this.exportLoading = false;
      }).catch(() => {});
    }
  }
};
</script>
