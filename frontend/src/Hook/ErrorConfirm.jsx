import { useState, useEffect } from "react";
import { Alert, Snackbar } from "@mui/material";
import { useSelector, useDispatch } from "react-redux";
import { closeError, editErrorType } from "../persistStore";
import { useNavigate } from "react-router-dom";

const ErrorConfirm = ({ error }) => {
  const open = useSelector((state) => state.error);
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const handleClose = (event, reason) => {
    if (reason === "clickaway") {
      return;
    }
    setTimeout(()=>{
      dispatch(editErrorType(''));
    },100)
    dispatch(closeError());
  };
  const [confirm, setConfirm] = useState({
    severity: "",
    content: "",
  });
  useEffect(() => {
    let severity = "";
    let content = "";

    switch (error) {
      case "suWithdrawl":
        severity = "success";
        content = "회원탈퇴를 완료했습니다.";
        break;
      case "vaildNewPw":
        severity = "error";
        content = "새로운 비밀번호가 유효하지 않습니다!";
        break;
      case "suEdit":
        severity = "success";
        content = "회원정보를 수정했습니다.";
        break;
      case "NU":
        severity = "error";
        content = "아이디가 존재하지 않습니다!";
        break;
      case "EP":
        severity = "error";
        content = "가입하지 않은 이메일이나 아이디입니다.";
        break;
      case "EI":
        severity = "error";
        content = "가입하지 않은 이메일입니다.";
        break;
      case "FindId":
        severity = "success";
        content = "아이디 찾기에 성공 하였습니다.";
        break;
      case "FindPw":
        severity = "success";
        content = "이메일에 새로운 비밀번호를 보냈습니다.";
        break;
      case "SUSignUp":
        severity = "success";
        content = "회원가입에 성공 하였습니다.";
        break;
      case "SUCertification":
        severity = "success";
        content = "인증에 성공 하였습니다.";
        break;
      case "SF":
        severity = "error";
        content = "아이디 혹은 비밀번호가 틀렸습니다.";
        break;
      case "CF":
        severity = "error";
        content = "이메일 인증에 실패하였습니다.";
        break;
      case "MF":
        severity = "error";
        content = "유효하지 않은 이메일입니다.";
        break;
      case "DE":
        severity = "error";
        content = "이미 가입된 이메일입니다.";
        break;

      case "SUEMAIL":
        severity = "info";
        content = "이메일에 인증코드를 전송했습니다.";
        break;
      case "idError":
        severity = "warning";
        content = "아이디 중복확인을 해야 합니다.";
        break;
      case "SUNAME":
        severity = "success";
        content = "사용가능한 닉네임입니다.";
        break;
      case "DN":
        severity = "warning";
        content = "중복된 닉네임입니다.";
        break;
      case "DI":
        severity = "warning";
        content = "중복된 아이디입니다.";
        break;
      case "SU":
        severity = "success";
        content = "사용가능한 아이디입니다.";
        break;
      case "LOGIN":
        severity = "success";
        content = "로그인이 성공하였습니다!";
        break;
      case "NP":
        severity = "error";
        content = "접근 권한이 없습니다!";
        break;
      case "ET":
        severity = "info";
        content = "로그인이 만료되었습니다.";
        navigate("/");
        dispatch({ type: "accessToken", payload: "" });
        dispatch({ type: "nickname", payload: "" });
        break;
      case "IT":
      case "NT":
      case "NE":
        severity = "warning";
        content = "로그인이 필요한 서비스입니다.";
        break;
      case "TITLE":
        severity = "warning";
        content = "제목을 작성해 주세요.";
        break;
      case "CATEGORY":
        severity = "warning";
        content = "카테고리를 선택해 주세요.";
        break;
      case "THUMBNAIL":
        severity = "warning";
        content = "사진을 첨부해 주세요.";
        break;
      case "ADDRESS":
        severity = "warning";
        content = "주소를 입력 후 주소 등록 버튼을 클릭해 주세요.";
        break;
      case "CONTENT":
        severity = "warning";
        content = "내용을 작성해 주세요.";
        break;
      case "STARTDATE":
        severity = "warning";
        content = "시작일을 선택해 주세요.";
        break;
      case "ENDDATE":
        severity = "warning";
        content = "종료일을 선택해 주세요.";
        break;
      case "LINK":
        severity = "warning";
        content = "문의 채팅 링크를 작성해 주세요.";
        break;
      case "STUFF":
        severity = "warning";
        content = "재료를 입력해 주세요.";
        break;

      case "REGISTER":
        severity = "info";
        content = "등록이 완료되었습니다.";
        break;
      case "MODIFY":
        severity = "info";
        content = "수정이 완료되었습니다.";
        break;

      case "COMMENT":
        severity = "info";
        content = "댓글 등록이 완료되었습니다.";
        break;
      case "COMMENT_ERROR":
        severity = "info";
        content = "새로고침 후 다시 이용해 주세요.";
        break;
      case "DELETE":
        severity = "info";
        content = "삭제 완료되었습니다.";
        break;
      case "PROFILE SUCCESS":
        severity = "success";
        content = "프로필 사진 변경이 완료되었습니다.";
        break;
      case "CHEF APPLY":
        severity = "success";
        content = "쉐프 신청이 완료되었습니다.";
        break;
      case "NL":
        severity = "warning";
        content = "클래스가 존재하지 않습니다.";
        break;
      case "NR":
        severity = "warning";
        content = "레시피가 존재하지 않습니다.";
        break;
      case "NV":
        severity = "warning";
        content = "리뷰가 존재하지 않습니다.";
        break;
      case "NN":
        severity = "warning";
        content = "해당 정보가 존재하지 않습니다.";
        break;
      case "DL":
        severity = "warning";
        content = "이미 쉐프신청을 완료했습니다.";
        break;
      case "CANCEL SUCCESS":
        severity = "success";
        content = "취소/환불이 완료되었습니다.";
        break;
      case "REVIEW SUCCESS":
        severity = "success";
        content = "리뷰 수정이 완료되었습니다.";
        break;
      case "REVIEW UPDATE SUCCESS":
        severity = "success";
        content = "리뷰 등록이 완료되었습니다.";
        break;
      case "CHEF APPLY SUCCESS":
        severity = "success";
        content = "쉐프 권한 허가처리했습니다.";
        break;
      case "CHEF APPLY FAIL":
        severity = "success";
        content = "쉐프 권한 반려처리했습니다.";
        break;
      case "BANNER APPLY SUCCESS":
        severity = "success";
        content = "배너등록이 완료되었습니다.";
        break;

      case "ONLYCHEF":
        severity="warning";
        content="쉐프만 작성 가능합니다.";
        break;
      case "BUYCOMPLETE":
        severity="warning";
        content="이미 구매한 클래스입니다.";
        break;
        case "BUYCOMPLETE":
          severity="warning";
          content="이미 구매한 클래스입니다.";
          break;
          case "NAVER_LOGIN_ERROR":
        severity="warning";
        content="네이버 로그인이 불가능합니다.";
        break;
        case "KAKAO_LOGIN_ERROR":
        severity="warning";
        content="카카오 로그인이 불가능합니다.";
        break;
        case "BUYCANCEL":
        severity="warning";
        content="결제가 취소되었습니다.";
        break;
        case "PASSWORDBLANK":
        severity="warning";
        content="비밀번호를 입력해야 변경이 가능합니다.";
        break;
      default:
        severity = "error";
        content = "알 수 없는 오류가 발생했습니다.";
        break;
    }

    setConfirm({ severity, content });
  }, [error]);

  return (
    <Snackbar
      open={open}
      autoHideDuration={2000}
      onClose={handleClose}
      anchorOrigin={{ vertical: "top", horizontal: "center" }}
    >
      <Alert
        onClose={handleClose}
        severity={confirm.severity}
        variant="filled"
        sx={{ width: "100%" }}
      >
        {confirm.content}
      </Alert>
    </Snackbar>
  );
};
export default ErrorConfirm;
