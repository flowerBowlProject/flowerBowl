import React from 'react'
import { styled } from '@mui/material/styles';
import {Button} from '@mui/material';
const Button_logo_style =styled(Button)(({theme})=>({
    color: 'inherit',
    border: 'inherit',
    whiteSpace:'nowrap',
    '&:hover': {
      color: theme.palette.main.br,
      border: 'none',
      backgroundColor: 'transparent'
    }
  }));
  export default Button_logo_style;