import { useState,useEffect } from "react";
import { Alert,Snackbar } from "@mui/material";
import { useSelector,useDispatch } from "react-redux";
import { closeError } from "../persistStore";
import { useNavigate } from "react-router-dom";

const ErrorConfirm = ({error})=>{
    const open=useSelector(state=>state.error);
    const navigate=useNavigate();
    const dispatch=useDispatch();
    const handleClose=(event,reason)=>{
        if(reason==='clickaway'){return;}
        dispatch(closeError());
    }
    const [confirm,setConfirm]=useState({
        severity:'',
        content:''
    })
    useEffect(() => {
        let severity = '';
        let content = '';

        switch (error) {
            case 'suWithdrawl':
                severity='success'
                content='회원탈퇴를 완료했습니다.'
                break;
            case 'vaildNewPw':
                severity='error';
                content='새로운 비밀번호가 유효하지 않습니다!'
                break;
            case 'suEdit':
                severity='success';
                content="회원정보를 수정했습니다.";
                break;
            case 'NU':
                severity='error';
                content="아이디가 존재하지 않습니다!";
                break;
            case 'EP':
                severity='error';
                content='가입하지 않은 이메일이나 아이디입니다.';
                break;
            case 'EI':
                severity='error';
                content='가입하지 않은 이메일입니다.';
                break;
            case 'FindId':
                severity='success';
                content='아이디 찾기에 성공 하였습니다.';
                break;
            case 'FindPw':
                severity='success';
                content='이메일에 새로운 비밀번호를 보냈습니다.';
                break;
            case 'SUSignUp':
                severity='success';
                content='회원가입에 성공 하였습니다.';
                break;
            case 'SUCertification':
                severity='success';
                content='인증에 성공 하였습니다.';
                break;
            case 'SF':
                severity='error';
                content='아이디 혹은 비밀번호가 틀렸습니다.';
                break;
            case 'CF':
                severity='error';
                content='이메일 인증에 실패하였습니다.';
                break;
            case 'MF':
                severity='error';
                content='유효하지 않은 이메일입니다.';
                break;
            case 'DE':
                severity='error';
                content='이미 가입된 이메일입니다.';
                break;
            
            case 'SUEMAIL':
                severity = "info";
                content = "이메일에 인증코드를 전송했습니다.";
                break;
            case 'idError':
                severity='warning';
                content='아이디 중복확인을 해야 합니다.'
                break;
            case 'SUNAME':
                severity = "success";
                content = "사용가능한 닉네임입니다.";
                break;
            case 'DN':
                severity = "warning";
                content = "중복된 닉네임입니다.";
                break;
            case 'DI':
                severity = "warning";
                content = "중복된 아이디입니다.";
                break;
            case 'SU':
                severity = "success";
                content = "사용가능한 아이디입니다.";
                break;
            case 'LOGIN':
                severity = "success";
                content = "로그인이 성공하였습니다!";
                break;
            case 'NP':
                severity = 'error';
                content = '접근 권한이 없습니다!';
                break;
            case 'ET':
                severity = 'info';
                content = '로그인이 만료되었습니다.';
                navigate('/')
                dispatch({type:'accessToken',payload:""})
                break;
            case 'IT':
            case 'NT':
                severity = 'warning';
                content = '로그인이 필요한 서비스입니다.';
                break;
            default:
                severity = 'error';
                content = '알 수 없는 오류가 발생했습니다.';
                break;
        }

        setConfirm({ severity, content });
    }, [error]);
    
    
            return(
             <Snackbar open={open} autoHideDuration={3000} onClose={handleClose} anchorOrigin={{ vertical: 'top', horizontal: 'center' }}>
                <Alert onClose={handleClose} severity={confirm.severity} variant='filled' sx={{width:'100%'}}>
                       {confirm.content}
                </Alert>
             </Snackbar>
            );

            
}
export default ErrorConfirm;