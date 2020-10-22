import React, { ChangeEvent } from "react";
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
  return (
    <div className="grid grid-cols-4 space-x-2 mt-3">
      <label htmlFor="cellWidth">Width:</label>
      <input
        id="cellWidth"
        className="col-span-2"
        type="range"
        min="0.05"
        max="0.95"
        step="0.05"
        onChange={onInputChange("cellWidth")}
        value={canvasBarcodeField.cellWidth}
      />
      <span className="text-right">{canvasBarcodeField.cellWidth}</span>

      <label htmlFor="x">X:</label>
      <input
        id="x"
        className="col-span-2"
        type="range"
        min="0"
        max={labelType.width}
        step="1"
        onChange={onInputChange("x")}
        value={canvasBarcodeField.x}
      />
      <span className="text-right">{canvasBarcodeField.x}</span>

      <label htmlFor="y">Y:</label>
      <input
        id="y"
        className="col-span-2"
        type="range"
        min="0"
        max={labelType.height}
        step="1"
        onChange={onInputChange("y")}
        value={canvasBarcodeField.y}
      />
      <span className="text-right">{canvasBarcodeField.y}</span>

      <label htmlFor="height">Height:</label>
      <input
        id="height"
        className="col-span-2"
        type="range"
        min="1"
        max={labelType.height}
        step="1"
        onChange={onInputChange("height")}
        value={canvasBarcodeField.height || ""}
      />
      <span className="text-right">{canvasBarcodeField.height}</span>
    </div>
  );
};

export default BarcodeLabelInput;
