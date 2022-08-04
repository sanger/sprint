const defaultOptions = require("@sanger/ui-styling/tailwind.config");
module.exports = {
  ...defaultOptions,
  content: [
      ...defaultOptions.content,
      "./src/**/*.{html,tsx}"
  ],
  prefix: '',
  important: false,
  separator: ':',
  theme: {
    ...defaultOptions.theme,
    colors: {
      ...defaultOptions.theme.colors,
      sanger: {
        'blue-light': '#B2C9D3',
        'blue-medium': '#597FBA',
        'blue': '#2D3A87',
        'blue-dark': '#232642',
      },

      yellow: '#FBC02D',
      orange: '#FD8230',
      pink: '#E91E63',
      red: '#9C2222',

      'blue-light': '#25D7FD',
      turquoise: '#00ACC1',
      blue: '#039BE5',
      navy: '#01579B',

      'pink-light': '#EC407A',
      'purple-light': '#EA80FC',
       purple: '#9C27B0',
      'purple-dark': '#4A148C',

      'green-light': '#D4E157',
      green: '#8BC34A',
      'green-blue': '#4DB6AC',
      'green-dark': '#1B5E20',
    },
  },
  corePlugins: {},
  plugins: [],
}
