import { useState, useEffect } from "react";
import { Alert, Snackbar } from "@mui/material";
import { useSelector, useDispatch } from "react-redux";
import { closeError } from "../persistStore";
const ErrorConfirm = ({ error }) => {
    const open = useSelector(state => state.error);
    const dispatch = useDispatch();
    const handleClose = (event, reason) => {
        if (reason === 'clickaway') { return; }
        dispatch(closeError());
    }
    const [confirm, setConfirm] = useState({
        severity: '',
        content: ''
    })
    useEffect(() => {
        let severity = '';
        let content = '';

        switch (error) {
            case 'NU':
                severity = 'error';
                content = "아이디가 존재하지 않습니다!";
            case 'EP':
                severity = 'error';
                content = '가입하지 않은 이메일이나 아이디입니다.';
                break;
            case 'EI':
                severity = 'error';
                content = '가입하지 않은 이메일입니다.';
                break;
            case 'FindId':
                severity = 'success';
                content = '아이디 찾기에 성공 하였습니다.';
                break;
            case 'FindPw':
                severity = 'success';
                content = '이메일에 새로운 비밀번호를 보냈습니다.';
                break;
            case 'SUSignUp':
                severity = 'success';
                content = '회원가입에 성공 하였습니다.';
                break;
            case 'SUCertification':
                severity = 'success';
                content = '인증에 성공 하였습니다.';
                break;
            case 'SF':
                severity = 'error';
                content = '아이디 혹은 비밀번호가 틀렸습니다.';
                break;
            case 'CF':
                severity = 'error';
                content = '이메일 인증에 실패하였습니다.';
                break;
            case 'MF':
                severity = 'error';
                content = '유효하지 않은 이메일입니다.';
                break;
            case 'DE':
                severity = 'error';
                content = '이미 가입된 이메일입니다.';
                break;

            case 'SUEMAIL':
                severity = "info";
                content = "이메일에 인증코드를 전송했습니다.";
                break;
            case 'idError':
                severity = 'warning';
                content = '아이디 중복확인을 해야 합니다.'
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
                break;
            case 'IT':
            case 'NT':
            case 'NE':
                severity = 'warning';
                content = '로그인이 필요한 서비스입니다.';
                break;

            case 'TITLE':
                severity = 'warning';
                content = '제목을 작성해 주세요.';
                break;
            case 'CATEGORY':
                severity = 'warning';
                content = '카테고리를 선택해 주세요.';
                break;
            case 'THUMBNAIL':
                severity = 'warning';
                content = '사진을 첨부해 주세요.';
                break;
            case 'ADDRESS':
                severity = 'warning';
                content = '주소를 입력 후 주소 등록 버튼을 클릭해 주세요.';
                break;
            case 'CONTENT':
                severity = 'warning';
                content = '내용을 작성해 주세요.';
                break;
            case 'STARTDATE':
                severity = 'warning';
                content = '시작일을 선택해 주세요.';
                break;
            case 'ENDDATE':
                severity = 'warning';
                content = '종료일을 선택해 주세요.';
                break;
            case 'LINK':
                severity = 'warning';
                content = '문의 채팅 링크를 작성해 주세요.';
                break;
            case 'STUFF':
                severity = 'warning';
                content = '재료를 입력해 주세요.';
                break;

            case 'REGISTER':
                severity = 'info';
                content = '등록이 완료되었습니다.';
                break;
            case 'MODIFY':
                severity = 'info';
                content = '수정이 완료되었습니다.';
                break;

            case 'COMMENT':
                severity = 'info';
                content = '댓글 등록이 완료되었습니다.';
                break;
            case 'COMMENT_ERROR':
                severity = 'info';
                content = '새로고침 후 다시 이용해 주세요.';
                break;
            case 'DELETE':
                severity = 'info';
                content = '삭제 완료되었습니다.';
                break;
            default:
                severity = 'error';
                content = '알 수 없는 오류가 발생했습니다.';
                break;
        }

        setConfirm({ severity, content });
    }, [error]);


    return (
        <Snackbar open={open} autoHideDuration={6000} onClose={handleClose} anchorOrigin={{ vertical: 'top', horizontal: 'center' }}>
            <Alert onClose={handleClose} severity={confirm.severity} variant='filled' sx={{ width: '100%' }}>
                {confirm.content}
            </Alert>
        </Snackbar>
    );


}
export default ErrorConfirm;