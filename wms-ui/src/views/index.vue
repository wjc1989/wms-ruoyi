<template>
  <div class="home-wrapper">
    <el-card style="margin: 20px 20px; font-size: 14px">
      <div slot="header"><span>Workstation</span></div>
      <div class="first">
        <img alt="" :src="avatar" style="width: 50px; height: 50px; float:left; margin-right:10px;border-radius:50%" />
        <p style="font-size:16px;margin-bottom:8px">{{ name }}，{{ hello }}</p>
        <p style="font-size:12px;color:rgb(185, 181, 189);width: 300px;">Today is：{{ nowTime }}</p>
      </div>

    </el-card>
    <el-row class="pl20 pr20" :gutter="10">
      <el-col :span="12">
        <el-card shadow="always" style="padding-bottom: 20px;">
          <div>
            <el-row :gutter="40" class="panel-group">
              <el-col :xs="12" :sm="12" :lg="8" class="card-panel-col">
                <div class="card-panel" @click="handleGo('/basic/warehouse')">
                  <div class="card-panel-icon-wrapper icon-message">
                    <svg-icon icon-class="table" class-name="card-panel-icon" />
                  </div>
                  <div class="card-panel-description">
                    <div class="card-panel-text">
                      Warehouse
                    </div>
                    <count-to :start-val="0" :end-val="warehouseList.length" :duration="3000" class="card-panel-num" />
                  </div>
                </div>
              </el-col>
              <el-col :xs="12" :sm="12" :lg="8" class="card-panel-col">
                <div class="card-panel" @click="handleGo('/basic/supplier')">
                  <div class="card-panel-icon-wrapper icon-people">
                    <svg-icon icon-class="peoples" class-name="card-panel-icon" />
                  </div>
                  <div class="card-panel-description">
                    <div class="card-panel-text">
                      Supplier
                    </div>
                    <count-to :start-val="0" :end-val="supplierList.length" :duration="2600" class="card-panel-num" />
                  </div>
                </div>
              </el-col>
              <el-col :xs="12" :sm="12" :lg="8" class="card-panel-col">
                <div class="card-panel" @click="handleGo('/relation/carrier')">
                  <div class="card-panel-icon-wrapper icon-people">
                    <svg-icon icon-class="guide" class-name="card-panel-icon" />
                  </div>
                  <div class="card-panel-description">
                    <div class="card-panel-text">
                      Carrier
                    </div>
                    <count-to :start-val="0" :end-val="carrierList.length" :duration="2600" class="card-panel-num" />
                  </div>
                </div>
              </el-col>
              <el-col :xs="12" :sm="12" :lg="8" class="card-panel-col">
                <div class="card-panel" @click="handleGo('/relation/customer')">
                  <div class="card-panel-icon-wrapper icon-message">
                    <svg-icon icon-class="star" class-name="card-panel-icon" />
                  </div>
                  <div class="card-panel-description">
                    <div class="card-panel-text">
                      Customer
                    </div>
                    <count-to :start-val="0" :end-val="customerList.length" :duration="3000" class="card-panel-num" />
                  </div>
                </div>
              </el-col>
              <el-col :xs="12" :sm="12" :lg="8" class="card-panel-col">
                <div class="card-panel" @click="goAnchor('warn')">
                  <div class="card-panel-icon-wrapper icon-money">
                    <svg-icon icon-class="eye-open" class-name="card-panel-icon" />
                  </div>
                  <div class="card-panel-description">
                    <div class="card-panel-text">
                      Stock Alert
                    </div>
                    <count-to :start-val="0" :end-val="totalWarn" :duration="3200" class="card-panel-num" />
                  </div>
                </div>
              </el-col>
              <el-col :xs="12" :sm="12" :lg="8" class="card-panel-col">
                <div class="card-panel" @click="goAnchor('expiry')">
                  <div class="card-panel-icon-wrapper icon-money">
                    <svg-icon icon-class="bug" class-name="card-panel-icon" />
                  </div>
                  <div class="card-panel-description">
                    <div class="card-panel-text">
                      Expiry
                    </div>
                    <count-to :start-val="0" :end-val="totalExpiry" :duration="3200" class="card-panel-num" />
                  </div>
                </div>
              </el-col>
            </el-row>
          </div>
        </el-card>
        <el-card shadow="always" style="padding-bottom: 20px; font-size: 14px;margin-top: 20px;margin-bottom: 20px;">
          <div slot="header" ref="warn"><span>Warn</span></div>
          <WmsTable v-loading="loading" :data="warnList">
            <el-table-column align="left" label="ID" prop="id" width="72"></el-table-column>
            <el-table-column align="left" label="Goods" prop="itemName">
              <template v-slot="{ row }"><span style="color:red">{{ row.itemName }}</span></template>
            </el-table-column>
            <el-table-column align="left" label="No." prop="itemNo"></el-table-column>
            <el-table-column align="left" label="Warehouse" width="200">
              <template v-slot="{ row }"><span v-if="row.warehouseName">{{ row.warehouseName }}</span><span
                  v-if="row.areaName">/{{ row.areaName }}</span></template>
            </el-table-column>
            <el-table-column align="left" label="Count">
              <template v-slot="{ row }"><span style="color:red;font-weight: bold;">{{ row.quantity }}</span></template>
            </el-table-column>
            <el-table-column align="left" label="Safty Count" prop="saftyQuantity"></el-table-column>
          </WmsTable>

          <pagination v-show="totalWarn > 0" :total="totalWarn" :page.sync="queryWarnParams.pageNum"
            :limit.sync="queryWarnParams.pageSize" @pagination="getWarnList" :autoScroll="ifScroll" />
        </el-card>
      </el-col>
      <el-col :span="12">

        <el-card style="padding-bottom: 20px; font-size: 14px;margin-top: 20px;">
          <div slot="header" ref="expiry"><span>Expiry Goods</span></div>
          <WmsTable v-loading="loading" :data="expiryList">
            <el-table-column label="ID" align="center" prop="itemNo" v-if="columns[0].visible" />
            <el-table-column label="Goods" align="center" prop="itemName" v-if="columns[1].visible">
              <template slot-scope="scope">
                <span style="color:red;">{{ scope.row.itemName }}</span>
              </template>
            </el-table-column>
            <el-table-column label="Type" align="center" prop="itemTypeName" v-if="columns[2].visible" />
            <el-table-column label="Unit" align="center" prop="unit" v-if="columns[3].visible" />
            <el-table-column label="Warehouse" align="center" prop="warehouseName"  width="100" v-if="columns[6].visible" />
            <el-table-column label="Area" align="center" prop="areaName" v-if="columns[5].visible" />
            <el-table-column label="Shelf Name" align="center" prop="rackName" v-if="columns[4].visible" />
            <el-table-column label="Safty Count" align="center" prop="quantity"  width="100" v-if="columns[7].visible" />
            <el-table-column label="Expiry Date" align="center" prop="expiryDate" width="180" v-if="columns[8].visible">
              <template slot-scope="scope">
                <span style="color:red;font-weight: bold;">{{ parseTime(scope.row.expiryDate, "{yyyy}-{mm}-{dd}")
                }}</span>
              </template>
            </el-table-column>
            <el-table-column label="Remark" align="center" prop="remark" v-if="columns[9].visible" />
          </WmsTable>
          <pagination v-show="totalExpiry > 0" :total="totalExpiry" :page.sync="queryExpiryParams.pageNum"
            :limit.sync="queryExpiryParams.pageSize" @pagination="getExpiryList" :autoScroll="ifScroll" />
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import PanelGroup from '@/views/components/PanelGroup'
import { mapGetters } from 'vuex'
import CountTo from "vue-count-to"
import { listWarnInventory } from "@/api/wms/inventory";
import { listExpiryItem } from "@/api/wms/item";

export default {
  components: {
    PanelGroup,
    CountTo
  },
  data() {
    return {
      tableData: [{
        date: '1',
        name: '',
        address: '20'
      }],
      nowTime: '',
      hello: '',
      warnList: [],
      expiryList: [],
      queryWarnParams: {
        pageNum: 1,
        pageSize: 10
      },
      queryExpiryParams: {
        pageNum: 1,
        pageSize: 10
      },
      //Warn条数
      totalWarn: 0,
      totalExpiry: 0,
      columns: [
        { key: 1, label: "No.", visible: true },
        { key: 2, label: "Item", visible: true },
        { key: 3, label: "Type", visible: true },
        { key: 4, label: "Unit", visible: true },
        { key: 5, label: "Shelf Name", visible: false },
        { key: 6, label: "Area", visible: true },
        { key: 7, label: "Warehouse", visible: true },
        { key: 8, label: "Safty Quantity", visible: true },
        { key: 9, label: "Expiry Date", visible: true },
        { key: 11, label: "Remark", visible: false },
      ],
      ifScroll: false
    }
  },
  computed: {
    ...mapGetters([
      'avatar',
      'name',
      "areaList",
      "warehouseList",
      "supplierList",
      "customerList",
      "carrierList",
      "warehouseMap",
      "areaMap"
    ])
  },
  created() {
    this.showTimes()
    this.helloTimes()
    this.getWarnList()
    this.getExpiryList()
  },
  methods: {
    getWarnList() {
      this.loading = true;
      const { pageNum, pageSize } = this.queryWarnParams;
      const pageReq = { page: pageNum - 1, size: pageSize }
      listWarnInventory(pageReq).then((response) => {
        const { content, totalElements } = response
        this.warnList = content;
        this.totalWarn = totalElements;
        this.loading = false;
      });
    },
    getExpiryList() {
      this.loading = true;
      const { pageNum, pageSize } = this.queryExpiryParams;
      const pageReq = { page: pageNum - 1, size: pageSize }
      listExpiryItem(pageReq).then((response) => {
        const { content, totalElements } = response
        content.forEach((item) => {
          item.warehouseName = this.warehouseMap.get(item.warehouseId);
        });
        content.forEach((item) => {
          item.areaName = this.areaMap.get(item.areaId);
        });
        this.expiryList = content;
        this.totalExpiry = totalElements;
        this.loading = false;
      });
    },
    timeFormate(timeStamp) {
      let year = new Date(timeStamp).getFullYear()
      let month =
        new Date(timeStamp).getMonth() + 1 < 10
          ? '0' + (new Date(timeStamp).getMonth() + 1)
          : new Date(timeStamp).getMonth() + 1
      let date =
        new Date(timeStamp).getDate() < 10
          ? '0' + new Date(timeStamp).getDate()
          : new Date(timeStamp).getDate()
      let hh =
        new Date(timeStamp).getHours() < 10
          ? '0' + new Date(timeStamp).getHours()
          : new Date(timeStamp).getHours()
      let mm =
        new Date(timeStamp).getMinutes() < 10
          ? '0' + new Date(timeStamp).getMinutes()
          : new Date(timeStamp).getMinutes()
      let ss =
        new Date(timeStamp).getSeconds() < 10
          ? '0' + new Date(timeStamp).getSeconds()
          : new Date(timeStamp).getSeconds()
      let week = new Date(timeStamp).getDay()
      let weeks = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday']
      let getWeek = '' + weeks[week]
      this.nowTime =
        year +
        '-' +
        month +
        '-' +
        date +
        '-' +
        ' ' +

        getWeek
    },
    showTimes() {
      this.timeFormate(new Date())

    },
    helloTimes() {
      let hh = new Date().getHours()
      if (0 < hh && hh < 12) {
        this.hello = 'Good Morning'
      } else if (hh < 18) {
        this.hello = 'Good Afternoon'
      } else {
        this.hello = 'Good Night'
      }
    },
    handleGo(path) {
      this.$router.push(path)
    },
    goAnchor(elementName) {
      this.$refs[elementName].scrollIntoView({
        behavior: "smooth",  // 平滑过渡
        block: "start"  	// 上边框与视窗顶部平齐。默认值
      });
    }
  }
}
</script>

<style scoped lang="stylus">
.home-wrapper

  .li {
    float: left;
    width: 25%;
    font-size: 12px;
    color: rgb(185, 181, 189);
  }

  .da {
    float: left;
    width: 25%;
    font-size: 20px;
  }

  .first {
    float: left;
    margin-bottom: 20px;
  }

  .el-table .warning-row {
    background: rgb(39, 138, 230);
  }
  .panel-group {
    margin-top: 18px;

    .card-panel-col {
      margin-bottom: 32px;
    }

    .card-panel {
      height: 108px;
      cursor: pointer;
      font-size: 12px;
      position: relative;
      overflow: hidden;
      color: #666;
      background: #fff;
      box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
      border-color: rgba(0, 0, 0, .05);

      &:hover {
        .card-panel-icon-wrapper {
          color: #fff;
        }

        .icon-people {
          background: #40c9c6;
        }

        .icon-message {
          background: #36a3f7;
        }

        .icon-money {
          background: #f4516c;
        }
      }

      .icon-people {
        color: #40c9c6;
      }

      .icon-message {
        color: #36a3f7;
      }

      .icon-money {
        color: #f4516c;
      }

      .card-panel-icon-wrapper {
        float: left;
        margin: 14px 0 0 14px;
        padding: 16px;
        transition: all 0.38s ease-out;
        border-radius: 6px;
      }

      .card-panel-icon {
        float: left;
        font-size: 48px;
      }

      .card-panel-description {
        float: right;
        font-weight: bold;
        margin: 26px;
        margin-left: 0px;

        .card-panel-text {
          line-height: 18px;
          color: rgba(0, 0, 0, 0.45);
          font-size: 16px;
          margin-bottom: 12px;
        }

        .card-panel-num {
          font-size: 20px;
        }
      }
    }
  }

  @media (max-width:550px) {
    .card-panel-description {
      display: none;
    }

    .card-panel-icon-wrapper {
      float: none !important;
      width: 100%;
      height: 100%;
      margin: 0 !important;

      .svg-icon {
        display: block;
        margin: 14px auto !important;
        float: none !important;
      }
    }
  }
</style>
