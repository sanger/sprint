import { BarcodeField, BarcodeType } from "../../types/graphql-global-types";
import { Printers_printers_labelType } from "../../queries/types/Printers";
import { TwoDimensional } from "../../types";
import dataMatrixCalculator from "./DataMatrixCalculator";
import oneDCalculator from "./OneDCalculator";

export interface DimensionsCalculator {
  /**
   * Get the width of the barcode to be rendered on the canvas in px
   * @param barcodeField
   * @param labelType
   * @param canvasDimensions
   * @return number width in px of the barcode
   */
  getDrawnWidth: (
    barcodeField: BarcodeField,
    labelType: Printers_printers_labelType,
    canvasDimensions: TwoDimensional
  ) => number;

  /**
   * Get the height of the barcode to be rendered on the canvas in px
   * @param barcodeField
   * @param labelType
   * @param canvasDimensions
   * @return number height in px of the barcode
   */
  getDrawnHeight: (
    barcodeField: BarcodeField,
    labelType: Printers_printers_labelType,
    canvasDimensions: TwoDimensional
  ) => number;
}

export function getDimensionsCalculatorByBarcodeType(
  barcodeType: BarcodeType
): DimensionsCalculator {
  switch (barcodeType) {
    case BarcodeType.datamatrix:
      return dataMatrixCalculator;
    case BarcodeType.ean13:
    case BarcodeType.code39:
    case BarcodeType.code128:
      return oneDCalculator;
  }
}
