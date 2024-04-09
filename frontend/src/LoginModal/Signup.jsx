import React from 'react'
import {Grid,Typography,Modal,Box,TextField} from '@mui/material'
import Button_contain_style from '../Component/Button_contain_style';
import Input_search_style from '../MainPage/Input_search_style';
const Form_Signup=({title,but_text,place_text,helper_text,but_exis})=>{
    return(
        <Grid contianer xs={5} direction='column' mt='1vw' ml='2.5vw'>
                <Grid container xs direction='row'>
                    <Grid item xs={6}>
                        <Typography color='main.br' variant='h6'>{title}</Typography>
                    </Grid>
                    <Grid item xs={6}>
                        {but_exis?
                        <Button_contain_style width='5vw' size='small' sx={{height:'1vw'}}>{but_text}</Button_contain_style>
                        :null}
                    </Grid>
                </Grid>
                <Grid xs item mt='0.2vw'>
                    <Input_search_style sx={{width:'15vw'}} variant='outlined' size='small' placeholder={place_text} helperText={helper_text} />
                </Grid>
        </Grid>
    );
}
const Signup=({open})=>{
    return(
    <Modal open={open}>
        <Box mt='10vw' mx='30vw' border='3px solid #F6C47B' bgcolor='#ffffff' borderRadius={1}>
            <Grid container xs direcion='column' mt='2vw'>
                <Form_Signup title='아이디' but_text='중복확인' place_text='아이디를 입력하세요' helper_text='8~15자로 작성해 주세요.' but_exis={true}/>
                <Form_Signup title='닉네임' but_text='중복확인' place_text='닉네임을 입력하세요'  but_exis={true}/>
                <Form_Signup title='비밀번호' place_text='비밀번호를 입력하세요' helper_text='영문,숫자,특수문자 포함 8~15자로 작성해 주세요.'/>
                <Form_Signup title='이메일' but_text='이메일인증' place_text='이메일을 입력하세요'  but_exis={true}/>
                <Form_Signup title='비밀번호 확인'  place_text='비밀번호를 입력하세요' />
                <Form_Signup title='휴대폰 번호'  place_text='휴대폰번호를 입력하세요.' />
                <Grid item xs={12} mt='3vw' mb='0.5vw'>
                    <Button_contain_style sx={{ml:'12vw'}} width='15vw' >회원가입</Button_contain_style>
                </Grid>
                <Grid item xs mb='3vw'> 
                    <Typography  color='main.br' align='center' display='inline' ml='13vw' onClick=''  >로그인</Typography> 
                    <Typography  color='main.br' align='center' display='inline'  > | </Typography> 
                    <Typography  color='main.br' align='center' display='inline'  onClick=''>아이디 찾기</Typography> 
                    <Typography  color='main.br' align='center' display='inline' > | </Typography> 
                    <Typography  color='main.br' align='center' display='inline'  onClick=''>비밀번호 찾기</Typography> 
                </Grid>
            </Grid>
            
        </Box>
    </Modal>
    );
}
export default Signup;