<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="120px" size="medium"
             class="ry_form">

      <el-form-item label="Type" prop="typeName">
        <el-input
          v-model="queryParams.typeName"
          placeholder="Please Input Type"
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
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['wms:itemType:add']"
        >New
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>


    <el-table
      v-loading="loading"
      :data="wmsItemTypeList"
      row-key="itemTypeId"
      :default-expand-all="isExpandAll"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column label="Type" align="center" prop="typeName" width="260" v-if="columns[2].visible"/>
      <el-table-column label="Sort" align="center" prop="orderNum" width="200"/>

      <el-table-column prop="status" label="Status"  width="100" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="Create Time" align="center" prop="createTime" width="200">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>

      <el-table-column label="Operate" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['wms:itemType:edit']"
          >Modify
          </el-button>
          <!-- <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['system:wmsItemType:add']"
          >Add
          </el-button>-->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['wms:itemType:remove']"
          >Delete
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- Add OrModifyItemType表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="130px" class="dialog-form-two">

        <el-row>
          <el-col :span="24" v-if="form.parentId !== 0">
            <el-form-item label="Parent Category" prop="parentId">
              <treeselect v-model="form.parentId" :options="deptOptions" :normalizer="normalizer"
                          placeholder="Select Parent Category"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="Category" prop="typeName">
              <el-input v-model="form.typeName" placeholder="Please Input Category"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Sort" prop="orderNum">
              <el-input-number v-model="form.orderNum" controls-position="right" :min="0"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="Status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
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
  addWmsItemType,
  delWmsItemType,
  exportWmsItemType,
  getWmsItemType, itemTypeTreeselect,
  listWmsItemTypeNoPage,
  updateWmsItemType
} from "@/api/wms/itemType";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "WmsItemType",
  dicts: ['sys_normal_disable'],
  components: {Treeselect},
  data() {
    return {
      // Expand，默认AllExpand
      isExpandAll: true,
      // Department树选项
      deptOptions: [],
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
      // ItemType表表格Data
      wmsItemTypeList: [],
      // 弹出层标题
      title: "",
      // Show弹出层
      open: false,
      // SearchParams
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        parentId: null,
        ancestors: null,
        typeName: null,
        orderNum: undefined,
        status: null,
      },
      // 表Params
      form: {},
      // 表校验
      rules: {
        typeName: [
          {required: true, message: "Please Input TypeItem", trigger: "blur"},
        ]
      },
      columns: [
        {key: 1, label: "ParentId", visible: true},
        {key: 2, label: "Root", visible: true},
        {key: 3, label: "Category Name", visible: true},
        {key: 4, label: "Sort", visible: true},
        {key: 5, label: "Status（0Normal 1Disable）", visible: true},
      ],
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** Expand/CollapseOperate */
    toggleExpandAll() {
      this.refreshTable = false;
      this.isExpandAll = !this.isExpandAll;
      this.$nextTick(() => {
        this.refreshTable = true;
      });
    },
    /** 转换ItemTypeData结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.itemTypeId,
        label: node.typeName,
        children: node.children
      };
    },
    /** SearchItemType表列表 */
    getList() {
      this.loading = true;
      const {pageNum, pageSize} = this.queryParams;
      const query = {...this.queryParams, pageNum: undefined, pageSize: undefined};
      const pageReq = {page: pageNum - 1, size: pageSize};
      listWmsItemTypeNoPage(query).then(response => {
        this.wmsItemTypeList = this.handleTree(response.data, "itemTypeId");
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
        itemTypeId: null,
        parentId: null,
        ancestors: null,
        typeName: null,
        orderNum: undefined,
        status: "0",
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
      this.ids = selection.map(item => item.itemTypeId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** AddButtonOperate */
    handleAdd(row) {
      this.reset();
      this.open = true;
      this.title = "New";
      if (row != undefined) {
        this.form.parentId = row.itemTypeId;
      }
      listWmsItemTypeNoPage().then(response => {
        this.deptOptions = this.handleTree(response.data, "itemTypeId");
      });

    },
    /** ModifyButtonOperate */
    handleUpdate(row) {
      this.reset();
      const itemTypeId = row.itemTypeId || this.ids
      getWmsItemType(itemTypeId).then(response => {
        this.form = response;
        this.open = true;
        this.title = "Modify";
      });
      listWmsItemTypeNoPage().then(response => {
        this.deptOptions = this.handleTree(response.data, "itemTypeId");
      });
    },
    /** 提交Button */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.itemTypeId != null) {
            updateWmsItemType(this.form).then(response => {
              this.$modal.msgSuccess("Modify Successful");
              this.open = false;
              this.getList();
            });
          } else {
            addWmsItemType(this.form).then(response => {
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
      const itemTypeIds = row.itemTypeId || this.ids;
      this.$modal.confirm(' Do you want delete "' + itemTypeIds + '"？').then(function () {
        return delWmsItemType(itemTypeIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Delete Successful");
      }).catch(() => {
      });
    },
    /** ExportButtonOperate */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal.confirm('Export All？').then(() => {
        this.exportLoading = true;
        return exportWmsItemType(queryParams);
      }).then(response => {
        this.$download.download(response);
        this.exportLoading = false;
      }).catch(() => {
      });
    }
  }
};
</script>
