<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="120px" size="medium" class="ry_form">
      <el-form-item label="No." prop="carrierNo">
        <el-input
          v-model.trim="queryParams.carrierNo"
          placeholder="Please Input No."
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Name" prop="carrierName">
        <el-input
          v-model.trim="queryParams.carrierName"
          placeholder="Please Input Name"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Address" prop="address">
        <el-input
          v-model.trim="queryParams.address"
          placeholder="Please Input Address"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Phone" prop="mobile">
        <el-input
          v-model.trim="queryParams.mobile"
          placeholder="Please Input Phone"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="Contacts" prop="contact">
        <el-input
          v-model.trim="queryParams.contact"
          placeholder="Please Input Contacts"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Leave" prop="level">
        <el-select v-model="queryParams.level" placeholder="Please select Leave" clearable size="small" @change="handleQuery">
              <el-option
                  v-for="dict in dict.type.wms_carrier_level"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
        </el-select>
      </el-form-item>
      <template v-if="showMoreCondition">
      <el-form-item label="Email" prop="email">
        <el-input
          v-model.trim="queryParams.email"
          placeholder="Please Input Email"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
    </template>
      <el-form-item class="flex_one tr">
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">Search</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">Reset</el-button>
        <el-button :icon="showMoreCondition ? 'el-icon-arrow-up' : 'el-icon-arrow-down'" size="mini" @click="showMoreCondition = !showMoreCondition">{{showMoreCondition ? 'Collapse' : 'Expand'}}</el-button>
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
          v-hasPermi="['wms:carrier:add']"
        >New</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['wms:carrier:edit']"
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
          v-hasPermi="['wms:carrier:remove']"
        >Delete</el-button>
      </el-col> -->
      <!-- <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['wms:carrier:export']"
        >Export</el-button>
      </el-col> -->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <WmsTable v-loading="loading" :data="wmsCarrierList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="No." align="center" prop="carrierNo" v-if="columns[0].visible"/>
      <el-table-column label="Name" align="center" prop="carrierName" v-if="columns[1].visible"/>
      <el-table-column label="Address" align="center" prop="address" v-if="columns[2].visible"/>
      <el-table-column label="Phone" align="center" prop="mobile" v-if="columns[3].visible"/>
       <el-table-column label="Contacts" align="center" prop="contact" v-if="columns[4].visible"/>
      <el-table-column label="Leave" align="center" prop="level" v-if="columns[5].visible"/>
      <el-table-column label="Email" align="center" prop="email" v-if="columns[6].visible"/>
      <el-table-column label="Remark" align="center" prop="remark" v-if="columns[7].visible"/>
      <el-table-column label="Operate" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click.stop="handleUpdate(scope.row)"
            v-hasPermi="['wms:carrier:edit']"
          >Modify</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['wms:carrier:remove']"
          >Delete</el-button>
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

    <!-- Add OrModify Carrier对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="50%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="108px" inline class="dialog-form-two">
        <el-form-item label="No." prop="carrierNo">
          <el-input v-model="form.carrierNo" placeholder="Please Input No." />
        </el-form-item>
        <el-form-item label="Name" prop="carrierName">
          <el-input v-model="form.carrierName" placeholder="Please Input Name" />
        </el-form-item>
        <el-form-item label="Address" prop="address">
          <el-input v-model="form.address" placeholder="Please Input Address" />
        </el-form-item>
        <el-form-item label="Phone" prop="mobile">
          <el-input v-model="form.mobile" placeholder="Please Input Phone" />
        </el-form-item>

        <el-form-item label="Contacts" prop="contact">
          <el-input v-model="form.contact" placeholder="Please Input Contacts" />
        </el-form-item>
        <el-form-item label="Leave" prop="level">
          <el-select v-model="form.level" placeholder="Please select Leave">
            <el-option
                  v-for="dict in dict.type.wms_carrier_level"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="Email" prop="email">
          <el-input v-model="form.email" placeholder="Please Input Email" />
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
import { listWmsCarrier, getWmsCarrier, delWmsCarrier, addWmsCarrier, updateWmsCarrier, exportWmsCarrier } from "@/api/wms/carrier";
import WmsTable from "@/components/WmsTable/index.vue";

export default {
    name: "WmsCarrier",
    dicts: ["wms_carrier_level"],
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
            //  Carrier表格Data
            wmsCarrierList: [],
            // 弹出层标题
            title: "",
            // Show弹出层
            open: false,
            // SearchParams
            queryParams: {
                pageNum: 1,
                pageSize: 10,
                carrierNo: null,
                carrierName: null,
                address: null,
                mobile: null,
                tel: null,
                contact: null,
                level: null,
                email: null,
            },
            // 表Params
            form: {},
            // 表校验
            rules: {
                carrierNo: [
                    { required: true, message: "No. is required", trigger: "blur" }
                ],
                carrierName: [
                    { required: true, message: "Name is required", trigger: "blur" }
                ],
            },
            columns: [
                { key: 1, label: "No.", visible: true },
                { key: 2, label: "Name", visible: true },
                { key: 3, label: "Address", visible: true },
                { key: 4, label: "Phone", visible: true },
                 { key: 6, label: "Contacts", visible: true },
                { key: 7, label: "Leave", visible: true },
                { key: 8, label: "Email", visible: true },
                { key: 9, label: "Remark", visible: true },
            ],
            showMoreCondition: false
        };
    },
    created() {
        this.getList();
    },
    methods: {
        /** Search Carrier列表 */
        getList() {
            this.loading = true;
            const { pageNum, pageSize } = this.queryParams;
            const query = { ...this.queryParams, pageNum: undefined, pageSize: undefined };
            const pageReq = { page: pageNum - 1, size: pageSize };
            listWmsCarrier(query, pageReq).then(response => {
                const { content, totalElements } = response;
                this.wmsCarrierList = content;
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
                carrierNo: null,
                carrierName: null,
                address: null,
                mobile: null,
                tel: null,
                contact: null,
                level: null,
                email: null,
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
            this.ids = selection.map(item => item.id);
            this.single = selection.length !== 1;
            this.multiple = !selection.length;
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
            const id = row.id || this.ids;
            getWmsCarrier(id).then(response => {
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
                        updateWmsCarrier(this.form).then(response => {
                            this.$modal.msgSuccess("Modify Successful");
                            this.open = false;
                            this.getList();
                            this.$store.dispatch('wms/getCarrier')
                        });
                    }
                    else {
                        addWmsCarrier(this.form).then(response => {
                            this.$modal.msgSuccess("Add Successful");
                            this.open = false;
                            this.getList();
                            this.$store.dispatch('wms/getCarrier')
                        });
                    }
                }
            });
        },
        /** DeleteButtonOperate */
        handleDelete(row) {
            const ids = row.id || this.ids;
            this.$modal.confirm(" Do you want delete  CarrierNo.\"" + ids + "\"？").then(function () {
                return delWmsCarrier(ids);
            }).then(() => {
                this.getList();
                this.$store.dispatch('wms/getCarrier')
                this.$modal.msgSuccess("Delete Successful");
            }).catch(() => { });
        },
        /** ExportButtonOperate */
        handleExport() {
            const queryParams = this.queryParams;
            this.$modal.confirm("Export All Carrier？").then(() => {
                this.exportLoading = true;
                return exportWmsCarrier(queryParams);
            }).then(response => {
                this.$download.download(response);
                this.exportLoading = false;
            }).catch(() => { });
        }
    },
    components: { WmsTable }
};
</script>
