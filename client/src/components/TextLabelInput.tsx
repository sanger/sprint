import React, { ChangeEvent } from "react";
import { CanvasTextField } from "../types";
import { Printers_printers_labelType } from "../queries/types/Printers";

const TextLabelInput: React.FC<{
  canvasTextField: CanvasTextField;
  onInputChange: (
    prop: string
  ) => (
    e: ChangeEvent<HTMLInputElement> | ChangeEvent<HTMLSelectElement>
  ) => void;
  labelType: Printers_printers_labelType;
}> = ({ canvasTextField, onInputChange, labelType }) => {
  return (
    <div className="grid grid-cols-4 space-x-2 mt-3">
      <label htmlFor="fontSize">Size:</label>
      <input
        id="fontSize"
        className="col-span-2"
        type="range"
        min="0.1"
        max={labelType.height}
        step="0.1"
        onChange={onInputChange("fontSize")}
        value={canvasTextField.fontSize}
      />
      <span className="text-right">{canvasTextField.fontSize}</span>

      <label htmlFor="textX">X:</label>
      <input
        id="textX"
        className="col-span-2"
        type="range"
        min="0"
        max={labelType.width}
        step="1"
        onChange={onInputChange("x")}
        value={canvasTextField.x}
      />
      <span className="text-right">{canvasTextField.x}</span>

      <label htmlFor="textY">Y:</label>
      <input
        id="textY"
        className="col-span-2"
        type="range"
        min="0"
        max={labelType.height}
        step="1"
        onChange={onInputChange("y")}
        value={canvasTextField.y}
      />
      <span className="text-right">{canvasTextField.y}</span>
    </div>
  );
};

export default TextLabelInput;
