<template>
  <div class="receipt-order-edit-wrapper app-container">
    <div class="receipt-order-content">
      <el-form label-width="108px" :model="form" ref="form" :rules="rules">
        <el-form-item label="SettlementNo." prop="inventorySettlementNo">
          <el-input
            class="w200"
            v-model="form.inventorySettlementNo"
            placeholder="SettlementNo."
            disabled="disabled"
          ></el-input>
        </el-form-item>
        <el-form-item label="Settlement Cycle" prop="inventorySettlementPeriod">
          <el-date-picker
            v-model="form.inventorySettlementPeriod"
            type="daterange"
            align="right"
            unlink-panels
            range-separator=" To "
            start-placeholder="Start time"
            end-placeholder="End time"
            :picker-options="pickerOptions">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="Remark" prop="remark">
          <el-input
            v-model="form.remark"
            placeholder="Remark...Maximum 100 characters"
            rows="3"
            maxlength="100"
            type="textarea"
            show-word-limit="show-word-limit"
          ></el-input>
        </el-form-item>
      </el-form>
      <el-divider></el-divider>
      <div class="flex-center mb8">
        <div class="flex-one large-tip bolder-font">Settlement Detail</div>
        <div class="ops">
          <el-button type="primary" plain="plain" size="small" @click="startSettle"> Start Settlement</el-button>
        </div>
      </div>
      <div class="table">
        <table class="common-table">
          <tr>
            <th>Item Info</th>
            <th>Warehouse</th>
            <th>Last Inventory</th>
            <th>Current Inbound</th>
            <th>Current Outbound </th>
            <th>Current Count</th>
            <th>Current Inventory</th>
          </tr>
          <tr v-for="(it, index) in form.details">
            <td align="center">{{ it.itemName }} <br>No.：{{ it.itemNo }}</td>
            <td align="center">{{ it.warehouseName }} {{ it.warehouseNo }}</td>
            <td align="center">
              <el-input-number
                v-model="it.previousBalance"
                :precision="2"
                :min="0"
              ></el-input-number>
            </td>
            <td align="center">
              <el-input-number
                v-model="it.currentEnter"
                :precision="2"
                :min="0"
              ></el-input-number>
            </td>
            <td align="center">
              <el-input-number
                v-model="it.currentOut"
                :precision="2"
                :min="0"
              ></el-input-number>
            </td>
            <td align="center">
              <el-input-number
                v-model="it.currentCheck"
                :precision="2"
                :min="0"
              ></el-input-number>
            </td>
            <td align="center">
              <el-input-number
                v-model="it.currentBalance"
                :precision="2"
                :min="0"
              ></el-input-number>
            </td>
          </tr>
        </table>
        <el-empty
          v-if="!form.details || form.details.length === 0"
          :image-size="48"
        ></el-empty>
      </div>
      <div class="tc mt16">
        <el-button @click="cancel">Cancel</el-button>
        <el-button @click="submitForm(11)" type="primary">Save</el-button>
        <el-button @click="submitFinishForm" type="success">Settlement完成</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import {randomId} from "@/utils/RandomUtils";
import {listByTime} from "@/api/wms/inventorySettlementDetail";
import {Message} from "element-ui";
import {addOrUpdateWmsInventorySettlement, getWmsInventorySettlement} from "@/api/wms/inventorySettlement";
import moment from "moment";

export default {
  name: "WmsInventorySettle",
  data() {
    return {
      // 预设 TimeSelect Date范围
      pickerOptions: {
        shortcuts: [{
          text: 'Last week',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: 'Last month',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: 'Last three month',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
      types: ["png", "jpg", "jpeg"],
      // 表Params
      form: {
        details: [],
      },
      // 表校验
      rules: {},
    };
  },
  created() {
    const {id, settlementType} = this.$route.query;
    if (id) {
      this.loadDetail(id);
    } else {
      this.reset();
      this.form.settlementType = settlementType
      // this.loadSettlement();
    }
  },
  methods: {
    // 获取当期的Settlement初始列表
    startSettle() {
      let [startTime, endTime] = this.form.inventorySettlementPeriod || [];
      if (startTime){
        startTime = moment(startTime).format('YYYY-MM-DD HH:mm:ss')
        endTime = moment(endTime).format('YYYY-MM-DD HH:mm:ss')
      }
      const settlementType =  this.form.settlementType
      const rep = {startTime, endTime,settlementType}
      listByTime(rep).then((response) => {
        const details = response;
        this.form = {
          ...this.form,
          details,
        };
      })
    },
    /** CancelButton */
    cancel() {
      const {settlementType} = this.form
      if (Number(settlementType) === 1) {
        this.$tab.closeOpenPage({path: "/settlement/inventorySettlementMonth?settlementType=1"});
      } else {
        this.$tab.closeOpenPage({path: "/settlement/inventorySettlementYear?settlementType=2"});
      }
    },
    loadSettlement() {
      listByTime().then((response) => {
        const details = response;
        this.form = {
          ...this.form,
          details,
        };
      })
    },
    /** 提交Button */
    submitFinishForm() {
      //Settlement完成22
      this.submitForm(22);
    },
    submitForm(inventorySettlementStatus = 11) {
      if (inventorySettlementStatus === 22 && this.form.details.length === 0) {
        //未AddItem
        Message({
          message: "Please Start Settlement！",
          type: "error",
        });
        return false;
      }
      this.$refs["form"].validate((valid) => {
        if (!valid) {
          return;
        }
        const [startTime, endTime] = this.form.inventorySettlementPeriod || [];
        // moment(value).format('YYYY-MM-DD HH:mm:ss')
        this.form.inventorySettlementStartTime = moment(startTime).format('YYYY-MM-DD HH:mm:ss')
        this.form.inventorySettlementEndTime = moment(endTime).format('YYYY-MM-DD HH:mm:ss')
        this.form.inventorySettlementStatus = inventorySettlementStatus;


        const req = {...this.form};

        addOrUpdateWmsInventorySettlement(req).then((response) => {
          this.$modal.msgSuccess(this.form.id ? "Modify Successful" : "Add Successful");
          this.cancel();
        });
      });
    },
    /** 加载 Count Detail */
    loadDetail(id) {
      getWmsInventorySettlement(id).then((response) => {
        const {details, items, inventorySettlementStartTime, inventorySettlementEndTime} = response;
        const map = {};
        (items || []).forEach((it) => {
          map[it.id] = it;
        });
        const inventorySettlementPeriod = [inventorySettlementStartTime, inventorySettlementEndTime];
        details && details.forEach((it) => (it.prod = map[it.itemId]));
        this.form = {
          ...response,
          inventorySettlementPeriod,
          details,
        };
      });
    },
    // 表Reset
    reset() {
      this.form = {
        id: null,
        inventorySettlementNo: "IS-" + randomId(),
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        inventorySettlementPeriod: null
      };
      this.resetForm("form");
    },
  },
};
</script>
<style lang="stylus">
.receipt-order-edit-wrapper
  .receipt-order-content
    width 80%
    min-width 900px
    margin 0 auto
</style>
