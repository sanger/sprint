const defaultOptions = require("@sanger/ui-styling/tailwind.config");

module.exports = {
  ...defaultOptions,
  content: {
    ...defaultOptions.content,
    content: ["./src/**/*.{html,tsx}"],
  },
  plugins: [],
  important: true,
};
