import React from 'react';
import {Grid,Typography,Modal,Box,ButtonGroup,Button} from '@mui/material'
import InputMember from './InputMember';
import Button_contain_style from '../Component/ButtonContainStyle';
import { useState } from 'react';
import './CommonModal.css'
import { styled } from '@mui/material/styles';
import axios from 'axios';
import { url } from '../url';
import {useDispatch} from 'react-redux';

const ButtonLoginStyle =styled(Button)(({theme})=>({
    color: 'inherit',
    border: 'inherit',
    whiteSpace:'nowrap',
    '&:hover': {
      color: theme.palette.main.or,
      backgroundColor: 'transparent'
    }
  }));

const handleLogin= async(e,user,dispatch=()=>{})=>{
    e.preventDefault();
    try{
    const response= await axios.post(`${url}/api/auth/signIn`,{ 
        user_id:user.id,
        user_password:user.pw
    });
    dispatch({type:"accessToken",payload:response.data.access_token})
    }catch(error){
        console.error(error);
    }
}
const CommonModal=({open,name_1,name_2,helpertext_1,helpertext_2,but_name,text_1,text_2,text_3,api_login,type_pass,handleOpen,vaildTest_1,vaildTest_2})=>{
    const dispatch = useDispatch();
    const [user,setUser]=useState({
        id:'',
        pw:'',
        email:''
    });
    const handleSetUser=(name,value)=>{
        switch(name){
            case '아이디':
                setUser(prevUser=>({
                    ...prevUser,
                    id: value
                }))
                break;
            case '비밀번호':
                setUser(prevUser=>({
                    ...prevUser,
                    pw: value
                }))
                break;
            case '이메일':
                setUser(prevUser=>({
                    ...prevUser,
                    email: value
                }))
                break;
            default:
                break;
        }
    }
    const [butDisable,setButDisable]=useState(true);
    const [butDisable_2,setButDisable_2]=useState(true);
    return(
        <Modal open={open}>
            <Box bgcolor='main.br' mt='10vw' mx='30vw' border='3px solid #F6C47B' borderRadius={1} width='40vw' height='30vw'>
                <Grid container direction='column' mt='2vw'>
                    {name_1?<InputMember name={name_1} helpertext={helpertext_1} vaildTest={vaildTest_1} setBut={setButDisable} setUser={handleSetUser}/>:<Grid item height='3.5vw' />}
                    <InputMember type_pass={type_pass} name={name_2} helpertext={helpertext_2} vaildTest={vaildTest_2} setBut={setButDisable_2} setUser={handleSetUser}/>
                    {name_1?null:<Grid item height='3.5vw' />}
                    <Grid item textAlign='center'>
                        <Button_contain_style  disabled={butDisable|| butDisable_2}  width='15vw' sx={{mb:'2vw'}} onClick={(e)=>handleLogin(e,user,dispatch)}>{but_name}</Button_contain_style>
                    </Grid>
                    <Grid item xs textAlign='center' >
                        <ButtonGroup  color='secondary' variant='text'  sx={{color:'#ffffff',mb:'2vw'}}>
                            <ButtonLoginStyle onClick={handleOpen} >{text_1}</ButtonLoginStyle>
                            <ButtonLoginStyle onClick={handleOpen} >{text_2}</ButtonLoginStyle>
                            <ButtonLoginStyle onClick={handleOpen} >{text_3}</ButtonLoginStyle>
                        </ButtonGroup>
                    </Grid>
                    {api_login?
                    <Grid item xs  mb='3vw' >
                        <Grid container justifyContent='center'>
                        <Grid item xs textAlign='right'>
                            <img src='../../images/But_kakao.png'   className='kakao' height='40vw'/>
                        </Grid>
                        <Grid item xs  textAlign='left'>
                            <img src='../../images/But_naver.png'  className='naver' width='150px'  />
                        </Grid>
                        </Grid>
                    </Grid>:null
                    }
                </Grid>
            </Box>
        </Modal>
    );
}
export default CommonModal;