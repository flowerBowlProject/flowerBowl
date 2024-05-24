import React from 'react'
import { styled } from '@mui/material/styles';
import {ButtonGroup} from '@mui/material';
const ButtonGroupText=styled(ButtonGroup)(({theme})=>({
    color:theme.palette.main.or,
    border:'none',
    marginLeft:'10vw',
    gap:'1vw'
}));
export default ButtonGroupText;