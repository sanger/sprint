// All Actions will have a type
import {Printers_printers_labelType} from "../../queries/types/Printers";
import {CanvasBarcodeField, CanvasField, CanvasPosition, CanvasTextField, TwoDimensional} from "../../types";
import {LabelTypes_labelTypes} from "../../queries/types/LabelTypes";

interface HasActionType<T> {
  type: T
}

// Actions

/**
 * Set label types
 * e.g. dispatch({ type: "SET_LABEL_TYPES", value: [LabelType...]})
 */
export type SetLabelTypes = HasActionType<"SET_LABEL_TYPES"> & {
  value: LabelTypes_labelTypes[]
}

/**
 * Set label type
 * e.g. dispatch(type: "SET_LABEL_TYPE", labelType: labelType)
 */
export type SetLabelType = HasActionType<"SET_LABEL_TYPE"> & {
  labelType: LabelTypes_labelTypes
}

/**
 * Set canvasToParentScale
 * e.g. dispatch(type: "SET_CANVAS_TO_PARENT_SCALE", value: 0.4)
 */
export type SetCanvasToParentScale = HasActionType<"SET_CANVAS_TO_PARENT_SCALE"> & {
  value: number
}

/**
 * Set rendering context
 * dispatch({ type: "SET_RENDERING_CONTEXT", value: ctx})
 */
export type SetRenderingContext = HasActionType<"SET_RENDERING_CONTEXT"> & {
  value: CanvasRenderingContext2D
}

/**
 * Set canvasDimensions
 * dispatch({ type: "SET_CANVAS_DIMENSIONS", value: { width: 300, height: 200 }})
 */
export type SetCanvasDimensions = HasActionType<"SET_CANVAS_DIMENSIONS"> & {
  value: TwoDimensional
}

/**
 * Add CanvasTextField
 */
export type AddCanvasTextField = HasActionType<"ADD_CANVAS_TEXT_FIELD"> & {
  options?: Partial<CanvasTextField>
};

/**
 * Add CanvasBarcodeField
 */
export type AddCanvasBarcodeField = HasActionType<"ADD_CANVAS_BARCODE_FIELD"> & {
  options?: Partial<CanvasBarcodeField>
};

/**
 * Update a field
 * e.g. dispatch(type: "UPDATE_CANVAS_FIELD", canvasField, { x: 2, y, 10 })
 */
export type UpdateCanvasField = HasActionType<"UPDATE_CANVAS_FIELD"> & {
  canvasField: CanvasField,
  updates: Partial<CanvasField>
}

/**
 * Rotate anti-clockwise
 * e.g. dispatch({ type: "ROTATE_ANTI_CLOCKWISE", canvasField })
 */
export type RotateAntiClockwise = HasActionType<"ROTATE_ANTI_CLOCKWISE"> & {
  canvasField: CanvasField
}

/**
 * Rotate clockwise
 * e.g. dispatch({ type: "ROTATE_CLOCKWISE", canvasField })
 */
export type RotateClockwise = HasActionType<"ROTATE_CLOCKWISE"> & {
  canvasField: CanvasField
}

/**
 * Update Barcode Field Drawn Dimensions (used for getting the width and height of a barcode once it's been drawn)
 * e.g. dispatch({ type: "UPDATE_BARCODE_FIELD_DRAWN_DIMENSIONS", canvasField, value: { width: 50, height: 20 }})
 */
export type UpdateBarcodeFieldDrawnDimensions = HasActionType<"UPDATE_BARCODE_FIELD_DRAWN_DIMENSIONS"> & {
  canvasField: CanvasField
  value: TwoDimensional
}

/**
 * Remove a field
 * e.g. dispatch(type: "REMOVE_CANVAS_FIELD", canvasField)
 */
export type RemoveCanvasField = HasActionType<"REMOVE_CANVAS_FIELD"> & {
  canvasField: CanvasField
}

/**
 * Mouse down
 * dispatch({ type: "MOUSE_DOWN", value: { x: 3, y: 5 }})
 */
export type MouseDown = HasActionType<"MOUSE_DOWN"> & {
  value: CanvasPosition
}

/**
 * Double click
 * dispatch({ type: "DOUBLE_CLICK", value: { x: 4, y: 1} });
 */
export type DoubleClick = HasActionType<"DOUBLE_CLICK"> & {
  value: CanvasPosition
}

/**
 * Set canvasMousePosition
 * dispatch({ type: "SET_CANVAS_MOUSE_POSITION", value: { x: 3, y: 5 }})
 */
export type SetCanvasMousePosition = HasActionType<"SET_CANVAS_MOUSE_POSITION"> & {
  value: CanvasPosition
}

/**
 * Mouse up
 * dispatch({ type: "MOUSE_UP"})
 */
export type MouseUp = HasActionType<"MOUSE_UP">;

/**
 * Set selectedCanvasField
 * dispatch({ type: "SET_SELECTED_CANVAS_FIELD", value: canvasField})
 */
export type SetSelectedCanvasField = HasActionType<"SET_SELECTED_CANVAS_FIELD"> & {
  value: CanvasField
};

/**
 * Delete selected canvasField
 * dispatch({ type: "DELETE_SELECTED_CANVAS_FIELD" })
 */
export type DeleteSelectedCanvasField = HasActionType<"DELETE_SELECTED_CANVAS_FIELD">;

export type Action = SetLabelTypes |
    SetLabelType |
    SetCanvasToParentScale |
    AddCanvasTextField |
    AddCanvasBarcodeField |
    SetCanvasDimensions |
    SetRenderingContext |
    UpdateCanvasField |
    RotateAntiClockwise |
    RotateClockwise |
    UpdateBarcodeFieldDrawnDimensions |
    RemoveCanvasField |
    MouseDown |
    DoubleClick |
    SetCanvasMousePosition |
    MouseUp |
    SetSelectedCanvasField |
    DeleteSelectedCanvasField;