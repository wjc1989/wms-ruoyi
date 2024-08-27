<template>
  <div class="inventory-movement-status-wrapper app-container" v-loading="loading">
    <div class="inventory-movement-content">
      <el-form label-width="108px" :model="form" ref="form" :rules="rules">
        <el-form-item label="MoveNo." prop="inventoryMovementNo">{{ form.inventoryMovementNo }}</el-form-item>
        <el-form-item label="MoveStatus" prop="status">{{ statusMap.get(form.status + '') }}</el-form-item>
        <el-form-item label="Remark" prop="remark">{{ form.remark }}</el-form-item>
      </el-form>
      <el-divider></el-divider>
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
          <el-button v-if="mergeDetailStatusArray.length === 1" type="primary" plain="plain" size="small"
                     @click="batch">Set Outbound Status
          </el-button>
        </el-col>

        <el-col :span="1.5">
          <el-button size="small" icon="el-icon-aim" type="warning" plain="plain"
                     @click="onBatchSetInventory('targetPlace')">
            Target Warehouse
          </el-button>
        </el-col>

      </el-row>
      <el-dialog title="Please select MoveStatus" :visible.sync="open" width="50%" append-to-body="append-to-body">
        <DictRadio v-model="dialogStatus" :radioData="dialogStatusRange"></DictRadio>
        <div class="dialog-footer" slot="footer">
          <el-button type="primary" @click="dialogConfirm">OK</el-button>
          <el-button @click="cancelDialog">Cancel</el-button>
        </div>
      </el-dialog>
      <div class="table">
        <el-form ref="form" :model="form" :show-message="false">
          <WmsTable :data="form.details" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center"></el-table-column>
            <el-table-column label="Goods Name" align="center" prop="prod.itemName"></el-table-column>
            <el-table-column label="Goods No." align="center" prop="prod.itemNo"></el-table-column>
            <el-table-column label="Category" align="center" prop="prod.itemType"></el-table-column>
            <el-table-column label="Plan Count" align="center" prop="planQuantity"></el-table-column>
            <el-table-column label="Real Count" align="center" width="150">
              <template slot-scope="scope">
                <el-input-number v-model="scope.row.realQuantity" :min="1" :max="2147483647" size="small"
                                 :disabled="scope.row.finish"></el-input-number>
              </template>
            </el-table-column>
            <el-table-column label="Source  Warehouse" align="center" width="200">

              <template slot-scope="scope">
                <el-form-item :prop=" 'details.' + scope.$index + '.sourcePlace' "
                              :rules="[{ required: true, message: 'Please select Source  Warehouse', trigger: 'change' }]"
                              style="margin-bottom: 0!important;">
                  <WmsWarehouseCascader v-model="scope.row.sourcePlace" size="small"
                                        :disabled="scope.row.finish"></WmsWarehouseCascader>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column label="Target  Warehouse" align="center" width="200">

              <template slot-scope="scope">
                <el-form-item :prop=" 'details.' + scope.$index + '.targetPlace' "
                              :rules="[{ required: true, message: 'Please select Target  Warehouse', trigger: 'change' }]"
                              style="margin-bottom: 0!important;">
                  <WmsWarehouseCascader v-model="scope.row.targetPlace" size="small"
                                        :disabled="scope.row.finish"></WmsWarehouseCascader>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column label="MoveStatus" width="150">
              <template slot-scope="{ row }">
                <DictSelect v-model="row.moveStatus" :options="row.range" size="small"
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
    </div>
    <BatchWarehouseDialog
      :visible.sync="batchDialogVisible"
      :form-data.sync="batchForm"
      @confirmed="onBatchDialogFinished"
    ></BatchWarehouseDialog>
  </div>
</template>

<script>
import {addOrUpdateWmsInventoryMovement, getWmsInventoryMovement} from '@/api/wms/inventoryMovement'
import ItemSelect from '@/views/components/ItemSelect'
import BatchWarehouseDialog from "@/views/components/wms/BatchWarehouseDialog/index.vue";

export default {
  name: 'WmsInventoryMovement',
  components: {BatchWarehouseDialog, ItemSelect},
  dicts: ['wms_movement_status'],
  computed: {
    statusMap() {
      let obj = this.dict.type.wms_movement_status.map(item => [item.value, item.label])
      let map = new Map(obj)
      return map
    },
    mergeDetailStatusArray() {
      const arr = this.sourceDetails || []
      return [...new Set(arr.filter(it => it.moveStatus !== null).map(it => it.moveStatus))]
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
      batchDialogField: '',
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
      finish: false
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
    dialogConfirm() {
      if (!this.dialogStatus) {
        this.$modal.alert('Please select MoveStatus')
        return
      }
      this.form.details.forEach(detail => {
        if (this.ids.includes(detail.id)) {
          detail.moveStatus = this.dialogStatus
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
      this.$tab.closeOpenPage({path: '/inventoryMovement'})
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
          let {sourcePlace, targetPlace} = it;
          return {
            id: it.id,
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
          this.$message.warning('同一个ItemNo 能Select 相同的Warehouse、Area、 Rack')
          return;
        }
        const req = {...this.form, details}
        addOrUpdateWmsInventoryMovement(req).then(response => {
          this.$modal.msgSuccess(this.form.id ? 'Modify Successful' : 'Add Successful')
          this.cancel()
        })
      })
    },
    loadDetail(id) {
      this.loading = true
      getWmsInventoryMovement(id).then(response => {
        const {details, items} = response
        const map = {};
        (items || []).forEach(it => {
          map[it.id] = it
        })
        details && details.forEach(it => {
          it.prod = map[it.itemId]
          if ((!it.sourcePlace || it.sourcePlace.length === 0) && it.prod) {
            it.sourcePlace = it.prod.sourcePlace;
          }
          if ((!it.targetPlace || it.targetPlace.length === 0) && it.prod) {
            it.targetPlace = it.prod.targetPlace;
          }
          it.range = this.getRange(it.moveStatus)
          it.finish = it.moveStatus === 23;
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
      const arr = this.dict.type.wms_movement_status
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
.inventory-movement-status-wrapper
  .inventory-movement-content
    width 80%
    min-width 900px
    margin 0 auto
</style>
