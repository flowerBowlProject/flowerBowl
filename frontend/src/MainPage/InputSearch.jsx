import React from 'react'
import InputSearchStyle from './InputSearchStyle';
import { useState } from 'react';
import SearchIcon from '@mui/icons-material/Search';
import {InputAdornment,Grid,IconButton,InputBase } from '@mui/material';
import { useNavigate } from 'react-router-dom';
const InputSearch=({icon_boolean,width})=>{
    const [text_search,setText_search]=useState('');
    const navigate=useNavigate();
    const handleChange=(event)=>{
        const value=event.target.value;
        setText_search(value);
    }
    const handleSearch=()=>{
        navigate(`/searchList?keyword=${text_search}`)
    }
    const handleKeyDown=(e)=>{

        if(e.key==='Enter'){
            handleSearch();
        }
    }
    return(
        <>
         <InputSearchStyle  variant='outlined'  sx={{width:{width},ml:'12vw'}} size='small' InputProps={{endAdornment:(
            <InputAdornment position='end' >
                <IconButton type='button' onClick={handleSearch} >
                <SearchIcon  />
                </IconButton>
            </InputAdornment>)}} onChange={handleChange} onKeyDown={handleKeyDown} /> 
           
        </>
    );
}
export default InputSearch;