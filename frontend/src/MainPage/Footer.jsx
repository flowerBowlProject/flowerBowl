import React from 'react'
import './Footer.css';
import {Grid,Typography,Box} from '@mui/material';
import ButtonContain from '../Component/ButtonContain';
import ButtonOutlined from '../Component/ButtonOutlined';
import { useState } from 'react';
import {Link} from 'react-router-dom';
import Signup from '../LoginModal/Signup';
import CommonModal from '../LoginModal/CommonModal';
const Text=({mainTitle,content_1,content_2,Link_1,Link_2})=>{
    return(
        <Grid sm={1} container direction='column' >
            <Grid xl={4} item >
                <Typography fontSize='1.2em' color='main.or'>
                    {mainTitle}
                </Typography>
            </Grid>
            <Grid item>
                <Link to={Link_1} style={{textDecoration:'none'}}>
                <Typography color='#ffffff'>
                    {content_1}
                </Typography>
                </Link>
                <Link to={Link_2} style={{textDecoration:'none'}}>
                <Typography color='#ffffff'>
                    {content_2}
                </Typography>
                </Link>
            </Grid>
        </Grid>
    );
}


const Footer =()=>{
    const [open, setOpen] = useState([false, false, false, false]);
  const handleOpen = (event) => {
    const innerText = event.target.innerText;
    if (innerText === "회원가입") setOpen([true, false, false, false]);
    else if (innerText === "로그인") setOpen([false, true, false, false]);
    else if (innerText === "아이디 찾기") setOpen([false, false, true, false]);
    else if (innerText === "비밀번호 찾기")
      setOpen([false, false, false, true]);
    console.log(innerText);
  };
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
                <Text mainTitle='레시피' content_1='레시피 등록' content_2='레시피 보러가기' Link_1={
                    '/registerRecipe'} Link_2={'/recipeList'}/>
                <Text mainTitle='클래스' content_1='클래스 등록' content_2='클래스 보러가기' Link_1={
                    '/registerClass'} Link_2={'/classList'}/>
                <Text mainTitle='커뮤니티' content_1='커뮤니티 등록' content_2='커뮤니티 보러가기'
                Link_1={'/registerCommunity'} Link_2={'/communityList'}/>
                <Grid container direction='column' sm={1} gap='0.9vw'>
                    <Grid item>                    
                        <ButtonContain handleClick={handleOpen}   variant='contained' size='large' text='로그인' />
                    </Grid>
                    <Grid item xl>
                        <ButtonOutlined  handleClick={handleOpen} variant='outlined' size='large' text='회원가입'/>
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
                <Signup open={open[0]} handleOpen={handleOpen} />
                <CommonModal
                  open={open[1]}
                  handleOpen={handleOpen}
                  name_1="아이디"
                  name_2="비밀번호"
                  helpertext_1="8~15자로 작성해 주세요."
                  helpertext_2="영문,숫자,특수문자 포함 8~15자로 작성해 주세요."
                  but_name="로그인"
                  text_1="회원가입"
                  text_2="아이디 찾기"
                  text_3="비밀번호 찾기"
                  api_login={true}
                  type_pass={true}
                />
                <CommonModal
                  open={open[2]}
                  handleOpen={handleOpen}
                  name_1="이름"
                  name_2="이메일"
                  helpertext_2="이메일 형식에 맞춰 작성해 주세요."
                  but_name="아이디 찾기"
                  text_1="회원가입"
                  text_2="로그인"
                  text_3="비밀번호 찾기"
                  api_login={false}
                  type_pass={false}
                />
                <CommonModal
                  open={open[3]}
                  handleOpen={handleOpen}
                  name_1="아이디"
                  name_2="이메일"
                  helpertext_1="8~15자로 작성해 주세요."
                  helpertext_2="이메일 형식에 맞춰 작성해 주세요."
                  but_name="비밀번호 찾기"
                  text_1="회원가입"
                  text_2="아이디 찾기"
                  text_3="로그인"
                  api_login={false}
                  type_pass={false}
                />
        </Grid>
    );
  
}


export default Footer;