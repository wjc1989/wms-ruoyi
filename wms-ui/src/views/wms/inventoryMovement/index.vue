<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="120px" size="medium"
             class="ry_form">
      <el-form-item label="MoveStatus" prop="status">
        <DictRadio v-model="queryParams.status" @change="handleQuery" size="small"
                   :radioData="dict.type.wms_movement_status" :showAll="'all'"/>
      </el-form-item>
      <el-form-item label="No." prop="inventoryMovementNo">
        <el-input
          v-model="queryParams.inventoryMovementNo"
          placeholder="Please Input No."
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item class="flex_one tr">
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">Search</el-button>
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
          v-hasPermi="['wms:inventoryMovement:add']"
        >New
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" ></right-toolbar>
    </el-row>

    <WmsTable v-loading="loading" :data="wmsInventoryMovementList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="No." align="center" prop="inventoryMovementNo" />
      <el-table-column label="Status" align="center" prop="status" >
        <template slot-scope="scope">
          <el-tag size="medium" effect="plain" :type="getStatusTag(scope.row)">
            {{ getStatus(scope.row) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="Create Time" align="center" prop="createTime" width="180" >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Remark" align="center" prop="remark" >
        <template v-slot="{ row }">
          <el-popover placement="left" width="300" trigger="hover" :content="row.remark" popper-class="popperOptions">
            <p class="showOverTooltip" slot="reference">{{ row.remark }}</p>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="Item" align="center" prop="remark" >
        <template slot-scope="scope">
          <p>Plan Count：{{ scope.row.detailCount }}</p>
          <p>Real Count：{{ scope.row.itemCount }}</p>
        </template>
      </el-table-column>
      <el-table-column label="Operate" align="center" class-name="small-padding fixed-width">
        <template v-slot="{ row }">
          <el-button
            v-if="21 === row.status"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click.stop="handleUpdate(row)"
            v-hasPermi="['wms:inventoryMovement:edit']"
          >Modify
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click.stop="handleDelete(row)"
            v-hasPermi="['wms:inventoryMovement:remove']"
          >Delete
          </el-button>
          <el-button
            v-if="row.detailCount"
            size="mini"
            type="text"
            icon="el-icon-truck"
            @click.stop="handleStatus(row)"
            v-hasPermi="['wms:inventoryMovement:status']"
          >Move
          </el-button>
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
import {
  listWmsInventoryMovement,
  delWmsInventoryMovement,
  exportWmsInventoryMovement
} from "@/api/wms/inventoryMovement";
import {mapGetters} from "vuex";

export default {
  name: "WmsInventoryMovement",
  dicts: ['wms_movement_status'],
  computed: {
    ...mapGetters(['warehouseMap', 'areaMap', 'rackMap']),
    statusMap() {
      let obj = this.dict.type.wms_movement_status.map(item => [item.value, item.label])
      let map = new Map(obj)
      return map
    },
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
      // Inventory移动表格Data
      wmsInventoryMovementList: [],
      // 弹出层标题
      title: "",
      // Show弹出层
      open: false,
      // SearchParams
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        inventoryMovementNo: null,
        status: null
      },
      // 表Params
      form: {},
      // 表校验
      rules: {},
      columns: [
        {key: 1, label: "No.", visible: true},
        {key: 2, label: "Status", visible: true},
        {key: 3, label: "Create Time", visible: true},
        {key: 4, label: "Remark", visible: true},
        {key: 5, label: "Item", visible: true},
      ],
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getStatusTag(row) {
      switch (row.status) {
        case 21:
          return "info";
        case 22:
          return "warning";
        case 24:
          return "danger";
        case 23:
          return "success";
      }
    },
    getStatus(row) {
      return this.statusMap.get(row.status + "")
    },
    /** SearchInventory移动列表 */
    getList() {
      this.loading = true;
      const {pageNum, pageSize} = this.queryParams;
      const query = {...this.queryParams, pageNum: undefined, pageSize: undefined};
      const pageReq = {page: pageNum - 1, size: pageSize};
      listWmsInventoryMovement(query, pageReq).then(response => {
        const {content, totalElements} = response
        this.wmsInventoryMovementList = content;
        this.total = totalElements;
        this.loading = false;
      });
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
      this.$router.push({path: "/wms/inventoryMovement/edit"});
    },
    /** ModifyButtonOperate */
    handleUpdate(row) {
      const id = row.id || this.ids
      this.$router.push({path: "/wms/inventoryMovement/edit", query: {id}});
    },
    handleStatus(row) {
      const id = row.id || this.ids
      this.$router.push({path: "/wms/inventoryMovement/status", query: {id}});
    },
    /** DeleteButtonOperate */
    handleDelete(row) {
      const ids = row.id || this.ids;
      const inventoryMovementNo = row.inventoryMovementNo
      this.$modal.confirm(' Do you want delete "' + inventoryMovementNo + '"？').then(function () {
        return delWmsInventoryMovement(ids);
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
        return exportWmsInventoryMovement(queryParams);
      }).then(response => {
        this.$download.download(response);
        this.exportLoading = false;
      }).catch(() => {
      });
    }
  }
};
</script>
<style lang="stylus">
.popperOptions[x-placement^=left] .popper__arrow::after {
  border-left-color: #565D6B;
}

.popperOptions[x-placement^=right] .popper__arrow::after {
  border-right-color: #565D6B;
}

.popperOptions[x-placement^=bottom] .popper__arrow::after {
  border-bottom-color: #565D6B;
}

.popperOptions[x-placement^=top] .popper__arrow::after {
  border-top-color: #565D6B;
}

.popperOptions {
  background-color: #565D6B;
  color: #FFFFFF;
  border: #565D6B;
}
</style>
