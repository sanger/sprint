import React, { ChangeEvent } from "react";
import { CanvasTextField } from "../types";
import { Printers_printers_labelType } from "../queries/types/Printers";
import { Font } from "../types/graphql-global-types";

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
    <div className="label-input-grid mt-3">
      <label htmlFor="fontSize">Size:</label>
      <input
        id="fontSize"
        type="range"
        min="0.1"
        max={labelType.height}
        step="0.1"
        onChange={onInputChange("fontSize")}
        value={canvasTextField.fontSize}
      />
      <span>{canvasTextField.fontSize}</span>

      <label htmlFor="textX">X:</label>
      <input
        id="textX"
        type="range"
        min="0"
        max={labelType.width}
        step="1"
        onChange={onInputChange("x")}
        value={canvasTextField.x}
      />
      <span>{canvasTextField.x}</span>

      <label htmlFor="textY">Y:</label>
      <input
        id="textY"
        type="range"
        min="0"
        max={labelType.height}
        step="1"
        onChange={onInputChange("y")}
        value={canvasTextField.y}
      />
      <span>{canvasTextField.y}</span>
    </div>
  );
};

export default TextLabelInput;
