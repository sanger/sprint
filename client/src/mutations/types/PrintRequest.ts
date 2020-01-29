/* tslint:disable */
/* eslint-disable */
// This file was automatically generated and should not be edited.

import { PrintRequest } from "./../../types/graphql-global-types";

// ====================================================
// GraphQL mutation operation: PrintRequest
// ====================================================

export interface PrintRequest_print {
  __typename: "PrintResult";
  /**
   *  The id (if given) that can be used to check the status of a print job
   */
  jobId: string | null;
}

export interface PrintRequest {
  /**
   * Print some labels.
   * If the printer is not in config, the printerType must be specified
   */
  print: PrintRequest_print | null;
}

export interface PrintRequestVariables {
  printRequest: PrintRequest;
  printer: string;
}
