import React from "react";
import { LabelTypes_labelTypes_printers } from "../queries/types/LabelTypes";

const Printers: React.FC<{
  printers: LabelTypes_labelTypes_printers[];
  selectedPrinterName: string;
  disabled: boolean;
  onChange: (printerName: string) => void;
}> = ({ printers, selectedPrinterName, disabled, onChange }) => {
  const options = printers.map((printer, i) => {
    return (
      <option key={i} value={printer.hostname}>
        {printer.hostname}
      </option>
    );
  });

  return (
    <select
      className="h-10 w-1/4 font-medium tracking-wider rounded shadow-md mr-2"
      value={selectedPrinterName}
      disabled={disabled}
      onChange={e => onChange(e.target.value)}
    >
      {options}
    </select>
  );
};

export default Printers;
