// Type definitions for bwip-js 1.7.3
// Project: https://github.com/metafloor/bwip-js
// Definitions by: Chris Smith <https://github.com/jbeast>

declare module "bwip-js/browser-bwipjs" {
  import ToCanvasOptions = BwipJs.ToCanvasOptions;
  export = toCanvas;

  declare function toCanvas(
    cvs: string | HTMLCanvasElement,
    opts: ToCanvasOptions,
    callback: (err?: string | Error, cvs?: HTMLCanvasElement) => void
  ): void;
}

declare namespace BwipJs {
  export interface ToCanvasOptions {
    bcid: "code128" | "code39" | "datamatrix" | "ean13";
    text: string;

    parse?: boolean;
    parsefunc?: boolean;

    height?: number;
    width?: number;

    scaleX?: number;
    scaleY?: number;
    scale?: number;

    rotate?: "N" | "R" | "L" | "I";

    paddingwidth?: number;
    paddingheight?: number;

    monochrome?: boolean;
    alttext?: boolean;

    includetext?: boolean;
    textfont?: string;
    textsize?: number;
    textgaps?: number;

    textxalign?:
      | "offleft"
      | "left"
      | "center"
      | "right"
      | "offright"
      | "justify";
    textyalign?: "below" | "center" | "above";
    textxoffset?: number;
    textyoffset?: number;

    showborder?: boolean;
    borderwidth?: number;
    borderleft?: number;
    borderright?: number;
    bordertop?: number;
    boraderbottom?: number;

    barcolor?: string;
    backgroundcolor?: string;
    bordercolor?: string;
    textcolor?: string;

    addontextxoffset?: number;
    addontextyoffset?: number;
    addontextfont?: string;
    addontextsize?: number;

    guardwhitespace?: boolean;
    guardwidth?: number;
    guardheight?: number;
    guardleftpos?: number;
    guardrightpos?: number;
    guardleftypos?: number;
    guardrightypos?: number;

    sizelimit?: number;

    includecheck?: boolean;
    includecheckintext?: boolean;

    inkspread?: number;
    inkspreadh?: number;
    inkspreadv?: number;
  }
}
