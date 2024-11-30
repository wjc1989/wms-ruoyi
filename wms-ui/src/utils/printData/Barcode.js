var width = 50;
var height = 70;
var rotate = 0;
var marginX = 1.0;
var marginY = 3.0;
var barCodeWidth = 45;
var barCodeHeight = 20;
var fontSize = 3.2;
var lineWidth = 0.4;

export function getBarcodePrintData(code,text) {
  return {
    InitDrawingBoardParam: {
      width: width,
      height: height,
      rotate: rotate,
      path: "ZT001.ttf",
      verticalShift: 0,
      HorizontalShift: 0,
    },

    elements: [
      {
        type: "barCode",
        json: {
          x: 0 ,
          y: 2 ,
          height: barCodeHeight,
          width: barCodeWidth,
          value: code,
          codeType: 24,
          rotate: 0,
          fontSize: fontSize,
          textHeight: fontSize,
          textPosition: 0,
        },
      },   {
        type: 'line',
        json: {
          "x": marginX,
          "y": barCodeHeight+marginY ,
          "height": lineWidth,
          "width": barCodeWidth,
          "rotate": 0,
          "lineType": 1,
          "dashwidth": [1, 1],
        }
      },{
        type: 'text',
        json: {
          "x": marginX ,
          "y": barCodeHeight+marginY+1,
          "height": height-1-barCodeHeight-marginY*2,
          "width": barCodeWidth,
          "value": text,
          "fontFamily": "宋体",
          "rotate": 0,
          "fontSize": fontSize,
          "textAlignHorizonral": 0,
          "textAlignVertical": 1,
          "letterSpacing": 0.0,
          "lineSpacing": 1.0,
          "lineMode": 6,
          "fontStyle": [true, false, false, false],
        }
      }
    ],
  };

}
