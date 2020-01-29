import { BarcodeType } from "../types/graphql-global-types";
import IBarcodeGenerator from "./barcodeGenerators/IBarcodeGenerator";
import bwipBarcodeGenerator from "./barcodeGenerators/BwipBarcodeGenerator";
import jsBarcodeBarcodeGenerator from "./barcodeGenerators/JsBarcodeBarcodeGenerator";

export const is2D = (barcodeType: BarcodeType): boolean => {
  switch (barcodeType) {
    case BarcodeType.code128:
    case BarcodeType.code39:
    case BarcodeType.ean13:
      return false;
    case BarcodeType.datamatrix:
      return true;
  }
};

export const getBarcodeGeneratorByBarcodeType = (
  barcodeType: BarcodeType
): IBarcodeGenerator => {
  if (is2D(barcodeType)) {
    return bwipBarcodeGenerator;
  } else {
    return jsBarcodeBarcodeGenerator;
  }
};
