<template>
  <div class="">
    <el-form class="ry_form" v-show="showSearch" :inline="true" label-width="130px" :model="queryParams" ref="queryForm"
             size="medium">
      <el-form-item label="Outbound Status" prop="shipmentOrderStatus">
        <DictRadio v-model="queryParams.shipmentOrderStatus" :radioData="dict.type.wms_shipment_status" :showAll="'all'"
                   size="small" @change="handleQuery"></DictRadio>
      </el-form-item>
      <el-form-item label="Outbound Type" prop="shipmentOrderType">
        <DictRadio v-model="queryParams.shipmentOrderType" :radioData="dict.type.wms_shipment_type" :showAll="'all'"
                   size="small" @change="handleQuery"></DictRadio>
      </el-form-item>
      <el-form-item label="Outbound No." prop="shipmentOrderNo">
        <el-input v-model="queryParams.shipmentOrderNo" clearable="clearable" placeholder="Please Input Outbound No." size="small"
                  @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="Project" prop="orderNo">
        <el-input v-model="queryParams.orderNo" clearable="clearable" placeholder="Please Input No." size="small"
                  @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="Custom" prop="customerId">
        <WmsCustomerSelect v-model="queryParams.customerId" size="small"></WmsCustomerSelect>
      </el-form-item>
      <el-form-item class="flex_one tr">
        <el-button icon="el-icon-search" size="mini" type="primary" @click="handleQuery">Search</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">Reset</el-button>
      </el-form-item>
    </el-form>
    <el-row class="mb8" :gutter="10">
      <el-col :span="1.5">
        <el-button v-hasPermi="['wms:shipmentOrder:add']" icon="el-icon-plus" plain="plain" size="mini" type="primary"
                   @click="handleAdd()">New
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button icon="el-icon-check" size="mini" type="warning" :disabled="!waveAble"
                   @click="handleWave()">Wave Task
        </el-button>
      </el-col>
      <right-toolbar  :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <WmsTable v-loading="loading" :data="wmsShipmentOrderList" @selection-change="handleSelectionChange">
      <el-table-column align="center" type="selection" width="55"></el-table-column>
      <el-table-column  align="center" label="Outbound No."
                       prop="shipmentOrderNo"></el-table-column>
      <el-table-column  align="center" label="Outbound Type">
        <template slot-scope="scope">
          <el-tag effect="plain" size="medium" :type="getShipmentOrderTypeTag(scope.row)">
            {{ getShipmentOrderType(scope.row) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column  align="center" :formatter="getCustomer" label="Custom"></el-table-column>
      <el-table-column  align="center" label="Project" prop="orderNo"></el-table-column>
      <el-table-column  align="center" label="Outbound Status">
        <template slot-scope="scope">
          <el-tag effect="plain" size="medium" :type="getShipmentOrderStatusTag(scope.row)">
            {{ getShipmentOrderStatus(scope.row) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column  align="center" label="Wave No." prop="waveNo"></el-table-column>
      <el-table-column  align="center" label="Remark" prop="remark">
        <template v-slot="{ row }">
          <el-popover placement="left" width="300" trigger="hover" :content="row.remark" popper-class="popperOptions">
            <p class="showOverTooltip" slot="reference">{{ row.remark }}</p>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column align="center" class-name="small-padding fixed-width" label="Operate">
        <template v-slot="{ row }">
          <el-button v-hasPermi="['wms:shipmentOrder:edit']"
                     v-if="ShipmentOrderConstant.Status.NOT_IN === row.shipmentOrderStatus && !row.waveNo" icon="el-icon-edit"
                     size="mini" type="text" @click.stop="handleUpdate(row)">Modify
          </el-button>
          <el-button v-hasPermi="['wms:shipmentOrder:remove']" icon="el-icon-delete" size="mini" type="text"
                     v-if="!row.waveNo && row.shipmentOrderStatus !== ShipmentOrderConstant.Status.ALL_IN" @click.stop="handleDelete(row)">Delete
          </el-button>
          <el-button v-hasPermi="['wms:shipmentOrder:status']" v-if="row.detailCount && !row.waveNo && row.shipmentOrderStatus !== ShipmentOrderConstant.Status.ALL_IN" icon="el-icon-truck" size="mini"
                     type="text" @click.stop="handleStatus(row)">Outbound
          </el-button>
          <el-button icon="el-icon-printer" size="mini" type="text" @click.stop="printOut(row,true)">Print</el-button>
        </template>
      </el-table-column>
    </WmsTable>
    <pagination v-show="total>0" :limit.sync="queryParams.pageSize" :page.sync="queryParams.pageNum" :total="total"
                @pagination="getList"></pagination>
    <el-dialog :visible.sync="modalObj.show" :title="modalObj.title" :width="modalObj.width">
      <template v-if="modalObj.component === 'print-type'">
        <el-radio-group v-model="modalObj.form.value">
         <!-- <el-radio :label="1">lodopPrint</el-radio>-->
          <el-radio :label="2" >Browser Print</el-radio>
        </el-radio-group>
      </template>
      <template v-if="modalObj.form.value === 2 || modalObj.component === 'window-print-preview'">
        <shipment-order-print :row="modalObj.form.row" ref="receiptOrderPrintRef"></shipment-order-print>
      </template>
      <template slot="footer" class="dialog-footer">
        <el-button @click="modalObj.cancel">Cancel</el-button>
        <el-button @click="modalObj.ok" type="primary">OK</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import {
  listWmsShipmentOrder,
  delWmsShipmentOrder,
  exportWmsShipmentOrder,
  getWmsShipmentOrder
} from '@/api/wms/shipmentOrder'
import {mapGetters} from 'vuex'
import {STOCK_OUT_TEMPLATE} from '@/utils/printData'
import ShipmentOrderPrint from '@/views/wms/shipmentOrder/ShipmentOrderPrint'
import {ShipmentOrderConstant} from '@/constant/ShipmentOrderConstant.ts'
import {addWave} from "@/api/wms/wave";

export default {
  name: 'wmsShipmentOrder',
  components: {ShipmentOrderPrint},
  dicts: ['wms_shipment_type', 'wms_shipment_status'],
  computed: {
    ShipmentOrderConstant() {
      return ShipmentOrderConstant
    },
    ...mapGetters(['customerMap', 'warehouseMap', 'areaMap', 'rackMap']),
    shipmentTypeMap() {
      let obj = this.dict.type.wms_shipment_type.map(item => [item.value, item.label])
      let map = new Map(obj)
      return map
    },
    shipmentStatusMap() {
      let obj = this.dict.type.wms_shipment_status.map(item => [item.value, item.label])
      let map = new Map(obj)
      return map
    }
  },
  data() {
    return {
      modalObj: {
        show: false,
        title: 'Select Print',
        width: '520px',
        component: null,
        form: {
          value: 2,
          row: null
        },
        ok: () => {
        },
        cancel: () => {
        }
      },
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
      // ShowWave 作业Button
      waveAble: false,
      // ShowSearch
      showSearch: true,
      // 总条数
      total: 0,
      // Outbound 表格Data
      wmsShipmentOrderList: [],
      // SearchParams
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        shipmentOrderNo: null,
        shipmentOrderType: null,
        customerId: null,
        orderNo: null,
        shipmentOrderStatus: null
      },
      // 表校验
      rules: {},
      columns: [
        {key: 1, label: 'Outbound No.', visible: true},
        {key: 2, label: 'Outbound Type', visible: true},
        {key: 3, label: 'Supplier', visible: true},
        {key: 4, label: 'No.', visible: true},
        {key: 5, label: 'Outbound Status', visible: true},
        {key: 6, label: 'Remark', visible: true}
      ]
    }
  },
  created() {
    this.getList()
  },
  methods: {
    handleWave() {
      addWave({ids:this.ids}).then(res => {
        this.$emit('switchTab',{} )
        this.getList()
      })
    },
    getShipmentOrderTypeTag(row) {
      switch (row.shipmentOrderType) {
        case ShipmentOrderConstant.Type.SALE:
          return 'success'
        case ShipmentOrderConstant.Type.OUTSOURCING:
          return 'warning'
        case ShipmentOrderConstant.Type.TRANSFER:
          return 'danger'
      }
    },
    getShipmentOrderStatusTag(row) {
      switch (row.shipmentOrderStatus) {
        case ShipmentOrderConstant.Status.NOT_IN:
          return 'info'
        case ShipmentOrderConstant.Status.PART_IN:
          return 'warning'
        case ShipmentOrderConstant.Status.DROP:
          return 'danger'
        case ShipmentOrderConstant.Status.ALL_IN:
          return 'success'
      }
    },
    getShipmentOrderType(row) {
      return this.shipmentTypeMap.get(row.shipmentOrderType + '')
    },
    getShipmentOrderStatus(row) {
      return this.shipmentStatusMap.get(row.shipmentOrderStatus + '')
    },
    getCustomer(row, column) {
      return this.customerMap.get(row.customerId)
    },
    /** SearchOutbound 列表 */
    getList() {
      this.loading = true
      const {pageNum, pageSize} = this.queryParams
      const query = {...this.queryParams, pageNum: undefined, pageSize: undefined}
      const pageReq = {page: pageNum - 1, size: pageSize}
      listWmsShipmentOrder(query, pageReq).then(response => {
        const {content, totalElements} = response
        this.wmsShipmentOrderList = content
        this.total = totalElements
        this.loading = false
      })
    },
    /** SearchButtonOperate */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** ResetButtonOperate */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中Data
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.waveAble = selection.length > 1
      this.multiple = !selection.length
    },
    /** AddButtonOperate */
    handleAdd() {
      this.$router.push({path: '/wms/shipmentOrder/edit'})
    },
    printOut(row, print) {
      this.modalObj = {
        show: true,
        title: 'Select Print',
        width: '520px',
        component: 'print-type',
        form: {
          value: 2,
          row
        },
        ok: () => {
          this.modalObj.show = false
          if (this.modalObj.form.value === 1) {
            this.doPrintOut(row, false)
          } else {
            this.windowPrintOut(row, print)
          }
        },
        cancel: () => {
          this.modalObj.show = false
        }
      }
    },
    doPrintOut(row, print) {
      this.getDetail(row).then(result => {
        if (print) {
          this.$lodop.print(STOCK_OUT_TEMPLATE, [result])
        } else {
          this.$lodop.preview(STOCK_OUT_TEMPLATE, [result])
        }
      })
    },
    windowPrintOut(row, print) {
      this.getDetail(row).then(res => {
        if (print) {
          this.modalObj.form.row = res
          this.$nextTick(() => {
            this.$refs['receiptOrderPrintRef'].start()
          })
          return
        }
        this.$nextTick(() => {
          this.modalObj = {
            show: true,
            title: 'Browser Printing Preview',
            width: '800px',
            component: 'window-print-preview',
            form: {
              value: 1,
              row,
              print
            },
            ok: () => {
              this.modalObj.show = false
            },
            cancel: () => {
              this.modalObj.show = false
            }
          }
        })
      })
    },
    getDetail(row) {
      //Search Detail
      return getWmsShipmentOrder(row.id).then(response => {
        const {details, items} = response
        const map = {};
        (items || []).forEach(it => {
          map[it.id] = it
        })
        let detailList = [], totalCount = 0
        details && details.forEach(it => {
          const prod = map[it.itemId]
          totalCount += it.planQuantity
          let place = this.warehouseMap.get(it.warehouseId)
          if (it.areaId) {
            place += `/${this.areaMap.get(it.areaId)}`
          }
          if (it.rackId) {
            place += `/${this.rackMap.get(it.rackId)}`
          }
          detailList.push({
            itemName: prod.itemName,
            itemNo: prod.itemNo,
            itemType: prod.itemType,
            planQuantity: it.planQuantity,
            place
          })
        })
        return {
          remark: row.remark,
          shipmentOrderNo: row.shipmentOrderNo,
          customerName: this.customerMap.get(row.customerId),
          orderNo: row.orderNo,
          shipmentType: this.shipmentTypeMap.get(row.shipmentOrderType + ''),
          createTime: row.createTime,
          details: detailList,
          totalCount
        }
      })
    },
    /** ModifyButtonOperate */
    handleUpdate(row) {
      const id = row.id || this.ids
      this.$router.push({path: '/wms/shipmentOrder/edit', query: {id}})
    },
    handleStatus(row) {
      const id = row.id || this.ids
      this.$router.push({path: '/wms/shipmentOrder/status', query: {id}})
    },
    /** DeleteButtonOperate */
    handleDelete(row) {
      const ids = row.id || this.ids
      const shipmentOrderNo = row.shipmentOrderNo
      this.$modal.confirm(' Do you want delete Outbound No."' + shipmentOrderNo + '"？').then(function () {
        return delWmsShipmentOrder(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('Delete Successful')
      }).catch(() => {
      })
    },
    /** ExportButtonOperate */
    handleExport() {
      const queryParams = this.queryParams
      this.$modal.confirm('Export AllOutbound ？').then(() => {
        this.exportLoading = true
        return exportWmsShipmentOrder(queryParams)
      }).then(response => {
        this.$download.download(response)
        this.exportLoading = false
      }).catch(() => {
      })
    }
  }
}
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
