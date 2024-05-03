import React, { useState ,useEffect} from "react";
import FormSignup from "../../Component/FormSignup";
import PersonIcon from "@mui/icons-material/Person";
import Tooltip from "@mui/material/Tooltip";
import "./ProfileImage.css";
import Withdrawl from "./Withdraw";
import { Grid,Modal,Box,Typography,TextField } from "@mui/material";
import ButtonContain from "../../Component/ButtonContain";
import ButtonContainStyle from "../../Component/ButtonContainStyle";
import axios from 'axios';
import { url } from "../../url";
import { useDispatch ,useSelector} from "react-redux";
import { editErrorType ,openError,setMemberName,ShowDuplication,openEmailCheck,setMermberEmail,closeEmailCheck, setMemberid, setMemberTel, setMemberNewPw} from "../../persistStore";
import ErrorConfirm from "../../Hook/ErrorConfirm";
import ButtonOutlined from "../../Component/ButtonOutlined";
import { useTheme } from "@emotion/react";
import {useNavigate} from 'react-router-dom';
const Profile = () => {
  const navigate=useNavigate();
  const dispatch=useDispatch();
  const theme=useTheme();
  const accessToken=useSelector(state=>state.accessToken)
  const [nickNameChange,setNickNameChange]=useState(false);
  const [emailChange,setEmailChange]=useState(false);
  const [newPwChange,setNewPwChange]=useState(true);
  const [checkPwChange,setCheckPwChange]=useState(true);
  const [imageFile, setImageFile] = useState(null);
  const [passConfirm, setPassConfirm] = useState("");
  const [butDisable, setButDisable] = useState(true);
  const user=useSelector(state=>state.member)
  const [emailCode,setEmailCode]=useState('');
  const CheckEmailOpen=useSelector(state=>state.emailCheck)
  const handleChangeImage = (event) => {
    setImageFile(event.target.files[0]);
  };
  const handleChangeEmailCode=(e)=>{
    const value=e.target.value
    setEmailCode(value);
  }
  const handleCloseEmailCheck=()=>{
    dispatch(closeEmailCheck())
  }
  const hanldeWithDrawlUser=async()=>{
    navigate('/')
    try{
      const response=await axios.patch(`${url}/api/users/withdrawal`,null,
      {
        headers:{
          Authorization: `Bearer ${accessToken}`
        }
      })
      dispatch(editErrorType('suWithdrawl'))
      dispatch(openError())
      dispatch({type:'accessToken',payload:""})
    }catch(error){
      console.log(error)
      dispatch(editErrorType(error.response.data.code))
      dispatch(openError())
    }
    
  }
  useEffect(()=>{
    const fetchData=async()=>{
      try{
          const response=await axios.get(`${url}/api/users/info`,{
            headers:{
              Authorization: `Bearer ${accessToken}`
            }
          })
          dispatch(setMemberid(response.data.user_id))
          dispatch(setMemberName(response.data.user_nickname))
          dispatch(setMemberTel(response.data.user_phone))
          dispatch(setMermberEmail(response.data.user_email))
          
      }catch(error){
          dispatch(editErrorType(error.response.data.code))
          dispatch(openError())
      }
      
    }
    fetchData();
  },[accessToken])
  const handleEditUser=async()=>{
    if(user.memberNewPw.length>=1&&newPwChange||user.memberNewPw.length>=1&&checkPwChange){
        dispatch(editErrorType('vaildNewPw'))
        dispatch(openError())
        
    }else{
    var reqeustData={
        new_phone: user.memberTel,
        user_password: user.memberPw,
        user_file_sname:'',
        user_file_oname:''
    }
    if(nickNameChange)
      reqeustData.new_nickname=user.memberName
    if(emailChange)
      reqeustData.new_email= user.memberEmail
    if(user.memberNewPw!==''){
      reqeustData.new_pw= user.memberNewPw;}
    else{
      reqeustData.new_pw=user.memberPw
    }
    console.log(reqeustData)
    try{
      const response=await axios.patch(`${url}/api/users/info`,reqeustData,{
        headers:{
          Authorization: `Bearer ${accessToken}`
        }
      }
      )
      dispatch(editErrorType('suEdit'))
      dispatch(openError())
    }catch(error){
      dispatch(editErrorType(error.response.data.code))
      dispatch(openError())
    }
  }
  }
  const handleCheckName=async(name)=>{
    try{
      const response=await axios.post(`${url}/api/auth/checkNickname`,
      {
        user_nickname:name
      });
      setNickNameChange(true);
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
     if(user.memberId===''){
       dispatch(editErrorType("idError"));
       dispatch(openError());
     }else{
     try{
       const response=await axios.post(`${url}/api/auth/sendEmail`,{
        user_email: mail,
         user_id: user.memberId
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
       {user_email:user.memberEmail,
       certification_num:emailCode})
       setEmailChange(true);
       dispatch(editErrorType("SUCertification"));
       dispatch(openError());
       dispatch(closeEmailCheck())
     }catch(error){
       dispatch(editErrorType(error.response.data.code));
       dispatch(openError());

     }
     }
  const handleBut = (type, bool) => {
    switch(type){
      case 'pw':
        setButDisable(bool)
        break;
      case 'newPw':
        setNewPwChange(bool)
        break;  
      default:
        setCheckPwChange(bool)
        break;
    }
  };
  return (
    <>
      <Grid container direction="row">
        <Grid item xs={3}>
          <Grid container direction="column" ml="1.5em" mt="2em">
            <Grid item ml="-1em">
              <div className="ProfileImage">
                <PersonIcon
                  sx={{ color: "black", width: "100px", height: "100px" }}
                />
              </div>
            </Grid>
            <Grid item mt="0.5em" ml="-0.5em">
              <ButtonContainStyle
                component="label"
                width="5vw"
                sx={{ height: "1vw" }}
              >
                <input
                  id={"file-input"}
                  style={{ display: "none" }}
                  type="file"
                  name="imageFile"
                  onChange={handleChangeImage}
                />
                사진 변경
              </ButtonContainStyle>
            </Grid>
            <Grid item mt="3em" ml="-1em">
              <Tooltip title="파일을 첨부해주세요" followCursor>
                <div className="ProfileImage">
                  <PersonIcon
                    sx={{ color: "black", width: "100px", height: "100px" }}
                  />
                </div>
              </Tooltip>
            </Grid>
            <Grid item mt="0.5em" ml="-0.5em">
              <ButtonContainStyle
                component="label"
                width="5vw"
                sx={{ height: "1vw" }}
              >
                <input
                  id={"file-input"}
                  style={{ display: "none" }}
                  type="file"
                  name="imageFile"
                  onChange={handleChangeImage}
                />
                쉐프 신청
              </ButtonContainStyle>
            </Grid>
          </Grid>
        </Grid>

        <Grid item xs={4.5}>
          <Grid container direction="column">
            <Grid item>
              <Grid container direction="column" ml="-10em" rowGap={4}>
                <Grid item>
                  <FormSignup
                    size="joy"
                    title="아이디"
                    valueUser={user.memberId}
                    helper_text="8~15자로 작성해 주세요."
                    vaild="id"
                    disable={true}
                  />
                </Grid>
                <Grid item>
                  <FormSignup
                    size="joy"
                    title="닉네임"
                    but_text="중복확인"
                    valueUser={user.memberName}
                    but_exis={true}
                    vaild='name'
                    handleCheck={handleCheckName}
                  />
                </Grid>
                <Grid item>
                  <FormSignup
                    size="joy"
                    title="휴대폰 번호"
                    valueUser={user.memberTel}
                    helper_text="올바른 휴대폰 번호를 입력해 주세요."
                    but_exis={false}
                    vaild="tel"
            
                  />
                </Grid>
                <Grid item>
                  <FormSignup
                    size="joy"
                    title="이메일"
                    but_text="이메일 인증"
                    valueUser={user.memberEmail}
                    but_exis={true}
                    helper_text="올바른 이메일을 입력해 주세요."
                    vaild="email"
                    handleCheck={hanldeSendEmail}
                  />
                </Grid>
              </Grid>
            </Grid>

            {/* 모니터일 때 ml=18em */}
            <Grid item ml="15em" mb="2em" mt="2.5em">
              <div className="change">
                <ButtonContain text="변경" size="medium" disable={butDisable} handleClick={handleEditUser} />
              </div>
            </Grid>
          </Grid>
        </Grid>

        <Grid item xs={4.5}>
          <Grid container direction="column" ml="-10em" rowGap={-5}>
            <Grid item height="7em">
              <FormSignup size="joy" handleBut={handleBut} title="비밀번호" helper_text="영문,숫자,특수문자 포함 8~15자로 작성해 주세요." place_text="*****" pass_exis={true} vaild="pw"/>
            </Grid>
            <Grid item height="7em">
              <FormSignup
                size="joy"
                title="새 비밀번호 변경"
                place_text="******"
                helper_text="영문, 숫자, 특수문자 포함 8~15자로 작성해주세요."
                pass_exis={true}
                vaild="newPw"
                setPass={setPassConfirm}
                handleBut={handleBut}
              />
            </Grid>
            <Grid item height="7em">
              <FormSignup
                size="joy"
                title="새 비밀번호 확인"
                place_text="******"
                helper_text="비밀번호가 일치하지 않습니다."
                pass_exis={true}
                vaild={passConfirm}
                pass_confirm={true}
                handleBut={handleBut}
              />
            </Grid>
            <Grid item>
              <div className="withdrawl">
                <Withdrawl hanldeWithDrawlUser={hanldeWithDrawlUser}/>
              </div>
            </Grid>
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
      </Grid>
      <ErrorConfirm error={useSelector(state=>state.errorType)}/>
    </>
  );
};

export default Profile;
