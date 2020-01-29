/* tslint:disable */
/* eslint-disable */
// This file was automatically generated and should not be edited.

// ====================================================
// GraphQL query operation: LabelTypes
// ====================================================

export interface LabelTypes_labelTypes_printers {
  __typename: "Printer";
  /**
   *  The hostname of the printer
   */
  hostname: string;
}

export interface LabelTypes_labelTypes {
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
  /**
   *  The printers supporting this label type
   */
  printers: LabelTypes_labelTypes_printers[];
}

export interface LabelTypes {
  /**
   *  Get the list of label types
   */
  labelTypes: LabelTypes_labelTypes[];
}
