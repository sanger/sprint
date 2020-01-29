import React, { ChangeEvent } from "react";
import { BarcodeType } from "../types/graphql-global-types";
import { CanvasBarcodeField } from "../types";
import { Printers_printers_labelType } from "../queries/types/Printers";

const BarcodeLabelInput: React.FC<{
  canvasBarcodeField: CanvasBarcodeField;
  onInputChange: (
    prop: string
  ) => (
    e: ChangeEvent<HTMLInputElement> | ChangeEvent<HTMLSelectElement>
  ) => void;
  labelType: Printers_printers_labelType;
}> = ({ canvasBarcodeField, onInputChange, labelType }) => {
  const barcodeTypes: JSX.Element[] = Object.values(BarcodeType).map(
    barcodeType => {
      return (
        <option key={barcodeType} value={barcodeType}>
          {barcodeType}
        </option>
      );
    }
  );

  return (
    <div className="label-input-grid mt-3">
      <label htmlFor="cellWidth">Width:</label>
      <input
        id="cellWidth"
        type="range"
        min="0.1"
        max="0.9"
        step="0.1"
        onChange={onInputChange("cellWidth")}
        value={canvasBarcodeField.cellWidth}
      />
      <span>{canvasBarcodeField.cellWidth}</span>

      <label htmlFor="x">X:</label>
      <input
        id="x"
        type="range"
        min="0"
        max={labelType.width}
        step="1"
        onChange={onInputChange("x")}
        value={canvasBarcodeField.x}
      />
      <span>{canvasBarcodeField.x}</span>

      <label htmlFor="y">Y:</label>
      <input
        id="y"
        type="range"
        min="0"
        max={labelType.height}
        step="1"
        onChange={onInputChange("y")}
        value={canvasBarcodeField.y}
      />
      <span>{canvasBarcodeField.y}</span>

      <label htmlFor="height">Height:</label>
      <input
        id="height"
        type="range"
        min="1"
        max={labelType.height}
        step="1"
        onChange={onInputChange("height")}
        value={canvasBarcodeField.height || ""}
      />
      <span>{canvasBarcodeField.height}</span>
    </div>
  );
};

export default BarcodeLabelInput;
