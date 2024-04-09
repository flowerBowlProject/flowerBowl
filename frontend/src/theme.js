import { createTheme } from "@mui/material/styles";
import './App.css';
export const theme = createTheme({
	palette: {
      main: {
        yl: '#FEFBEC',
        gr: '#B0A695',
        or: '#F6C47B',
        br: '#B9835C',
        wh: '#ffffff'
      },
      secondary:{
        main: '#F6C47B',
        
      }
},
    typography:{
        fontFamily: 'GowunBatang-Regular'
    },
    shadows: 'none',
    Bu:{
      color:'inherit',
      border:'inherit',
      '&:hover': {
          color:'main.br',
          border:'none'
          ,backgroundColor:'transparent'
      }
    }
  });