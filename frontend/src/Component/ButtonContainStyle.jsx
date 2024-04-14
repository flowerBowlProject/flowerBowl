import React from 'react'
import { styled } from '@mui/material/styles';
import {Button} from '@mui/material';
const ButtonContainStyle =styled(Button)(({theme,width})=>({
    width: width,
    backgroundColor: theme.palette.main.or,
    color: '#ffffff',
    whiteSpace:'nowrap',
    border:'2px solid transparent',
    '&:hover': {
      color: theme.palette.main.or,
      backgroundColor: theme.palette.main.br,
      borderColor: theme.palette.main.yl,
      border: `2px solid #ffffff`
    }
  }));
  export default ButtonContainStyle;