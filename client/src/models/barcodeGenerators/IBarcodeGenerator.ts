import { CanvasBarcodeField } from "../../types";

export default interface IBarcodeGenerator {
  /**
   * Function that creates a data encoded barcode image
   *
   * @param {CanvasBarcodeField} canvasBarcodeField
   * @returns {Promise<string>} This string should be a data encoded version of the barcode image
   */
  (canvasBarcodeField: CanvasBarcodeField): Promise<string>;
}
