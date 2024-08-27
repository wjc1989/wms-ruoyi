<template>
  <div class="app-container" v-if="show">
    <el-card style="margin-bottom: 12px;">
      <div class="clearfix" style="margin-top: 10px;">
        <div style="width: 200px;margin-bottom: 10px;" class="left">
          <el-input placeholder="Please Input WarehouseNo./Item" v-model="queryWarehouseParam" class="input-with-select"
            suffix-icon="el-icon-search" @keyup.enter.native="handleSearchWarehouse">
          </el-input>
        </div>
        <div class="left">
          <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAddWarehouse"
            v-hasPermi="['wms:warehouse:add']" style="margin: 3px 10px;">New Warehouse</el-button>
        </div>
      </div>
    </el-card>
    <el-empty description="No Data" v-if="wmsWarehouseList.length == 0"></el-empty>
    <el-card v-else>
      <el-tabs :tab-position="tabPosition" style="height: 600px;" @tab-click="handleTabClick" ref="leftRef">
        <el-tab-pane v-for="(it, index) in wmsWarehouseList" :label="it.warehouseName" :key="it.id">
          <div class="content-wrapper">
            <div class="clearfix">
              <div style="color:#409EFF;font-weight: bold;font-size: 15px;" class="content-margin left">Basic Info</div>
              <el-link type="primary" plain size="mini" @click="handleUpdateWarehouse" v-hasPermi="['wms:warehouse:add']"
                class="left" style="margin-left: 10px;text-decoration: underline;" :underline="false">Modify</el-link>
              <el-link type="danger" plain size="mini" @click="handleDeleteWarehouse" v-hasPermi="['wms:warehouse:add']"
                class="left" style="margin-left: 10px;text-decoration: underline;" :underline="false">Delete</el-link>
            </div>
            <div class="content-margin info-box">
              <el-row :gutter="20">
                <el-col :span="8">
                  <div>
                    <div style="font-weight: bold;">Warehouse Name</div>
                    <div style="margin-top: 10px;">{{ it.warehouseName }}</div>
                  </div>
                </el-col>
                <el-col :span="8">
                  <div>
                    <div style="font-weight: bold;">Warehouse No.</div>
                    <div style="margin-top: 10px;">{{ it.warehouseNo }}</div>
                  </div>
                </el-col>
                <el-col :span="8">
                  <div>
                    <div style="font-weight: bold;">Warehouse Remark</div>
                    <div style="margin-top: 10px;">{{ it.remark }}</div>
                  </div>
                </el-col>
              </el-row>
            </div>
          </div>
          <div class="content-wrapper">
            <el-tabs v-model="activeName" @tab-click="handleClick">
              <el-tab-pane label="Area Info" name="first">
                <div class="clearfix oper_area">
                  <div class="left">
                    <el-input placeholder="Please Input Area/Name" v-model="queryAreaParam" class="input-with-select"
                      suffix-icon="el-icon-search" @keyup.enter.native="handleSearchArea">
                    </el-input>
                  </div>
                  <div class="left" style="margin: 3px 10px;">
                    <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAddArea()"
                      v-hasPermi="['wms:area:add']">New Area</el-button>
                  </div>
                </div>

                <WmsTable v-loading="loading" :data="wmsAreaList">
                  <el-table-column label="No." align="center" prop="areaNo"  />
                  <el-table-column label="Warehouse" align="center" prop="warehouseName"  />
                  <el-table-column label="Area Name" align="center" prop="areaName"  />
                  <el-table-column label="Remark" align="center" prop="remark"  />
                  <el-table-column label="Operate" align="center" class-name="small-padding fixed-width">
                    <template slot-scope="scope">
                      <el-button size="mini" type="text" icon="el-icon-edit" @click.stop="handleUpdateArea(scope.row)"
                        v-hasPermi="['wms:area:edit']">Modify</el-button>
                      <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                        v-hasPermi="['wms:area:remove']">Delete</el-button>
                    </template>
                  </el-table-column>
                </WmsTable>
              </el-tab-pane>
            </el-tabs>
          </div>
        </el-tab-pane>
      </el-tabs>
    </el-card>
    <!-- Add OrModifyWarehouse对话框 -->
    <el-dialog :title="title" :visible.sync="openWarehouse" width="50%" append-to-body>
      <el-form ref="warehouseForm" :model="warehouseForm" :rules="rules" label-width="108px" inline
        class="dialog-form-two">
        <el-form-item label="No." prop="warehouseNo">
          <el-input v-model="warehouseForm.warehouseNo" placeholder="Please Input No." />
        </el-form-item>
        <el-form-item label="Name" prop="warehouseName">
          <el-input v-model="warehouseForm.warehouseName" placeholder="Please Input Name" />
        </el-form-item>
        <el-form-item label="Remark" prop="remark">
          <el-input v-model="warehouseForm.remark" placeholder="Please Input Remark" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitWarehouseForm">OK</el-button>
        <el-button @click="cancel">Cancel</el-button>
      </div>
    </el-dialog>
    <!-- Add OrModifyArea对话框 -->
    <el-dialog :title="title" :visible.sync="openArea" width="50%" append-to-body>
      <el-form ref="areaForm" :model="areaForm" :rules="rulesArea" label-width="108px" inline class="dialog-form-two">
        <el-form-item label="Area No." prop="areaNo">
          <el-input v-model="areaForm.areaNo" placeholder="Please Input No." />
        </el-form-item>
        <el-form-item label="Area Name" prop="areaName">
          <el-input v-model="areaForm.areaName" placeholder="Please Input Item" />
        </el-form-item>
        <el-form-item label="Warehouse" prop="warehouseId">
          <!-- <el-select v-model="form.warehouseId" placeholder="Please Input 货仓Item" clearable size="small">
            <el-option v-for="item in warehouseList" :key="item.id" :label="item.warehouseName" :value="item.id">
            </el-option>
          </el-select> -->
          <el-input v-model="areaForm.warehouseId" :disabled="true" />
        </el-form-item>
        <el-form-item label="Remark" prop="remark">
          <el-input v-model="areaForm.remark" placeholder="Please Input Remark" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitAreaForm">OK</el-button>
        <el-button @click="cancel">Cancel</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {mapGetters} from "vuex";
  import {
    addWmsWarehouse,
    delWmsWarehouse,
    exportWmsWarehouse,
    getWmsWarehouse,
    listWmsWarehouse,
    updateWmsWarehouse,
  } from "@/api/wms/warehouse";
  import {addWmsArea, delWmsArea, getWmsArea, listWmsArea, updateWmsArea,} from "@/api/wms/area";

  export default {
  name: "WmsWarehouse",

  data() {
    return {
      show: false,
      // 遮罩层
      loading: true,
      // Export遮罩层
      exportLoading: false,
      // 非个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // ShowSearch
      showSearch: true,
      // 总条数
      total: 0,
      // Warehouse表格Data
      wmsWarehouseList: [],
      // Area表格Data
      wmsAreaList: [],
      // 弹出层标题
      title: "",
      // Show弹出层
      openWarehouse: false,
      openArea: false,
      // SearchParams
      queryParams: {
        warehouseNo: null,
        warehouseName: null,
      },
      queryAreaParams: {
        areaNo: null,
        areaNameLike: null,
        warehouseId: null,
        pageNum: 1,
        pageSize: 10
      },
      // 表Params
      form: {},
      warehouseForm: {},
      areaForm: {},
      // 表校验
      rules: {
        warehouseNo: [
          { required: true, message: "No. is required", trigger: "blur" },
        ],
        warehouseName: [
          { required: true, message: "Warehouse Name is required", trigger: "blur" },
        ],
      },
      // 表校验
      rulesArea: {
        areaNo: [{ required: true, message: "No. is required", trigger: "blur" }],
        areaName: [
          { required: true, message: "Area Name is required", trigger: "blur" },
        ],
        warehouseId: [
          { required: true, message: "Warehouse is required", trigger: "blur" },
        ],
      },
      // columns: [
      //   { key: 1, label: "No.", visible: true },
      //   { key: 2, label: "Item", visible: true },
      //   { key: 4, label: "Remark", visible: true },
      // ],
      columns: [
        { key: 1, label: "No.", visible: true },
        { key: 2, label: "Name", visible: true },
        { key: 3, label: "Warehouse", visible: false },
        { key: 4, label: "Remark", visible: true },
      ],
      // tab位置
      tabPosition: 'left',
      selectedWarehouseId: null,
      // 右侧横向tab默认选中
      activeName: 'first',
      // SearchAreaParams
      queryAreaParam: null,
      queryWarehouseParam: null
    };
  },
  computed: {
    ...mapGetters(["warehouseList", "warehouseMap", "areaList", "areaMap", "rackList", "rackMap","userId"])
  },
  async created() {
    this.show = true;
    this.getList()
  },
  methods: {
    /** SearchWarehouse列表 */
    getList() {
      this.selectedWarehouseId = null
      const query = {
        ...this.queryParams,
        pageNum: undefined,
        pageSize: undefined,
      };
      const pageReq = undefined;
      listWmsWarehouse(query, pageReq).then((response) => {
        const { content, totalElements } = response;
        this.wmsWarehouseList = content;
        this.setChildren();
        this.loadAreas()
      });
    },

    setChildren() {
      let warehouseChildMap = new Map()
      let areaChildMap = new Map()

      this.rackList.forEach((rack) => {
        let children = areaChildMap.get(rack.areaId)
        if (!children) {
          children = []
        };
        children.push({ key: 'rack' + rack.id, id: rack.id, warehouseName: rack.rackName, warehouseNo: rack.rackNo })
        areaChildMap.set(rack.areaId, children)
      });

      this.areaList.forEach((area) => {
        let children = warehouseChildMap.get(area.warehouseId)
        if (!children) {
          children = []
        };
        children.push({ key: 'area' + area.id, id: area.id, warehouseName: area.areaName, warehouseNo: area.areaNo, children: areaChildMap.get(area.id) })
        warehouseChildMap.set(area.warehouseId, children)
      });

      this.wmsWarehouseList.forEach((warehouse) => {
        warehouse.isParent = 1;
        warehouse.key = warehouse.id
        warehouse.children = warehouseChildMap.get(warehouse.id)
      })

    },


    // CancelButton
    cancel() {
      this.openWarehouse = false;
      this.openArea = false;
      this.resetWarehouseForm();
      this.resetAreaForm();
    },
    // 表Reset
    resetWarehouseForm() {
      this.warehouseForm = {
        id: null,
        warehouseNo: null,
        warehouseName: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
      };
      this.resetForm("warehouseForm");
    },
    resetAreaForm() {
      this.areaForm = {
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
      this.resetForm("areaForm");
    },
    /** SearchButtonOperate */
    handleQuery() {
      // this.queryParams.pageNum = 1;
      this.getList();
    },
    /** ResetButtonOperate */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** AddWarehouseButtonOperate */
    handleAddWarehouse() {
      this.resetWarehouseForm();
      this.openWarehouse = true;
      this.title = "New Warehouse";
    },
    /** AddAreaButtonOperate */
    handleAddArea() {
      this.resetAreaForm();
      this.areaForm.warehouseId = this.selectedWarehouseId;
      this.openArea = true;
      this.title = "New Area";
    },
    /** ModifyAreaButtonOperate */
    handleUpdateArea(row) {
      this.resetAreaForm();
      const id = row.id || this.ids;
      getWmsArea(id).then((response) => {
        this.areaForm = response;
        this.openArea = true;
        this.title = "Modify Area";
      });
    },
    /** ModifyWarehouseButtonOperate */
    handleUpdateWarehouse() {
      this.resetWarehouseForm();
      const id = this.selectedWarehouseId;
      getWmsWarehouse(id).then((response) => {
        this.warehouseForm = response;
        this.openWarehouse = true;
        this.title = "Modify Warehouse";
      });
    },
    /** 提交Button（AddWarehouse） */
    submitWarehouseForm() {
      this.$refs["warehouseForm"].validate((valid) => {
        if (valid) {
          if (this.warehouseForm.id != null) {
            updateWmsWarehouse(this.warehouseForm).then((response) => {
              this.$store.dispatch("wms/getWarehouseList");
              this.$modal.msgSuccess("Modify Warehouse Successful");
              this.openWarehouse = false;
              this.getList();
            });
          } else {
            addWmsWarehouse(this.warehouseForm).then((response) => {
              this.$store.dispatch("wms/getWarehouseList");
              this.$modal.msgSuccess("Add Warehouse Successful");
              this.openWarehouse = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 提交Button（AddArea） */
    submitAreaForm() {
      this.$refs["areaForm"].validate((valid) => {
        if (valid) {
          if (this.areaForm.id != null) {
            updateWmsArea(this.areaForm).then((response) => {
              this.$store.dispatch("wms/getAreaList");
              this.$modal.msgSuccess("Modify Warehouse Successful");
              this.openArea = false;
              this.loadAreas();
            });
          } else {
            addWmsArea(this.areaForm).then((response) => {
              this.$store.dispatch("wms/getAreaList");
              this.$modal.msgSuccess("Add Warehouse Successful");
              this.openArea = false;
              this.loadAreas();
            });
          }
        }
      });
    },
    /**DeleteAreaButton */
    handleDelete(row) {
      const ids = row.id;
      this.$modal
        .confirm(' Do you want delete AreaNo."' + ids + '"？')
        .then(function () {
          return delWmsArea(ids);
        })
        .then(() => {
          this.loadAreas();
          this.$store.dispatch("wms/getAreaList");
          this.$modal.msgSuccess("Delete Warehouse Successful");
        })
        .catch(() => { });
    },
    /**DeleteWarehouseButton */
    handleDeleteWarehouse() {
      const warehouseId = this.selectedWarehouseId;
      this.$modal
        .confirm(' Do you want delete Warehouse No."' + warehouseId + '"？')
        .then(function () {
          return delWmsWarehouse(warehouseId)
        })
        .then(() => {
          this.getList();
          if (this.wmsWarehouseList.length != 0) {
            this.$refs.leftRef.currentName = '0'
          }
          this.$store.dispatch("wms/getWarehouseList");
          this.$modal.msgSuccess("Delete Warehouse Successful");
        })
        .catch(() => { })
    },
    /** ExportButtonOperate */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal
        .confirm("Export All Warehouse？")
        .then(() => {
          this.exportLoading = true;
          return exportWmsWarehouse(queryParams);
        })
        .then((response) => {
          this.$download.download(response);
          this.exportLoading = false;
        })
        .catch(() => { });
    },
    /** 左侧Warehouse Label选中Operate */
    handleTabClick(tab, event) {
      this.queryAreaParam = null
      this.selectedWarehouseId = this.wmsWarehouseList[tab.index].id
      this.loadAreas()
      // this.queryAreaParams.warehouseId = tab.warehouseId;
      // this.loadAreas()
    },
    /** 加载选中Warehouse的Area Info */
    loadAreas() {
      this.loading = true;
      if (this.selectedWarehouseId == null) {
        if (this.wmsWarehouseList.length == 0) {
          this.wmsAreaList = [];
          return;
        }
        this.queryAreaParams.warehouseId = this.wmsWarehouseList[0].id;
        this.selectedWarehouseId = this.wmsWarehouseList[0].id;
      } else {
        this.queryAreaParams.warehouseId = this.selectedWarehouseId;
      }
      const { pageNum, pageSize } = this.queryAreaParams;
      const query = {
        ...this.queryAreaParams,
        pageNum: undefined,
        pageSize: undefined,
      };
      const pageReq = undefined;
      listWmsArea(query, pageReq).then((response) => {
        const { content, totalElements } = response;
        this.wmsAreaList = content;
        this.total = totalElements;
        this.loading = false;
      })
    },
    // WarehouseSearchButton
    handleSearchWarehouse() {
      this.queryAreaParam = null
      if (!this.queryWarehouseParam) {
        this.wmsWarehouseList = this.warehouseList;
      } else {
        this.wmsWarehouseList = this.warehouseList.filter(it => it.warehouseNo == this.queryWarehouseParam || it.warehouseName == this.queryWarehouseParam)
      }
      this.loadAreas()
    },
    // AreaSearchButton
    handleSearchArea() {
      if (!this.queryAreaParam) {
        this.wmsAreaList = this.areaList.filter(it => it.warehouseId == this.selectedWarehouseId);
        return;
      }
      this.wmsAreaList = this.areaList.filter(it => it.warehouseId == this.selectedWarehouseId && (it.areaNo == this.queryAreaParam || it.areaName == this.queryAreaParam));
    }
  },
};
</script>
<style>
.content-wrapper {
  /* margin-left: 20px; */
  /* width: 400px; */
  margin-bottom: 16px;
}

.content-margin {
  margin-bottom: 16px;
}

.info-box {
  /* background-color: #F7FAFC; */
  width: 500px;
  height: 60px;
  padding: 10px 10px;
}

.el-tabs__item {
  width: 200px;
}

.clearfix:after {
  content: ' ';
  display: block;
  height: 0;
  clear: both;
}

.left {
  float: left;
}

.right {
  float: right;
}

.oper_area {
  margin-bottom: 10px;
}

.title {
  font-size: 18px;
  font-weight: bold;
  text-align: right;
  width: 200px;
}
</style>
