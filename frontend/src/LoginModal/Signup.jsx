import React,{useState} from "react";
import { Grid, Button, Modal, Box, ButtonGroup,Typography,TextField} from "@mui/material";
import ButtonContainStyle from "../Component/ButtonContainStyle";
import FormSignup from "../Component/FormSignup";
import styled from "@emotion/styled";
import axios from "axios";
import { url } from "../url";
import ErrorConfirm from "../Hook/ErrorConfirm";
import { useSelector ,useDispatch} from "react-redux";
import { ShowDuplication, closeEmailCheck, editErrorType ,openEmailCheck,openError, setMemberName, setMemberid,setMermberEmail} from "../persistStore";
import { useTheme } from "@emotion/react";
import InputMember from "./InputMember";
import ButtonOutlined from "../Component/ButtonOutlined";
const ButtonLoginStyle =styled(Button)(({theme})=>({
  color: theme.palette.main.br,
  border: 'inherit',
  whiteSpace:'nowrap',
  '&:hover': {
    color: theme.palette.main.or,
    backgroundColor: 'transparent'
  }
}));
const Signup = ({ open,handleOpen,handleClose }) => {
  const dispatch=useDispatch();
  const handleCloseEmailCheck=()=>{
    dispatch(closeEmailCheck())
  }
  const handleCheckId=async(id)=>{
    try{
    const response=await axios.post(`${url}/api/auth/checkId`,{
      user_id:id
    });
      dispatch(setMemberid(id));
      dispatch(editErrorType(response.data.code));
      dispatch(openError());
    }catch(error){
      
      dispatch(editErrorType(error.response.data.code));
      dispatch(openError());
      dispatch(ShowDuplication('id'));
    }
  }
  const handleCheckName=async(name)=>{
    try{
      const response=await axios.post(`${url}/api/auth/checkNickname`,
      {
        user_nickname:name
      });
      dispatch(setMemberName(name))
      dispatch(editErrorType("SUNAME"));
      dispatch(openError());
    }catch(error){
      console.log(error)
      dispatch(editErrorType(error.response.data.code));
      dispatch(openError());
      dispatch(ShowDuplication('name'));
    }
  }
  const hanldeSendEmail=async(mail)=>{
    if(userid===''){
      dispatch(editErrorType("idError"));
      dispatch(openError());
    }else{
    try{
      const response=await axios.post(`${url}/api/auth/sendEmail`,{
        user_email: mail,
        user_id: userid
      })
      dispatch(editErrorType("SUEMAIL"));
      dispatch(openError());
      dispatch(openEmailCheck())
      dispatch(setMermberEmail(mail))
    }
    catch(error){
        dispatch(editErrorType(error.response.data.code));
        dispatch(openError());

    }
    }
  }
  const handleCertifiedEmail=async()=>{
    try{
      const response=await axios.post(`${url}/api/auth/checkEmail`,
      {user_email:userMail,
      certification_num:emailCode})
      dispatch(editErrorType("SUCertification"));
      dispatch(openError());
      dispatch(closeEmailCheck())
    }catch(error){
      dispatch(editErrorType(error.response.data.code));
      dispatch(openError());

    }
  }
  const handleSignup=async()=>{
    console.log(user)
    try{
      const response=await axios.post(`${url}/api/auth/signUp`,
      { user_id:user.memberId,
        user_password:user.memberPw,
        user_nickname:user.memberName,
        user_phone:user.memberTel,
        user_email:user.memberEmail}
      ) 
      handleClose();
      dispatch(editErrorType("SUSignUp"));
      dispatch(openError());
    }catch(error){
      dispatch(editErrorType(error.response.data.code));
      dispatch(openError());
    }

  }
  const [passConfirm,setPassConfirm]=useState('');
  const [butDisable,setButDisable]=useState([true,true,true,true,true]);
  const userid=useSelector(state=>state.member.memberId)
  const userMail=useSelector(state=>state.member.memberEmail)
  const user=useSelector(state=>state.member)
  const CheckEmailOpen=useSelector(state=>state.emailCheck)
  const [emailCode,setEmailCode]=useState('');
  const handleChangeEmailCode=(e)=>{
    const value=e.target.value
    setEmailCode(value);
  }

  const theme=useTheme();
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
    <Modal open={open} onClose={handleClose}> 
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
            handleCheck={handleCheckId}
          />
          <FormSignup
            title="닉네임"
            but_text="중복확인"
            place_text="닉네임을 입력하세요"
            but_exis={true}
            size='towel'
            handleBut={handleBut}
            handleCheck={handleCheckName}
            vaild='name'
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
            handleCheck={hanldeSendEmail}
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
            <ButtonContainStyle  onClick={handleSignup} width="15vw"  disabled={butDisable[0]||butDisable[1]||butDisable[2]||butDisable[3]||butDisable[4]}>
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
        <Modal open={CheckEmailOpen} onClose={handleCloseEmailCheck} >
          <Box mt='15vw' mx='37.5vw' width='25vw' height='15vw' backgroundColor='main.or' borderRadius={1} border={`3px solid ${theme.palette.main.br}`} >
              <Grid container  mt='3.5vw'>
                <Grid item xs={6} textAlign='center' pt='0.7vw' >
                  <Typography>인증코드</Typography> 
                </Grid>
                <Grid item xs={6} textAlign='left'>
                  <TextField onChange={handleChangeEmailCode} variant='filled' size='small' sx={{width:'10vw'}}/> 
                </Grid>
                <Grid item xs textAlign='center' mt='4vw'>
                  <ButtonOutlined text='전송' size='verySmall' handleClick={handleCertifiedEmail}/>
            
                </Grid>
              </Grid>
              
          </Box>
        </Modal>
        <ErrorConfirm error={useSelector(state=>state.errorType)}/>
      </Box>
    </Modal>
  );
};
export default Signup;
