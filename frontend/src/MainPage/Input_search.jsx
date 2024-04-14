import React from 'react'
import Input_search_style from './Input_search_style';
import { useState } from 'react';
import SearchIcon from '@mui/icons-material/Search';
import {InputAdornment,Grid} from '@mui/material';
const Input_search=({icon_boolean,width})=>{
    const [text_search,setText_search]=useState('');
    const handleChange=(event)=>{
        setText_search(event.target.value);
    }
    return(
        <Input_search_style sx={{width:{width},ml:'12vw'}} size='small' InputProps={{endAdornment:(
            <InputAdornment position='end'>
                <SearchIcon  />
            </InputAdornment>)}} value={text_search} onChange={handleChange} />
    );
}
export default Input_search;