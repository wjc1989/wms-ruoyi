<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="120px" size="medium"
      class="ry_form">
      <el-form-item label="No." prop="itemNo">
        <el-input v-model="queryParams.itemNo" placeholder="Please Input No." clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="Name" prop="itemName">
        <el-input v-model="queryParams.itemName" placeholder="Please Input Item" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="Type" prop="itemType">
        <treeselect v-model="queryParams.itemType" style="max-width: 180px" :options="deptOptions" :show-count="true"
          placeholder="Please select Type" />
      </el-form-item>
      <el-form-item label="Unit" prop="unit">
        <el-input v-model="queryParams.unit" placeholder="Please Input Unit" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="Shelf Name" prop="rackId">
        <el-input v-model="queryParams.rackId" placeholder="Please Input Shelf Name" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="Area" prop="areaId">
        <el-input v-model="queryParams.areaId" placeholder="Please Input Area" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="Warehouse" prop="warehouseId">
        <el-input v-model="queryParams.warehouseId" placeholder="Please Input Warehouse" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <template v-if="showMoreCondition">
        <el-form-item label="Safty Count" prop="quantity">
          <el-input v-model="queryParams.quantity" placeholder="Please Input Safty Count" clearable size="small"
            @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item label="Expiry Date" prop="expiryDate">
          <el-date-picker clearable size="small" v-model="queryParams.expiryDate" type="datetime"
            value-format="yyyy-MM-ddTHH:mm:ss" placeholder="Select Expiry Date">
          </el-date-picker>
        </el-form-item>
      </template>
      <el-form-item class="flex_one tr">
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">Search</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">Reset</el-button>
        <el-button :icon="showMoreCondition ? 'el-icon-arrow-up' : 'el-icon-arrow-down'" size="mini"
          @click="showMoreCondition = !showMoreCondition">{{ showMoreCondition ? "Collapse" : "Expand" }}</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['wms:item:add']">New</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['wms:item:edit']">Modify</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['wms:item:remove']">Delete</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" :loading="exportLoading" @click="handleExport"
          v-hasPermi="['wms:item:export']">Export</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-download" size="mini" :disabled="multiple" :loading="exportLoading" @click="handlePrint" >Print</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button :type="initBool?'success':'danger'" plain  size="mini"   @click="handleConnect" :disabled="initBool" >{{connectPrintText}}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" ></right-toolbar>
    </el-row>

    <WmsTable v-loading="loading" :data="wmsItemList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="No." align="center" prop="itemNo"  />
      <el-table-column label="Goods Name" align="center" prop="itemName"  />
      <el-table-column label="Pic" align="center" prop="pics"  >
        <template slot-scope="scope">
          <span><ImagePreview v-if="scope.row.pics!=null" :src="scope.row.pics" class="listimage" ></ImagePreview></span>
        </template>
      </el-table-column>
      <el-table-column label="Code" align="center" prop="pics"  >
        <template slot-scope="scope">
          <span><ImagePreview v-if="scope.row.codePath!=null" :width="160"  :height="45" :src="scope.row.codePath" class="listimage" ></ImagePreview></span>
        </template>
      </el-table-column>
      <el-table-column label="Category" align="center" prop="itemTypeName"  />
      <el-table-column label="Unit" align="center" prop="unit"  />
      <el-table-column label="Safty Count" align="center" prop="quantity"  />
      <el-table-column label="Expiry Date" align="center" prop="expiryDate" width="180" >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.expiryDate, "") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Remark" align="center" prop="remark"  />
      <el-table-column label="Operate" align="center" class-name="small-padding fixed-width" width="170">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click.stop="handleUpdate(scope.row)"
            v-hasPermi="['wms:item:edit']">Modify</el-button>
          <el-button icon="el-icon-printer" size="mini" type="text"  @click.stop="handlePrintbyRow(scope.row)">Print</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['wms:item:remove']">Delete</el-button>
        </template>
      </el-table-column>
    </WmsTable>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- Add OrModifyItem对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="50%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="125px" class="dialog-form-two">
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="Goods Name" prop="itemName">
              <el-input v-model="form.itemName" placeholder="Please Input Item" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Category" prop="itemType">
              <treeselect v-model="form.itemType" :options="deptOptions" :show-count="true" placeholder="Please select Type" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">

          <el-col :span="12">
            <el-form-item label="Unit" prop="unit">
              <el-input v-model="form.unit" placeholder="Please Input Unit" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Safty Count" prop="quantity">
              <el-input v-model="form.quantity" placeholder="Please Input Safty Count" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">


          <el-col :span="12">
            <el-form-item label="Expiry Date" prop="expiryDate">
              <el-date-picker style="width: 100%" clearable size="small" v-model="form.expiryDate" type="datetime"
                              value-format="yyyy-MM-dd HH:mm:ss" placeholder="Select Expiry Date">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">

          <el-col :span="24">
            <el-form-item label="Remark" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="Please Input Remark" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :span="24">
          <el-col :span="24">
            <el-form-item label="" prop="pics">
              <ImageUpload v-model="form.pics"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">OK</el-button>
        <el-button @click="cancel">Cancel</el-button>
      </div>
    </el-dialog>
    <ItemPrint :codePath="codePath" ref="itemPrintRef"></ItemPrint>
  </div>
</template>

<script>
import {
  listWmsItem,
  getWmsItem,
  delWmsItem,
  addWmsItem,
  updateWmsItem,
  exportWmsItem,
} from "@/api/wms/item";
import { mapGetters } from "vuex";
import Treeselect from "@riophae/vue-treeselect";
import { itemTypeTreeselect } from "@/api/wms/itemType";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import ItemPrint from "@/views/wms/item/ItemPrint";


import Socket from "@/utils/Socket";
import NMPrintSocket from "@/utils/Print";
import { textPrintData } from "@/utils/printData/Text";
import { getBarcodePrintData } from "@/utils/printData/Barcode";
import { qrCodePrintData } from "@/utils/printData/QrCode";
import { linePrintData } from "@/utils/printData/Line";
import { graphPrintData } from "@/utils/printData/Graph";
import { imgPrintData } from "@/utils/printData/Img";
import { combinationPrintData } from "@/utils/printData/Combination";
import { batchPrintData } from "@/utils/printData/Batch";
export default {
  name: "WmsItem",
  components: { Treeselect,ItemPrint },
  dicts: ["wms_item_type"],
  computed: {
    ...mapGetters(['warehouseMap', 'warehouseList', 'areaList', 'areaMap', 'rackList', 'rackMap']),
  },
  data() {
    const jsonObj = {
      printerImageProcessingInfo: {
        printQuantity: 1,
      },
    };
    return {
      // 遮罩层
      loading: true,
      // Department树选项
      deptOptions: [],
      baseUrl:process.env.VUE_APP_BASE_API,
      // Export遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      selectRows:[],
      // 非个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // ShowSearch
      showSearch: true,
      // 总条数
      total: 0,
      // Item表格Data
      wmsItemList: [],
      //  Rack表格Data
      rackListByArea: [],
      // Area表格Data
      areaListByWarehouse: [],
      // 弹出层标题
      title: "",
      // Show弹出层
      open: false,

      // SearchParams
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        itemNo: null,
        itemName: null,
        itemType: null,
        unit: null,
        rackId: null,
        areaId: null,
        warehouseId: null,
        quantity: null,
        expiryDate: null,
      },
      // 表Params
      form: {},
      // 表校验
      rules: {
        itemNo: [{ required: true, message: "No. is required", trigger: "blur" }],
        itemName: [
          { required: true, message: "Item is required", trigger: "blur" },
        ],

      },
      columns: [
        { key: 1, label: "No.", visible: true },
        { key: 2, label: "Name", visible: true },
        { key: 3, label: "Pic", visible: true },
        { key: 4, label: "Category", visible: true },
        { key: 5, label: "Unit", visible: true },

        { key: 6, label: "Safty Count", visible: true },
        { key: 7, label: "Expiry Date", visible: true },
        { key: 8, label: "Remark", visible: false },
      ],
      codePath:"",
      showMoreCondition: false,
      textPrintData,
      qrCodePrintData,
      linePrintData,
      graphPrintData,
      imgPrintData,
      combinationPrintData,
      batchPrintData,
      jsonObj,
      // 打印服务是否连接成功
      printSocketOpen: false,
      nMPrintSocket: null,
      usbPrinters: {},
      wifiPrinters: {},
      onlineUsbBool: false,
      onlineWifiBool: false,
      usbSelectPrinter: "",
      wifiSelectPrinter: "",
      initBool: false,
      density: 8,
      label_type: 1,
      print_mode: 2,
      auto_shut_down: 1,
      wifiName: "",
      wifiPassword: "",
      connectPrintText:"Connect Label Print"
    };
  },
  mounted() {
    // 创建socket实例
    const socketData = new Socket("ws://127.0.0.1:37989");

    socketData.open(
      (openBool) => {
        console.log(openBool, "openBool");
        this.printSocketOpen=openBool;

      },
      (msg) => {
        if (msg.resultAck.callback != undefined) {
          const callbackName = msg.resultAck.callback.name;
          const msgInfo = msg.resultAck.info;
          if (callbackName == "onCoverStatusChange") {
            //盒盖状态：0-闭合、1-打开
            console.log("盒盖状态", msgInfo.capStatus);
          } else if (callbackName == "onElectricityChange") {
            //"power" : 0-4, // 电池电量等级（共5档）
            console.log("电池电量等级", msgInfo.power);
          }
        }
      }
    );
    // 创建打印实例
    this.nMPrintSocket = new NMPrintSocket(socketData);
  },
  created() {
    this.getList();
    itemTypeTreeselect().then(response => {
      this.deptOptions = response.data
    });
  },
  methods: {
    async handleConnect(){
      await this.getPrinters();
      if(this.usbSelectPrinter){
        console.log("打印机已选择")
        await this.selectOnLineUsbPrinter();
        if(this.onlineUsbBool){
          console.log("打印机已经连接")
          await this.init();
        }
      }

    },
    async getPrinters() {
      if (!this.printSocketOpen) {
        this.$modal.msgWarning("Print socket not open please wait...");
        return ;
      }
       try {
        const allPrintersRes = await this.nMPrintSocket.getAllPrinters();
        console.log(allPrintersRes, "allPrintersRes");
        if (allPrintersRes.resultAck.errorCode === 0) {
          const allPrinters = JSON.parse(allPrintersRes.resultAck.info);
          this.usbPrinters = { ...allPrinters };
          this.usbSelectPrinter = Object.keys(this.usbPrinters)[0];
          console.log("usbSelectPrinter:",this.usbSelectPrinter);
          console.log("usbPrinters:", this.usbPrinters);
        } else {
          this.$modal.msgWarning("No find print...");
        }
      } catch (err) {
        console.error(err);
      }
    },
    //初始化SDK
    async init() {
      if (!this.printSocketOpen) {
        this.$modal.msgWarning("Print socket not open please wait...");
        return ;
      }
      //初始化数据
      try {
        const res = await this.nMPrintSocket.initSdk({ fontDir: "" });
        if (res.resultAck.errorCode == 0) {
          console.log("初始化成功");
          this.initBool = true;
          this.connectPrintText="Connect Successful"
        } else {
          console.log("初始化失败");
          this.initBool = false;
          this.connectPrintText="Connect Failed"
        }
      } catch (err) {
        console.error(err);
      }
    },
    // 连接打印机
    async selectOnLineUsbPrinter() {
      if (!this.printSocketOpen) {
        this.$modal.msgWarning("Print socket not open please wait...");
        return ;
      }
      console.log("开始连接打印机");
      try {
        const res = await this.nMPrintSocket.selectPrinter(
          this.usbSelectPrinter,
          parseInt(this.usbPrinters[this.usbSelectPrinter])
        );
        console.log("选择打印机", res);

        if (res.resultAck.errorCode === 0) {
          console.log("连接成功");
          this.onlineUsbBool = true;
        } else {
          console.log("连接失败");
          this.onlineUsbBool = false;
          alert("连接失败");
        }
      } catch (err) {
        console.error(err);
      }
    },
    async handlePrintbyRow(row){
      let contentArr = [];
      contentArr.push(getBarcodePrintData(row.itemNo,row.remark));
      this.batchPrintJob(contentArr);
    },
    async handlePrint() {


      if (!this.initBool){
        this.$modal.msgWarning("Please connect print first");
        return;
      }
      let contentArr = [];
      this.selectRows.forEach(row=>{
        contentArr.push(getBarcodePrintData(row.itemNo,row.remark));
      })
      console.log(contentArr)
      this.batchPrintJob(contentArr);
    },
    //批量打印列表数据
    async batchPrintJob(list) {
      const printQuantity =this.jsonObj.printerImageProcessingInfo.printQuantity;
      try {
        const startRes = await this.nMPrintSocket.startJob(
          this.density,
          this.label_type,
          this.print_mode,
          list.length * printQuantity
        );
        if (startRes.resultAck.errorCode == 0) {
          // 提交打印任务
          await this.printTag(list, 0);
        }
      } catch (err) {
        console.error(err);
      }
    },
    // 绘制打印标签
    async printTag(list, x) {
      console.log(list, x);
      //设置画布尺寸
      try {
        const res = await this.nMPrintSocket.InitDrawingBoard(
          list[x].InitDrawingBoardParam
        );
        if (res.resultAck.errorCode != 0) {
          return;
        }
        // 提交打印任务
        this.printItem(list, x, list[x].elements, 0);
      } catch (err) {
        console.error(err);
      }
    },
    async printItem(list, x, item, i) {
      try {
        if (i < item.length) {
          console.log("item:",JSON.stringify(item[i]));
          let arrParse;
          switch (item[i].type) {
            case "text":
              //绘制文本
              arrParse = await this.nMPrintSocket.DrawLableText(item[i].json);
              console.log("textresult:",arrParse)
              if (arrParse.resultAck.errorCode != 0) {
                return;
              }
              i++;
              await this.printItem(list, x, item, i);
              break;
            case "qrCode":
              arrParse = await this.nMPrintSocket.DrawLableQrCode(item[i].json);
              //绘制二维码
              if (arrParse.resultAck.errorCode !== 0) {
                return;
              }
              i++;
              await this.printItem(list, x, item, i);
              break;
            case "barCode":
              //绘制一维码
              arrParse = await this.nMPrintSocket.DrawLableBarCode(
                item[i].json
              );
              if (arrParse.resultAck.errorCode !== 0) {
                return;
              }
              i++;
              await this.printItem(list, x, item, i);
              break;
            case "line":
              //绘制线条
              arrParse = await this.nMPrintSocket.DrawLableLine(item[i].json);
              if (arrParse.resultAck.errorCode !== 0) {
                return;
              }
              i++;
              await this.printItem(list, x, item, i);
              break;
            case "graph":
              //绘制边框
              arrParse = await this.nMPrintSocket.DrawLableGraph(item[i].json);
              if (arrParse.resultAck.errorCode != 0) {
                return;
              }

              i++;
              await this.printItem(list, x, item, i);
              break;
            case "image":
              //绘制边框
              arrParse = await this.nMPrintSocket.DrawLableImage(item[i].json);
              if (arrParse.resultAck.errorCode != 0) {
                return;
              }
              i++;
              await this.printItem(list, x, item, i);
              break;
          }
        } else {
          //遍历完成，开始打印
          const commitRes = await this.nMPrintSocket.commitJob(
            null,
            JSON.stringify(this.jsonObj)
          );
          //回调页码为数据总长度且回调打印份数数据等于当前页需要打印的份数数据时，结束打印任务
          if (
            commitRes.resultAck.printQuantity == list.length &&
            commitRes.resultAck.onPrintPageCompleted ==
            this.jsonObj.printerImageProcessingInfo.printQuantity
          ) {
            //结束打印任务
            const endRes = await this.nMPrintSocket.endJob();
            if (endRes.resultAck.errorCode === 0) {
              console.log("打印完成");
            }
            return;
          }
          //回调为提交成功，同时数据未发送完成时，可继续提交数据
          if (commitRes.resultAck.errorCode === 0 && x < list.length - 1) {
            //数据提交成功，数据下标+1
            console.log("发送下一页打印数据： ");
            x++;
            await this.printTag(list, x);
          }
        }
      } catch (err) {
        console.error(err);
      }
    },
    windowPrintOut(row, print) {
      // alert(print)
      console.log("row:",row)
      this.codePath = row.codePath;
      this.$nextTick(() => {
        this.$refs['itemPrintRef'].start()
      })
      // this.getOrderDetail(row).then(res => {
      //   console.log(res)
      //   if (print) {
      //     this.modalObj.form.row = res
      //     this.$nextTick(() => {
      //       this.$refs['receiptOrderPrintRef'].start()
      //     })
      //     return
      //   }
      // })
    },
    /** SearchItem列表 */
    getList() {
      this.loading = true;
      const { pageNum, pageSize } = this.queryParams;
      const query = {
        ...this.queryParams,
        pageNum: undefined,
        pageSize: undefined,
      };
      const pageReq = { page: pageNum - 1, size: pageSize };
      listWmsItem(query, pageReq).then((response) => {
        const { content, totalElements } = response;
        content.forEach((item) => {
          item.warehouseName = this.warehouseMap.get(item.warehouseId);
        });
        content.forEach((item) => {
          item.areaName = this.areaMap.get(item.areaId);
        });
        content.forEach((item) => {
          item.rackName = this.rackMap.get(item.rackId);
        });
        console.log("listWmsItem:",content)
        this.wmsItemList = content;
        this.total = totalElements;
        this.loading = false;
      });
    },
    // CancelButton
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表Reset
    reset() {
      this.form = {
        id: null,
        itemNo: null,
        itemName: null,
        itemType: null,
        unit: null,
        rackId: null,
        areaId: null,
        warehouseId: null,
        quantity: null,
        expiryDate: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        pics:null
      };
      this.resetForm("form");
    },
    /** SearchButtonOperate */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** ResetButtonOperate */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中Data
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.selectRows = selection;

      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** AddButtonOperate */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "New";
    },
    /** ModifyButtonOperate */
    async handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      await getWmsItem(id).then((response) => {
        this.form = response;
        this.open = true;
        this.title = "Modify";
      });
    },

    /** 提交Button */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.place) {
            this.form.warehouseId = this.form.place[0]
            this.form.areaId = this.form.place[1]
            this.form.rackId = this.form.place[2]
          }
          if (this.form.id != null) {
            updateWmsItem(this.form).then((response) => {
              this.$modal.msgSuccess("Modify Successful");
              this.open = false;
              this.getList();
            });
          } else {
            addWmsItem(this.form).then((response) => {
              this.$modal.msgSuccess("Add Successful");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** DeleteButtonOperate */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm(' Do you want delete Goods No."' + ids + '"？')
        .then(function () {
          return delWmsItem(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("Delete Successful");
        })
        .catch(() => { });
    },
    /** ExportButtonOperate */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal
        .confirm("Export All？")
        .then(() => {
          this.exportLoading = true;
          return exportWmsItem(queryParams);
        })
        .then((response) => {
          this.$download.download(response);
          this.exportLoading = false;
        })
        .catch(() => { });
    },
  },
};
</script>
