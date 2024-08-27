<template>
  <div class="shipment-order-status-wrapper app-container" v-loading="loading">
    <div class="shipment-order-content">
      <el-row class="mb8 mt10" :gutter="10">
        <el-col :span="1.5">
          <div class="flex-one large-tip bolder-font">Outbound  Detail</div>
        </el-col>
      </el-row>

      <el-dialog title="Please select Outbound Status" :visible.sync="open" width="50%" append-to-body="append-to-body">
        <DictRadio v-model="dialogStatus" :radioData="dialogStatusRange"></DictRadio>
        <div class="dialog-footer" slot="footer">
          <el-button type="primary" @click="dialogConfirm">OK</el-button>
          <el-button @click="cancelDialog">Cancel</el-button>
        </div>
      </el-dialog>

      <WmsTable :data="form.details" @selection-change="handleSelectionChange">
        <el-table-column label="Outbound " align="center" width="200" prop="orderNo"/>
        <el-table-column label="Goods Name" align="center" prop="prod.itemName"></el-table-column>
        <el-table-column label="Goods No." align="center" prop="prod.itemNo"></el-table-column>
        <el-table-column label="Plan Count" align="center" prop="planQuantity"></el-table-column>

      </WmsTable>
      <el-row class="mb8 mt20" :gutter="10">
        <el-col>
          <div class="flex-one large-tip bolder-font">Remark</div>
          <el-input class="mt10" type="textarea" v-model="form.remark"/>
        </el-col>
      </el-row>
      <el-row class="mb8 mt20" :gutter="10">
        <el-col :span="1.5">
          <div class="flex-one large-tip bolder-font">拣货 Detail</div>
        </el-col>

        <el-col :span="1.5">
          <el-button size="small" icon="el-icon-check" type="warning" plain="plain" @click="dialogFormVisible = true">
             Distribute Warehouse
          </el-button>
        </el-col>
<!--        <el-col :span="1.5">-->
<!--          <el-button size="small" type="danger" plain="plain" @click="onCancelInventory">-->
<!--            Cancel Distribute -->
<!--          </el-button>-->
<!--        </el-col>-->
        <el-col :span="1.5">
          <el-button size="small" type="success" plain="plain" icon="el-icon-delete-location"
                     @click="onBatchSetInventory">
            Set Warehouse
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button v-if="mergeDetailStatusArray.length === 1" type="primary" plain="plain" size="small"
                     @click="batch">Set Outbound Status
          </el-button>
        </el-col>
      </el-row>
      <WmsTable :data="form.allocationDetails" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"
                         :selectable="(row)=>!row.finish"></el-table-column>
        <el-table-column label="Outbound " align="center" width="200" prop="orderNo"/>
        <el-table-column label="Goods Name" align="center" prop="prod.itemName"></el-table-column>
        <el-table-column label="Goods No." align="center" prop="prod.itemNo"></el-table-column>
        <el-table-column label="Count" align="center" prop="planQuantity"></el-table-column>
        <el-table-column label="Real Count" align="center" width="150">
          <template slot-scope="scope">
            <el-input-number v-model="scope.row.realQuantity" :min="1" :max="2147483647" size="small"
                             :disabled="scope.row.finish"></el-input-number>
          </template>
        </el-table-column>
        <el-table-column label="Warehouse" align="center" width="200">
          <template slot-scope="scope">
            <WmsWarehouseCascader v-model="scope.row.place" size="small"
                                  :disabled="scope.row.finish"></WmsWarehouseCascader>
          </template>
        </el-table-column>
        <el-table-column label="Outbound Status" width="150">
          <template slot-scope="{ row }">
            <DictSelect v-model="row.shipmentOrderStatus" :options="row.range" size="small"
                        :disabled="row.finish"></DictSelect>
          </template>
        </el-table-column>
      </WmsTable>
      <div class="tc mt16">
        <el-button @click="cancel">Cancel</el-button>
        <el-button @click="submitForm" type="primary" :disabled="finish">Add</el-button>
      </div>
      <BatchWarehouseDialog
        :visible.sync="batchDialogVisible"
        :form-data.sync="batchForm"
        @confirmed="onBatchDialogFinished"
      ></BatchWarehouseDialog>
      <el-dialog title="Auto Distribute Warehouse" :visible.sync="dialogFormVisible" width="400px">
        <el-form :model="dialogForm">
          <el-form-item label=" Distribute Plan" label-width="98px"
                        :rules="[{ required: true, message: 'Please select  Distribute Plan', trigger: 'change' }]">
            <el-select v-model="dialogForm.region" placeholder="Please select  Distribute Plan">
              <el-option label="Smallest inventory is prioritized" :value="1"></el-option>
              <el-option label="Largest inventory is prioritized" :value="2"></el-option>
              <el-option label="First in,first out(FIFO)" :value="3" disabled></el-option>
              <el-option label="First to expire,first out" :value="4" disabled></el-option>
              <el-option label="Appropriate inventory priority" :value="5" disabled></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">Cancel</el-button>
          <el-button type="primary" @click="allocated()">OK</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import ItemSelect from '@/views/components/ItemSelect'
import {mapGetters} from 'vuex'
import WmsCarrier from "@/views/wms/carrier/index.vue";
import BatchWarehouseDialog from "@/views/components/wms/BatchWarehouseDialog/index.vue";
import {ShipmentOrderConstant} from "@/constant/ShipmentOrderConstant.ts";
import {cancelAllocatedInventory, confirmWave, getWave, waveAllocatedInventory} from "@/api/wms/wave";

export default {
  name: 'WmsShipmentOrder',
  components: {BatchWarehouseDialog, WmsCarrier, ItemSelect},
  dicts: ['wms_shipment_type', 'wms_shipment_status'],
  computed: {
    ShipmentOrderConstant() {
      return ShipmentOrderConstant
    },
    ...mapGetters(['customerMap', 'carrierMap']),
    shipmentStatusMap() {
      let obj = this.dict.type.wms_shipment_status.map(item => [item.value, item.label])
      let map = new Map(obj)
      return map
    },
    mergeDetailStatusArray() {
      const arr = this.sourceDetails || []
      return [...new Set(arr.filter(it => it.shipmentOrderStatus !== null).map(it => it.shipmentOrderStatus))]
    },
    dialogStatusRange() {
      if (this.mergeDetailStatusArray.length !== 1) {
        return []
      }
      return this.getRange(this.mergeDetailStatusArray[0])
    }
  },
  data() {
    return {
      //  Distribute Warehouse
      dialogFormVisible: false,
      dialogForm: {
        region: 1,
      },
      // Set Warehouse
      batchDialogVisible: false,
      batchForm: {
        place: []
      },
      open: false,
      // 遮罩层
      loading: true,
      ids: [],
      // 表Params
      form: {
        details: []
      },
      sourceDetails: [],
      // 表校验
      rules: {},
      dialogStatus: null,
      // 非多个禁用
      multiple: true,
      finish: false,
      // 物流管理
      deliveryTitle: '',
      waveOrderId: null,
      columns: [
        {key: 1, label: "Outbound No.", visible: false},
        {key: 2, label: " CarrierId", visible: true},
        {key: 3, label: "Outbound Date", visible: true},
        {key: 4, label: "Tracking No.", visible: true},
        {key: 5, label: "Remark", visible: true},
      ],
    }
  },
  created() {
    // console.log(this.$route.query, "this.$route.query")
    const {id} = this.$route.query
    if (id) {
      this.waveOrderId = id
      this.loadDetail(id)
    } else {
      this.cancel()
    }
  },
  methods: {
    /** Cancel Distribute  */
    onCancelInventory(){
      cancelAllocatedInventory(this.waveOrderId).then(res=>{
        this.$modal.msgSuccess(res ? 'Modify Successful' : 'Modify失败')
        this.cancel()
      })
    },
    /** Auto Distribute  Warehouse */
    allocated() {
      waveAllocatedInventory({id:this.waveOrderId,type:this.dialogForm.region}).then(response => {
        this.$modal.msgSuccess(" Distribute  Successful");
        this.dialogFormVisible = false;
        const {details, items, allocationDetails} = response
        const map = {};
        (items || []).forEach(it => {
          map[it.id] = it
        })
        details && details.forEach(it => {
          it.prod = map[it.itemId]
          if ((!it.place || it.place.length === 0) && it.prod) {
            it.place = it.prod.place;
          }
          it.range = this.getRange(it.shipmentOrderStatus)
          it.finish = it.shipmentOrderStatus === 13
        })
        let count = 1;
        allocationDetails && allocationDetails.forEach(it => {
          it.id = count++;
          it.prod = map[it.itemId]
          if ((!it.place || it.place.length === 0) && it.prod) {
            it.place = it.prod.place;
          }
          it.range = this.getRange(it.shipmentOrderStatus)
          it.finish = it.shipmentOrderStatus === 13
        })
        this.sourceDetails = allocationDetails.map(it => ({...it}))
        this.finish = allocationDetails.filter(it => !it.finish)?.length === 0
        this.form = {
          ...response,
          details,
          allocationDetails
        }
        // this.loadDetail(this.shipmentOrderId)
      });
    },
    /** Set Warehouse */
    onBatchSetInventory() {
      const {allocationDetails} = this.form
      if (!allocationDetails || allocationDetails.length === 0) {
        this.$modal.msgError('Please Add Item')
        return
      }
      // 未选中
      if (!this.ids.length) {
        this.$modal.msgError('Please Select Item')
        return
      }
      this.batchDialogVisible = true
    },
    /** Set Warehouse 完成事件 */
    onBatchDialogFinished() {
      this.batchDialogVisible = false
      const [warehouseId, areaId, rackId] = this.batchForm.place || []
      this.form.allocationDetails.forEach(it => {
        // 仅更新已选中
        if (this.ids.includes(it.id)) {
          it.place = [warehouseId, areaId, rackId].filter(Boolean)
        }
      })
    },
    // 格式化 Carrier
    getCarrier(row, column) {
      return this.carrierMap.get(row.carrierId)
    },
    dialogConfirm() {
      if (!this.dialogStatus) {
        this.$modal.alert('Please select Outbound Status')
        return
      }
      this.form.allocationDetails.forEach(detail => {
        if (this.ids.includes(detail.id)) {
          detail.shipmentOrderStatus = this.dialogStatus
        }
      })
      this.cancelDialog()
    },
    cancelDialog() {
      this.open = false
      this.dialogStatus = null
    },
    // 多选框选中Data
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.multiple = !selection.length
    },
    batch() {
      if (this.multiple) {
        this.$modal.alert('Please Select Item')
      } else {
        this.open = true
      }
    },
    cancel() {
      this.$tab.closeOpenPage({path: '/wms/shipmentOrder'})
    },
    /** 提交Button */
    submitForm() {
      this.form.allocationDetails.forEach(it=>{
        if (it.place) {
          it.warehouseId = it.place[0]
          it.areaId = it.place[1]
          it.rackId = it.place[2]
        } else {
          it.warehouseId = null
          it.areaId = null
          it.rackId = null
        }
        it.delFlag = 0;
      })
      const req = {...this.form,id:this.waveOrderId,remark:this.form.remark}
      confirmWave(req).then(response => {
        if (response.code == 398) {
          return
        }
        this.$modal.msgSuccess('Add Successful')
        this.cancel()
      })

    },
    loadDetail(id) {
      this.loading = true
      getWave(id).then(response => {
        const {details, items} = response
        const map = {};
        (items || []).forEach(it => {
          map[it.id] = it
        })
        details && details.forEach(it => {
          it.prod = map[it.itemId]
          if ((!it.place || it.place.length === 0) && it.prod) {
            it.place = it.prod.place;
          }
          it.range = this.getRange(it.shipmentOrderStatus)
          it.finish = it.shipmentOrderStatus === 13
        })
        this.sourceDetails = details.map(it => ({...it}))
        this.finish = details.filter(it => !it.finish)?.length === 0
        this.form = {
          ...response,
          details,
          allocationDetails: details.map(it => ({...it}))
        }
      }).finally(() => {
        this.loading = false
      })
    },
    getRange(status) {
      const arr = this.dict.type.wms_shipment_status
      if (status === 4 || status === 3) {
        return arr.filter(it => +it.value === status).map(it => ({label: it.label, value: it.value}))
      }
      if (status === 2) {
        return arr.filter(it => +it.value >= status && +it.value !== 4).map(it => ({
          label: it.label,
          value: it.value
        }))
      }
      return arr.filter(it => +it.value >= status).map(it => ({label: it.label, value: it.value}))
    }
  }
}
</script>
<style lang="stylus">
.shipment-order-status-wrapper
  .shipment-order-content
    width 80%
    min-width 900px
    margin 0 auto
</style>
