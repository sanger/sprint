import JsBarcode from "jsbarcode";
import { memoize } from "lodash";

import IBarcodeGenerator from "./IBarcodeGenerator";
import { CanvasBarcodeField } from "../../types";
import { BarcodeType } from "../../types/graphql-global-types";

function getFormatByBarcodeType(barcodeType: BarcodeType) {
  switch (barcodeType) {
    case BarcodeType.code128:
      return "CODE128";
    case BarcodeType.code39:
      return "CODE39";
    case BarcodeType.ean13:
      return "EAN13";
    case BarcodeType.datamatrix:
      throw new Error("JsBarcode does not support datamatrix");
  }
}

const jsBarcodeBarcodeGenerator: IBarcodeGenerator = (
  canvasBarcodeField: CanvasBarcodeField
) => {
  return new Promise<string>((resolve, reject) => {
    const detachedCanvas = document.createElement("canvas");

    JsBarcode(detachedCanvas, canvasBarcodeField.value, {
      format: getFormatByBarcodeType(canvasBarcodeField.barcodeType),
      width: canvasBarcodeField.drawnCellWidth,
      height: canvasBarcodeField.drawnHeight,
      displayValue: true,
      textMargin: 0,
      fontSize: 10,
      margin: 1
    });

    resolve(detachedCanvas.toDataURL("image/png"));
  });
};

export default memoize(jsBarcodeBarcodeGenerator);
