/* tslint:disable */
/* eslint-disable */
// This file was automatically generated and should not be edited.

// ====================================================
// GraphQL query operation: Printers
// ====================================================

export interface Printers_printers_labelType {
  __typename: "LabelType";
  /**
   *  The name of this label type
   */
  name: string;
  /**
   *  The width of this label type in mm
   */
  width: number;
  /**
   *  The height of this label type in mm
   */
  height: number;
  /**
   *  The distance in mm between the top of each label and the top of the next
   */
  displacement: number;
}

export interface Printers_printers {
  __typename: "Printer";
  /**
   *  The hostname of the printer
   */
  hostname: string;
  /**
   *  The type of labels loaded into the printer
   */
  labelType: Printers_printers_labelType;
}

export interface Printers {
  /**
   *  Get the list of printers, optionally filtered by label type
   */
  printers: Printers_printers[];
}

export interface PrintersVariables {
  labelType?: string | null;
}
