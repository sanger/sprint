import React, { useEffect } from "react";
import { useLazyQuery } from "@apollo/react-hooks";
import _ from "lodash";

import LabelTypeSelector from "./LabelTypeSelector";
import ActionButtons from "./ActionButtons";
import LabelDesigner from "./LabelDesigner";
import FieldList from "./FieldList";
import useLayoutReducer from "../hooks/layoutReducer";

import labelTypesQuery from "../queries/labelTypesQuery";
import { LabelTypes } from "../queries/types/LabelTypes";
import { Layout } from "../types/graphql-global-types";
import Loading from "./Loading";
import { CSSTransition } from "react-transition-group";

const SprintPlanning: React.FC = () => {
  const [state, dispatch] = useLayoutReducer();
  const [loadLabelTypes, { error, called, loading, data }] = useLazyQuery<
    LabelTypes
  >(labelTypesQuery);

  useEffect(() => {
    let timer = setTimeout(() => {
      loadLabelTypes();
    }, 1200);

    return () => {
      clearTimeout(timer);
    };
  }, [loadLabelTypes]);

  useEffect(() => {
    if (!data || !data.labelTypes) return;

    dispatch({ type: "SET_LABEL_TYPES", value: data.labelTypes });

    if (state.initialQueryParams && state.initialQueryParams.labelType) {
      let labelType = _.find(data.labelTypes, {
        name: state.initialQueryParams.labelType
      });

      if (labelType) {
        dispatch({ type: "SET_LABEL_TYPE", labelType });

        if (state.initialQueryParams.printRequest) {
          state.initialQueryParams.printRequest.layouts.forEach(
            (layout: Layout) => {
              layout.barcodeFields &&
                layout.barcodeFields.forEach(barcodeField => {
                  dispatch({
                    type: "ADD_CANVAS_BARCODE_FIELD",
                    options: barcodeField
                  });
                });

              layout.textFields &&
                layout.textFields.forEach(textField => {
                  dispatch({
                    type: "ADD_CANVAS_TEXT_FIELD",
                    options: textField
                  });
                });
            }
          );
        }
      }
    }
  }, [data, dispatch, state.initialQueryParams]);

  useEffect(() => {
    document.addEventListener("keydown", e => {
      // Delete the currently selected field but only if an input isn't focused
      if (
        (e.key === "Backspace" || e.key === "Delete") &&
        (!document.activeElement || document.activeElement.nodeName !== "INPUT")
      ) {
        dispatch({ type: "DELETE_SELECTED_CANVAS_FIELD" });
      }
    });
  }, [dispatch]);

  if (loading || !called) {
    return (
      <div className="flex w-screen h-screen items-center justify-center">
        <Loading />
      </div>
    );
  }

  if (error) return <ErrorPage />;

  return (
    <CSSTransition timeout={600} in={true} appear={true} classNames="fade-in">
      <div className="sprint-planning h-screen max-h-screen p-3">
        <h1 className="heading tracking-widest text-5xl font-hairline text-white text-center border-b border-yellow">
          SPrint
        </h1>

        <ActionButtons
          labelType={state.labelType}
          fields={state.canvasFields}
        />

        <svg
          xmlns="http://www.w3.org/2000/svg"
          className="fill-current text-yellow h-20 place-self-center"
          viewBox="0 0 899.898 309.554"
          preserveAspectRatio="xMidYMid meet"
        >
          <path d="M402.07 133.762c-.171-2.906-1.71-5.129-3.933-6.669-2.053-1.708-4.788-2.564-7.525-2.564-4.788 0-10.26 1.028-10.26 6.841 0 2.564 2.052 3.763 3.933 4.617 5.642 2.393 18.468 3.079 29.754 6.841 11.286 3.592 21.206 10.26 21.206 24.795 0 24.624-23.599 31.294-45.146 31.294-20.862 0-43.777-8.379-44.29-31.294h32.32c.171 3.078 1.539 5.642 4.105 7.867 1.709 1.537 4.958 2.564 9.063 2.564 4.446 0 11.456-1.71 11.456-6.841 0-5.13-2.907-6.669-18.639-9.404-25.821-4.447-36.251-12.656-36.251-27.875 0-22.402 24.11-28.215 41.895-28.215 19.153 0 42.238 5.3 43.093 28.044H402.07v-.001z" />
          <path d="M525.516 176.854c0 6.671.342 14.195 4.104 19.837h-34.542c-.855-2.393-1.368-5.984-1.196-8.38h-.343c-7.181 8.209-17.613 10.602-28.557 10.602-17.101 0-31.294-8.206-31.294-26.846 0-28.045 32.833-27.019 50.103-30.269 4.617-.854 9.234-2.221 9.234-7.866 0-5.984-5.643-8.209-11.115-8.209-10.431 0-12.482 5.301-12.655 9.064h-31.463c1.026-24.966 24.624-29.07 45.659-29.07 42.408 0 42.066 17.613 42.066 34.713l-.001 36.424zm-32.491-20.006c-4.104 2.051-8.721 3.25-13.338 4.276-7.523 1.708-11.457 3.591-11.457 9.575 0 4.104 4.446 8.209 10.602 8.209 7.696 0 13.681-4.618 14.193-13.852v-8.208zM533.179 107.94h32.662v11.286h.342c6.326-8.891 15.389-13.507 28.899-13.507 15.903 0 30.438 9.746 30.438 30.096v60.877h-33.858v-46.513c0-10.26-1.197-17.441-11.115-17.441-5.814 0-13.51 2.906-13.51 17.101v46.854h-33.858V107.94zM724.327 185.235c0 17.613-4.617 42.41-46.171 42.41-21.033 0-43.264-5.644-46.342-29.584h33.516c.342.854 1.197 2.051 1.882 3.078 2.052 3.078 5.643 6.497 9.918 6.497 10.944 0 13.339-12.994 13.339-20.348v-8.209h-.343c-5.472 7.867-13.679 12.311-23.769 12.311-26.163 0-37.963-19.494-37.963-43.264 0-23.083 13.168-42.408 38.476-42.408 9.918 0 19.323 3.934 23.256 12.996h.343V107.94h33.858v77.295zm-46.855-15.563c12.484 0 13.851-11.968 13.851-21.887 0-9.747-4.788-17.443-14.706-17.443-9.576 0-14.364 8.209-14.364 17.613 0 10.09 3.077 21.717 15.219 21.717M759.743 159.925c.855 10.089 6.669 17.27 17.272 17.27 5.472 0 11.457-2.05 14.193-6.326h31.636c-6.841 19.153-25.479 28.045-45.657 28.045-28.388 0-49.763-16.759-49.763-46.342 0-25.821 18.811-46.855 46.855-46.855 35.226 0 50.447 19.666 50.447 54.207l-64.983.001zm32.489-17.613c0-8.208-6.155-14.877-14.876-14.877-10.09 0-15.905 5.302-17.443 14.877h32.319zM828.126 107.94h32.491v14.193h.342c5.472-10.601 12.655-16.414 24.966-16.414 3.42 0 6.669.513 9.918 1.367v29.925c-3.42-1.196-6.498-2.222-13.338-2.222-13.339 0-20.52 7.867-20.52 26.848v35.055h-33.859V107.94zM365.704 224.744h-12.296v-8.633h12.296v8.633zm-12.297 3.476h12.296v32.231h-12.296V228.22zM368.687 228.221h11.862v4.099h.125c2.297-3.23 5.59-4.907 10.495-4.907 5.776 0 11.054 3.539 11.054 10.929v22.11h-12.296v-16.891c0-3.727-.435-6.335-4.036-6.335-2.112 0-4.907 1.056-4.907 6.211v17.014h-12.296l-.001-32.23zM423.792 237.598c-.061-1.056-.621-1.862-1.428-2.42-.744-.623-1.739-.934-2.732-.934-1.74 0-3.727.373-3.727 2.484 0 .933.745 1.366 1.428 1.677 2.05.87 6.707 1.117 10.806 2.485 4.1 1.305 7.701 3.727 7.701 9.005 0 8.942-8.57 11.365-16.395 11.365-7.576 0-15.898-3.044-16.083-11.365h11.736c.062 1.116.559 2.049 1.491 2.855.622.561 1.802.933 3.291.933 1.616 0 4.161-.622 4.161-2.483 0-1.864-1.055-2.425-6.769-3.417-9.377-1.614-13.165-4.596-13.165-10.122 0-8.136 8.757-10.249 15.215-10.249 6.955 0 15.34 1.926 15.65 10.185h-11.18v.001z" />
          <path d="M452.075 228.221h6.582v7.576h-6.582v11.24c0 2.983.683 4.286 3.788 4.286.931 0 1.863-.062 2.794-.187v9.316c-2.483 0-5.279.372-7.887.372-5.216 0-10.992-.807-10.992-9.625v-15.403h-5.465v-7.576h5.465v-9.811h12.297v9.812zM472.938 224.744h-12.296v-8.633h12.296v8.633zm-12.298 3.476h12.296v32.231H460.64V228.22zM492.559 228.221h6.582v7.576h-6.582v11.24c0 2.983.682 4.286 3.788 4.286.931 0 1.863-.062 2.794-.187v9.316c-2.483 0-5.279.372-7.887.372-5.217 0-10.992-.807-10.992-9.625v-15.403h-5.466v-7.576h5.466v-9.811h12.297v9.812zM534.413 260.453h-11.861v-4.099h-.125c-2.297 3.23-5.59 4.907-10.496 4.907-5.774 0-11.054-3.539-11.054-10.929v-22.11h12.296v16.891c0 3.727.435 6.335 4.038 6.335 2.111 0 4.905-1.056 4.905-6.211v-17.014h12.297v32.23zM552.25 228.221h6.582v7.576h-6.582v11.24c0 2.983.682 4.286 3.788 4.286.931 0 1.863-.062 2.794-.187v9.316c-2.483 0-5.279.372-7.887.372-5.218 0-10.992-.807-10.992-9.625v-15.403h-5.466v-7.576h5.466v-9.811h12.297v9.812zM570.938 247.101c.31 3.664 2.422 6.271 6.272 6.271 1.988 0 4.161-.744 5.154-2.296h11.489c-2.483 6.953-9.253 10.185-16.58 10.185-10.309 0-18.072-6.088-18.072-16.831 0-9.376 6.831-17.018 17.016-17.018 12.793 0 18.32 7.144 18.32 19.689h-23.599zm11.8-6.398c0-2.981-2.236-5.402-5.402-5.402-3.666 0-5.777 1.925-6.335 5.402h11.737zM432.066 59.57c-2.206-7.384-7.654-11.458-16.953-11.458-7.174 0-12.642 3.641-15.41 9.008l2.538-8.176H389.49L384.942 69.6h-.25l-4.379-20.655h-11.485L364.699 69.6h-.19l-4.866-20.655h-12.737l10.111 33.209h12.554l4.866-20.547h.19l4.738 20.547h12.561l5.962-19.193c-.122.88-.203 1.773-.203 2.687 0 11.065 7.634 17.339 17.989 17.339 7.126 0 13.563-2.958 16.392-9.299v8.466h12.175V36.465h-12.175V59.57zm-16.39-3.529c3.567 0 5.875 2.565 5.875 5.895h-12.182c.622-3.851 2.808-5.895 6.307-5.895zm16.392 16.632h-10.964c-1.062 1.611-3.303 2.375-5.306 2.375-3.986 0-6.166-2.761-6.484-6.72h22.753v4.345h.001zM598.608 75.048c-4.007 0-6.179-2.761-6.491-6.721h23.911c-.054-12.859-5.617-20.215-18.11-20.215-8.69 0-14.869 5.333-16.784 12.615v-1.414c0-5.38-2.802-11.201-11.058-11.201-4.799 0-7.749 1.719-9.435 3.384-.176.197-.365.393-.487.575-.63.65-.948 1.096-1.191 1.354-1.556-3.458-5.312-5.313-8.744-5.313-4.494 0-8.114 1.408-10.673 5.313h-.197v-4.48h-11.803v33.21h12.182v-17.99c0-3.512 1.062-6.132 4.311-6.132 3.932 0 4.175 2.998 4.175 6.132v17.989h12.25V64.165c0-3.512 1.056-6.132 4.298-6.132 3.878 0 4.189 2.998 4.189 6.132v17.989h12.182v-11.37c2.01 7.837 8.717 12.202 17.346 12.202 7.499 0 14.246-3.262 16.805-10.314H603.9c-1.049 1.611-3.296 2.376-5.292 2.376zm-.127-19.007c3.56 0 5.854 2.565 5.854 5.895h-12.162c.629-3.851 2.815-5.895 6.308-5.895zM509.875 48.112c-9.096 0-14.463 4.575-16.601 10.727-1.787-7.465-8.608-10.727-15.796-10.727-9.042 0-15.918 5.367-17.468 13.752V36.465h-12.182v45.689h12.182V69.627c1.53 8.812 8.189 13.36 17.095 13.36 7.614 0 13.888-3.79 16.209-10.68 2.166 6.118 7.519 10.68 16.561 10.68 12.175 0 17.671-8.257 17.671-17.474 0-9.205-5.495-17.401-17.671-17.401zm-27.458 20.911c-.325 2.694-1.996 4.934-5.184 4.934-3.932 0-5.374-3.066-5.374-8.385 0-4.034.995-8.439 5.624-8.439 2.876 0 4.609 1.922 4.609 5.251h10.335a21.716 21.716 0 00.054 6.639h-10.064zm27.46 4.936c-4.805 0-5.502-4.866-5.502-8.447 0-3.58.697-8.378 5.502-8.378 4.798 0 5.489 4.798 5.489 8.378 0 3.581-.69 8.447-5.489 8.447z" />
          <g>
            <path d="M21.814 72.84H9.369a3.462 3.462 0 01-3.462-3.462V56.934a3.462 3.462 0 013.462-3.462h12.444a3.462 3.462 0 013.462 3.462v12.444a3.461 3.461 0 01-3.461 3.462zM164.38 80.372h-22.123a6.154 6.154 0 01-6.154-6.154V52.094a6.154 6.154 0 016.154-6.154h22.123a6.154 6.154 0 016.154 6.154v22.123a6.155 6.155 0 01-6.154 6.155zM207.523 30.159h-16.592a4.616 4.616 0 01-4.616-4.616V8.951a4.616 4.616 0 014.616-4.616h16.592a4.616 4.616 0 014.616 4.616v16.592a4.616 4.616 0 01-4.616 4.616zM210.289 80.372h-22.123a6.154 6.154 0 01-6.154-6.154V52.094a6.154 6.154 0 016.154-6.154h22.123a6.154 6.154 0 016.154 6.154v22.123a6.155 6.155 0 01-6.154 6.155zM251.358 26.931h-12.444a3.462 3.462 0 01-3.462-3.462V11.025a3.462 3.462 0 013.462-3.462h12.444a3.462 3.462 0 013.462 3.462v12.444a3.462 3.462 0 01-3.462 3.462zM299.341 76.068h-16.592a4.616 4.616 0 01-4.616-4.616V54.86a4.616 4.616 0 014.616-4.616h16.592a4.616 4.616 0 014.616 4.616v16.592a4.616 4.616 0 01-4.616 4.616zM23.888 167.885H7.295a4.616 4.616 0 01-4.616-4.616v-16.592a4.616 4.616 0 014.616-4.616h16.592a4.616 4.616 0 014.616 4.616v16.592a4.615 4.615 0 01-4.615 4.616zM72.562 126.28H50.439a6.154 6.154 0 01-6.154-6.154V98.003a6.154 6.154 0 016.154-6.154h22.123a6.154 6.154 0 016.154 6.154v22.123a6.154 6.154 0 01-6.154 6.154zM72.562 172.189H50.439a6.154 6.154 0 01-6.154-6.154v-22.123a6.154 6.154 0 016.154-6.154h22.123a6.154 6.154 0 016.154 6.154v22.123a6.154 6.154 0 01-6.154 6.154zM120.683 175.632H94.135a7.385 7.385 0 01-7.385-7.385v-26.548a7.385 7.385 0 017.385-7.385h26.548a7.385 7.385 0 017.385 7.385v26.548a7.385 7.385 0 01-7.385 7.385zM166.592 129.724h-26.548a7.385 7.385 0 01-7.385-7.385V95.791a7.385 7.385 0 017.385-7.385h26.548a7.385 7.385 0 017.385 7.385v26.548a7.385 7.385 0 01-7.385 7.385zM166.592 175.632h-26.548a7.385 7.385 0 01-7.385-7.385v-26.548a7.385 7.385 0 017.385-7.385h26.548a7.385 7.385 0 017.385 7.385v26.548a7.385 7.385 0 01-7.385 7.385zM212.501 129.724h-26.548a7.385 7.385 0 01-7.385-7.385V95.791a7.385 7.385 0 017.385-7.385h26.548a7.385 7.385 0 017.385 7.385v26.548a7.385 7.385 0 01-7.385 7.385zM256.197 172.189h-22.123a6.154 6.154 0 01-6.154-6.154v-22.123a6.154 6.154 0 016.154-6.154h22.123a6.154 6.154 0 016.154 6.154v22.123a6.153 6.153 0 01-6.154 6.154zM72.562 264.007H50.439a6.154 6.154 0 01-6.154-6.154V235.73a6.154 6.154 0 016.154-6.154h22.123a6.154 6.154 0 016.154 6.154v22.123a6.154 6.154 0 01-6.154 6.154zM120.683 221.541H94.135a7.385 7.385 0 01-7.385-7.385v-26.548a7.385 7.385 0 017.385-7.385h26.548a7.385 7.385 0 017.385 7.385v26.548a7.385 7.385 0 01-7.385 7.385zM164.38 264.007h-22.123a6.154 6.154 0 01-6.154-6.154V235.73a6.154 6.154 0 016.154-6.154h22.123a6.154 6.154 0 016.154 6.154v22.123a6.154 6.154 0 01-6.154 6.154zM212.501 221.541h-26.548a7.385 7.385 0 01-7.385-7.385v-26.548a7.385 7.385 0 017.385-7.385h26.548a7.385 7.385 0 017.385 7.385v26.548a7.385 7.385 0 01-7.385 7.385zM210.289 264.007h-22.123a6.154 6.154 0 01-6.154-6.154V235.73a6.154 6.154 0 016.154-6.154h22.123a6.154 6.154 0 016.154 6.154v22.123a6.154 6.154 0 01-6.154 6.154zM256.197 218.098h-22.123a6.154 6.154 0 01-6.154-6.154v-22.123a6.154 6.154 0 016.154-6.154h22.123a6.154 6.154 0 016.154 6.154v22.123a6.153 6.153 0 01-6.154 6.154zM297.267 256.475h-12.444a3.462 3.462 0 01-3.462-3.462v-12.444a3.462 3.462 0 013.462-3.462h12.444a3.462 3.462 0 013.462 3.462v12.444a3.462 3.462 0 01-3.462 3.462zM21.814 302.384H9.369a3.462 3.462 0 01-3.462-3.462v-12.444a3.462 3.462 0 013.462-3.462h12.444a3.462 3.462 0 013.462 3.462v12.444a3.461 3.461 0 01-3.461 3.462zM253.432 305.612H236.84a4.616 4.616 0 01-4.616-4.616v-16.592a4.616 4.616 0 014.616-4.616h16.592a4.616 4.616 0 014.616 4.616v16.592a4.616 4.616 0 01-4.616 4.616z" />
          </g>
        </svg>

        <LabelTypeSelector
          selectedLabelType={state.labelType}
          labelTypes={state.labelTypes}
          dispatch={dispatch}
        />

        <LabelDesigner state={state} dispatch={dispatch} />

        <FieldList
          labelType={state.labelType}
          layout={state.canvasFields}
          selectedCanvasField={state.selectedCanvasField}
          superSelectedCanvasField={state.superSelectedCanvasField}
          dispatch={dispatch}
        />
      </div>
    </CSSTransition>
  );
};

const ErrorPage: React.FC = () => {
  return (
    <CSSTransition timeout={600} in={true} appear={true} classNames="fade-in">
      <div className="flex flex-col w-screen h-screen items-center justify-center">
        <h1 className="text-5xl text-white">Error :(</h1>
        <h2 className="text-3xl text-white">
          There was an error contacting the the SPrint API
        </h2>
      </div>
    </CSSTransition>
  );
};

export default SprintPlanning;
