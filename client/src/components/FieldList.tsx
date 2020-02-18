import React, { Dispatch } from "react";

import { Printers_printers_labelType } from "../queries/types/Printers";
import { CanvasField } from "../types";
import { Action } from "../hooks/layoutReducer/actions";
import LabelInput from "./LabelInput";

const FieldList: React.FC<{
  labelType?: Printers_printers_labelType;
  layout: CanvasField[];
  selectedCanvasField?: CanvasField;
  superSelectedCanvasField?: CanvasField;
  dispatch: Dispatch<Action>;
}> = ({
  labelType,
  layout,
  selectedCanvasField,
  superSelectedCanvasField,
  dispatch
}) => {
  let labels: JSX.Element[] = [];

  // When the user hasn't selected a label type yet
  if (!labelType) {
    labels = [<Helper key={-1} message="Select a Label Type to start" />];

    // When the user has selected a label type, but not added any fields
  } else if (labelType && layout.length === 0) {
    labels = [
      <Helper key={-2} message="Add some fields with the buttons below" />
    ];
  } else if (labelType && layout.length > 0) {
    labels = layout.map((canvasField: CanvasField, i: number) => {
      return (
        <LabelInput
          canvasField={canvasField}
          key={i}
          labelType={labelType}
          dispatch={dispatch}
          selectedCanvasField={selectedCanvasField}
          superSelectedCanvasField={superSelectedCanvasField}
        />
      );
    });
  }

  return (
    <div className="flex flex-col p-3 h-full">
      <div className="flex-grow relative h-full overflow-y-auto">
        <div className="max-h-full bottom-0 absolute w-full">{labels}</div>
      </div>

      <div className="flex flex-row mt-2">
        <button
          className="btn btn-transition flex-grow w-1/2 mr-3"
          disabled={!labelType}
          onClick={() => dispatch({ type: "ADD_CANVAS_TEXT_FIELD" })}
        >
          <span className="text-xl">{"\u002B"}</span> Text
        </button>
        <button
          className="btn btn-transition flex-grow w-1/2"
          disabled={!labelType}
          onClick={() => dispatch({ type: "ADD_CANVAS_BARCODE_FIELD" })}
        >
          <span className="text-xl">{"\u002B"}</span> Barcode
        </button>
      </div>
    </div>
  );
};

const Helper: React.FC<{ message: string }> = ({ message }) => {
  return (
    <div
      style={{ opacity: 0.9 }}
      className="bg-white rounded-lg shadow-md mb-4 p-3 mt-4 text-center"
    >
      <h2>{message}</h2>
    </div>
  );
};

export default FieldList;
