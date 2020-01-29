import { BarcodeField, TextField } from "./graphql-global-types";

export type Field = BarcodeField | TextField;

export const isTextField = (field: Field): field is TextField => {
  return (field as TextField).fontSize !== undefined;
};

interface ICanvasField {
  canvasX: number;
  canvasY: number;
  drawnWidth: number;
  drawnHeight: number;
  canvasRotation: number; // Angle to be rotated in radians
}

export interface CanvasTextField extends TextField, ICanvasField {
  canvasFont: CanvasFont;
}

export interface CanvasBarcodeField extends BarcodeField, ICanvasField {
  canvasValue: string;
  drawnCellWidth: number;
  img?: HTMLImageElement;
}

export type CanvasField = CanvasTextField | CanvasBarcodeField;

export const isCanvasTextField = (
  field: CanvasTextField | CanvasBarcodeField
): field is CanvasTextField => {
  return (field as CanvasTextField).fontSize !== undefined;
};

export enum CanvasFont {
  mono = "courier",
  proportional = "Helvetica"
}

export interface CanvasPosition {
  x: number;
  y: number;
}

export interface TwoDimensional {
  width: number;
  height: number;
}
