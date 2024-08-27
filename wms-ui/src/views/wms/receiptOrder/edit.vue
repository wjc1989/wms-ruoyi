<template>
  <div class="receipt-order-edit-wrapper app-container">
    <div class="receipt-order-content">
      <el-form label-width="138px" :model="form" ref="form" :rules="rules">
        <el-form-item label="Inbound Order No." prop="receiptOrderNo">
          <el-input class="w200" v-model="form.receiptOrderNo" placeholder="Inbound No." disabled="disabled"></el-input>
        </el-form-item>

        <el-form-item label="Supplier" prop="supplierId">
          <WmsSupplierSelect v-model="form.supplierId"></WmsSupplierSelect>
          <el-button type="primary"   size="small" @click="addSupplier" style="margin-left: 10px">+</el-button>
        </el-form-item>
 <!--       <el-form-item label="Amount" prop="payableAmount" v-show="hasSupplier">
          <el-input-number v-model="form.payableAmount" :precision="2" :min="0" label="Please Input Amount"></el-input-number>
        </el-form-item>-->
        <el-form-item label="Project" prop="orderNo">
          <el-input v-model="form.orderNo" placeholder="Please Input Project"></el-input>
        </el-form-item>
        <el-form-item label="Contact" prop="contact">
          <el-input v-model="form.contact" placeholder="Please Input Contact"></el-input>
        </el-form-item>
        <el-form-item label="Phone" prop="phone">
          <el-input v-model="form.phone"  placeholder="Please Input Phone"></el-input>
        </el-form-item>
        <el-form-item label="Remark" prop="remark">
          <el-input v-model="form.remark" placeholder="Remark...Maximum 100 characters" rows="3" maxlength="100" type="textarea"
                    show-word-limit="show-word-limit"></el-input>
        </el-form-item>
      </el-form>
      <el-divider></el-divider>
      <div class="flex-center mb8">
        <div class="flex-one large-tip bolder-font">
          <el-row class="mb8 mt10" :gutter="10">
            <el-col :span="1.5">
              <div class="flex-one large-tip bolder-font">Goods Detail</div>
            </el-col>
            <el-col :span="1.5">
              <el-button size="small" type="success" plain="plain" icon="el-icon-delete-location"
                         @click="onBatchSetInventory"  >
                Set Warehouse
              </el-button>
            </el-col>
          </el-row>
        </div>
        <div class="ops">
          <el-button type="primary" plain="plain" size="small" @click="showAddItem">Add Item</el-button>
        </div>
      </div>
      <div class="table">
        <WmsTable :data="form.details" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center"></el-table-column>
          <el-table-column label="Goods Name" align="center" prop="prod.itemName"></el-table-column>
          <el-table-column label="Goods No." align="center" prop="prod.itemNo"></el-table-column>
          <el-table-column label="Category" align="center" prop="prod.itemTypeName"></el-table-column>
          <el-table-column label="Count" align="center" prop="planQuantity" width="150">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.planQuantity" placeholder="Count" :min="1" size="small"
                               :max="2147483647"></el-input-number>
            </template>
          </el-table-column>
          <el-table-column label="Warehouse" align="center" width="200">
            <template slot-scope="scope">
              <WmsWarehouseCascader v-model="scope.row.place" size="small"></WmsWarehouseCascader>
            </template>
          </el-table-column>
 <!--         <el-table-column label="Amount" align="center" width="150">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.money" :precision="2" @change="selectMoney" size="small"
                               :min="0" label="Please Input Amount"></el-input-number>
            </template>
          </el-table-column>-->
          <el-table-column label="Operate" align="center">
            <template slot-scope="scope">
              <a class="red" @click="form.details.splice(scope.$index, 1)">Delete</a>
            </template>
          </el-table-column>
        </WmsTable>
        <!-- <el-empty v-if="!form.details || form.details.length === 0" :image-size="48"></el-empty> -->
      </div>
 <!--     <div class="tc mt16">
        <el-button type="primary" plain="plain" size="small" @click="showAddItem">Add Item</el-button>
      </div>-->
      <div class="tc mt16">
        <el-button @click="cancel">Close</el-button>
        <el-button @click="submitForm" type="primary">Save Draft</el-button>
        <el-button @click="submitForm(true)" type="success">Save & In Bround</el-button>
      </div>
    </div>
    <el-dialog :visible="modalObj.show" :title="modalObj.title" :width="modalObj.width" @close="modalObj.cancel">
      <template v-if="modalObj.component === 'add-item'">
        <item-select ref="item-select" :data="this.form.details"></item-select>
      </template>
      <span slot="footer">
        <el-button v-if="modalObj.cancel" @click="modalObj.cancel">Cancel</el-button>
        <el-button v-if="modalObj.ok" type="primary" @click="modalObj.ok">OK</el-button>
      </span>
    </el-dialog>
    <BatchWarehouseDialog
      :visible.sync="batchDialogVisible"
      :form-data.sync="batchForm"
      @confirmed="onBatchDialogFinished"
    ></BatchWarehouseDialog>
  </div>
</template>

<script>
import {addOrUpdateWmsReceiptOrder, getWmsReceiptOrder} from '@/api/wms/receiptOrder'
import {randomId} from '@/utils/RandomUtils'
import ItemSelect from '@/views/components/ItemSelect'
import BatchWarehouseDialog from "@/views/components/wms/BatchWarehouseDialog/index.vue";

export default {
  name: 'WmsReceiptOrder',
  components: {BatchWarehouseDialog, ItemSelect},
  dicts: ['wms_receipt_type'],
  data() {
    return {
      ids: [],
      // 非多个禁用
      multiple: true,
      // Set Warehouse
      batchDialogVisible: false,
      readOnly:false,
      batchForm: {
        place: []
      },
      // 表Params
      form: {
        details: [],
        payableAmount: 0.00,
        supplierId: null
      },
      // 表校验
      rules: {},
      modalObj: {
        show: false,
        title: '',
        width: '50%',
        component: null,
        model: {},
        ok: () => {
        },
        cancel: () => {
        }
      },
      hasSupplier: false
    }
  },
  // watch: {
  //   'form.supplierId': {
  //     immediate: true,
  //     handler(value) {
  //       if (value) {
  //         this.hasSupplier = true
  //       } else {
  //         this.hasSupplier = false
  //       }
  //     }
  //   }
  // },
  created() {

    const {id} = this.$route.query
    if (id) {
      this.loadDetail(id)
    } else {
      this.reset()
    }
  },
  methods: {

    addSupplier(){
      this.$router.push({path: '/basic/supplier'})
    },
    // 多选框选中Data
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.multiple = !selection.length
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
    /** 统计InboundAmount */
    selectMoney() {
      let sum = 0;
      this.form.details.map(item => {
        if (!isNaN(parseFloat(item.money))) {
          sum += item.money
        }
        return item.money
      })
      this.form.payableAmount = sum
    },
    cancel() {
      this.$tab.closeOpenPage({path: '/receiptOrder'})
    },
    inbround(){

    },
    /** 提交Button */
    submitForm(isInbround) {
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
            money: it.money,
            warehouseId: it.prod.warehouseId,
            planQuantity: it.planQuantity,
            realQuantity: it.planQuantity,
            receiptOrderStatus: isInbround?3:0,//it.receiptOrderStatus,
            delFlag: 0
          }
        })
        const req = {...this.form, details}

        addOrUpdateWmsReceiptOrder(req).then(response => {
          this.$modal.msgSuccess(this.form.id ? 'Modify Successful' : 'Add Successful');
          this.cancel();
        })
      })
    },
    loadDetail(id) {
      getWmsReceiptOrder(id).then(response => {
        const {details, items} = response
        const map = {};
        //循环商品放入map
        (items || []).forEach(it => {
          map[it.id] = it
        });
        //循环入库明细把商品信息填上
        details && details.forEach(it => it.prod = map[it.itemId])

        this.form = {
          ...response,
          details
        }
        console.log("loadDetail-form:",this.form)
      })
    },
    // 表Reset
    reset() {
      this.form = {
        id: null,
        receiptOrderNo: 'R-' + randomId(),
        receiptOrderType: 1,
        supplierId: null,
        orderNo: null,
        receiptOrderStatus: 0,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        details: [],
        contact:null,
        phone:null
      }
      this.resetForm('form')
    },
    confirmSelectItem() {
      const value = this.$refs['item-select'].getRightList()
      this.form.details = value.map(it => {
        return {
          id: it.id,
          prod: it,
          planQuantity: null,
          realQuantity: null,
          place: [],
          receiptOrderStatus: 0,
          delFlag: 0
        }
      })
      this.closeModal()
    },
    closeModal() {
      this.modalObj.show = false
    },
    showAddItem() {
      try {
        this.$refs['item-select'].initDetailsList(this.form.details)
      } catch (err) {

      }
      const ok = () => this.confirmSelectItem()
      const cancel = () => this.closeModal()
      this.modalObj = {
        show: true,
        title: 'Add Item',
        width: '50%',
        component: 'add-item',
        model: {},
        ok,
        cancel
      }
    }
  }
}
</script>
<style lang="stylus">
.receipt-order-edit-wrapper
  .receipt-order-content
    width 80%
    min-width 900px
    margin 0 auto
</style>
