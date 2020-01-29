import React from "react";
import { useMutation } from "@apollo/react-hooks";

import printRequestMutation from "../mutations/printRequestMutation";
import {
  PrintRequest,
  PrintRequestVariables
} from "../mutations/types/PrintRequest";
import { buildPrintRequest } from "../factories/PrintRequestFactory";
import { CanvasField } from "../types";

const PrintButton: React.FC<{
  selectedPrinterName: string;
  disabled: boolean;
  canvasFields: CanvasField[];
}> = ({ selectedPrinterName, disabled, canvasFields }) => {
  const [print, { data, loading, error }] = useMutation<
    PrintRequest,
    PrintRequestVariables
  >(printRequestMutation);

  // TODO: Do something more helpful with errors
  if (error) console.error(error);

  const onClick = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    e.preventDefault();

    return print({
      variables: {
        printer: selectedPrinterName,
        printRequest: buildPrintRequest(canvasFields)
      }
    });
  };

  // TODO: Do something with jobIds
  console.log(data);

  return (
    <button
      disabled={disabled || loading}
      onClick={onClick}
      className="btn btn-transition px-8"
    >
      Print
    </button>
  );
};

export default PrintButton;
