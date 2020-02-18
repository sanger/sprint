import { Reducer, useEffect, useReducer } from "react";
import _ from "lodash";

import { Action, UpdateCanvasField } from "./actions";
import {
  CanvasBarcodeField,
  CanvasField,
  CanvasPosition,
  CanvasTextField,
  isCanvasTextField,
  TwoDimensional
} from "../../types";
import { Printers_printers_labelType } from "../../queries/types/Printers";
import {
  buildCanvasBarcodeField,
  buildCanvasField,
  buildCanvasTextField
} from "../../factories/CanvasFieldFactory";
import { HookRouter, useQueryParams } from "hookrouter";
import { buildPrintRequest } from "../../factories/PrintRequestFactory";
import {
  Layout,
  PrintRequest,
  Rotation
} from "../../types/graphql-global-types";
import { LabelTypes_labelTypes } from "../../queries/types/LabelTypes";

// What the URL query parameters should look like
interface queryParams {
  labelType?: string;
  printRequest?: string;
}

// What the query parameters look like in state
interface initialQueryParams {
  labelType?: string;
  printRequest: PrintRequest;
}

// What the state of the app looks like
export interface AppState {
  initialQueryParams?: initialQueryParams;
  labelTypes?: LabelTypes_labelTypes[];
  labelType?: LabelTypes_labelTypes;
  canvasFields: CanvasField[];
  canvasToParentScale: number;
  canvasMousePosition: CanvasPosition;
  draggingOffset?: CanvasPosition;
  isDragging: boolean;
  selectedCanvasField?: CanvasField;
  superSelectedCanvasField?: CanvasField;
  canvasDimensions: TwoDimensional;
  ctx?: CanvasRenderingContext2D;
}

const initialState: AppState = {
  canvasFields: [],
  canvasToParentScale: 0.6,
  canvasMousePosition: { x: 0, y: 0 },
  isDragging: false,
  canvasDimensions: { width: 50, height: 50 } // Doesn't really matter, will change before user sees
};

const useLayoutReducer = (): [AppState, (action: Action) => void] => {
  // Hook to get and set URL query parameters
  const [queryParams, setQueryParams] = useQueryParams();
  const [state, dispatch] = useReducer(reducer, queryParams, init);
  const {
    labelTypes,
    labelType,
    canvasFields,
    ctx,
    initialQueryParams
  } = state;

  // Add canvas fields from the initial query params
  useEffect(() => {
    // If there was no label type in the URL do nothing
    if (!initialQueryParams?.labelType || !ctx) return;

    // Otherwise find it...
    let labelType = _.find(labelTypes, {
      name: initialQueryParams.labelType
    });
    if (!labelType) return;

    // Set it as the current label type
    dispatch({ type: "SET_LABEL_TYPE", labelType });

    // Build any fields from the URL too
    initialQueryParams?.printRequest?.layouts?.forEach((layout: Layout) => {
      layout?.barcodeFields?.forEach(barcodeField => {
        dispatch({
          type: "ADD_CANVAS_BARCODE_FIELD",
          options: barcodeField
        });
      });

      layout?.textFields?.forEach(textField => {
        dispatch({
          type: "ADD_CANVAS_TEXT_FIELD",
          options: textField
        });
      });
    });
  }, [dispatch, labelTypes, ctx, initialQueryParams]);

  // Update the query params with canvasFields' information
  useEffect(() => {
    let newQueryParams: queryParams = {};

    if (labelType) {
      newQueryParams.labelType = labelType.name;
    }

    if (canvasFields) {
      newQueryParams.printRequest = JSON.stringify(
        buildPrintRequest(canvasFields)
      );
    }

    setQueryParams(newQueryParams, true);
  }, [setQueryParams, labelTypes, labelType, canvasFields]);

  return [state, dispatch];
};

export default useLayoutReducer;

/**
 * Function that gets passed to useLayoutReducer's useReducer function to build the initial state.
 *
 * @param {HookRouter.QueryParams} queryParams
 * @returns {AppState}
 */
const init = (queryParams: HookRouter.QueryParams): AppState => {
  if (queryParams.hasOwnProperty("printRequest")) {
    queryParams.printRequest = JSON.parse(queryParams.printRequest);
  }
  return Object.assign({}, initialState, { initialQueryParams: queryParams });
};

/**
 * Reducer to handle actions and return a new AppState
 *
 * @param {AppState} state
 * @param {Action} action
 * @returns {AppState}
 */
const reducer: Reducer<AppState, Action> = (state, action): AppState => {
  let canvasField: CanvasField | undefined;
  let canvasFields: CanvasField[] = [];
  let options: Partial<AppState> = {};

  switch (action.type) {
    case "ROTATE_ANTI_CLOCKWISE":
      return {
        ...state,
        canvasFields: setCanvasFields(state, {
          type: "UPDATE_CANVAS_FIELD",
          canvasField: action.canvasField,
          updates: { rotation: rotateAntiClockWise(action.canvasField) }
        })
      };

    case "ROTATE_CLOCKWISE":
      return {
        ...state,
        canvasFields: setCanvasFields(state, {
          type: "UPDATE_CANVAS_FIELD",
          canvasField: action.canvasField,
          updates: { rotation: rotateClockWise(action.canvasField) }
        })
      };

    case "DELETE_SELECTED_CANVAS_FIELD":
      canvasFields = state.canvasFields;

      if (state.selectedCanvasField) {
        canvasFields = state.canvasFields.filter(
          canvasField => canvasField !== state.selectedCanvasField
        );
      }

      return { ...state, canvasFields };

    case "DOUBLE_CLICK":
      canvasField = hitCanvasField(action.value, state.canvasFields);

      if (canvasField) {
        options.superSelectedCanvasField = canvasField;
        options.selectedCanvasField = canvasField;
      }

      return { ...state, ...options };
    case "SET_LABEL_TYPES":
      return { ...state, labelTypes: action.value };

    case "SET_SELECTED_CANVAS_FIELD":
      return { ...state, selectedCanvasField: action.value };

    // TODO: Surely there's a way to update state without triggering a re-render?
    // This is almost certainly not the right way
    case "UPDATE_BARCODE_FIELD_DRAWN_DIMENSIONS":
      state.canvasFields.forEach(canvasField => {
        if (action.canvasField !== canvasField) {
          return;
        }

        canvasField.drawnWidth = action.value.width;
        canvasField.drawnHeight = action.value.height;
      });

      return state;

    case "MOUSE_DOWN":
      canvasField = hitCanvasField(action.value, state.canvasFields);

      if (canvasField) {
        options.isDragging = true;
        options.draggingOffset = {
          x: action.value.x - canvasField.canvasX,
          y: action.value.y - canvasField.canvasY
        };
        options.selectedCanvasField = canvasField;
      }

      return { ...state, ...options };

    case "MOUSE_UP":
      return { ...state, isDragging: false };

    case "SET_CANVAS_MOUSE_POSITION":
      if (!state.isDragging || !state.selectedCanvasField) {
        return { ...state };
      }

      options.canvasFields = state.canvasFields.map(canvasField => {
        if (canvasField !== state.selectedCanvasField) {
          return canvasField;
        }

        if (state.labelType && state.ctx && state.draggingOffset) {
          const builderOptions = {
            x: Math.round(
              ((action.value.x - state.draggingOffset.x) /
                state.canvasDimensions.width) *
                state.labelType.width
            ),
            y: Math.round(
              ((action.value.y - state.draggingOffset.y) /
                state.canvasDimensions.height) *
                state.labelType.height
            )
          };

          options.selectedCanvasField = buildCanvasField(
            state.labelType as Printers_printers_labelType,
            state.canvasDimensions,
            state.ctx as CanvasRenderingContext2D,
            Object.assign({}, state.selectedCanvasField, builderOptions)
          );

          // TODO: Make this less side-effecty
          return options.selectedCanvasField;
        }

        return canvasField;
      });

      return { ...state, ...options, canvasMousePosition: action.value };

    case "REMOVE_CANVAS_FIELD":
      return {
        ...state,
        canvasFields: state.canvasFields.filter(
          canvasField => canvasField !== action.canvasField
        )
      };

    case "UPDATE_CANVAS_FIELD":
      return {
        ...state,
        canvasFields: setCanvasFields(state, action)
      };

    case "SET_RENDERING_CONTEXT":
      return { ...state, ctx: action.value };

    case "ADD_CANVAS_TEXT_FIELD":
      canvasFields = [...state.canvasFields];
      let newCanvasTextField: CanvasTextField | undefined;

      if (state.labelType && state.ctx) {
        newCanvasTextField = buildCanvasTextField(
          state.labelType,
          state.canvasDimensions,
          state.ctx,
          action.options || {}
        );
        canvasFields.push(newCanvasTextField);
      }

      return {
        ...state,
        canvasFields,
        selectedCanvasField: newCanvasTextField
      };

    case "ADD_CANVAS_BARCODE_FIELD":
      canvasFields = [...state.canvasFields];
      let newCanvasBarcodeField: CanvasBarcodeField | undefined;

      if (state.labelType && state.ctx) {
        newCanvasBarcodeField = buildCanvasBarcodeField(
          state.labelType,
          state.canvasDimensions,
          state.ctx,
          action.options || {}
        );
        canvasFields.push(newCanvasBarcodeField);
      }

      return {
        ...state,
        canvasFields,
        selectedCanvasField: newCanvasBarcodeField
      };

    case "SET_CANVAS_DIMENSIONS":
      canvasFields = state.canvasFields;

      if (state.labelType && state.ctx) {
        canvasFields = state.canvasFields.map(canvasField => {
          return buildCanvasField(
            state.labelType as Printers_printers_labelType,
            action.value,
            state.ctx as CanvasRenderingContext2D,
            canvasField
          );
        });
      }
      return { ...state, canvasDimensions: action.value, canvasFields };
    case "SET_CANVAS_TO_PARENT_SCALE":
      return { ...state, canvasToParentScale: action.value };

    case "SET_LABEL_TYPE":
      if (state.ctx) {
        canvasFields = state.canvasFields.map(canvasField => {
          return buildCanvasField(
            action.labelType as Printers_printers_labelType,
            state.canvasDimensions,
            state.ctx as CanvasRenderingContext2D,
            canvasField
          );
        });
      }

      return {
        ...state,
        labelType: action.labelType,
        canvasFields
      };

    default:
      throw new Error(`Unrecognised action: ${action}`);
  }
};

function hitCanvasField(
  position: CanvasPosition,
  canvasFields: CanvasField[]
): CanvasField | undefined {
  return canvasFields.find(canvasField => {
    if (isCanvasTextField(canvasField)) {
      if (
        canvasField.rotation === Rotation.north &&
        position.x >= canvasField.canvasX &&
        position.x <= canvasField.canvasX + canvasField.drawnWidth &&
        position.y <= canvasField.canvasY &&
        position.y >= canvasField.canvasY - canvasField.drawnHeight
      ) {
        return true;
      } else if (
        canvasField.rotation === Rotation.east &&
        position.x >= canvasField.canvasX &&
        position.x <= canvasField.canvasX + canvasField.drawnHeight &&
        position.y >= canvasField.canvasY &&
        position.y <= canvasField.canvasY + canvasField.drawnWidth
      ) {
        return true;
      } else if (
        canvasField.rotation === Rotation.south &&
        position.x <= canvasField.canvasX &&
        position.x >= canvasField.canvasX - canvasField.drawnWidth &&
        position.y >= canvasField.canvasY &&
        position.y <= canvasField.canvasY + canvasField.drawnWidth
      ) {
        return true;
      } else if (
        canvasField.rotation === Rotation.west &&
        position.x <= canvasField.canvasX &&
        position.x >= canvasField.canvasX - canvasField.drawnHeight &&
        position.y <= canvasField.canvasY &&
        position.y >= canvasField.canvasY - canvasField.drawnWidth
      ) {
        return true;
      }
    } else {
      if (
        canvasField.rotation === Rotation.north &&
        position.x >= canvasField.canvasX &&
        position.x <= canvasField.canvasX + canvasField.drawnWidth &&
        position.y >= canvasField.canvasY &&
        position.y <= canvasField.canvasY + canvasField.drawnHeight
      ) {
        return true;
      } else if (
        canvasField.rotation === Rotation.east &&
        position.x >= canvasField.canvasX - canvasField.drawnHeight &&
        position.x <= canvasField.canvasX &&
        position.y >= canvasField.canvasY &&
        position.y <= canvasField.canvasY + canvasField.drawnWidth
      ) {
        return true;
      } else if (
        canvasField.rotation === Rotation.south &&
        position.x >= canvasField.canvasX - canvasField.drawnWidth &&
        position.x <= canvasField.canvasX &&
        position.y <= canvasField.canvasY &&
        position.y >= canvasField.canvasY - canvasField.drawnHeight
      ) {
        return true;
      } else if (
        canvasField.rotation === Rotation.west &&
        position.x >= canvasField.canvasX &&
        position.x <= canvasField.canvasX + canvasField.drawnHeight &&
        position.y <= canvasField.canvasY &&
        position.y >= canvasField.canvasY - canvasField.drawnWidth
      ) {
        return true;
      }
    }

    return false;
  });
}

function rotateClockWise(canvasField: CanvasField): Rotation {
  switch (canvasField.rotation) {
    case Rotation.east:
      return Rotation.south;
    case Rotation.north:
      return Rotation.east;
    case Rotation.south:
      return Rotation.west;
    case Rotation.west:
      return Rotation.north;
    default:
      throw new Error(`Don't know how to rotate ${canvasField.rotation}`);
  }
}

function rotateAntiClockWise(canvasField: CanvasField): Rotation {
  switch (canvasField.rotation) {
    case Rotation.east:
      return Rotation.north;
    case Rotation.north:
      return Rotation.west;
    case Rotation.south:
      return Rotation.east;
    case Rotation.west:
      return Rotation.south;
    default:
      throw new Error(`Don't know how to rotate ${canvasField.rotation}`);
  }
}

function setCanvasFields(state: AppState, action: UpdateCanvasField) {
  return state.canvasFields.map(canvasField => {
    if (action.canvasField !== canvasField) {
      return canvasField;
    }

    if (state.labelType && state.ctx) {
      return buildCanvasField(
        state.labelType,
        state.canvasDimensions,
        state.ctx,
        Object.assign({}, canvasField, action.updates)
      );
    }

    return canvasField;
  });
}
