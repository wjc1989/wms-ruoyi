<template>
  <kr-print-designer
    :temp-value="tempValue"
    :widget-options="widgets"
    :lodop-license="licenseInfo"
    @save="handleSave"
  />

</template>

<script>
export default {
  data() {
    return {
      tempValue: {
        "default": "true",
        "title": "Outbound Example",
        "type": 1,
        "width": 770,
        "height": 500,
        "pageWidth": 215,
        "pageHeight": 140,
        "tempItems": [{
          "type": "braid-txt",
          "isEdit": 1,
          "dragable": true,
          "resizable": true,
          "width": 770,
          "height": 25,
          "left": 0,
          "top": 10,
          "title": "Outbound No.",
          "value": "{ Outbound No. } ",
          "defaultValue": "CK-1234567890",
          "name": "stockoutCode",
          "style": {
            "zIndex": 0,
            "FontSize": 12,
            "FontColor": "#  000000",
            "Bold": false,
            "Italic": false,
            "Underline": false,
            "Alignment": "center",
            "ItemType": 1
          },
          "uuid": "7f7b0b45da"
        }, {
          "type": "braid-txt",
          "isEdit": 1,
          "dragable": true,
          "resizable": true,
          "width": 110,
          "height": 20,
          "left": 15,
          "top": 40,
          "title": "Custom",
          "value": "Custom/Supplier：",
          "defaultValue": "Custom",
          "name": "",
          "style": {
            "zIndex": 0,
            "FontSize": 9,
            "FontColor": "#  000000",
            "Bold": false,
            "Italic": false,
            "Underline": false,
            "Alignment": "left",
            "ItemType": 1
          },
          "uuid": "54ca3dc671"
        }, {
          "type": "braid-txt",
          "isEdit": 1,
          "dragable": true,
          "resizable": true,
          "width": 110,
          "height": 20,
          "left": 375,
          "top": 40,
          "title": "Custom",
          "value": "People：",
          "defaultValue": "Custom",
          "name": "",
          "style": {
            "zIndex": 0,
            "FontSize": 9,
            "FontColor": "#  000000",
            "Bold": false,
            "Italic": false,
            "Underline": false,
            "Alignment": "left",
            "ItemType": 1
          },
          "uuid": "dae9eb86e4"
        }, {
          "type": "braid-txt",
          "isEdit": 1,
          "dragable": true,
          "resizable": true,
          "width": 110,
          "height": 20,
          "left": 15,
          "top": 60,
          "title": "Custom",
          "value": "OutboundType：",
          "defaultValue": "Custom",
          "name": "",
          "style": {
            "zIndex": 0,
            "FontSize": 9,
            "FontColor": "#  000000",
            "Bold": false,
            "Italic": false,
            "Underline": false,
            "Alignment": "left",
            "ItemType": 1
          },
          "uuid": "317ae87dea"
        }, {
          "type": "braid-txt",
          "isEdit": 1,
          "dragable": true,
          "resizable": true,
          "width": 110,
          "height": 20,
          "left": 375,
          "top": 60,
          "title": "Custom",
          "value": "Date：",
          "defaultValue": "Custom",
          "name": "",
          "style": {
            "zIndex": 0,
            "FontSize": 9,
            "FontColor": "#  000000",
            "Bold": false,
            "Italic": false,
            "Underline": false,
            "Alignment": "left",
            "ItemType": 1
          },
          "uuid": "d8cf0f23b0"
        }, {
          "type": "braid-txt",
          "isEdit": 1,
          "dragable": true,
          "resizable": true,
          "width": 268,
          "height": 20,
          "left": 95,
          "top": 40,
          "title": "Custom/Supplier",
          "value": "{ Custom/Supplier } ",
          "defaultValue": "Apple",
          "name": "unitName",
          "style": {
            "zIndex": 0,
            "FontSize": 9,
            "FontColor": "#  000000",
            "Bold": false,
            "Italic": false,
            "Underline": false,
            "Alignment": "left",
            "ItemType": 1
          },
          "uuid": "a77126a142"
        }, {
          "type": "braid-txt",
          "isEdit": 1,
          "dragable": true,
          "resizable": true,
          "width": 299,
          "height": 20,
          "left": 425,
          "top": 40,
          "title": "Manager",
          "value": "{ Manager } ",
          "defaultValue": "zj001",
          "name": "handlerName",
          "style": {
            "zIndex": 0,
            "FontSize": 9,
            "FontColor": "#  000000",
            "Bold": false,
            "Italic": false,
            "Underline": false,
            "Alignment": "left",
            "ItemType": 1
          },
          "uuid": "d8f17a27f5"
        }, {
          "type": "braid-txt",
          "isEdit": 1,
          "dragable": true,
          "resizable": true,
          "width": 257,
          "height": 20,
          "left": 80,
          "top": 60,
          "title": "Outbound Type",
          "value": "{ Outbound Type } ",
          "defaultValue": "Outbound ",
          "name": "stockoutType",
          "style": {
            "zIndex": 0,
            "FontSize": 9,
            "FontColor": "#  000000",
            "Bold": false,
            "Italic": false,
            "Underline": false,
            "Alignment": "left",
            "ItemType": 1
          },
          "uuid": "4be08abbb3"
        }, {
          "type": "braid-txt",
          "isEdit": 1,
          "dragable": true,
          "resizable": true,
          "width": 230,
          "height": 20,
          "left": 410,
          "top": 60,
          "title": "Create Time",
          "value": "{ Create Time } ",
          "defaultValue": "2020-08-27 12: 00: 00",
          "name": "created",
          "style": {
            "zIndex": 0,
            "FontSize": 9,
            "FontColor": "#  000000",
            "Bold": false,
            "Italic": false,
            "Underline": false,
            "Alignment": "left",
            "ItemType": 0
          },
          "uuid": "83ef9c90be"
        }, {
          "type": "braid-table",
          "isEdit": 0,
          "dragable": true,
          "resizable": true,
          "width": 740,
          "height": 275,
          "left": 15,
          "top": 85,
          "title": "Outbound Detail",
          "value": "{ details } ",
          "defaultValue": [{
            "productName": "Apple ipone11pro",
            "skuName": "iphone11pro256g",
            "specModel": "165L",
            "quantity": 3,
            "snCode": "[SPP1Y79G1]",
            "price": "23.00"
          }, {
            "productName": "Apple ipone11pro",
            "skuName": "iphone11pro124g",
            "specModel": "165L",
            "quantity": 3,
            "snCode": "[SPP1Y79G1]",
            "price": "12.00"
          }],
          "tabelHtml": "",
          "columnsAttr": [{
            "title": "Goods Name",
            "value": "{ Goods Name } ",
            "name": "productName"
          }, {
            "title": "skuItem",
            "value": "{ skuItem } ",
            "name": "skuName"
          }, {
            "title": "Sku",
            "value": "{ Sku } ",
            "name": "specModel"
          }, {
            "title": "Count",
            "value": "{ Count } ",
            "name": "quantity"
          }, {
            "title": "Price",
            "value": "{ Price } ",
            "name": "price"
          }, {
            "title": "Goods No.",
            "value": "Goods No. Detail：<br>{Goods No.}",
            "name": "snCode",
            "trSet": {
              "title": "Goods No. Detail:",
              "col": 5,
              "margin": "50px"
            }
          }],
          "columns": [{
            "title": "Goods Name",
            "value": "{Goods Name}",
            "name": "productName"
          }, {
            "title": "skuItem",
            "value": "{skuItem}",
            "name": "skuName"
          }, {
            "title": "Sku",
            "value": "{Sku}",
            "name": "specModel"
          }, {
            "title": "Count",
            "value": "{Count}",
            "name": "quantity"
          }, {
            "title": "Price",
            "value": "{Price}",
            "name": "price"
          }],
          "selectCol": ["productName", "skuName", "specModel", "quantity", "price"],
          "name": "details",
          "style": {
            "zIndex": 0,
            "Alignment": "left",
            "FontSize": 9,
            "FontColor": "#000000",
            "BorderColor": "#000000",
            "AutoHeight": true,
            "BottomMargin": 0
          },
          "uuid": "8f1ee2cff3"
        }, {
          "type": "braid-txt",
          "isEdit": 1,
          "dragable": true,
          "resizable": true,
          "width": 110,
          "height": 20,
          "left": 15,
          "top": 370,
          "title": "Custom",
          "value": "OutboundWarehouse：",
          "defaultValue": "Custom",
          "name": "",
          "style": {
            "zIndex": 0,
            "FontSize": 9,
            "FontColor": "#  000000",
            "Bold": false,
            "Italic": false,
            "Underline": false,
            "Alignment": "left",
            "ItemType": 0
          },
          "uuid": "6171703d27"
        }, {
          "type": "braid-txt",
          "isEdit": 1,
          "dragable": true,
          "resizable": true,
          "width": 110,
          "height": 20,
          "left": 495,
          "top": 370,
          "title": "Custom",
          "value": "Total Amount：",
          "defaultValue": "Custom",
          "name": "",
          "style": {
            "zIndex": 0,
            "FontSize": 9,
            "FontColor": "#  000000",
            "Bold": false,
            "Italic": false,
            "Underline": false,
            "Alignment": "left",
            "ItemType": 0
          },
          "uuid": "15134dd251"
        }, {
          "type": "braid-txt",
          "isEdit": 1,
          "dragable": true,
          "resizable": true,
          "width": 110,
          "height": 20,
          "left": 15,
          "top": 390,
          "title": "Custom",
          "value": "Contacts：",
          "defaultValue": "Custom",
          "name": "",
          "style": {
            "zIndex": 0,
            "FontSize": 9,
            "FontColor": "#  000000",
            "Bold": false,
            "Italic": false,
            "Underline": false,
            "Alignment": "left",
            "ItemType": 0
          },
          "uuid": "8f53513be0"
        }, {
          "type": "braid-txt",
          "isEdit": 1,
          "dragable": true,
          "resizable": true,
          "width": 110,
          "height": 20,
          "left": 255,
          "top": 390,
          "title": "Custom",
          "value": "Phone：",
          "defaultValue": "Custom",
          "name": "",
          "style": {
            "zIndex": 0,
            "FontSize": 9,
            "FontColor": "#  000000",
            "Bold": false,
            "Italic": false,
            "Underline": false,
            "Alignment": "left",
            "ItemType": 0
          },
          "uuid": "74dad4adb2"
        }, {
          "type": "braid-txt",
          "isEdit": 1,
          "dragable": true,
          "resizable": true,
          "width": 110,
          "height": 20,
          "left": 15,
          "top": 410,
          "title": "Custom",
          "value": "Address：",
          "defaultValue": "Custom",
          "name": "",
          "style": {
            "zIndex": 0,
            "FontSize": 9,
            "FontColor": "#  000000",
            "Bold": false,
            "Italic": false,
            "Underline": false,
            "Alignment": "left",
            "ItemType": 0
          },
          "uuid": "4ed4accd35"
        }, {
          "type": "braid-txt",
          "isEdit": 1,
          "dragable": true,
          "resizable": true,
          "width": 110,
          "height": 20,
          "left": 15,
          "top": 430,
          "title": "Custom",
          "value": "Remark：",
          "defaultValue": "Custom",
          "name": "",
          "style": {
            "zIndex": 0,
            "FontSize": 9,
            "FontColor": "#  000000",
            "Bold": false,
            "Italic": false,
            "Underline": false,
            "Alignment": "left",
            "ItemType": 0
          },
          "uuid": "6b5f65c726"
        }, {
          "type": "braid-txt",
          "isEdit": 1,
          "dragable": true,
          "resizable": true,
          "width": 110,
          "height": 20,
          "left": 15,
          "top": 450,
          "title": "Custom",
          "value": "Manager：",
          "defaultValue": "Custom",
          "name": "",
          "style": {
            "zIndex": 0,
            "FontSize": 9,
            "FontColor": "#  000000",
            "Bold": false,
            "Italic": false,
            "Underline": false,
            "Alignment": "left",
            "ItemType": 0
          },
          "uuid": "c71258f82d"
        }, {
          "type": "braid-txt",
          "isEdit": 1,
          "dragable": true,
          "resizable": true,
          "width": 110,
          "height": 20,
          "left": 375,
          "top": 450,
          "title": "Custom",
          "value": "Sign：",
          "defaultValue": "Custom",
          "name": "",
          "style": {
            "zIndex": 0,
            "FontSize": 9,
            "FontColor": "#  000000",
            "Bold": false,
            "Italic": false,
            "Underline": false,
            "Alignment": "left",
            "ItemType": 0
          },
          "uuid": "648e537904"
        }, {
          "type": "braid-txt",
          "isEdit": 1,
          "dragable": true,
          "resizable": true,
          "width": 400,
          "height": 20,
          "left": 75,
          "top": 370,
          "title": "Outbound Warehouse",
          "value": "{Outbound Warehouse}",
          "defaultValue": "Warehouse",
          "name": "warehouseName",
          "style": {
            "zIndex": 0,
            "FontSize": 9,
            "FontColor": "#  000000",
            "Bold": false,
            "Italic": false,
            "Underline": false,
            "Alignment": "left",
            "ItemType": 0
          },
          "uuid": "10855eefc1"
        }, {
          "type": "braid-txt",
          "isEdit": 1,
          "dragable": true,
          "resizable": true,
          "width": 180,
          "height": 20,
          "left": 555,
          "top": 370,
          "title": "Total Amount",
          "value": "{Total Amount}",
          "defaultValue": "123.00",
          "name": "totalPrice",
          "style": {
            "zIndex": 0,
            "FontSize": 9,
            "FontColor": "#  000000",
            "Bold": false,
            "Italic": false,
            "Underline": false,
            "Alignment": "left",
            "ItemType": 0
          },
          "uuid": "7a425ef108"
        }, {
          "type": "braid-txt",
          "isEdit": 1,
          "dragable": true,
          "resizable": true,
          "width": 180,
          "height": 20,
          "left": 60,
          "top": 390,
          "title": "Receiver",
          "value": "{Receiver}",
          "defaultValue": "",
          "name": "receivePerson",
          "style": {
            "zIndex": 0,
            "FontSize": 9,
            "FontColor": "#  000000",
            "Bold": false,
            "Italic": false,
            "Underline": false,
            "Alignment": "left",
            "ItemType": 0
          },
          "uuid": "29a5f24c1b"
        }, {
          "type": "braid-txt",
          "isEdit": 1,
          "dragable": true,
          "resizable": true,
          "width": 190,
          "height": 20,
          "left": 290,
          "top": 390,
          "title": "Receive Phone",
          "value": "{ReceiverPhone}",
          "defaultValue": "Receiver",
          "name": "receivePhone",
          "style": {
            "zIndex": 0,
            "FontSize": 9,
            "FontColor": "#  000000",
            "Bold": false,
            "Italic": false,
            "Underline": false,
            "Alignment": "left",
            "ItemType": 0
          },
          "uuid": "ae0fd46895"
        }, {
          "type": "braid-txt",
          "isEdit": 1,
          "dragable": true,
          "resizable": true,
          "width": 680,
          "height": 20,
          "left": 50,
          "top": 410,
          "title": "Receive Address",
          "value": "{Receive Address}",
          "defaultValue": "",
          "name": "address",
          "style": {
            "zIndex": 0,
            "FontSize": 9,
            "FontColor": "#  000000",
            "Bold": false,
            "Italic": false,
            "Underline": false,
            "Alignment": "left",
            "ItemType": 0
          },
          "uuid": "dae1e2cac9"
        }, {
          "type": "braid-txt",
          "isEdit": 1,
          "dragable": true,
          "resizable": true,
          "width": 660,
          "height": 20,
          "left": 50,
          "top": 430,
          "title": "Info",
          "value": "{Info}",
          "defaultValue": "",
          "name": "remark",
          "style": {
            "zIndex": 0,
            "FontSize": 9,
            "FontColor": "#  000000",
            "Bold": false,
            "Italic": false,
            "Underline": false,
            "Alignment": "left",
            "ItemType": 0
          },
          "uuid": "a745e40eae"
        }, {
          "type": "braid-txt",
          "isEdit": 1,
          "dragable": true,
          "resizable": true,
          "width": 292,
          "height": 20,
          "left": 50,
          "top": 450,
          "title": "Creator",
          "value": "{Creator}",
          "defaultValue": "jz002",
          "name": "createUserName",
          "style": {
            "zIndex": 0,
            "FontSize": 9,
            "FontColor": "#  000000",
            "Bold": false,
            "Italic": false,
            "Underline": false,
            "Alignment": "left",
            "ItemType": 0
          },
          "uuid": "2d1b141163"
        }]
      },
      widgets: [
        {
          type: 'braid-txt',
          isEdit: 1,
          title: 'Custom',
          value: 'Custom',
          defaultValue: 'Custom',
          name: ''
        },
        {
          type: 'braid-txt',
          isEdit: 0,
          title: 'Company',
          value: '{Company}',
          defaultValue: 'Company',
          name: 'companyName'
        },
        {
          type: 'braid-txt',
          isEdit: 1,
          title: 'Outbound No.',
          value: '{Outbound No.}',
          defaultValue: 'CK-1234567890',
          name: 'stockoutCode'
        },
        {
          type: 'bar-code',
          isEdit: 1,
          title: 'No.',
          value: '{No.}',
          defaultValue: 'CK-1234567890',
          name: 'stockoutCode',
          lodopStyle: { QRCodeVersion: '1', QRCodeErrorLevel: 'L' },
        },
        {
          type: 'braid-txt',
          isEdit: 1,
          title: 'Custom/Supplier',
          value: '{Custom/Supplier}',
          defaultValue: 'Apple',
          name: 'unitName'
        },
        {
          type: 'braid-txt',
          isEdit: 1,
          title: 'Manager',
          value: '{Manager}',
          defaultValue: 'zj001',
          name: 'handlerName'
        },
        {
          type: 'braid-txt',
          isEdit: 1,
          title: 'Outbound Type',
          value: '{Outbound Type}',
          defaultValue: 'Outbound ',
          name: 'stockoutType'
        },
        {
          type: 'braid-txt',
          isEdit: 1,
          title: 'Outbound Warehouse',
          value: '{Outbound Warehouse}',
          defaultValue: 'Warehouse',
          name: 'warehouseName'
        },
        {
          type: 'braid-txt',
          isEdit: 1,
          title: 'Outbound  Time',
          value: '{Outbound  Time}',
          defaultValue: '2020-08-27 12:00:00',
          name: 'businessDate'
        },
        {
          type: 'braid-txt',
          isEdit: 1,
          title: 'Creator',
          value: '{Creator}',
          defaultValue: 'jz002',
          name: 'createUserName'
        },
        {
          type: 'braid-txt',
          isEdit: 1,
          title: 'Create Time',
          value: '{Create Time}',
          defaultValue: '2020-08-27 12:00:00',
          name: 'created'
        },
        {
          type: 'braid-txt',
          isEdit: 1,
          title: 'Receiver',
          value: '{Receiver}',
          defaultValue: '',
          name: 'receivePerson'
        },
        {
          type: 'braid-txt',
          isEdit: 1,
          title: 'Receive Phone',
          value: '{ReceiverPhone}',
          defaultValue: 'Receiver',
          name: 'receivePhone'
        },
        {
          type: 'braid-txt',
          isEdit: 1,
          title: 'Receive Address',
          value: '{Receive Address}',
          defaultValue: '',
          name: 'address'
        },
        {
          type: 'braid-txt',
          isEdit: 1,
          title: 'Total Amount',
          value: '{Total Amount}',
          defaultValue: '123.00',
          name: 'totalPrice'
        },
        {
          type: 'braid-txt',
          isEdit: 1,
          title: 'Info',
          value: '{Info}',
          defaultValue: '',
          name: 'remark'
        },
        {
          type: 'braid-html',
          isEdit: 1,
          title: 'Page',
          value: '{Page ##/##}',
          defaultValue: '<font><span tdata=\'pageNO\'>Page ##</span>/<span tdata=\'pageCount\'>##</span></font>',
          name: ''
        },
        {
          type: 'braid-table',
          isEdit: 0,
          title: 'Outbound Goods Detail',
          name: 'details',
          value: '{details}',
          style: {},
          defaultValue: [
            {
              productName: 'Apple ipone11pro',
              skuName: 'iphone11pro256g',
              specModel: '165L',
              quantity: 3,
              snCode: '[SPP1Y79G1]',
              price: '23.00'
            },
            {
              productName: 'Apple ipone11pro',
              skuName: 'iphone11pro124g',
              specModel: '165L',
              quantity: 3,
              snCode: ' [SPP1Y79G1]',
              price: '12.00'
            }

          ],
          tabelHtml: '',
          columnsAttr: [
            {
              title: 'Goods Name',
              value: '{Goods Name}',
              name: 'productName',
            },
            {
              title: 'skuItem',
              value: '{skuItem}',
              name: 'skuName',
            },
            {
              title: 'Sku',
              value: '{Sku}',
              name: 'specModel'
            },
            {
              title: 'Count',
              value: '{Count}',
              name: 'quantity'
            },
            {
              title: 'Price',
              value: '{Price}',
              name: 'price'
            },
            {
              title: 'Goods No.',
              value: 'Goods No. Detail：<br>{Goods No.}',
              name: 'snCode',
              trSet: {
                title: 'Goods No. Detail:',
                col: 5,
                margin: '50px'
              },
            }
          ],
          columns: []
        }
      ],
      licenseInfo: {}
    }
  },
  methods: {
    handleSave() {
      const data = [{
        stockoutCode: 'CK2421412412423421', created: "2022-09-01 12:33:22",
        details: [{
          "productName": "Apple ipone11pro",
          "skuName": "iphone11pro256g",
          "specModel": "165L",
          "quantity": 3,
          "snCode": "[SPP1Y79G1]",
          "price": "23.00"
        }, {
          "productName": "Apple ipone22pro",
          "skuName": "iphone11pro124g",
          "specModel": "165L",
          "quantity": 5,
          "snCode": "[SPP1Y79G1]",
          "price": "12.00"
        }]
      }]
      this.$lodop.preview(this.tempValue, data);
    },
  }
}
</script>
