<template>
  <div class="receipt-order-status-wrapper app-container" v-loading="loading">
    <div class="receipt-order-content">
      <el-form label-width="138px" :model="form" ref="form" :rules="rules">
        <el-row>
          <el-col :span="12">
            <el-form-item label="Inbound Order No." prop="receiptOrderNo">
              {{form.receiptOrderNo}}
            </el-form-item>


            <!--       <el-form-item label="Amount" prop="payableAmount" v-show="hasSupplier">
                     <el-input-number v-model="form.payableAmount" :precision="2" :min="0" label="Please Input Amount"></el-input-number>
                   </el-form-item>-->

            <el-form-item label="Contact" prop="contact">
              {{form.contact}}
            </el-form-item>
            <el-form-item label="Phone" prop="phone">
              {{form.phone}}
            </el-form-item>
            <el-form-item label="Create By" prop="remark">
              {{form.createName}}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Supplier" prop="supplierId">
              {{supplierMap.get(form.supplierId)}}
            </el-form-item>
            <el-form-item label="Project" prop="orderNo">
              {{form.orderNo}}
            </el-form-item>
            <el-form-item label="Remark" prop="remark">
              {{form.remark}}
            </el-form-item>
            <el-form-item label="Create Time" prop="remark">
              {{form.createTime}}
            </el-form-item>

          </el-col>
        </el-row>



      </el-form>
      <el-divider></el-divider>
      <el-row class="mb8 mt10" :gutter="10">
        <el-col :span="1.5">
          <div class="flex-one large-tip bolder-font">Goods Detail</div>
        </el-col>
    <!--    <el-col :span="1.5">
          <el-button size="small" type="success" plain="plain" icon="el-icon-delete-location"
                     @click="onBatchSetInventory">
            Set Warehouse
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button v-if="mergeDetailStatusArray.length === 1" type="primary" plain="plain" size="small"
                     @click="batch">Set Inbound Status
          </el-button>
        </el-col>-->

      </el-row>

      <el-dialog title="Please select Status" :visible.sync="open" width="50%" append-to-body="append-to-body">
        <DictRadio v-model="dialogStatus" :radioData="dialogStatusRange"></DictRadio>
        <div class="dialog-footer" slot="footer">
          <el-button type="primary" @click="dialogConfirm">OK</el-button>
          <el-button @click="cancelDialog">Cancel</el-button>
        </div>
      </el-dialog>
      <div class="table">
        <el-form ref="form" :model="form" :show-message="false">
          <WmsTable :data="form.details" >
            <el-table-column label="NO." width="55" type="index" align="center"></el-table-column>
            <el-table-column label="Goods Name" align="center" prop="prod.itemName"></el-table-column>
            <el-table-column label="Goods No." align="center" prop="prod.itemNo"></el-table-column>
            <el-table-column label="Category" align="center" prop="prod.itemType" class="mb20"></el-table-column>
            <el-table-column label="Plan Count" align="center" prop="planQuantity" class="mb20"></el-table-column>
            <el-table-column label="Real Count" align="center" width="150" prop="realQuantity">

            </el-table-column>
            <el-table-column label="Warehouse" align="center" width="200" prop="place">
              <template slot-scope="scope">
                <el-form-item :prop=" 'details.' + scope.$index + '.place' "
                              :rules="[{ required: true, message: 'Please select Warehouse', trigger: 'change' }]"
                              style="margin-bottom: 0!important;">
                  <WmsWarehouseCascader v-model="scope.row.place" size="small" :disabled="scope.row.finish"></WmsWarehouseCascader>
                </el-form-item>
              </template>
            </el-table-column>
<!--            <el-table-column label="Status"  align="center" width="150">
              <template slot-scope="{ row }">
                <DictSelect v-model="row.receiptOrderStatus" :options="row.range" size="small"
                            @change="setReceiptOrderStatus" :disabled="row.finish"></DictSelect>
              </template>
            </el-table-column>-->
          </WmsTable>
        </el-form>
        <el-empty v-if="!form.details || form.details.length === 0" :image-size="48"></el-empty>
      </div>
      <div class="tc mt16">
        <el-button @click="cancel">Close</el-button>
       </div>
    </div>
    <BatchWarehouseDialog
      :visible.sync="batchDialogVisible"
      :form-data.sync="batchForm"
      @confirmed="onBatchDialogFinished"
    ></BatchWarehouseDialog>
  </div>
</template>

<script>
import {addOrUpdateWmsReceiptOrder, getWmsReceiptOrder} from '@/api/wms/receiptOrder'
import ItemSelect from '@/views/components/ItemSelect'
import {mapGetters} from 'vuex'
import BatchWarehouseDialog from "@/views/components/wms/BatchWarehouseDialog/index.vue";
import {ReceiptOrderConstant} from "@/constant/ReceiptOrderConstant.ts";

export default {
  name: 'WmsReceiptOrder',
  components: {BatchWarehouseDialog, ItemSelect},
  dicts: ['wms_receipt_type', 'wms_receipt_status'],
  computed: {
    ReceiptOrderConstant() {
      return ReceiptOrderConstant
    },
    ...mapGetters(['supplierMap']),
    receiptStatusMap() {
      let obj = this.dict.type.wms_receipt_status.map(item => [item.value, item.label])
      let map = new Map(obj)
      return map
    },
    mergeDetailStatusArray() {
      const arr = this.sourceDetails || []
      return [...new Set(arr.filter(it => it.receiptOrderStatus !== null).map(it => it.receiptOrderStatus))]
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
    }
  },
  created() {
    const {id} = this.$route.query
    if (id) {
      this.loadDetail(id)
    } else {
      this.cancel()
    }
  },
  methods: {
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
    dialogConfirm() {
      if (!this.dialogStatus) {
        this.$modal.alert('Please select Status')
        return
      }
      this.form.details.forEach(detail => {
        if (this.ids.includes(detail.id)) {
          detail.receiptOrderStatus = this.dialogStatus
        }
      })
      this.setReceiptOrderStatus()
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
      this.$tab.closeOpenPage({path: '/receiptOrder'})
    },
    /** 提交Button */
    submitForm() {
      this.$refs['form'].validate((valid, msg) => {
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
            receiptOrderStatus: it.receiptOrderStatus,
            delFlag: 0
          }
        })
        const req = {...this.form, details}
        addOrUpdateWmsReceiptOrder(req).then(response => {
          this.$modal.msgSuccess(this.form.id ? 'Modify Successful' : 'Add Successful')
          this.cancel()
        })
      })
    },
    setReceiptOrderStatus() {
      this.form.receiptOrderStatus = this.getReceiptOrderStatus()
    },
    getReceiptOrderStatus() {
      let receiptOrderStatusArray = []
      this.form.details.map(it => {
        receiptOrderStatusArray.push(Number(it.receiptOrderStatus))
      })
      if (receiptOrderStatusArray.length == 0) {
        return 0
      }
      for (let i = 0; i < 5; i++) {
        if (receiptOrderStatusArray.every((item) => item == i)) {
          return i
        }
      }
      if (receiptOrderStatusArray.includes(3) || receiptOrderStatusArray.includes(2)) {
        if (receiptOrderStatusArray.includes(0) || receiptOrderStatusArray.includes(1)) {
          return 2
        }
      }
      if (receiptOrderStatusArray.includes(1)) {
        return 1
      } else if (receiptOrderStatusArray.includes(2)) {
        return 2
      } else if (receiptOrderStatusArray.includes(0)) {
        return 0
      } else if (receiptOrderStatusArray.includes(3)) {
        return 3
      }
    },
    loadDetail(id) {
      this.loading = true
      getWmsReceiptOrder(id).then(response => {
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
          it.range = this.getRange(it.receiptOrderStatus)
          it.finish = it.receiptOrderStatus === 3
          it.realQuantity=it.planQuantity;
        })
        this.finish = details.filter(it => !it.finish)?.length === 0
        this.sourceDetails = details.map(it => ({...it}))
        this.form = {
          ...response,
          details
        }
      }).finally(() => {
        this.loading = false
      })
    },
    getRange(status) {
      const arr = this.dict.type.wms_receipt_status
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
.receipt-order-status-wrapper
  .receipt-order-content
    width 80%
    min-width 900px
    margin 0 auto
</style>
