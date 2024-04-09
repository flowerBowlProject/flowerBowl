import React from 'react'
import { styled } from '@mui/material/styles';
import {Button} from '@mui/material';
const Button_outlined_style =styled(Button)(({theme,width})=>({
    width: width,
    borderColor:theme.palette.main.or,
    color: theme.palette.main.or,
    whiteSpace:'nowrap',
    '&:hover': {
      color: theme.palette.main.br,
      borderColor: theme.palette.main.br,
      backgroundColor: 'transparent'
    }
  }));
  export default Button_outlined_style;