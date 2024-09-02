var offsetX = 0;
var offsetY = 0;
var width = 45;
var height = 24;
var rotate = 0;
var marginX = 1.0;
var marginY = 2.0;
var barCodeWidth = width - marginX * 2;
var barCodeHeight = height - marginY * 2;
var fontSize = 3.2;


export function getBarcodePrintData(code) {
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
          x: marginX + offsetX,
          y: marginY + offsetY,
          height: barCodeHeight,
          width: barCodeWidth,
          value: code,
          codeType: 24,
          rotate: 0,
          fontSize: fontSize,
          textHeight: fontSize,
          textPosition: 0,
        },
      },
    ],
  };

}
