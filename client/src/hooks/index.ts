import { useEffect, useState } from "react";

/**
 * Hook to call a side effect after a given delay
 * @param {(...args: any[]) => void} fn The function to call
 * @param {number} delay The delay before calling the function
 */
export const useSetTimeout = (fn: (...args: any[]) => void, delay: number) => {
  useEffect(() => {
    let timer = setTimeout(() => {
      fn();
    }, delay);

    return () => {
      clearTimeout(timer);
    };
  }, [fn, delay]);
};

/**
 * Hook to call a side effect on delete key
 * @param {() => void} fn
 */
export const useOnDeleteKey = (fn: () => void) => {
  useEffect(() => {
    document.addEventListener("keydown", e => {
      if (e.key === "Backspace" || e.key === "Delete") {
        fn();
      }
    });
  }, [fn]);
};

/**
 * Hook that manages when an amount of time has passed
 * @param {number} minimumWait time in milliseconds
 * @returns {boolean} has the minimum wait time elapsed?
 */
export const useMinimumWait = (minimumWait: number) => {
  const [minimumWaitElapsed, setMinimumWaitElapsed] = useState<boolean>(false);
  useSetTimeout(() => setMinimumWaitElapsed(true), minimumWait);
  return minimumWaitElapsed;
};
