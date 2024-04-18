import React,{useState} from "react";
import { Grid, Button, Modal, Box, ButtonGroup} from "@mui/material";
import ButtonContainStyle from "../Component/ButtonContainStyle";
import FormSignup from "../Component/FormSignup";
import styled from "@emotion/styled";
const ButtonLoginStyle =styled(Button)(({theme})=>({
  color: theme.palette.main.br,
  border: 'inherit',
  whiteSpace:'nowrap',
  '&:hover': {
    color: theme.palette.main.or,
    backgroundColor: 'transparent'
  }
}));
const Signup = ({ open,handleOpen }) => {
  const [passConfirm,setPassConfirm]=useState('');
  const [butDisable,setButDisable]=useState([true,true,true,true,true]);
  const handleConsole=()=>{
    console.log(butDisable)
  }
  const handleBut= (type,bool)=>{
    switch(type){
      case 'id':
        setButDisable(prevState =>{
            prevState[0]=bool;
            return [...prevState];
        })
        break;
      case 'pw':
        setButDisable(prevState =>{
          prevState[1]=bool;
          return [...prevState];
      })
      break;
      case 'email':
        setButDisable(prevState =>{
          prevState[2]=bool;
          return [...prevState];
      })
      break;
      case 'tel':
        setButDisable(prevState =>{
          prevState[4]=bool;
          return [...prevState];
      })
      break;
      default:
        setButDisable(prevState =>{
          prevState[3]=bool;
          return [...prevState];
      })
      break;

    }
  }
  return (
    <Modal open={open}>
      <Box
        mt="7vw"
        mx="25vw"
        border="3px solid #F6C47B"
        bgcolor="#ffffff"
        borderRadius={1}
        width="50vw"
        height='35vw'
      >
        <Grid container direction="row" mt="2vw" justifyContent="center">
          <FormSignup
            title="아이디"
            but_text="중복확인"
            place_text="아이디를 입력하세요"
            helper_text="8~15자로 작성해 주세요."
            but_exis={true}
            size='towel'
            vaild='id'
            handleBut={handleBut}
          />
          <FormSignup
            title="닉네임"
            but_text="중복확인"
            place_text="닉네임을 입력하세요"
            but_exis={true}
            size='towel'
            handleBut={handleBut}
          />
          <FormSignup
            title="비밀번호"
            place_text="비밀번호를 입력하세요"
            helper_text="영문,숫자,특수문자 포함 8~15자로 작성해 주세요."
            size='towel'
            vaild='pw'
            pass_exis='true'
            setPass={setPassConfirm}
            handleBut={handleBut}
          />
          <FormSignup
            title="이메일"
            but_text="이메일인증"
            place_text="이메일을 입력하세요"
            helper_text='이메일 형식이 맞지 않습니다.'
            but_exis={true}
            size='towel'
            vaild='email'  
            handleBut={handleBut}
          />
          <FormSignup
            title="비밀번호 확인"
            place_text="비밀번호를 입력하세요"
            helper_text="비밀번호가 일치하지 않습니다."
            size='towel'
            pass_exis='true'
            vaild={passConfirm}
            pass_confirm={true}
            handleBut={handleBut}
          />
          <FormSignup
            title="휴대폰 번호"
            place_text="휴대폰번호를 입력하세요."
            size='towel'
            helper_text='휴대폰 번호 형식이 맞지 않습니다.'
            vaild='tel'
            handleBut={handleBut}
          />

          <Grid item xs={12} mt="3vw" mb="0.5vw"  textAlign='center'>
            <ButtonContainStyle  width="15vw"  disabled={butDisable[0]||butDisable[1]||butDisable[2]||butDisable[3]||butDisable[4]}>
              회원가입
            </ButtonContainStyle>
          </Grid>
          <Grid item xs={12} mb="3vw"   textAlign='center'>
            <ButtonGroup
              color="secondary"
              variant="text"
              sx={{ color: "main.or"}}
            >
            <ButtonLoginStyle onClick={handleOpen}>로그인</ButtonLoginStyle>
            <ButtonLoginStyle onClick={handleOpen}>아이디 찾기</ButtonLoginStyle>
            <ButtonLoginStyle onClick={handleOpen}>비밀번호 찾기</ButtonLoginStyle>
            </ButtonGroup>
          </Grid>
        </Grid>
      </Box>
    </Modal>
  );
};
export default Signup;
