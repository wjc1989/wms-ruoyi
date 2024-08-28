<template>
  <div class="receipt-order-edit-wrapper app-container">
    <div class="receipt-order-content">
      <el-form label-width="108px" :model="form" ref="form" :rules="rules">
        <el-form-item label="Count" prop="inventoryCheckNo">
          <el-input class="w200" v-model="form.inventoryCheckNo" placeholder="Count" disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="Warehouse" prop="supplierId">
          <wms-warehouse-cascader v-model="form.place" size="small"></wms-warehouse-cascader>
        </el-form-item>
        <el-form-item label="Change" prop="inventoryCheckTotal">
          <el-input-number v-model="inventoryCheckTotal" :precision="2" disabled></el-input-number>
        </el-form-item>
        <el-form-item label="Remark" prop="remark">
          <el-input v-model="form.remark" placeholder="Remark...Maximum 100 characters" rows="3" maxlength="100" type="textarea"
            show-word-limit="show-word-limit"></el-input>
        </el-form-item>
        <el-form-item label="Attachment" prop="attachment">
          <file-upload v-model="form.attachment" :file-type="types"></file-upload>
        </el-form-item>
      </el-form>
      <el-divider></el-divider>
      <div class="flex-center mb8">
        <div class="flex-one large-tip bolder-font">Goods Detail</div>
        <div class="ops">
          <el-button type="primary" plain="plain" size="small" @click="showAddItem">Select Goods</el-button>
        </div>
      </div>
      <div class="table">
        <table class="common-table">
          <tr>
            <th>Goods No.</th>
            <th>Goods Name</th>
            <th>Warehouse</th>
            <th>Expected Inventory</th>
            <th>Real Inventory</th>
            <th>Change</th>
            <th>Operate</th>
          </tr>
          <tr v-for="(it, index) in form.details">
            <td align="center">{{ it.prod.itemNo }}</td>
            <td align="center">{{ it.prod.itemName }}</td>
            <td align="center">
              <WmsWarehouseCascader :disabled="!it.isNew" v-model="it.place" size="small" :goodsId="it.prod.itemId||it.id"></WmsWarehouseCascader>
            </td>
            <td align="center">
              {{ it.quantity }}
            </td>
            <td align="center">
              <el-input-number v-model="it.checkQuantity" :precision="2" :min="0" label="Please Input Inventory"></el-input-number>
            </td>
            <td align="center">
              {{ it.checkQuantity - it.quantity }}
            </td>
            <td align="center">
              <a class="blue" @click="showRemarkItem(it)">Remark</a>&nbsp;
              <a class="red" @click="form.details.splice(index, 1)">Delete</a>
            </td>
          </tr>
        </table>
        <!-- <el-empty v-if="!form.details || form.details.length === 0" :image-size="48"></el-empty> -->
      </div>
      <!--<div class="tc mt16" >
        <el-button type="primary" plain="plain" size="small" @click="showAddItem">Select Goods</el-button>
      </div>-->
      <div class="tc mt16">
        <el-button @click="cancel">Cancel</el-button>
        <el-button @click="submitForm(11)" type="primary">Save</el-button>
        <el-button @click="submitFinishForm" type="success">Count End</el-button>
      </div>
    </div>
    <el-dialog :visible="modalObj.show" :title="modalObj.title" :width="modalObj.width" @close="modalObj.cancel">
      <template v-if="modalObj.component === 'add-item'">
        <item-select ref="item-select"    ></item-select>
      </template>
      <span slot="footer">
        <el-button v-if="modalObj.cancel" @click="modalObj.cancel">Cancel</el-button>
        <el-button v-if="modalObj.ok" type="primary" @click="modalObj.ok">OK</el-button>
      </span>
    </el-dialog>
    <el-dialog :visible="remarkModalObj.show" :title="remarkModalObj.title" :width="remarkModalObj.width" @close="remarkModalObj.cancel">
      <template v-if="remarkModalObj.component === 'remark-item'">
        <el-input v-model="remarkModalObj.content" placeholder="Remark...Maximum 100 characters" rows="4" maxlength="100" type="textarea"
            show-word-limit="show-word-limit"></el-input>
      </template>
      <template v-slot:footer>
        <el-button v-if="remarkModalObj.cancel" @click="remarkModalObj.cancel">Cancel</el-button>
        <el-button v-if="remarkModalObj.ok" type="primary" @click="remarkModalObj.ok">OK</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { randomId } from '@/utils/RandomUtils'
import ItemSelect from '@/views/components/ItemSelect'
import { listWmsInventory } from "@/api/wms/inventory";
import { addOrUpdateWmsInventoryCheck, getWmsInventoryCheck } from "@/api/wms/inventoryCheck";
import { Message } from "element-ui";


export default {
  name: 'WmsInventoryCheck',
  components: { ItemSelect },
  data() {
    return {
      types: ['png', 'jpg', 'jpeg'],
      // 表Params
      form: {
        details: []
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
      //AddRemark对话框
      remarkModalObj: {
        show: false,
        title: '',
        width: '50%',
        component: null,
        model: {},
        content: '',
        ok: () => {},
        cancel: () => {}
      }
    }
  },
  computed: {
    inventoryCheckTotal: {
      get() {
        let sum = 0;
        this.form.details.map(item => {
          if (!isNaN(parseFloat(item.checkQuantity))) {
            sum += (item.checkQuantity - item.quantity)
          }
          return item.checkQuantity
        })
        return sum
      },
      set(value) {
      }
    }
  },
  created() {
    const { id } = this.$route.query
    if (id) {
      this.loadDetail(id)
    } else {
      this.reset()
    }
  },
  methods: {
    /** CancelButton */
    cancel() {
      this.$tab.closeOpenPage({ path: '/inventoryCheck' })
    },
    /** 提交Button */
    submitFinishForm() {
      //Count End22
      this.submitForm(22)
    },
    submitForm(inventoryCheckStatus = 11) {
      if (inventoryCheckStatus === 22 && this.form.details.length === 0) {
        //未AddItem
        Message({
          message: "Please Start Count ！",
          type: 'error'
        })
        return false;
      }
      this.$refs['form'].validate(valid => {
        if (!valid) {
          return
        }
        this.form.inventoryCheckStatus = inventoryCheckStatus
        this.form.inventoryCheckTotal = this.inventoryCheckTotal

        const details = this.form.details.map(it => {
          if (it.place) {
            it.warehouseId = it.place[0]
            it.areaId = it.place[1]
            it.rackId = it.place[2]
          } else {
            it.warehouseId = null
            it.areaId = null
            it.rackId = null
          }
          return {
            itemId: it.itemId,
            rackId: it.rackId,
            areaId: it.areaId,
            warehouseId: it.warehouseId,
            quantity: it.quantity,
            checkQuantity: it.checkQuantity,
            delFlag: 0,
            remark: it.remark
          }
        })

        const req = { ...this.form, details }


        addOrUpdateWmsInventoryCheck(req).then(response => {
          this.$modal.msgSuccess(this.form.id ? 'Modify Successful' : 'Add Successful')
          this.cancel();
        })
      })
    },
    /** 加载 Count Detail */
    loadDetail(id) {
      getWmsInventoryCheck(id).then(response => {
        const { details, items } = response
        const map = {};
        (items || []).forEach(it => {
          map[it.id] = it
        });
        details && details.forEach(it => it.prod = map[it.itemId])
        this.form = {
          ...response,
          details
        }
      })
    },
    // 表Reset
    reset() {
      this.form = {
        id: null,
        inventoryCheckNo: 'IV-' + randomId(),
        receiptOrderType: 1,
        supplierId: null,
        orderNo: null,
        receiptOrderStatus: 0,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        attachment: null,
        details: []
      }
      this.resetForm('form')
    },
    /** ItemSelect 框 */
    confirmSelectItem() {
      const value = this.$refs['item-select'].getRightList()

      // 当前选中Itemids
      this.prodIds = value.map(it => it.id)

      // 当前CountWarehouse
      const { place } = this.form


      const [warehouseId, areaId, rackId] = place || [];
      this.form.warehouseId = warehouseId
      this.form.areaId = areaId
      this.form.rackId = rackId

      let hasInventoryIds = new Set()
      //获取该Warehouse下Itemids的Inventory
      const query = { warehouseId, areaId, rackId }
      listWmsInventory(query, null).then(response => {
        const { content, totalElements } = response
        let wmsInventoryMap = content.filter(it => {
          if (this.prodIds.indexOf(it.itemId) > -1) {
            hasInventoryIds.add(it.itemId)
            return true;
          }
          return false;
        })


        //无Inventory
        this.form.details = value.filter(it => {
          return !hasInventoryIds.has(it.id)
        }).map(it => {
          console.log("value:",it)
          let place = [this.form.warehouseId]
          if (this.form.areaId) {
            place.push(this.form.areaId)
          }
          return {
            isNew: true,
            prod: {
              itemName: it.itemName,
              itemNo: it.itemNo,
            },
            itemId: it.id,
            rackId: this.form.rackId,
            areaId: this.form.areaId,
            place: place,
            warehouseId: this.form.warehouseId,
            quantity: 0.00,
            checkQuantity: 0.00,
            receiptOrderStatus: 0,
            delFlag: 0,
            remark: null
          }
        })

        //有Inventory
        this.form.details.push(...
          wmsInventoryMap.map(it => {
            let place = [it.warehouseId]
            if (it.areaId) {
              place.push(it.areaId)
            }
            if(it.rackId){
              place.push(it.rackId)
            }
            return {
              isNew: false,
              prod: {
                itemName: it.itemName,
                itemNo: it.itemNo,
              },
              itemId: it.itemId,
              rackId: it.rackId,
              areaId: it.areaId,
              place: place,
              warehouseId: it.warehouseId,
              quantity: it.quantity,
              checkQuantity: it.quantity,
              receiptOrderStatus: 0,
              delFlag: 0,
              remark: it.remark
            }
          })
        )
      })

      this.closeModal()
    },
    closeModal() {
      this.modalObj.show = false
      this.remarkModalObj.show = false
    },
    showAddItem() {
      try {
        this.$refs['item-select'].initDetailsList()
      }catch (err){

      }
      const ok = () => this.confirmSelectItem()
      const cancel = () => this.closeModal()
      this.modalObj = {
        show: true,
        title: 'Select Goods',
        width: '50%',
        component: 'add-item',
        model: {},
        ok,
        cancel
      }
    },
    showRemarkItem(it){
      const ok = () => this.confirmAddRemark(it)
      const cancel = () => this.closeModal()
      this.remarkModalObj = {
        show: true,
        title: 'AddRemark',
        width: '40%',
        component: 'remark-item',
        content:it.remark,
        model: {},
        ok,
        cancel
      }
    },
    confirmAddRemark(it){
      it.remark = this.remarkModalObj.content
      this.closeModal()
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
