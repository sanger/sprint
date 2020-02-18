import { CanvasPosition } from "../types";

/**
 * Clear the canvas (make it all one colour).
 *
 * @param {CanvasRenderingContext2D} ctx
 * @param {number} width the width of the canvas
 * @param {number} height the height of the canvas
 * @param {string} fillStyle the colour to "clear" the canvas in. Defaults to white.
 */
export const clearCanvas = (
  ctx: CanvasRenderingContext2D,
  width: number,
  height: number,
  fillStyle: string = "#fff"
): void => {
  ctx.clearRect(0, 0, width, height);
  ctx.fillStyle = fillStyle;
  ctx.fillRect(0, 0, width, height);
};

/**
 * Rotates the canvas around a new origin, executes the callback, then returns the canvas to its original state.
 *
 * @param {CanvasRenderingContext2D} ctx
 * @param {number} x X coordinate to translate the new origin to
 * @param {number} y Y coordinate to translate the new origin to
 * @param {number} rotation Angle in radians to rotate
 * @param {() => void} cb Function to be called after canvas has rotated around new origin
 */
export const withCanvasRotation = (
  ctx: CanvasRenderingContext2D,
  x: number,
  y: number,
  rotation: number,
  cb: () => void
): void => {
  // Save the canvas context
  ctx.save();
  // Translate to the new origin...
  ctx.translate(x, y);
  // ...and rotate around it
  ctx.rotate(rotation);
  cb();
  // Restore to the save position
  ctx.restore();
};

/**
 * Gives the position of the mouse over the canvas after a MouseEvent e.g. click, dblclick, mouseover, etc.
 *
 * @param {CanvasRenderingContext2D} ctx
 * @param {MouseEvent} e
 * @returns {CanvasPosition}
 */
export const getCanvasMousePosition = (
  ctx: CanvasRenderingContext2D,
  e: MouseEvent
): CanvasPosition => {
  const rect = ctx.canvas.getBoundingClientRect();
  return {
    x: ((e.clientX - rect.left) / (rect.right - rect.left)) * ctx.canvas.width,
    y: ((e.clientY - rect.top) / (rect.bottom - rect.top)) * ctx.canvas.height
  };
};
