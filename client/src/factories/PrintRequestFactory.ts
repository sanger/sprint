import _ from "lodash";

import {
  CanvasBarcodeField,
  CanvasField,
  CanvasTextField,
  isCanvasTextField
} from "../types";
import {
  BarcodeField,
  Layout,
  PrintRequest,
  TextField
} from "../types/graphql-global-types";

/**
 * Build a PrintRequest from an array of CanvasFields
 *
 * @param {CanvasField[]} canvasFields
 * @returns {PrintRequest}
 */
export const buildPrintRequest = (
  canvasFields: CanvasField[]
): PrintRequest => {
  return {
    layouts: buildLayout(canvasFields)
  };
};

const buildLayout = (canvasFields: CanvasField[]): Layout[] => {
  const layout: Layout = canvasFields.reduce<Layout>(
    (memo, canvasField) => {
      if (isCanvasTextField(canvasField)) {
        memo.textFields && memo.textFields.push(buildTextField(canvasField));
      } else {
        memo.barcodeFields &&
          memo.barcodeFields.push(buildBarcodeField(canvasField));
      }
      return memo;
    },
    { textFields: [], barcodeFields: [] }
  );

  // Always just one (but could change)
  return [layout];
};

const buildTextField = (canvasTextField: CanvasTextField): TextField => {
  return _.pick(canvasTextField, [
    "fontSize",
    "font",
    "rotation",
    "value",
    "x",
    "y"
  ]);
};

const buildBarcodeField = (
  canvasBarcodeField: CanvasBarcodeField
): BarcodeField => {
  return _.pick(canvasBarcodeField, [
    "x",
    "y",
    "value",
    "cellWidth",
    "height",
    "rotation",
    "barcodeType"
  ]);
};
