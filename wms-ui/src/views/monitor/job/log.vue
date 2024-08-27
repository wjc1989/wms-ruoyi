<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="Job Name" prop="jobName">
        <el-input
          v-model="queryParams.jobName"
          placeholder="Please Input Job Name"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Group" prop="jobGroup">
        <el-select
          v-model="queryParams.jobGroup"
          placeholder="Please select Group"
          clearable
          style="width: 240px"
        >
          <el-option
            v-for="dict in dict.type.sys_job_group"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="执行Status" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="Please select 执行Status"
          clearable
          style="width: 240px"
        >
          <el-option
            v-for="dict in dict.type.sys_common_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="执行 Time">
        <el-date-picker
          v-model="dateRange"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="Start time"
          end-placeholder="End time"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">Search</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">Reset</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['monitor:job:remove']"
        >Delete</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          @click="handleClean"
          v-hasPermi="['monitor:job:remove']"
        >Clear</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['monitor:job:export']"
        >Export</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-close"
          size="mini"
          @click="handleClose"
        >Close</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="jobLogList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="LogsNo." width="80" align="center" prop="jobLogId" />
      <el-table-column label="Job Name" align="center" prop="jobName" :show-overflow-tooltip="true" />
      <el-table-column label="Group" align="center" prop="jobGroup" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_job_group" :value="scope.row.jobGroup"/>
        </template>
      </el-table-column>
      <el-table-column label="调用Target 字符串" align="center" prop="invokeTarget" :show-overflow-tooltip="true" />
      <el-table-column label="Logs Info" align="center" prop="jobMessage" :show-overflow-tooltip="true" />
      <el-table-column label="执行Status" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_common_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="执行 Time" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Operate" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
            v-hasPermi="['monitor:job:query']"
          >Detail</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 调度LogsDetail -->
    <el-dialog title="调度LogsDetail" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" label-width="120px" size="mini">
        <el-row>
          <el-col :span="12">
            <el-form-item label="LogsNo.：">{{ form.jobLogId }}</el-form-item>
            <el-form-item label="Job Name：">{{ form.jobName }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label=" Task分组：">{{ form.jobGroup }}</el-form-item>
            <el-form-item label="执行 Time：">{{ form.createTime }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="调用方法：">{{ form.invokeTarget }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="Logs Info：">{{ form.jobMessage }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="执行Status：">
              <div v-if="form.status == 0">正常</div>
              <div v-else-if="form.status == 1">失败</div>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label=" Exception Info：" v-if="form.status == 1">{{ form.exceptionInfo }}</el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="open = false">Close</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getJob} from "@/api/monitor/job";
import { listJobLog, delJobLog, cleanJobLog } from "@/api/monitor/jobLog";

export default {
  name: "JobLog",
  dicts: ['sys_common_status', 'sys_job_group'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非多个禁用
      multiple: true,
      // ShowSearch
      showSearch: true,
      // 总条数
      total: 0,
      // 调度Logs表格Data
      jobLogList: [],
      // Show弹出层
      open: false,
      // Date范围
      dateRange: [],
      // 表Params
      form: {},
      // SearchParams
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        jobName: undefined,
        jobGroup: undefined,
        status: undefined
      }
    };
  },
  created() {
    const jobId = this.$route.query.jobId;
    if (jobId !== undefined && jobId != 0) {
      getJob(jobId).then(response => {
        this.queryParams.jobName = response.data.jobName;
        this.queryParams.jobGroup = response.data.jobGroup;
        this.getList();
      });
    } else {
      this.getList();
    }
  },
  methods: {
    /** Search调度Logs列表 */
    getList() {
      this.loading = true;
      listJobLog(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.jobLogList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    // 返回Button
    handleClose() {
      const obj = { path: "/monitor/job" };
      this.$tab.closeOpenPage(obj);
    },
    /** SearchButtonOperate */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** ResetButtonOperate */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中Data
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.jobLogId);
      this.multiple = !selection.length;
    },
    /** DetailButtonOperate */
    handleView(row) {
      this.open = true;
      this.form = row;
    },
    /** DeleteButtonOperate */
    handleDelete(row) {
      const jobLogIds = this.ids;
      this.$modal.confirm(' Do you want delete 调度LogsNo."' + jobLogIds + '"？').then(function() {
        return delJobLog(jobLogIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Delete Successful");
      }).catch(() => {});
    },
    /** ClearButtonOperate */
    handleClean() {
      this.$modal.confirm('OKClear所有调度Logs？').then(function() {
        return cleanJobLog();
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Clear Successful");
      }).catch(() => {});
    },
    /** ExportButtonOperate */
    handleExport() {
      this.download('/monitor/jobLog/export', {
        ...this.queryParams
      }, `log_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
