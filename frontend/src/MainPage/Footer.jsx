import React from 'react'
import './Footer.css';
import {Grid,Typography,Box} from '@mui/material';
import Button_contain_style  from '../Component/Button_contain_style';
import Button_outlined_style from '../Component/Button_outlined_style';
import { useState } from 'react';
const Text=({mainTitle,content_1,content_2})=>{
    return(
        <Grid sm={1} container direction='column' >
            <Grid xl={4} item >
                <Typography fontSize='1.2em' color='main.or'>
                    {mainTitle}
                </Typography>
            </Grid>
            <Grid item>
                <Typography color='#ffffff'>
                    {content_1}
                </Typography>
                <Typography color='#ffffff'>
                    {content_2}
                </Typography>
            </Grid>
        </Grid>
    );
}


const Footer =()=>{
    return(
        <Grid container direction='column'  sx={{backgroundColor:'main.gr'}}>
            <Grid container direction='row' justifyContent="space-evenly" pt='3vw' mb='1vw'  >
                <Grid sm={6} container direction='row'>
                    <Grid item>  
                        <img width='2vw'  className='logoImg' src='../../images/logo.png' />
                    </Grid>
                    <Grid item >
                        <Typography fontSize='1.5em'  sx={{color:'#ffffff'}}>화반(花盤)</Typography>
                    </Grid>
                </Grid>
                <Text mainTitle='레시피' content_1='레시피 등록' content_2='레시피 보러가기'/>
                <Text mainTitle='클래스' content_1='클래스 등록' content_2='클래스 보러가기'/>
                <Text mainTitle='커뮤니티' content_1='커뮤니티 등록' content_2='커뮤니티 보러가기'/>
                <Grid container direction='column' sm={1} gap='0.9vw'>
                    <Grid item>                    
                        <Button_contain_style width='5vw'  variant='contained' sx={{height:'2vw'}}>로그인</Button_contain_style>
                    </Grid>
                    <Grid item xl>
                        <Button_outlined_style width='5vw' variant='outlined' sx={{height:'2vw'}}>회원가입</Button_outlined_style>
                    </Grid>
                </Grid>
                <Grid>
                </Grid>
            </Grid> 
            
                <Grid container xs={11}  direction='row'  py='1vw' ml='2vw'    borderTop='1px solid #F6C47B'  justifyContent={'space-between'}>
                    <Grid item    sm={2}  pt='2vw'> 
                        <Typography color='#ffffff' >© HWABAN Project GitHub</Typography>
                    </Grid>
                    <Grid item sm={1.8}  pt='2vw' mr='1vw'>
                        <Typography ml='1vw' color='#ffffff'>FrontEnd<Box component="span" mr='3vw' />Towel<Box component="span" mr='0.5vw'/>|<Box component="span" mr='0.5vw'/>Joy<Box component="span" mr='0.5vw'/>|<Box component="span" mr='0.5vw'/>Jean</Typography>
                        <Typography ml='1vw' color='#ffffff'>BackEnd<Box component="span"  mr='3.3vw'/>Lion<Box component="span" mr='0.98vw'/>|<Box component="span" mr='0.5vw'/>Neo<Box component="span" mr='0.2vw'/>|<Box component="span" mr='0.5vw'/>Muji</Typography>
                    </Grid>
                </Grid>
            
        </Grid>
    );
  
}


export default Footer;