import { CanvasBarcodeField } from "../../types";

export default interface IBarcodeGenerator {
  (canvasBarcodeField: CanvasBarcodeField): Promise<string>;
}