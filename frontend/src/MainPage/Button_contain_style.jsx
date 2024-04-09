import React from 'react'
import { styled } from '@mui/material/styles';
import {Button} from '@mui/material';
const Button_contain_style =styled(Button)(({theme,width})=>({
    width: width,
    backgroundColor: theme.palette.main.or,
    color: '#ffffff',
    whiteSpace:'nowrap',
    '&:hover': {
      color: theme.palette.main.or,
      backgroundColor: theme.palette.main.br
    }
  }));
  export default Button_contain_style;