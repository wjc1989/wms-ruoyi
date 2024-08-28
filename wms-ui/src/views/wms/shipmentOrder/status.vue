<template>
  <div class="shipment-order-status-wrapper app-container" v-loading="loading">
    <div class="shipment-order-content">
      <el-form label-width="138px" :model="form" ref="form" :rules="rules">
        <el-row :gutter="20">
          <el-col :span="10">
            <el-form-item label="Outbound Status" prop="shipmentOrderNo">
              <el-tag> {{ shipmentStatusMap.get(form.shipmentOrderStatus + '') }}</el-tag>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="Outbound Type" prop="shipmentOrderType">
              {{ selectDictLabel(dict.type.wms_shipment_type, form.shipmentOrderType) }}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="10">
            <el-form-item label="Outbound No." prop="shipmentOrderNo">{{ form.shipmentOrderNo }}</el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="Project" prop="orderNo">{{ form.orderNo }}</el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="10">
            <el-form-item label="Custom" prop="customerId">{{ customerMap.get(form.customerId) }}</el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="Remark" prop="remark">{{ form.remark }}</el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-divider/>

      <div class="flex-center mb8">
        <div class="flex-one large-tip bolder-font">Logistics</div>
        <div class="ops">
          <el-button v-if="mergeDetailStatusArray.length === 1" type="primary" plain="plain" size="small"
                     @click="deliveryAdd">Add Tracking
          </el-button>
        </div>
      </div>

      <div class="table">
        <WmsTable v-loading="loading" :data="form.delivery" @selection-change="handleSelectionChange">
           <el-table-column label="Carrier" align="center" :formatter="getCarrier" prop="carrierId"/>
          <el-table-column label="Outbound Date" align="center" prop="deliveryDate" width="180" >
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.deliveryDate, '') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="Tracking No." align="center" prop="trackingNo" >
            <!--        https://www.kuaidi100.com/chaxun?com=[]&nu=[]-->
            <template slot-scope="scope">
            <!--  <a
                target="_blank"
                :href=" 'https://www.kuaidi100.com/chaxun?com='+getCarrier(scope.row)+'&nu='+scope.row.trackingNo">-->
                {{
                  scope.row.trackingNo
                }}
              <!--</a>-->
            </template>
          </el-table-column>
          <el-table-column label="Remark" align="center" prop="remark" />
        </WmsTable>
      </div>
      <el-row class="mb8 mt10" :gutter="10">
        <el-col :span="1.5">
          <div class="flex-one large-tip bolder-font">Goods Detail</div>
        </el-col>
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

        <el-col :span="1.5">
          <el-button size="small" icon="el-icon-check" type="warning" plain="plain" @click="dialogFormVisible = true">
             Distribute Warehouse
          </el-button>
        </el-col>

      </el-row>

      <el-dialog title="Please select Outbound Status" :visible.sync="open" width="50%" append-to-body="append-to-body">
        <DictRadio v-model="dialogStatus" :radioData="dialogStatusRange"></DictRadio>
        <div class="dialog-footer" slot="footer">
          <el-button type="primary" @click="dialogConfirm">OK</el-button>
          <el-button @click="cancelDialog">Cancel</el-button>
        </div>
      </el-dialog>
      <div class="table">
        <el-form ref="form" :model="form" :show-message="false">
          <WmsTable :data="form.details" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center"
                             :selectable="(row)=>!row.finish"></el-table-column>
            <el-table-column label="Goods Name" align="center" prop="prod.itemName"></el-table-column>
            <el-table-column label="Goods No." align="center" prop="prod.itemNo"></el-table-column>
            <el-table-column label="Cagegory" align="center" prop="prod.itemType"></el-table-column>
            <el-table-column label="Count" align="center" width="150">
              <template slot-scope="scope">
                <el-input-number v-model="scope.row.realQuantity" :min="1" :max="2147483647" size="small"
                                 :disabled="scope.row.finish"></el-input-number>
              </template>
            </el-table-column>
            <el-table-column label="Warehouse" align="center" width="200">
              <template slot-scope="scope">
                <el-form-item :prop=" 'details.' + scope.$index + '.place' "
                              :rules="[{ required: true, message: 'Please select Warehouse', trigger: 'change' }]"
                              style="margin-bottom: 0!important;">
                  <WmsWarehouseCascader v-model="scope.row.place" size="small"
                                        :disabled="scope.row.finish" :goodsId="scope.row.itemId||scope.row.id" ></WmsWarehouseCascader>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column label="Outbound Status" width="150">
              <template slot-scope="{ row }">
                <DictSelect v-model="row.shipmentOrderStatus" :options="row.range" size="small"
                            :disabled="row.finish"></DictSelect>
              </template>
            </el-table-column>
          </WmsTable>
        </el-form>
        <el-empty v-if="!form.details || form.details.length === 0" :image-size="48"></el-empty>
      </div>
      <div class="tc mt16">
        <el-button @click="cancel">Cancel</el-button>
        <el-button @click="submitForm" type="primary" :disabled="finish">Save</el-button>
      </div>
      <!-- Add OrModifyOutbound Record对话框 -->
      <el-dialog :title="deliveryTitle" :visible.sync="deliveryOpen" width="50%" append-to-body>
        <el-form ref="deliveryForm" :model="deliveryForm" :rules="rules" label-width="118px" inline
                 class="dialog-form-two">
          <el-form-item label="Outbound " prop="shipmentOrderId">
            <el-input v-model="deliveryForm.shipmentOrderNo" disabled placeholder="Please Input Outbound "/>
          </el-form-item>
          <el-form-item label=" Carrier" prop="carrierId">
            <wms-carrier-select v-model="deliveryForm.carrierId"></wms-carrier-select>
          </el-form-item>
          <el-form-item label="Outbound Date" prop="deliveryDate">
            <el-date-picker clearable size="small"
                            v-model="deliveryForm.deliveryDate"
                            type="datetime"
                            value-format="yyyy-MM-ddTHH:mm:ss"
                            placeholder="Select Outbound Date">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="Tracking No." prop="trackingNo">
            <el-input v-model="deliveryForm.trackingNo" placeholder="Please Input Tracking No."/>
          </el-form-item>
          <el-form-item label="Remark" prop="remark">
            <el-input v-model="deliveryForm.remark" placeholder="Please Input Remark"/>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitdeliveryForm">OK</el-button>
          <el-button @click="canceldeliveryForm">Cancel</el-button>
        </div>
      </el-dialog>
      <BatchWarehouseDialog
        :visible.sync="batchDialogVisible"
        :form-data.sync="batchForm"
        @confirmed="onBatchDialogFinished"
      ></BatchWarehouseDialog>
      <el-dialog title="Auto Distribute Warehouse" :visible.sync="dialogFormVisible" width="500px">
        <el-form :model="dialogForm">
          <el-form-item label=" Distribute Plan" label-width="128px">
            <el-select v-model="dialogForm.region" style="width: 280px" placeholder="Please select  Distribute Plan">
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
import {addOrUpdateWmsShipmentOrder, allocatedInventory, getWmsShipmentOrder} from '@/api/wms/shipmentOrder'
import ItemSelect from '@/views/components/ItemSelect'
import {mapGetters} from 'vuex'
import WmsCarrier from "@/views/wms/carrier/index.vue";
import {addWmsDelivery, updateWmsDelivery} from "@/api/wms/delivery";
import BatchWarehouseDialog from "@/views/components/wms/BatchWarehouseDialog/index.vue";
import {ShipmentOrderConstant} from "@/constant/ShipmentOrderConstant.ts";

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
        date1: '',
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
      shipmentOrderId: null,
      deliveryOpen: false,
      deliveryForm: {},
      // Outbound Record表格Data
      wmsDeliveryList: [],
      columns: [
        {key: 1, label: "Outbound No.", visible: false},
        {key: 2, label: "CarrierId", visible: true},
        {key: 3, label: "Outbound Date", visible: true},
        {key: 4, label: "Tracking No.", visible: true},
        {key: 5, label: "Remark", visible: true},
      ],
    }
  },
  created() {
    const {id} = this.$route.query
    if (id) {
      this.shipmentOrderId = id
      this.loadDetail(id)
    } else {
      this.cancel()
    }
  },
  methods: {
    /** Auto Distribute  Warehouse */
    allocated() {
      allocatedInventory({id:this.shipmentOrderId,type:this.dialogForm.region}).then(response => {
        this.$modal.msgSuccess(" Distribute  Successful");
        this.dialogFormVisible = false;
        this.loadDetail(this.shipmentOrderId)
      });
    },
    /** Set Warehouse */
    onBatchSetInventory() {
      const {details} = this.form
      if (!details || details.length === 0) {
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
      this.form.details.forEach(it => {
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
    // Add物流对话框，提交Button
    submitdeliveryForm() {
      this.$refs["deliveryForm"].validate(valid => {
        if (valid) {
          if (this.deliveryForm.id != null) {
            updateWmsDelivery(this.deliveryForm).then(response => {
              this.$modal.msgSuccess("Modify Successful");
              this.deliveryOpen = false;
              this.loadDetail(this.shipmentOrderId)
            });
          } else {
            addWmsDelivery(this.deliveryForm).then(response => {
              this.$modal.msgSuccess("Add Successful");
              this.deliveryOpen = false;
              this.loadDetail(this.shipmentOrderId)
            });
          }
        }
      });
    },// Add物流对话框，CancelButton
    canceldeliveryForm() {
      this.deliveryOpen = false
    },
    // AddLogistics
    deliveryAdd() {
      this.deliveryTitle = "Add Tracking"
      this.deliveryOpen = true
      this.deliveryForm.shipmentOrderId = this.shipmentOrderId;
      this.deliveryForm.shipmentOrderNo=this.form.shipmentOrderNo;
    },
    dialogConfirm() {
      if (!this.dialogStatus) {
        this.$modal.alert('Please select Outbound Status')
        return
      }
      this.form.details.forEach(detail => {
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
      this.$tab.closeOpenPage({path: '/shipmentOrder'})
    },
    /** 提交Button */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (!valid) {
          this.$notify({
            title: 'Warning',
            message: "Verification failed",
            type: 'warning'
          });
          return
        }
        const details = this.form.details.map(it => {
          if (it.place) {
            it.prod.warehouseId = it.place[0]
            it.prod.areaId = it.place[1]
            it.prod.rackId = it.place[2]
          } else {
            it.prod.warehouseId = null
            it.prod.areaId = null
            it.prod.rackId = null
          }
          return {
            id: it.id,
            itemId: it.prod.id,
            rackId: it.prod.rackId,
            areaId: it.prod.areaId,
            warehouseId: it.prod.warehouseId,
            planQuantity: it.planQuantity,
            realQuantity: it.realQuantity,
            shipmentOrderStatus: it.shipmentOrderStatus,
            delFlag: 0
          }
        })
        const req = {...this.form, details}
        addOrUpdateWmsShipmentOrder(req).then(response => {
          if (response.code == 398) {
            return
          }
          this.$modal.msgSuccess(this.form.id ? 'Modify Successful' : 'Add Successful')
          this.cancel()
        })
      })
    },
    loadDetail(id) {
      this.loading = true
      getWmsShipmentOrder(id).then(response => {
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



          it.finish = it.shipmentOrderStatus === 13;
          if(it.shipmentOrderStatus==11){
            it.shipmentOrderStatus=13;
          }
          it.realQuantity=it.planQuantity;
        })

        this.sourceDetails = details.map(it => ({...it}))
        this.finish = details.filter(it => !it.finish)?.length === 0
        this.form = {
          ...response,
          details
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
