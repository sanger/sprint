/* tslint:disable */
/* eslint-disable */
// This file was automatically generated and should not be edited.

//==============================================================
// START Enums and Input Objects
//==============================================================

/**
 *  The type of barcode
 */
export enum BarcodeType {
  code128 = "code128",
  code39 = "code39",
  datamatrix = "datamatrix",
  ean13 = "ean13"
}

/**
 *  The type of font
 */
export enum Font {
  mono = "mono",
  proportional = "proportional"
}

/**
 *  The rotation of this field
 */
export enum Rotation {
  east = "east",
  north = "north",
  south = "south",
  west = "west"
}

/**
 *  A field representing a barcode
 */
export interface BarcodeField {
  x: number;
  y: number;
  rotation?: Rotation | null;
  cellWidth: number;
  height?: number | null;
  barcodeType: BarcodeType;
  value: string;
}

/**
 *  An explicit stating of the size of a label
 */
export interface LabelSize {
  width: number;
  height: number;
  displacement: number;
}

/**
 *  The contents (barcodes and text) of a single label
 */
export interface Layout {
  barcodeFields?: BarcodeField[] | null;
  textFields?: TextField[] | null;
  labelSize?: LabelSize | null;
}

/**
 *  A sequence of layouts, each representing one label
 */
export interface PrintRequest {
  layouts: Layout[];
}

/**
 *  A field representing some text
 */
export interface TextField {
  x: number;
  y: number;
  rotation?: Rotation | null;
  font?: Font | null;
  fontSize: number;
  value: string;
}

//==============================================================
// END Enums and Input Objects
//==============================================================
