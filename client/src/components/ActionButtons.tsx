import React, { useEffect, useState } from "react";
import PrintButton from "./PrintButton";
import { CanvasField } from "../types";
import Printers from "./Printers";
import { LabelTypes_labelTypes } from "../queries/types/LabelTypes";

const ActionButtons: React.FC<{
  labelType?: LabelTypes_labelTypes;
  fields: CanvasField[];
}> = ({ labelType, fields }) => {
  const printers = labelType ? labelType.printers : [];
  let selectedPrinter = "";
  const [selectedPrinterName, setSelectedPrinterName] = useState<string>(
    selectedPrinter
  );

  // When the labelType changes, change the currently selected Printer
  useEffect(() => {
    if (labelType && labelType.printers.length > 0) {
      setSelectedPrinterName(labelType.printers[0].hostname);
    }
  }, [labelType]);

  return (
    <div className="w-full flex flex-row items-center justify-end">
      <div className="mr-4 rounded text-gray-800">
        {/* TODO: Make this into an Import component */}
        <button className="h-10 px-2 py-1 border border-r-0 border-gray-900 bg-sanger-blue text-gray-100 cursor-pointer rounded-l btn-transition">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 512 512"
            className="w-6 h-4 fill-current"
          >
            <path d="M287.52 224.48c-3.36-3.36-8-5.088-12.736-4.64l-124.448 11.296c-6.176.576-11.52 4.672-13.6 10.496-2.112 5.856-.672 12.384 3.712 16.768l33.952 33.952L4.704 462.048c-6.24 6.24-6.24 16.384 0 22.624l22.624 22.624c6.24 6.272 16.352 6.272 22.624 0L219.648 337.6l33.952 33.952c4.384 4.384 10.912 5.824 16.768 3.744a16.265 16.265 0 0 0 5.856-3.744c2.592-2.592 4.288-6.048 4.608-9.888l11.328-124.448c.448-4.736-1.28-9.376-4.64-12.736z" />
            <path d="M480 0H32C14.336 0 0 14.336 0 32v320h64V64h384v384H160v64h320c17.696 0 32-14.304 32-32V32c0-17.664-14.304-32-32-32z" />
          </svg>
        </button>

        {/* TODO: Make this into an Export component */}
        <button className="h-10 px-2 py-1 border border-gray-900 bg-sanger-blue text-gray-100 cursor-pointer rounded-r btn-transition">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 512 512"
            className="w-6 h-4 fill-current"
          >
            <path d="M507.296 4.704a16.06 16.06 0 0 0-12.768-4.64L370.08 11.392c-6.176.576-11.488 4.672-13.6 10.496s-.672 12.384 3.712 16.768l33.952 33.952-169.696 169.664c-6.24 6.24-6.24 16.384 0 22.624l22.624 22.624c6.272 6.272 16.384 6.272 22.656.032l169.696-169.696 33.952 33.952c4.384 4.384 10.912 5.824 16.768 3.744a16.265 16.265 0 0 0 5.856-3.744c2.592-2.592 4.288-6.048 4.608-9.888l11.328-124.448a16.06 16.06 0 0 0-4.64-12.768z" />
            <path d="M448 192v256H64V64h256V0H32C14.304 0 0 14.304 0 32v448c0 17.664 14.304 32 32 32h448c17.664 0 32-14.336 32-32V192h-64z" />
          </svg>
        </button>
      </div>

      <Printers
        disabled={!labelType}
        printers={printers}
        selectedPrinterName={selectedPrinterName}
        onChange={(printerName: string) => setSelectedPrinterName(printerName)}
      />

      <PrintButton
        disabled={printers.length === 0}
        selectedPrinterName={selectedPrinterName}
        canvasFields={fields}
      />
    </div>
  );
};

export default ActionButtons;
