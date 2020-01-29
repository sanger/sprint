import React, {
  ChangeEvent,
  CSSProperties,
  Dispatch,
  useEffect,
  useRef
} from "react";
import { CanvasField, isCanvasTextField } from "../types";
import { Printers_printers_labelType } from "../queries/types/Printers";
import { Action } from "../hooks/layoutReducer/actions";
import TextLabelInput from "./TextLabelInput";
import BarcodeLabelInput from "./BarcodeLabelInput";

const LabelInput: React.FC<{
  canvasField: CanvasField;
  selectedCanvasField?: CanvasField;
  superSelectedCanvasField?: CanvasField;
  labelType: Printers_printers_labelType;
  dispatch: Dispatch<Action>;
}> = ({
  canvasField,
  superSelectedCanvasField,
  selectedCanvasField,
  labelType,
  dispatch
}) => {
  const divRef = useRef<HTMLDivElement>(null);
  const inputRef = useRef<HTMLInputElement>(null);

  useEffect(() => {
    if (selectedCanvasField && selectedCanvasField === canvasField) {
      scrollIntoView();
    }
  }, [selectedCanvasField, canvasField]);

  useEffect(() => {
    if (superSelectedCanvasField && superSelectedCanvasField === canvasField) {
      highlightInputText();
    }
  }, [superSelectedCanvasField, canvasField]);

  const highlightInputText = () => {
    inputRef.current && inputRef.current.select();
  };

  const scrollIntoView = () => {
    divRef.current && divRef.current.scrollIntoView({ behavior: "smooth" });
  };

  const onInputChange = (prop: string) => {
    return (
      e: ChangeEvent<HTMLSelectElement> | ChangeEvent<HTMLInputElement>
    ) => {
      dispatch({
        type: "UPDATE_CANVAS_FIELD",
        canvasField: canvasField,
        updates: { [prop]: e.currentTarget.value }
      });
    };
  };

  const removeField = (e: React.MouseEvent<HTMLAnchorElement, MouseEvent>) => {
    e.preventDefault();
    dispatch({ type: "REMOVE_CANVAS_FIELD", canvasField: canvasField });
  };

  let ChildLabelInput;

  if (isCanvasTextField(canvasField)) {
    ChildLabelInput = (
      <TextLabelInput
        canvasTextField={canvasField}
        labelType={labelType}
        onInputChange={onInputChange}
      />
    );
  } else {
    ChildLabelInput = (
      <BarcodeLabelInput
        canvasBarcodeField={canvasField}
        labelType={labelType}
        onInputChange={onInputChange}
      />
    );
  }

  const current: string =
    selectedCanvasField && selectedCanvasField === canvasField
      ? " current"
      : "";
  const style: CSSProperties = isCanvasTextField(canvasField)
    ? { fontFamily: `${canvasField.canvasFont}` }
    : {};

  return (
    <div
      ref={divRef}
      style={{ opacity: 0.9 }}
      className={`col${current} bg-white rounded-lg shadow-md mb-4 p-3 mt-4`}
    >
      <div className="flex flex-row justify-between align-top">
        <input
          className="p-1 cursor-pointer border-b"
          ref={inputRef}
          type="text"
          style={style}
          onClick={highlightInputText}
          onChange={onInputChange("value")}
          value={canvasField.value}
        />

        <a
          className="block text-4xl leading-none pl-3 text-red -mt-1 hover:text-red-700"
          onClick={removeField}
          href="#remove"
        >
          &times;
        </a>
      </div>

      <div className="flex flex-row justify-between mt-3">
        {isCanvasTextField(canvasField) ? (
          <FontFamilySelector />
        ) : (
          <BarcodeTypeSelector />
        )}

        <div className="flex flex-row">
          <div
            onClick={() => {
              dispatch({ type: "ROTATE_ANTI_CLOCKWISE", canvasField });
            }}
            className="px-2 py-1 border border-r-0 text-gray-900 cursor-pointer rounded-l hover:bg-gray-100"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              className="rotate-ac w-5 h-5 fill-current"
              viewBox="0 0 16 16"
            >
              <path d="M8 0C5 0 2.4 1.6 1.1 4.1L0 3v4h4L2.5 5.5C3.5 3.5 5.6 2 8 2c3.3 0 6 2.7 6 6s-2.7 6-6 6c-1.8 0-3.4-.8-4.5-2.1L2 13.2C3.4 14.9 5.6 16 8 16c4.4 0 8-3.6 8-8s-3.6-8-8-8z" />
            </svg>
          </div>

          <div
            onClick={() => {
              dispatch({ type: "ROTATE_CLOCKWISE", canvasField });
            }}
            className="px-2 py-1 border text-gray-900 cursor-pointer rounded-r hover:bg-gray-100"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              className="rotate-c w-5 h-5 fill-current"
              viewBox="0 0 16 16"
            >
              <path d="M16 7V3l-1.1 1.1C13.6 1.6 11 0 8 0 3.6 0 0 3.6 0 8s3.6 8 8 8c2.4 0 4.6-1.1 6-2.8l-1.5-1.3C11.4 13.2 9.8 14 8 14c-3.3 0-6-2.7-6-6s2.7-6 6-6c2.4 0 4.5 1.5 5.5 3.5L12 7h4z" />
            </svg>
          </div>
        </div>
      </div>

      {ChildLabelInput}
    </div>
  );
};

const FontFamilySelector: React.FC = () => {
  return (
    <div className="flex flex-row">
      <div className="px-2 py-1 text-gray-900 border border-r-0 cursor-pointer rounded-l text-sm font-serif hover:bg-gray-100">
        Proportional
      </div>

      <div className="px-2 py-1 text-gray-900 border cursor-pointer rounded-r text-sm font-mono hover:bg-gray-100">
        Mono
      </div>
    </div>
  );
};

const BarcodeTypeSelector: React.FC = () => {
  return (
    <div className="flex flex-row">
      <div className="px-2 py-1 text-gray-900 border border-r-0 cursor-pointer rounded-l text-sm font-serif hover:bg-gray-100">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          className="h-5 w-5 fill-current"
          viewBox="0 0 480 480"
        >
          <path d="M80 48H16C7.168 48 0 55.168 0 64v64c0 8.832 7.168 16 16 16s16-7.168 16-16V80h48c8.832 0 16-7.168 16-16s-7.168-16-16-16zM464 336c-8.832 0-16 7.168-16 16v48h-48c-8.832 0-16 7.168-16 16s7.168 16 16 16h64c8.832 0 16-7.168 16-16v-64c0-8.832-7.168-16-16-16zM464 48h-64c-8.832 0-16 7.168-16 16s7.168 16 16 16h48v48c0 8.832 7.168 16 16 16s16-7.168 16-16V64c0-8.832-7.168-16-16-16zM80 400H32v-48c0-8.832-7.168-16-16-16s-16 7.168-16 16v64c0 8.832 7.168 16 16 16h64c8.832 0 16-7.168 16-16s-7.168-16-16-16zM64 112h32v256H64zM128 112h32v192h-32zM192 112h32v192h-32zM256 112h32v256h-32zM320 112h32v192h-32zM384 112h32v256h-32zM128 336h32v32h-32zM192 336h32v32h-32zM320 336h32v32h-32z" />
        </svg>
      </div>

      <div className="px-2 py-1 text-gray-900 border border-r-0 cursor-pointer text-sm font-serif hover:bg-gray-100">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          className="h-5 w-5 fill-current"
          viewBox="0 0 480 480"
        >
          <path d="M0 44h16v352H0zM48 44H32v352h32V44zM192 44h-16v352h32V44zM328 44h-16v352h32V44zM376 44h-16v352h32V44zM80 44h16v352H80zM224 44h16v352h-16zM248 44h16v352h-16zM280 44h16v352h-16zM144 44h-32v352h48V44zM440 44h-32v352h48V44zM464 44h16v352h-16zM0 420h16v16H0zM32 420h32v16H32zM80 420h16v16H80zM112 420h48v16h-48zM176 420h32v16h-32zM224 420h16v16h-16zM248 420h48v16h-48zM312 420h32v16h-32zM360 420h32v16h-32zM408 420h72v16h-72z" />
        </svg>
      </div>

      <div className="px-2 py-1 text-gray-900 border border-r-0 cursor-pointer text-sm font-serif hover:bg-gray-100">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          className="h-5 w-5 fill-current"
          viewBox="0 0 512 512"
        >
          <path d="M395.13 72.348c-9.217 0-16.696 7.473-16.696 16.696v333.913c0 9.223 7.479 16.696 16.696 16.696s16.696-7.473 16.696-16.696V89.044c0-9.223-7.478-16.696-16.696-16.696zM205.913 72.348c-9.217 0-16.696 7.473-16.696 16.696v333.913c0 9.223 7.479 16.696 16.696 16.696 9.217 0 16.696-7.473 16.696-16.696V89.044c0-9.223-7.479-16.696-16.696-16.696zM128 72.348c-9.217 0-16.696 7.473-16.696 16.696v333.913c0 9.223 7.479 16.696 16.696 16.696s16.696-7.473 16.696-16.696V89.044c0-9.223-7.479-16.696-16.696-16.696zM33.391 72.348C14.949 72.348 0 87.297 0 105.739v300.522c0 18.442 14.949 33.391 33.391 33.391s33.391-14.949 33.391-33.391V105.739c.001-18.442-14.949-33.391-33.391-33.391zM300.522 72.348c-18.442 0-33.391 14.949-33.391 33.391v300.522c0 18.442 14.949 33.391 33.391 33.391s33.391-14.949 33.391-33.391V105.739c0-18.442-14.949-33.391-33.391-33.391z" />
          <path d="M300.522 72.348c-18.442 0-33.391 14.949-33.391 33.391v300.522c0 18.442 14.949 33.391 33.391 33.391s33.391-14.949 33.391-33.391V105.739c0-18.442-14.949-33.391-33.391-33.391zM478.609 72.348c-18.442 0-33.391 14.949-33.391 33.391v300.522c0 18.442 14.949 33.391 33.391 33.391S512 424.703 512 406.261V105.739c0-18.442-14.949-33.391-33.391-33.391z" />
        </svg>
      </div>

      <div className="px-2 py-1 text-gray-900 border cursor-pointer rounded-r text-sm font-mono hover:bg-gray-100">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          className="h-5 w-5 fill-current"
          viewBox="0 0 512 512"
        >
          <path d="M160 0H0v160h160V0zm-32 128H32V32h96v96z" />
          <path d="M64 64h32v32H64zM352 0v160h160V0H352zm128 128h-96V32h96v96z" />
          <path d="M416 64h32v32h-32zM0 512h160V352H0v160zm32-128h96v96H32v-96z" />
          <path d="M64 416h32v32H64zM256 0h64v32h-64zM256 128h32V96h32V64h-96V32h-32v64h64zM192 128h32v32h-32zM320 160h-32v32h-96v32h128zM32 288h32v-32H32v-64H0v128h32zM64 192h32v32H64z" />
          <path d="M192 320h64v-32h-32v-32h-64v-64h-32v64H96v64h32v-32h64zM288 256h32v64h-32zM288 352h-96v32h64v96h-64v32h96v-32h64v-32h-64z" />
          <path d="M192 416h32v32h-32zM320 352h32v64h-32zM480 416h-96v96h32v-64h64z" />
          <path d="M448 480h64v32h-64zM480 352h32v32h-32zM384 384h32v-96h-64v32h32zM448 224h-32v-32h-32v32h-32v32h128v-32h32v-32h-64zM448 288h64v32h-64z" />
        </svg>
      </div>
    </div>
  );
};

export default LabelInput;
