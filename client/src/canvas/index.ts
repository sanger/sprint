import { CanvasPosition } from "../types";

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
