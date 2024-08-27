<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--DepartmentData-->
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input
            v-model="deptName"
            placeholder="Please Input Department"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">
          <el-tree
            :data="deptOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            default-expand-all
            highlight-current
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>
      <!--User Data-->
      <el-col :span="20" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="Name" prop="userName">
            <el-input
              v-model="queryParams.userName"
              placeholder="Please Input Name"
              clearable
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="Phone" prop="phonenumber">
            <el-input
              v-model="queryParams.phonenumber"
              placeholder="Please Input Phone"
              clearable
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="Status" prop="status">
            <el-select
              v-model="queryParams.status"
              placeholder="User Status"
              clearable
              style="width: 240px"
            >
              <el-option
                v-for="dict in dict.type.sys_normal_disable"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="Create Time">
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
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
              v-hasPermi="['system:user:add']"
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
              v-hasPermi="['system:user:edit']"
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
              v-hasPermi="['system:user:remove']"
            >Delete</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="info"
              plain
              icon="el-icon-upload2"
              size="mini"
              @click="handleImport"
              v-hasPermi="['system:user:import']"
            >Import</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['system:user:export']"
            >Export</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="userList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="ID" align="center" key="userId" prop="userId" v-if="columns[0].visible" />
          <el-table-column label="Name" align="center" key="userName" prop="userName" v-if="columns[1].visible" :show-overflow-tooltip="true" />
          <el-table-column label="Nickname" align="center" key="nickName" prop="nickName" v-if="columns[2].visible" :show-overflow-tooltip="true" />
          <el-table-column label="Department" align="center" key="deptName" prop="dept.deptName" v-if="columns[3].visible" :show-overflow-tooltip="true" />
          <el-table-column label="Phone" align="center" key="phonenumber" prop="phonenumber" v-if="columns[4].visible" width="120" />
          <el-table-column label="Status" align="center" key="status" v-if="columns[5].visible">
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.status"
                active-value="0"
                inactive-value="1"
                @change="handleStatusChange(scope.row)"
              ></el-switch>
            </template>
          </el-table-column>
          <el-table-column label="Create Time" align="center" prop="createTime" v-if="columns[6].visible" width="160">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="Operate"
            align="center"
            width="160"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope" v-if="scope.row.userId !== 1">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['system:user:edit']"
              >Modify</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['system:user:remove']"
              >Delete</el-button>
              <el-dropdown size="mini" @command="(command) => handleCommand(command, scope.row)" v-hasPermi="['system:user:resetPwd', 'system:user:edit']">
                <span class="el-dropdown-link">
                  <i class="el-icon-d-arrow-right el-icon--right"></i>More
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item command="handleResetPwd" icon="el-icon-key"
                    v-hasPermi="['system:user:resetPwd']">ResetPassword</el-dropdown-item>
                  <el-dropdown-item command="handleAuthRole" icon="el-icon-circle-check"
                    v-hasPermi="['system:user:edit']"> Distribute Role</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
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
      </el-col>
    </el-row>

    <!-- Add OrModifyUser 配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="Nickname" prop="nickName">
              <el-input v-model="form.nickName" placeholder="Please Input Nickname" maxlength="30" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Department" prop="deptId">
              <treeselect v-model="form.deptId" :options="deptOptions" :show-count="true" placeholder="Please select Department" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="Phone" prop="phonenumber">
              <el-input v-model="form.phonenumber" placeholder="Please Input Phone" maxlength="11" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Email" prop="email">
              <el-input v-model="form.email" placeholder="Please Input Email" maxlength="50" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item v-if="form.userId == undefined" label="Name" prop="userName">
              <el-input v-model="form.userName" placeholder="Please Input Name" maxlength="30" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="form.userId == undefined" label="Password" prop="password">
              <el-input v-model="form.password" placeholder="Please Input Password" type="password" maxlength="20" show-password/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="Gender">
              <el-select v-model="form.sex" placeholder="Please select Gender">
                <el-option
                  v-for="dict in dict.type.sys_user_sex"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Status">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in dict.type.sys_normal_disable"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="Post">
              <el-select v-model="form.postIds" multiple placeholder="Please select Post">
                <el-option
                  v-for="item in postOptions"
                  :key="item.postId"
                  :label="item.postName"
                  :value="item.postId"
                  :disabled="item.status == 1"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Role">
              <el-select v-model="form.roleIds" multiple placeholder="Please select Role">
                <el-option
                  v-for="item in roleOptions"
                  :key="item.roleId"
                  :label="item.roleName"
                  :value="item.roleId"
                  :disabled="item.status == 1"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="Remark">
              <el-input v-model="form.remark" type="textarea" placeholder="Please Input Content"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">OK</el-button>
        <el-button @click="cancel">Cancel</el-button>
      </div>
    </el-dialog>

    <!-- User Import对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">Drag， Or<em>Click</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport" />Update Exists User Data
          </div>
          <span>Only support import xls、xlsx。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">Download Template</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">OK</el-button>
        <el-button @click="upload.open = false">Cancel</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listUser, getUser, delUser, addUser, updateUser, resetUserPwd, changeUserStatus } from "@/api/system/user";
import { getToken } from "@/utils/auth";
import { treeselect } from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "User",
  dicts: ['sys_normal_disable', 'sys_user_sex'],
  components: { Treeselect },
  data() {
    return {
      // 遮罩层
      loading: true,
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
      // User 表格Data
      userList: null,
      // 弹出层标题
      title: "",
      // Department树选项
      deptOptions: undefined,
      // Show弹出层
      open: false,
      // Department
      deptName: undefined,
      // 默认Password
      initPassword: undefined,
      // Date范围
      dateRange: [],
      // Post选项
      postOptions: [],
      // Role选项
      roleOptions: [],
      // 表Params
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // User ImportParams
      upload: {
        // Show弹出层（User Import）
        open: false,
        // 弹出层标题（User Import）
        title: "",
        // 禁用上传
        isUploading: false,
        // 更新已经存在的User Data
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的Address
        url: process.env.VUE_APP_BASE_API + "/system/user/importData"
      },
      // SearchParams
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: undefined,
        phonenumber: undefined,
        status: undefined,
        deptId: undefined
      },
      // 列 Info
      columns: [
        { key: 0, label: `ID`, visible: true },
        { key: 1, label: `Name`, visible: true },
        { key: 2, label: `Nickname`, visible: true },
        { key: 3, label: `Department`, visible: true },
        { key: 4, label: `Phone`, visible: true },
        { key: 5, label: `Status`, visible: true },
        { key: 6, label: `Create Time`, visible: true }
      ],
      // 表校验
      rules: {
        userName: [
          { required: true, message: "Name is required", trigger: "blur" },
          { min: 2, max: 20, message: 'Name Length must be between 2 and 20', trigger: 'blur' }
        ],
        nickName: [
          { required: true, message: "Nickname is required", trigger: "blur" }
        ],
        password: [
          { required: true, message: "Password is required", trigger: "blur" },
          { min: 5, max: 20, message: 'Password Length must be between 5 and 20', trigger: 'blur' }
        ],
        email: [
          {
            type: "email",
            message: "Please Input Right Email",
            trigger: ["blur", "change"]
          }
        ],
        phonenumber: [
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "Please Input Right Phone",
            trigger: "blur"
          }
        ]
      }
    };
  },
  watch: {
    // 根据Item筛选Department树
    deptName(val) {
      this.$refs.tree.filter(val);
    }
  },
  created() {
    this.getList();
    this.getTreeselect();
    this.getConfigKey("sys.user.initPassword").then(response => {
      this.initPassword = response.msg;
    });
  },
  methods: {
    /** SearchUser 列表 */
    getList() {
      this.loading = true;
      listUser(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.userList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /** SearchDepartment下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.deptOptions = response.data;
      });
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点击事件
    handleNodeClick(data) {
      this.queryParams.deptId = data.id;
      this.handleQuery();
    },
    // User StatusModify
    handleStatusChange(row) {
      let text = row.status === "0" ? "Enable" : "Disable";
      this.$modal.confirm('Confirm to "' + text + '""' + row.userName + '"User ？').then(function() {
        return changeUserStatus(row.userId, row.status);
      }).then(() => {
        this.$modal.msgSuccess(text + " Successful");
      }).catch(function() {
        row.status = row.status === "0" ? "1" : "0";
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
        userId: undefined,
        deptId: undefined,
        userName: undefined,
        nickName: undefined,
        password: undefined,
        phonenumber: undefined,
        email: undefined,
        sex: undefined,
        status: "0",
        remark: undefined,
        postIds: [],
        roleIds: []
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
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中Data
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.userId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    // MoreOperate触发
    handleCommand(command, row) {
      switch (command) {
        case "handleResetPwd":
          this.handleResetPwd(row);
          break;
        case "handleAuthRole":
          this.handleAuthRole(row);
          break;
        default:
          break;
      }
    },
    /** AddButtonOperate */
    handleAdd() {
      this.reset();
      this.getTreeselect();
      getUser().then(response => {
        this.postOptions = response.posts;
        this.roleOptions = response.roles;
        this.open = true;
        this.title = "New";
        this.form.password = this.initPassword;
      });
    },
    /** ModifyButtonOperate */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      const userId = row.userId || this.ids;
      getUser(userId).then(response => {
        this.form = response.data;
        this.postOptions = response.posts;
        this.roleOptions = response.roles;
        this.form.postIds = response.postIds;
        this.form.roleIds = response.roleIds;
        this.open = true;
        this.title = "Modify";
        this.form.password = "";
      });
    },
    /** ResetPasswordButtonOperate */
    handleResetPwd(row) {
      this.$prompt('Please Input New Password', "Info", {
        confirmButtonText: "OK",
        cancelButtonText: "Cancel",
        closeOnClickModal: false,
        inputPattern: /^.{5,20}$/,
        inputErrorMessage: "Password Length must be between 5 and 20"
      }).then(({ value }) => {
          resetUserPwd(row.userId, value).then(response => {
            this.$modal.msgSuccess("Modify Successful，新PasswordYes：" + value);
          });
        }).catch(() => {});
    },
    /**  Distribute RoleOperate */
    handleAuthRole: function(row) {
      const userId = row.userId;
      this.$router.push("/system/user-auth/role/" + userId);
    },
    /** 提交Button */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.userId != undefined) {
            updateUser(this.form).then(response => {
              this.$modal.msgSuccess("Modify Successful");
              this.open = false;
              this.getList();
            });
          } else {
            addUser(this.form).then(response => {
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
      const userIds = row.userId || this.ids;
      this.$modal.confirm(' Do you want delete ID"' + userIds + '"？').then(function() {
        return delUser(userIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Delete Successful");
      }).catch(() => {});
    },
    /** ExportButtonOperate */
    handleExport() {
      this.download('system/user/export', {
        ...this.queryParams
      }, `user_${new Date().getTime()}.xlsx`)
    },
    /** ImportButtonOperate */
    handleImport() {
      this.upload.title = "User Import";
      this.upload.open = true;
    },
    /** Download TemplateOperate */
    importTemplate() {
      this.download('system/user/importTemplate', {
      }, `user_template_${new Date().getTime()}.xlsx`)
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传 Successful处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "Import结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    }
  }
};
</script>
