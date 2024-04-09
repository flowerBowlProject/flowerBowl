import React from 'react'
import {AppBar,Toolbar,Typography,Container ,ButtonGroup,Grid,Link} from '@mui/material';
import './Header.css';
import Button_logo_style from './Button_logo_style';
import Button_contain_style  from './Button_contain_style';
import Button_outlined_style from './Button_outlined_style';
import Input_search from './Input_search';
import { styled } from '@mui/material/styles';
import { useState } from 'react';
import CommonModal from '../LoginModal/CommonModal';
import Signup from '../LoginModal/Signup';
const Text_title=styled(Typography)(({theme})=>({
    color:theme.palette.main.br,
    fontWeight:'Bold',
    fontSize:'1.2em',
    whiteSpace:'nowrap'
}));
const Button_group=styled(ButtonGroup)(({theme})=>({
    color:theme.palette.main.or,
    border:'none',
    marginLeft:'10vw',
    gap:'1vw'
}));
const Header =()=>{ 
   const [open,setOpen]=useState(false)
   const handleOpen = ()=>setOpen(true);
   const [openSignup,setOpenSignup]=useState(false)
   const handleOpenSignup = ()=>setOpenSignup(true);
   
    return(
        <AppBar sx={{backgroundColor:'main.yl'}} >
            <Toolbar sx={{gap:1}}>
                <Grid container >
                    <Grid item xs>
                    <img  width='2vw'  className='logoImg' src='../../images/logo.png' />
                    </Grid><Grid xs item marginTop='0.5vh'>
                    <Text_title>
                        화반(花盤)
                    </Text_title>
                    </Grid>
                    <Grid item xs={4}>
                <Button_group>
                    <Button_logo_style>
                        레시피
                    </Button_logo_style>
                    <Button_logo_style>
                        클래스
                    </Button_logo_style>
                    <Button_logo_style>
                        커뮤니티
                    </Button_logo_style>
                </Button_group>
                    </Grid>
                    <Grid item xs={3}>
                    <Input_search width='20vw'  size='small'/>
                    </Grid>
                    <Grid container xs={4} ml='1vw' >
                        <Grid item>
                                <Button_outlined_style width='5vw'  variant='outlined' sx={{marginLeft:'20vw'}} onClick={handleOpenSignup}>
                                    회원가입
                                </Button_outlined_style>
                                <Signup open={openSignup}>

                                </Signup>
                        </Grid>
                        <Grid item>
                                <Button_contain_style width='5vw' variant='contained' sx={{marginLeft:'1vw'}} onClick={handleOpen}>
                                로그인
                                </Button_contain_style>
                                <CommonModal open={open} name_1='아이디' name_2='비밀번호' helpertext_1='8~15자로 작성해 주세요.' helpertext_2='영문,숫자,특수문자 포함 8~15자로 작성해 주세요.' but_name='로그인' text_1='회원가입'
                                text_2='아이디 찾기' text_3='비밀번호 찾기' api_login={true} type_pass={true}>
                                </CommonModal>
                        </Grid>
                    </Grid>
                </Grid>
            </Toolbar>
        </AppBar>
    );
}
export default Header;