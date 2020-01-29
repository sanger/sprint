import toCanvas from 'bwip-js/browser-bwipjs';
import { memoize } from 'lodash';

import IBarcodeGenerator from "./IBarcodeGenerator";
import {BarcodeField, BarcodeType} from "../../types/graphql-global-types";
import {CanvasBarcodeField} from "../../types";
import {is2D} from "../barcodes";

enum BwipBarcodeTypes {
  code128 = "code128",
  code39 = "code39",
  datamatrix = "datamatrix",
  ean13 = "ean13"
}

// bwip-js normalizes the BWIPP width and height options to always be in millimeters.
// The resulting images are rendered at 72 dpi.
// To convert to pixels, use a factor of 2.835 px/mm (72 dpi / 25.4 mm/in).
const SCALING_FACTOR = 2.835;

const bwipBarcodeGenerator: IBarcodeGenerator = (canvasBarcodeField: CanvasBarcodeField) => {
  return new Promise<string>((resolve, reject) => {
    // Create a tmp canvas to draw the barcode and then convert that to an image...
    const detachedCanvas = document.createElement("canvas");

    let options: BwipJs.ToCanvasOptions = {
      bcid: getBarcodeType(canvasBarcodeField),   // Barcode type
      text: canvasBarcodeField.canvasValue,         // Text to encode
      scaleX: (canvasBarcodeField.cellWidth) * 10, // X scaling factor
      scaleY: 1
    };

    if (is2D(canvasBarcodeField.barcodeType)) {
      options.scaleY = options.scaleX;
    } else if (canvasBarcodeField.drawnHeight) {
      options.height = canvasBarcodeField.drawnHeight / SCALING_FACTOR;
    }

    toCanvas(detachedCanvas, options, function (err, cvs) {
      if (err) {
        reject(err);
      } else if (cvs) {
        resolve(cvs.toDataURL('image/png'));
      }
    });
  });
};

const getBarcodeType = (barcodeField: BarcodeField): BwipBarcodeTypes => {
  switch(barcodeField.barcodeType) {
    case BarcodeType.code128: return BwipBarcodeTypes.code128;
    case BarcodeType.code39: return BwipBarcodeTypes.code39;
    case BarcodeType.datamatrix: return BwipBarcodeTypes.datamatrix;
    case BarcodeType.ean13: return BwipBarcodeTypes.ean13;
    default: throw Error(`No support for ${barcodeField.barcodeType} barcodes`);
  }
};


export default memoize(bwipBarcodeGenerator);