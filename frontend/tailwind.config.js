/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{js,jsx}"],
  theme: {
    extend: {
      colors:{
        'yl': '#FEFBEC',
        'gr': '#B0A695',
        'or': '#F6C47B',
        'br': '#B9835C'
      }
    },
    fontFamily:{
      'ft': ["GowunBatang-Regular"]
    },
    fontWeight:{
      'bd':'bold'
    }
  },
  plugins: [],
}

