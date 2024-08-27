<template>
  <div class="app-container">
    <el-tabs v-model="activeName" >
      <el-tab-pane label="Order" name="first"></el-tab-pane>
      <el-tab-pane label="Wave Task" name="second"></el-tab-pane>
    </el-tabs>
    <Dashborad v-if="activeName == 'first'" @switchTab="activeName = 'second'"></Dashborad>
    <wave v-if="activeName == 'second'" ></wave>
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
import {ShipmentOrderConstant} from '@/constant/ShipmentOrderConstant.ts'
import {addWave} from "@/api/wms/wave";
import Dashborad from "@/views/wms/shipmentOrder/dashborad.vue";
import wave from "@/views/wms/wave/index.vue";

export default {
  name: 'wmsShipmentOrder',
  components: {Dashborad,wave},
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
      activeName: 'first',
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
        {key: 1, label: 'ID', visible: true},
        {key: 2, label: 'Type', visible: true},
        {key: 3, label: 'Supplier', visible: true},
        {key: 4, label: 'No.', visible: true},
        {key: 5, label: 'Status', visible: true},
        {key: 6, label: 'Remark', visible: true}
      ]
    }
  },
  created() {
    // this.getList()
  },
  methods: {
    handleWave() {
      addWave({ids: this.ids}).then(res => {
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
      this.$modal.confirm('Do you want delete "' + shipmentOrderNo + '"？').then(function () {
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
      this.$modal.confirm('Export All？').then(() => {
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
