import React from "react";
import {
  Grid,
  Button,
  Typography,
  Modal,
  Box,
  TextField,
  ButtonGroup,
} from "@mui/material";
import ButtonContainStyle from "../Component/ButtonContainStyle";
import FormSignup from "../Component/FormSignup";
import styled from "@emotion/styled";
const ButtonSignupStyle = styled(Button)(({ theme }) => ({
  color: "inherit",
  border: "inherit",
  whiteSpace: "nowrap",
  "&:hover": {
    color: theme.palette.main.br,
    backgroundColor: "transparent",
  },
}));
const Signup = ({ open, handleOpen }) => {
  return (
    <Modal open={open}>
      <Box
        mt="10vw"
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
          />
          <FormSignup
            title="닉네임"
            but_text="중복확인"
            place_text="닉네임을 입력하세요"
            but_exis={true}
          />
          <FormSignup
            title="비밀번호"
            place_text="비밀번호를 입력하세요"
            helper_text="영문,숫자,특수문자 포함 8~15자로 작성해 주세요."
            pass_exis={true}
          />
          <FormSignup
            title="이메일"
            but_text="이메일인증"
            place_text="이메일을 입력하세요"
            but_exis={true}
          />
          <FormSignup
            title="비밀번호 확인"
            place_text="비밀번호를 입력하세요"
            pass_exis={true}
          />
          <FormSignup
            title="휴대폰 번호"
            place_text="휴대폰번호를 입력하세요."
          />
          <Grid item xs={12} mt="3vw" mb="0.5vw"  textAlign='center'>
            <ButtonContainStyle  width="15vw">
              회원가입
            </ButtonContainStyle>
          </Grid>
          <Grid item xs={12} mb="3vw"  textAlign='center'>
            <ButtonGroup
              color="secondary"
              variant="text"
              sx={{ color: "main.or"}}
            >
              <ButtonSignupStyle onClick={handleOpen}>로그인</ButtonSignupStyle>
              <ButtonSignupStyle onClick={handleOpen}>
                아이디 찾기
              </ButtonSignupStyle>
              <ButtonSignupStyle onClick={handleOpen}>
                비밀번호 찾기
              </ButtonSignupStyle>
            </ButtonGroup>
          </Grid>
        </Grid>
      </Box>
    </Modal>
  );
};
export default Signup;
