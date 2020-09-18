import { Printers_printers_labelType } from "../queries/types/Printers";
import {
  CanvasBarcodeField,
  CanvasField,
  CanvasFont,
  CanvasTextField,
  Field,
  isTextField,
  TwoDimensional
} from "../types";
import {
  BarcodeField,
  BarcodeType,
  Font,
  Rotation,
  TextField
} from "../types/graphql-global-types";
import { getDimensionsCalculatorByBarcodeType } from "./dimension_calculators/DimensionsCalculator";

/**
 * Factory method for creating a TextField
 *
 * @param {Partial<TextField>} options
 * @param {Printers_printers_labelType} labelType
 * @returns {TextField}
 */
export const buildTextField = (
  options: Partial<TextField> = {},
  labelType?: Printers_printers_labelType
): TextField => {
  return Object.assign(
    {},
    {
      x: 1,
      y: labelType ? labelType.height - 1 : 1,
      rotation: Rotation.north,
      value: "TextField",
      font: Font.proportional,
      fontSize: labelType ? labelType.height / 4 : 1
    },
    options
  );
};

/**
 * Factory method for a CanvasTextField. Scales fonts and their position by the size of the canvas.
 *
 * @param {Printers_printers_labelType} labelType
 * @param {TwoDimensional} canvasDimensions
 * @param {CanvasRenderingContext2D} ctx
 * @param {Partial<TextField>} options
 * @returns {CanvasTextField}
 */
export const buildCanvasTextField = (
  labelType: Printers_printers_labelType,
  canvasDimensions: TwoDimensional,
  ctx: CanvasRenderingContext2D,
  options: Partial<TextField> = {}
): CanvasTextField => {
  const textField: TextField = buildTextField(options, labelType);

  let xRatio = textField.x / labelType.width;
  let yRatio = textField.y / labelType.height;

  let fontSizeRatio = textField.fontSize / labelType.height;

  const canvasTextField: CanvasTextField = {
    x: textField.x,
    y: textField.y,
    value: textField.value,
    drawnHeight: canvasDimensions.height * fontSizeRatio,
    drawnWidth: 0,
    canvasX: canvasDimensions.width * xRatio,
    canvasY: canvasDimensions.height * yRatio,
    canvasFont:
      textField.font === Font.mono ? CanvasFont.mono : CanvasFont.proportional,
    font: textField.font,
    fontSize: textField.fontSize,
    rotation: textField.rotation,
    canvasRotation: canvasRotation(textField.rotation)
  };

  ctx.font = `${canvasTextField.drawnHeight}px ${canvasTextField.canvasFont}`;
  const textMetrics: TextMetrics = ctx.measureText(textField.value);
  canvasTextField.drawnWidth = textMetrics.width;

  return canvasTextField;
};

/**
 * Return a random hex number as a string (including trailing zeroes)
 * @param length length of the hex string
 */
function randomHexString(length: number) {
  return Math.random()
    .toString(16)
    .slice(2, 2 + length)
    .toUpperCase();
}

/**
 * Factory method for a BarcodeField
 *
 * @param {Partial<CanvasBarcodeField>} options
 * @param {Printers_printers_labelType} labelType
 * @returns {BarcodeField}
 */
export const buildBarcodeField = (
  options: Partial<CanvasBarcodeField> = {},
  labelType?: Printers_printers_labelType
): BarcodeField => {
  return Object.assign(
    {},
    {
      x: 1,
      y: 1,
      rotation: Rotation.north,
      barcodeType: BarcodeType.code128,
      cellWidth: 0.1,
      value: `CGAP-${randomHexString(5)}`,
      height: labelType ? labelType.height / 4 : 1
    },
    options
  );
};

/**
 * Factory method for a CanvasBarcodeField. Scales barcode images and their position by the size of the canvas.
 *
 * @param {Printers_printers_labelType} labelType
 * @param {TwoDimensional} canvasDimensions
 * @param {CanvasRenderingContext2D} ctx
 * @param {Partial<CanvasBarcodeField>} options
 * @returns {CanvasBarcodeField}
 */
export const buildCanvasBarcodeField = (
  labelType: Printers_printers_labelType,
  canvasDimensions: TwoDimensional,
  ctx: CanvasRenderingContext2D,
  options: Partial<CanvasBarcodeField> = {}
): CanvasBarcodeField => {
  const barcodeField: BarcodeField = buildBarcodeField(options, labelType);

  let xRatio = barcodeField.x / labelType.width;
  let yRatio = barcodeField.y / labelType.height;
  let cellWidthRatio = barcodeField.cellWidth / labelType.width;

  const dimensionsCalculator = getDimensionsCalculatorByBarcodeType(
    barcodeField.barcodeType
  );

  return {
    drawnWidth: dimensionsCalculator.getDrawnWidth(
      barcodeField,
      labelType,
      canvasDimensions
    ),
    drawnHeight: dimensionsCalculator.getDrawnHeight(
      barcodeField,
      labelType,
      canvasDimensions
    ),
    drawnCellWidth: canvasDimensions.width * cellWidthRatio,
    canvasX: canvasDimensions.width * xRatio,
    canvasY: canvasDimensions.height * yRatio,
    rotation: barcodeField.rotation,
    canvasRotation: canvasRotation(barcodeField.rotation),
    canvasValue: canvasValue(barcodeField),
    x: barcodeField.x,
    y: barcodeField.y,
    barcodeType: barcodeField.barcodeType,
    cellWidth: barcodeField.cellWidth,
    value: barcodeField.value,
    height: barcodeField.height
  };
};

export const buildCanvasField = (
  labelType: Printers_printers_labelType,
  canvasDimensions: TwoDimensional,
  ctx: CanvasRenderingContext2D,
  field: Field
): CanvasField => {
  if (isTextField(field)) {
    return buildCanvasTextField(labelType, canvasDimensions, ctx, field);
  } else {
    return buildCanvasBarcodeField(labelType, canvasDimensions, ctx, field);
  }
};

/**
 * Pads or takes a substring of the barcode's value (if an EAN)
 *
 * @param {BarcodeField} barcodeField
 * @returns {string}
 */
function canvasValue(barcodeField: BarcodeField): string {
  if (
    barcodeField.barcodeType === BarcodeType.ean13 &&
    barcodeField.value.length < 13
  ) {
    return barcodeField.value.padStart(13, "0");
  } else if (
    barcodeField.barcodeType === BarcodeType.ean13 &&
    barcodeField.value.length > 13
  ) {
    return barcodeField.value.substr(0, 13);
  }
  return barcodeField.value;
}

/**
 * Works out the rotation (in radians) from an SPrint Rotation
 *
 * @param {Rotation | null | undefined} rotation
 * @returns {number}
 */
function canvasRotation(rotation: Rotation | null | undefined) {
  if (!rotation) return 0;

  switch (rotation) {
    case Rotation.east:
      return 90 * (Math.PI / 180);
    case Rotation.north:
      return 0;
    case Rotation.south:
      return 180 * (Math.PI / 180);
    case Rotation.west:
      return 270 * (Math.PI / 180);
    default:
      throw new Error(`Unrecognised rotation: ${rotation}`);
  }
}
