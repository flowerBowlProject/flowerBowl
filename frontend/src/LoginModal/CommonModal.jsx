import React from 'react';
import {Grid,Typography,Modal,Box,Link} from '@mui/material'
import Input_member_style from './Input_member_style';
import Input_member from './Input_member';
import Button_contain_style from '../MainPage/Button_contain_style';
import { useState } from 'react';
import './CommonModal.css'
const CommonModal=({open,name_1,name_2,helpertext_1,helpertext_2,but_name,text_1,text_2,text_3,api_login,type_pass,open_1,open_2,open_3})=>{
    const handleOpenModal=(event)=>{
        const clickText=event.target.innerText;
        console.log(clickText);
    }
    
    return(
        <Modal open={open}>
            <Box bgcolor='main.br' mt='10vw' mx='30vw' border='3px solid #F6C47B' borderRadius={1}>
                <Grid container direction='column' mt='2vw'>
                    <Input_member name={name_1} helpertext={helpertext_1}/>
                    <Input_member type_pass={type_pass} name={name_2} helpertext={helpertext_2}/>
                    <Grid item xs>
                        <Button_contain_style width='15vw' sx={{ml:'12vw',mb:'2vw'}}>{but_name}</Button_contain_style>
                    </Grid>
                    <Grid item xs>
                        <Typography  color='#ffffff' align='center' display='inline'sx={{ '&:hover': { cursor: "pointer",color:'main.or' } }}  ml='13vw' onClick=''  >{text_1}</Typography> 
                        <Typography  color='#ffffff' align='center' display='inline'  > | </Typography> 
                        <Typography  color='#ffffff' align='center' display='inline' sx={{ "&:hover": { cursor: "pointer",color:'main.or' } }}  onClick={handleOpenModal}>{text_2}</Typography> 
                        <Typography  color='#ffffff' align='center' display='inline' > | </Typography> 
                        <Typography  color='#ffffff' align='center' display='inline'  sx={{ "&:hover": { cursor: "pointer",color:'main.or' } }} onClick=''>{text_3}</Typography> 
                    </Grid>
                    {api_login?
                    <Grid container xs direction='row' alignItems='center' mb='3vw' mt='2vw'>
                        <Grid item xs>
                            <img src='../../images/But_kakao.png'   className='kakao' height='40vw'/>
                        </Grid>
                        <Grid item xs >
                            <img src='../../images/But_naver.png'  className='naver' width='150px'  />
                        </Grid>
                    </Grid>:null
                    }
                </Grid>
            </Box>
        </Modal>
    );
}
export default CommonModal;