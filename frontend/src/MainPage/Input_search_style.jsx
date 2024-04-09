import React from 'react'
import { styled } from '@mui/material/styles';
import {TextField} from '@mui/material';
import { useState } from 'react';


const Input_search_style=styled(TextField)(({ theme}) =>({
    '& .MuiOutlinedInput-root': {
        '& fieldset': {
          border:`2px solid ${theme.palette.main.br}`,
          borderRadius:100,
        },
        '&.Mui-focused fieldset': {
            borderColor: theme.palette.main.or
        },
        '&:hover fieldset': {
          borderColor: theme.palette.main.or
          }
        }
}));
export default Input_search_style;