import React from 'react'
import { styled } from '@mui/material/styles';
import {TextField} from '@mui/material';



const InputMemberStyle=styled(TextField)(({ theme}) =>({
  '& .MuiInput-underline:after': {
    borderBottomColor: theme.palette.main.wh,
  },
 
}));
export default InputMemberStyle;