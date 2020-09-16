import { BarcodeField } from "../../types/graphql-global-types";
import { Printers_printers_labelType } from "../../queries/types/Printers";
import { TwoDimensional } from "../../types";
import { DimensionsCalculator } from "./DimensionsCalculator";

interface DatamatrixSize {
  /**
   * Maximum capacity when barcode value is numeric.
   */
  numericCapacity: number;

  /**
   * Maximum capacity when barcode value is alphanumeric.
   */
  alphaNumericCapacity: number;

  /**
   * Number of dots (squares) for this capacity.
   */
  numberOfDots: number;
}

/**
 * From the CAB Squix docs
 */
const datamatrixSizes: DatamatrixSize[] = [
  { numericCapacity: 6, alphaNumericCapacity: 3, numberOfDots: 10 },
  { numericCapacity: 10, alphaNumericCapacity: 6, numberOfDots: 12 },
  { numericCapacity: 16, alphaNumericCapacity: 10, numberOfDots: 14 },
  { numericCapacity: 24, alphaNumericCapacity: 16, numberOfDots: 16 },
  { numericCapacity: 36, alphaNumericCapacity: 25, numberOfDots: 18 },
  { numericCapacity: 44, alphaNumericCapacity: 31, numberOfDots: 20 },
  { numericCapacity: 60, alphaNumericCapacity: 43, numberOfDots: 22 },
  { numericCapacity: 72, alphaNumericCapacity: 52, numberOfDots: 24 },
  { numericCapacity: 88, alphaNumericCapacity: 64, numberOfDots: 26 },
  { numericCapacity: 124, alphaNumericCapacity: 91, numberOfDots: 32 },
  { numericCapacity: 172, alphaNumericCapacity: 127, numberOfDots: 36 },
  { numericCapacity: 228, alphaNumericCapacity: 169, numberOfDots: 40 },
  { numericCapacity: 288, alphaNumericCapacity: 214, numberOfDots: 44 },
  { numericCapacity: 348, alphaNumericCapacity: 259, numberOfDots: 48 },
  { numericCapacity: 408, alphaNumericCapacity: 304, numberOfDots: 52 },
  { numericCapacity: 560, alphaNumericCapacity: 418, numberOfDots: 64 },
  { numericCapacity: 736, alphaNumericCapacity: 550, numberOfDots: 72 },
  { numericCapacity: 912, alphaNumericCapacity: 682, numberOfDots: 80 },
  { numericCapacity: 1152, alphaNumericCapacity: 862, numberOfDots: 88 },
  { numericCapacity: 1392, alphaNumericCapacity: 1042, numberOfDots: 96 },
  { numericCapacity: 1632, alphaNumericCapacity: 1222, numberOfDots: 104 },
  { numericCapacity: 2100, alphaNumericCapacity: 1573, numberOfDots: 120 },
  { numericCapacity: 2608, alphaNumericCapacity: 1954, numberOfDots: 132 },
  { numericCapacity: 3116, alphaNumericCapacity: 2335, numberOfDots: 144 }
];

const dataMatrixCalculator: DimensionsCalculator = {
  getDrawnHeight(
    barcodeField: BarcodeField,
    labelType: Printers_printers_labelType,
    canvasDimensions: TwoDimensional
  ): number {
    return this.getDrawnWidth(barcodeField, labelType, canvasDimensions);
  },
  getDrawnWidth(
    barcodeField: BarcodeField,
    labelType: Printers_printers_labelType,
    canvasDimensions: TwoDimensional
  ): number {
    return (
      (barcodeField.cellWidth / labelType.width) *
      canvasDimensions.width *
      getSizeFromValue(barcodeField.value)
    );
  }
};

/**
 * Get the number of dots in the datamatrix barcode from the value.
 * Returns the max number of dots from our list if it can't find a matching value.
 * @param value barcode value
 * @return number number of squares in row/column
 */
function getSizeFromValue(value: string): number {
  const valueLength = value.length;
  const capacityType: Extract<
    keyof DatamatrixSize,
    "alphaNumericCapacity" | "numericCapacity"
  > = isNaN(Number(value)) ? "alphaNumericCapacity" : "numericCapacity";
  return (
    datamatrixSizes.find(dmSize => dmSize[capacityType] >= valueLength)
      ?.numberOfDots ?? datamatrixSizes[datamatrixSizes.length - 1].numberOfDots
  );
}

export default dataMatrixCalculator;
