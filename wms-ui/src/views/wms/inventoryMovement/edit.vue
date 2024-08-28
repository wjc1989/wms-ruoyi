<template>
  <div class="inventory-movement-edit-wrapper app-container">
    <div class="inventory-movement-content">
      <el-form label-width="108px" :model="form" ref="form" :rules="rules">
        <el-form-item label="No." prop="inventoryMovementNo">
          <el-input class="w200" v-model="form.inventoryMovementNo" placeholder="No."
                    disabled="disabled"></el-input>
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
                         @click="onBatchSetInventory('sourcePlace')">
                Source Warehouse
              </el-button>
            </el-col>

            <el-col :span="1.5">
              <el-button size="small" icon="el-icon-aim" type="warning" plain="plain"
                         @click="onBatchSetInventory('targetPlace')">
                Target Warehouse
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
          <el-table-column label="Plan Count" align="center" prop="planQuantity" width="150">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.planQuantity" placeholder="Plan Count" size="small" :min="1"
                               :max="2147483647"></el-input-number>
            </template>
          </el-table-column>
          <el-table-column label="Source  Warehouse" align="center" width="200">
            <template slot-scope="scope">
              <WmsWarehouseCascader v-model="scope.row.sourcePlace" size="small" :goodsId="scope.row.itemId||scope.row.id"></WmsWarehouseCascader>
            </template>
          </el-table-column>
          <el-table-column label="Target  Warehouse" align="center" width="200">
            <template slot-scope="scope">
              <WmsWarehouseCascader v-model="scope.row.targetPlace" size="small" :goodsId="scope.row.itemId||scope.row.id"></WmsWarehouseCascader>
            </template>
          </el-table-column>
          <el-table-column label="Operate" align="center">
            <template slot-scope="scope">
              <a class="red" @click="form.details.splice(scope.$index, 1)">Delete</a>
            </template>
          </el-table-column>
        </WmsTable>
        <!--        <el-empty v-if="!form.details || form.details.length === 0" :image-size="48"></el-empty>-->
      </div>
      <div class="tc mt16">
        <el-button type="primary" plain="plain" size="small" @click="showAddItem">Add Item</el-button>
      </div>
      <div class="tc mt16">
        <el-button @click="cancel">Cancel</el-button>
        <el-button @click="submitForm" type="primary">Save</el-button>
      </div>
    </div>
    <el-dialog :visible="modalObj.show" :title="modalObj.title" :width="modalObj.width" @close="modalObj.cancel">
      <template v-if="modalObj.component === 'add-item'">
        <item-select ref="item-select"  ></item-select>
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
import {addOrUpdateWmsInventoryMovement, getWmsInventoryMovement} from '@/api/wms/inventoryMovement'
import {randomId} from '@/utils/RandomUtils'
import ItemSelect from '@/views/components/ItemSelect'
import BatchWarehouseDialog from "@/views/components/wms/BatchWarehouseDialog/index.vue";

export default {
  name: 'WmsMovementOrder',
  components: {BatchWarehouseDialog, ItemSelect},
  data() {
    return {
      ids: [],
      // 非多个禁用
      multiple: true,
      // Set Warehouse
      batchDialogVisible: false,
      batchDialogField: '',
      batchForm: {
        place: []
      },
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
      }
    }
  },
  created() {
    const {id} = this.$route.query
    if (id) {
      this.loadDetail(id)
    } else {
      this.reset()
    }
  },
  methods: {
    // 多选框选中Data
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.multiple = !selection.length
    },
    /** Set Warehouse */
    onBatchSetInventory(field) {
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
      this.batchDialogField = field
    },
    onBatchDialogFinished() {
      this.batchDialogVisible = false
      const [warehouseId, areaId, rackId] = this.batchForm.place || []
      this.form.details.forEach(it => {
        // 仅更新已选中
        if (this.ids.includes(it.id)) {
          it[this.batchDialogField] = [warehouseId, areaId, rackId].filter(Boolean)
        }
      })
    },
    cancel() {
      this.$tab.closeOpenPage({path: '/inventoryMovement'})
    },
    /** 提交Button */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (!valid) {
          return
        }
        const details = this.form.details.map(it => {
          let {sourcePlace, targetPlace} = it;
          return {
            itemId: it.prod.id,
            sourceRackId: sourcePlace ? sourcePlace[2] : null,
            sourceAreaId: sourcePlace ? sourcePlace[1] : null,
            sourceWarehouseId: sourcePlace ? sourcePlace[0] : null,
            targetRackId: targetPlace ? targetPlace[2] : null,
            targetAreaId: targetPlace ? targetPlace[1] : null,
            targetWarehouseId: targetPlace ? targetPlace[0] : null,
            planQuantity: it.planQuantity,
            realQuantity: it.realQuantity,
            moveStatus: it.moveStatus,
            delFlag: 0
          }
        })
        if (details.filter(it => !it.sourceWarehouseId || !it.targetWarehouseId)?.length > 0) {
          this.$message.warning('Please select Warehouse、Area Or Rack')
          return;
        }
        const arr = details.filter(it => it.sourceRackId === it.targetRackId && it.sourceAreaId === it.targetAreaId && it.sourceWarehouseId === it.targetWarehouseId)
        if (arr?.length > 0) {
          this.$message.warning('Location Not Changed')
          return;
        }
        const req = {...this.form, details}
        addOrUpdateWmsInventoryMovement(req).then(response => {
          this.$modal.msgSuccess(this.form.id ? 'Modify Successful' : 'Add Successful')
          this.cancel();
        })
      })
    },
    loadDetail(id) {
      getWmsInventoryMovement(id).then(response => {
        const {details, items} = response
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
        inventoryMovementNo: 'M-' + randomId(),
        status: 21,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        details: []
      }
      this.resetForm('form')
    },
    confirmSelectItem() {
      let value = this.$refs['item-select'].getRightList()
      value=value.map(it => {
        return {
          id:it.id,
          prod: it,
          planQuantity: null,
          targetPlace: [],
          sourcePlace: [],
          realQuantity: null,
          moveStatus: 21,
          delFlag: 0
        }
      })
      this.form.details.push(...value)
      this.closeModal()
    },
    closeModal() {
      this.modalObj.show = false
    },
    showAddItem() {
      try {
        this.$refs['item-select'].initDetailsList([])
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
.inventory-movement-edit-wrapper
  .inventory-movement-content
    width 80%
    min-width 900px
    margin 0 auto
</style>
