import { BarcodeField } from "../../types/graphql-global-types";
import { Printers_printers_labelType } from "../../queries/types/Printers";
import { TwoDimensional } from "../../types";
import { DimensionsCalculator } from "./DimensionsCalculator";

const oneDCalculator: DimensionsCalculator = {
  /**
   * Let whatever JS library that creates the barcode figure out the width
   */
  getDrawnWidth(
    barcodeField: BarcodeField,
    labelType: Printers_printers_labelType,
    canvasDimensions: TwoDimensional
  ): number {
    return 0;
  },

  getDrawnHeight(
    barcodeField: BarcodeField,
    labelType: Printers_printers_labelType,
    canvasDimensions: TwoDimensional
  ): number {
    if (!barcodeField.height) {
      return 0;
    }
    return (barcodeField.height / labelType.height) * canvasDimensions.height;
  }
};

export default oneDCalculator;
