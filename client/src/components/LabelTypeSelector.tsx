import React, { Dispatch } from "react";

import { Printers_printers_labelType } from "../queries/types/Printers";
import { Action } from "../hooks/layoutReducer/actions";

import { LabelTypes_labelTypes } from "../queries/types/LabelTypes";

const LabelTypeSelector: React.FC<{
  selectedLabelType?: Printers_printers_labelType;
  labelTypes?: LabelTypes_labelTypes[];
  dispatch: Dispatch<Action>;
}> = ({ selectedLabelType, labelTypes = [], dispatch }) => {
  const items = labelTypes.map(labelType => {
    const disabled =
      selectedLabelType && selectedLabelType.name === labelType.name;

    return (
      <li key={labelType.name}>
        <button
          className="btn btn-transition w-full mt-2 capitalize"
          disabled={disabled}
          onClick={e => {
            dispatch({ type: "SET_LABEL_TYPE", labelType });
          }}
        >
          {labelType.name}
        </button>
      </li>
    );
  });

  return (
    <div className="flex flex-col-reverse">
      <ul className="mt-2 p-3">{items}</ul>
    </div>
  );
};

export default LabelTypeSelector;
