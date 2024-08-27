export const STOCK_OUT_TEMPLATE = {
  "default": "true",
  "title": "Outbound ",
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
    "name": "shipmentOrderNo",
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
    "left": 39,
    "top": 40,
    "title": "Custom 义文本",
    "value": "Custom：",
    "defaultValue": "Custom 义文本",
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
    "title": "Custom 义文本",
    "value": "No.：",
    "defaultValue": "Custom 义文本",
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
    "top": 67,
    "title": "Custom 义文本",
    "value": "Outbound Type：",
    "defaultValue": "Custom 义文本",
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
    "left": 387,
    "top": 67,
    "title": "Custom 义文本",
    "value": "Date：",
    "defaultValue": "Custom 义文本",
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
    "left": 81,
    "top": 40,
    "title": "Custom",
    "value": "{ Custom } ",
    "defaultValue": "中国苹果",
    "name": "customerName",
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
    "title": "No.",
    "value": "{ No. } ",
    "defaultValue": "zj001",
    "name": "orderNo",
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
    "top": 67,
    "title": "Outbound Type",
    "value": "{ Outbound Type } ",
    "defaultValue": "销售Outbound ",
    "name": "shipmentType",
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
    "left": 425,
    "top": 67,
    "title": "Create Time",
    "value": "{ Create Time } ",
    "defaultValue": "2020-08-27 12: 00: 00",
    "name": "createTime",
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
    "top": 110,
    "title": "Outbound 商品明细",
    "value": "{ details } ",
    "tabelHtml": "",
    "columnsAttr": [{
      "title": "Goods Name",
      "value": "{ Goods Name } ",
      "name": "itemName"
    }, {
      "title": "Goods No.",
      "value": "{ Goods No. } ",
      "name": "itemNo"
    }, {
      "title": "Type",
      "value": "{ ItemType } ",
      "name": "itemType"
    }, {
      "title": "Count",
      "value": "{ Count } ",
      "name": "planQuantity"
    }, {
      "title": "Warehouse",
      "value": "{ Warehouse } ",
      "name": "place"
    }],
    "columns": [{
      "title": "Goods Name",
      "value": "{Goods Name}",
      "name": "itemName"
    }, {
      "title": "Goods No.",
      "value": "{Goods No.}",
      "name": "itemNo"
    }, {
      "title": "ItemType",
      "value": "{ItemType}",
      "name": "itemType"
    }, {
      "title": "Count",
      "value": "{Count}",
      "name": "planQuantity"
    }, {
      "title": "Warehouse",
      "value": "{Warehouse}",
      "name": "place"
    }],
    "selectCol": ["itemName", "itemNo", "itemType", "planQuantity", "place"],
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
    "left": 435,
    "top": 382,
    "title": "Custom 义文本",
    "value": "Total Count：",
    "defaultValue": "Custom 义文本",
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
    "top": 430,
    "title": "Custom 义文本",
    "value": "Remark：",
    "defaultValue": "Custom 义文本",
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
    "width": 180,
    "height": 20,
    "left": 492,
    "top": 382,
    "title": "Total Count",
    "value": "{Total Count}",
    "defaultValue": "123.00",
    "name": "totalCount",
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
    "width": 660,
    "height": 20,
    "left": 50,
    "top": 430,
    "title": "Remark",
    "value": "{Remark}",
    "defaultValue": "北京采购Inbound成都摘要",
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
  }]
}


export const STOCK_IN_TEMPLATE = {
  "default": "true",
  "title": "Inbound",
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
    "title": "Inbound No.",
    "value": "{ Inbound No. } ",
    "defaultValue": "CK-1234567890",
    "name": "receiptOrderNo",
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
    "left": 27,
    "top": 40,
    "title": "Custom 义文本",
    "value": "Supplier：",
    "defaultValue": "Custom 义文本",
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
    "title": "Custom 义文本",
    "value": "No.：",
    "defaultValue": "Custom 义文本",
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
    "top": 67,
    "title": "Custom 义文本",
    "value": "Type：",
    "defaultValue": "Custom 义文本",
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
    "left": 387,
    "top": 67,
    "title": "Custom 义文本",
    "value": "Date：",
    "defaultValue": "Custom 义文本",
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
    "left": 80,
    "top": 40,
    "title": "Supplier",
    "value": "{ Supplier } ",
    "defaultValue": "中国苹果",
    "name": "supplierName",
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
    "title": "No.",
    "value": "{ No. } ",
    "defaultValue": "zj001",
    "name": "orderNo",
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
    "top": 67,
    "title": "Type",
    "value": "{ Type } ",
    "defaultValue": "销售Outbound ",
    "name": "receiptType",
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
    "left": 425,
    "top": 67,
    "title": "Create Time",
    "value": "{ Create Time } ",
    "defaultValue": "2020-08-27 12: 00: 00",
    "name": "createTime",
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
    "top": 110,
    "title": "Inbound商品明细",
    "value": "{ details } ",
    "tabelHtml": "",
    "columnsAttr": [{
      "title": "Goods Name",
      "value": "{ Goods Name } ",
      "name": "itemName"
    }, {
      "title": "Goods No.",
      "value": "{ Goods No. } ",
      "name": "itemNo"
    }, {
      "title": "ItemType",
      "value": "{ ItemType } ",
      "name": "itemType"
    }, {
      "title": "Count",
      "value": "{ Count } ",
      "name": "planQuantity"
    }, {
      "title": "Warehouse",
      "value": "{ Warehouse } ",
      "name": "place"
    }],
    "columns": [{
      "title": "Goods Name",
      "value": "{Goods Name}",
      "name": "itemName"
    }, {
      "title": "Goods No.",
      "value": "{Goods No.}",
      "name": "itemNo"
    }, {
      "title": "ItemType",
      "value": "{ItemType}",
      "name": "itemType"
    }, {
      "title": "Count",
      "value": "{Count}",
      "name": "planQuantity"
    }, {
      "title": "Warehouse",
      "value": "{Warehouse}",
      "name": "place"
    }],
    "selectCol": ["itemName", "itemNo", "itemType", "planQuantity", "place"],
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
    "left": 435,
    "top": 382,
    "title": "Custom 义文本",
    "value": "Total Count：",
    "defaultValue": "Custom 义文本",
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
    "top": 430,
    "title": "Custom 义文本",
    "value": "Remark：",
    "defaultValue": "Custom 义文本",
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
    "width": 180,
    "height": 20,
    "left": 492,
    "top": 382,
    "title": "Total Count",
    "value": "{Total Count}",
    "defaultValue": "123.00",
    "name": "totalCount",
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
    "width": 660,
    "height": 20,
    "left": 50,
    "top": 430,
    "title": "Remark",
    "value": "{Remark}",
    "defaultValue": "北京采购Inbound成都摘要",
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
  }]
}
