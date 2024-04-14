
import {AppBar,Toolbar,Typography,Container ,ButtonGroup,Grid,Link} from '@mui/material';
import './Header.css';
import ButtonLogoStyle from '../Component/ButtonLogoStyle';
import ButtonContain from '../Component/ButtonContain';
import ButtonOutlined from '../Component/ButtonOutlined';
import InputSearch from './InputSearch';
import { styled } from '@mui/material/styles';
import { useState } from 'react';
import CommonModal from '../LoginModal/CommonModal';
import Signup from '../LoginModal/Signup';
import ButtonGroupText from '../Component/ButtonGroupText';
const TextTitle=styled(Typography)(({theme})=>({
    color:theme.palette.main.br,
    fontWeight:'Bold',
    fontSize:'1.2em',
    whiteSpace:'nowrap',
    marginTop:'0.2vw'
}));

const Header =()=>{ 
   const [open,setOpen]=useState([false,false,false,false])
   const handleOpen=(event)=>{
        const innerText=event.target.innerText;
        if(innerText==='회원가입') setOpen([true,false,false,false]);
        else if(innerText==='로그인') setOpen([false,true,false,false]);
        else if(innerText==='아이디 찾기') setOpen([false,false,true,false]);
        else if(innerText==='비밀번호 찾기') setOpen([false,false,false,true]);
        console.log(innerText);
};

    return(
        <AppBar sx={{backgroundColor:'main.yl'}} >
            <Toolbar>
                <Grid container  >
                    <Grid  item xs={1.1}>
                        <Grid container alignItems='center'>
                            <Grid item>
                                <img  width='2vw'  className='logoImg' src='../../images/logo.png' />
                            </Grid>
                            <Grid item >
                                <TextTitle>
                                        화반(花盤)
                                </TextTitle>
                            </Grid>                    
                        </Grid>
                    </Grid>
                    <Grid item xs={3} >
                <ButtonGroupText>
                    <ButtonLogoStyle>
                        레시피
                    </ButtonLogoStyle>
                    <ButtonLogoStyle>
                        클래스
                    </ButtonLogoStyle>
                    <ButtonLogoStyle>
                        커뮤니티
                    </ButtonLogoStyle>
                </ButtonGroupText>
                    </Grid>
                    <Grid item  xs={4} >
                    <InputSearch width='25vw' size='small' />
                    </Grid>
                    <Grid item  xs={2} ml='13vw'  >
                        <Grid container>
                        <Grid item ml='5vw'>
                                <ButtonOutlined size='large' text='회원가입' handleClick={handleOpen}/>
                                <Signup open={open[0]} handleOpen={handleOpen}  />                            
                        </Grid>
                        <Grid item ml='1vw'>
                                <ButtonContain size='large' text='로그인' handleClick={handleOpen} />
                                <CommonModal open={open[1]} handleOpen={handleOpen} name_1='아이디' name_2='비밀번호' helpertext_1='8~15자로 작성해 주세요.' helpertext_2='영문,숫자,특수문자 포함 8~15자로 작성해 주세요.' but_name='로그인' text_1='회원가입'
                                text_2='아이디 찾기' text_3='비밀번호 찾기' api_login={true} type_pass={true} />
                                <CommonModal open={open[2]} handleOpen={handleOpen} name_1='이름' name_2='이메일' helpertext_2='이메일 형식에 맞춰 작성해 주세요.' but_name='아이디 찾기' text_1='회원가입'
                                text_2='로그인' text_3='비밀번호 찾기' api_login={false} type_pass={false} />
                                <CommonModal open={open[3]} handleOpen={handleOpen} name_1='아이디' name_2='이메일' helpertext_1='8~15자로 작성해 주세요.' helpertext_2='이메일 형식에 맞춰 작성해 주세요.' but_name='비밀번호 찾기' text_1='회원가입'
                                text_2='아이디 찾기' text_3='로그인' api_login={false} type_pass={false} />
                        </Grid>
                        </Grid>
                    </Grid>
                </Grid>
            </Toolbar>
        </AppBar>
    );
}
export default Header;