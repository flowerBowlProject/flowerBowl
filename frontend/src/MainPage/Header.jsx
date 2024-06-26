import {
  AppBar,
  Toolbar,
  Typography,
  Container,
  ButtonGroup,
  Grid
} from "@mui/material";
import "./Header.css";
import ButtonLogoStyle from "../Component/ButtonLogoStyle";
import ButtonContain from "../Component/ButtonContain";
import ButtonOutlined from "../Component/ButtonOutlined";
import InputSearch from "./InputSearch";
import { styled } from "@mui/material/styles";
import { useState,useEffect } from "react";
import CommonModal from "../LoginModal/CommonModal";
import Signup from "../LoginModal/Signup";
import ButtonGroupText from "../Component/ButtonGroupText";
import {useNavigate,Link,NavLink} from 'react-router-dom';
import { useDispatch ,useSelector} from "react-redux";
import { closeError, editErrorType ,openError} from "../persistStore";
import axios from "axios";
import { url } from "../url";
const TextTitle = styled(Typography)(({ theme }) => ({
  color: theme.palette.main.br,
  fontWeight: "Bold",
  fontSize: "1.2em",
  whiteSpace: "nowrap",
  marginTop: "0.2vw",
}));
const Header = () => {

  const dispatch=useDispatch();
  const navigate=useNavigate();
  const accessToken=useSelector(state=>state.accessToken)
  const [role,setRole]=useState('')
  const [open, setOpen] = useState([false, false, false, false]);
  const handleOpen = (event) => {
    const innerText = event.target.innerText;
    console.log(innerText)
    if      (innerText === "회원가입") setOpen([true, false, false, false]);
    else if (innerText === "로그인") setOpen([false, true, false, false]);
    else if (innerText === "아이디 찾기") setOpen([false, false, true, false]);
    else if (innerText === "비밀번호 찾기")
      setOpen([false, false, false, true]);
    console.log(innerText);
  };
  const handleText = () => {
    if (role === 'ROLE_ADMIN') {
      return '관리자페이지';
    } else if (accessToken) {
      return '마이페이지';
    } else {
      return '회원가입';
    }
  };
  const text=handleText();
  const handleClose = ()=>{
    dispatch(closeError());
    setOpen([false,false,false,false]);
  }
  const handleLogout=()=>{
    navigate('/')
    dispatch({type:'accessToken',payload:""})
    setRole('')
    dispatch({type:'nickname', payload:''})
  }
  const handleMove=()=>{
    dispatch(closeError())
    if (role === 'ROLE_ADMIN')
    navigate('/Admin/admissionChef')
    else
    navigate('/Mypage/profile') 
  }
  
  useEffect(()=>{
    const roleCheck=async()=>{
      try{
      const response=await axios.get(`${url}/api/users/info`,{
        headers:{
        Authorization: `Bearer ${accessToken}`}
      })
      setRole(response.data.user_role)
    }catch(error){
      dispatch(editErrorType(error.response.data.code))
      dispatch(openError())
    }
    }
    if(accessToken!=='')
    roleCheck();

  },[accessToken])
  const handleClick=(e)=>{
    if (role === 'ROLE_ADMIN') {
      handleMove();
    } else if (accessToken) {
      handleMove();
    } else {
      handleOpen(e);
    }
  }
  return (
    <AppBar sx={{ backgroundColor: "main.yl" }}>
      <Toolbar>
        <Grid container>
          <Grid item xs={1.1}>
          <Link to='/'  style={{textDecoration:'none'}}>
            <Grid container alignItems="center">
              <Grid item>
                <img
                  width="2vw"
                  className="logoImg"
                  src="../../images/logo.png"
                />
              </Grid>
              <Grid item>
                <TextTitle  mb='0.3vw'>
                  
                    화반(花盤)
                  
                </TextTitle>
              </Grid>
            </Grid>
            </Link>  
          </Grid>
          <Grid item xs={3}>
            <ButtonGroupText >
            <NavLink to='/recipeList' style={{textDecoration:'none'}} className={({isActive})=>isActive?"focused":"unFocused"}><ButtonLogoStyle onClick={handleClose}>레시피</ButtonLogoStyle></NavLink>
            <NavLink to='/classList' style={{textDecoration:'none'}} className={({isActive})=>isActive?"focused":"unFocused"}><ButtonLogoStyle  onClick={handleClose}>클래스</ButtonLogoStyle></NavLink>
            <NavLink to='/communityList' style={{textDecoration:'none'}} className={({isActive})=>isActive?"focused":"unFocused"}><ButtonLogoStyle onClick={handleClose}>커뮤니티</ButtonLogoStyle></NavLink>
            </ButtonGroupText>
          </Grid>
          <Grid item xs={4}>
            <InputSearch width="25vw" size="small" />
          </Grid>
          <Grid item xs={2.1} ml="13vw">
            <Grid container>
              <Grid item ml="5vw">
                <ButtonOutlined
                  size="large"
                  text={text}
                  handleClick={handleClick}
                />
                <Signup open={open[0]} handleOpen={handleOpen} handleClose={handleClose}/>
              </Grid>
              <Grid item ml="1vw">
                <ButtonContain
                  size="large"
                  text={useSelector(state=>state.accessToken)?'로그아웃':"로그인"}
                  handleClick={useSelector(state=>state.accessToken)?handleLogout:handleOpen}
                />
                <CommonModal
                  open={open[1]}
                  handleOpen={handleOpen}
                  handleClose={handleClose}
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
                  vaildTest_1='id'
                  vaildTest_2='pw'
                  tag='login'
                />
                <CommonModal
                  open={open[2]}
                  handleOpen={handleOpen}
                  handleClose={handleClose}
                  name_2="이메일"
                  helpertext_2="이메일 형식에 맞춰 작성해 주세요."
                  but_name="아이디 찾기"
                  text_1="회원가입"
                  text_2="로그인"
                  text_3="비밀번호 찾기"
                  vaildTest_2='email'
                  api_login={false}
                  type_pass={false}
                  tag='searchId'
                />
                <CommonModal
                  open={open[3]}
                  handleOpen={handleOpen}
                  handleClose={handleClose}
                  name_1="아이디"
                  name_2="이메일"
                  helpertext_1="8~15자로 작성해 주세요."
                  helpertext_2="이메일 형식에 맞춰 작성해 주세요."
                  but_name="비밀번호 찾기"
                  text_1="회원가입"
                  text_2="아이디 찾기"
                  text_3="로그인"
                  vaildTest_1='id'
                  vaildTest_2='email'
                  api_login={false}
                  type_pass={false}
                  tag='searchPw'
                />
              </Grid>
            </Grid>
          </Grid>
        </Grid>
      </Toolbar>
    </AppBar>
  );
};
export default Header;
