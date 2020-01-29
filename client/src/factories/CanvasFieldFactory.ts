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
import { is2D } from "../models/barcodes";

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
      value: "XYZ123",
      height: labelType ? labelType.height / 4 : 1
    },
    options
  );
};

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

  let drawnHeight = 0;

  if (!is2D(barcodeField.barcodeType) && barcodeField.height) {
    drawnHeight =
      (barcodeField.height / labelType.height) * canvasDimensions.height;
  }

  const canvasBarcodeField: CanvasBarcodeField = {
    drawnHeight,
    drawnWidth: 0, // Don't know what this is until it's actually drawn
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

  return canvasBarcodeField;
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
