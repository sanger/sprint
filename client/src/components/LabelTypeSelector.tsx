import React from "react";

import { Printers_printers_labelType } from "../queries/types/Printers";
import { LabelTypes_labelTypes } from "../queries/types/LabelTypes";

const LabelTypeSelector: React.FC<{
  onSelect: (labelType: LabelTypes_labelTypes) => void;
  selectedLabelType?: Printers_printers_labelType;
  labelTypes?: LabelTypes_labelTypes[];
}> = ({ selectedLabelType, labelTypes = [], onSelect }) => {
  const items = labelTypes.map(labelType => {
    return (
      <li key={labelType.name}>
        <button
          className="btn btn-transition w-full mt-2 capitalize"
          disabled={selectedLabelType?.name === labelType.name}
          onClick={e => {
            onSelect(labelType);
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
