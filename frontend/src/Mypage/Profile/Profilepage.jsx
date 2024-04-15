import React, { useState } from "react";
import Form_Signup from "../../Component/Form_Signup";
import Button_contain_style from "../../Component/Button_contain_style";
import PersonIcon from "@mui/icons-material/Person";
import Tooltip from "@mui/material/Tooltip";
import "./ProfileImage.css";
import Withdrawl from "./Withdraw";
import { Grid, Typography, Modal, Box, TextField } from "@mui/material";

const Profile = () => {
  const [file, setFile] = useState(null);

  const handleFileChange = (event) => {
    setFile(event.target.files[0]);
  };

  const openFileSelector = () => {
    document.getElementById("fileInput").click();
  };

  return (
    <Box
      mt="-20vw"
      marginRight="50vw"
      ml="1000vw"
      mx="18vw"
      border="3px solid #F6C47B"
      bgcolor="#ffffff"
      borderRadius={1}
    >
      {/* 프로필 */}
      <Grid
        item
        xs={12}
        mt="3vw"
        mb="0.5vw"
        ml="3vw"
        display="flex"
        p="0.5"
        flexDirection="column"
      >
        <PersonIcon
          sx={{
            color: "black",
            width: "100px",
            height: "100px",
            position: "absolute",
          }}
        />
        <Button_contain_style onClick={openFileSelector}>
          사진 변경
        </Button_contain_style>
        <input
          type="file"
          id="fileInput"
          style={{ display: "none" }}
          onChange={handleFileChange}
        />

        {/* 툴팁 */}
        <div className="chefprofile">
          <Tooltip title="파일을 첨부해주세요" followCursor>
            <div className="ProfileImage" sx={{ p: 2 }}>
              <PersonIcon
                sx={{
                  color: "black",
                  width: "100px",
                  height: "100px",
                  position: "absolute",
                }}
              />
            </div>
          </Tooltip>
        </div>
        <Button_contain_style onClick={openFileSelector}>
          쉐프 신청
        </Button_contain_style>
        <input
          type="file"
          id="fileInput"
          style={{ display: "none" }}
          onChange={handleFileChange}
        />
      </Grid>
      {/* 폼형태 */}
      <Grid container xs direcion="row" mt="2vw">
        <Form_Signup
          sx={{ mb: "-10em" }}
          title="아이디"
          place_text="jean1030"
          helper_text="8~15자로 작성해주세요. 중복된 아이디입니다."
          but_exis={false}
        ></Form_Signup>
        <Form_Signup
          sx={{ mb: "-10em" }}
          title="닉네임"
          but_text="중복확인"
          place_text="치킨나라피자공주"
          helper_text="중복된 닉네임입니다."
          but_exis={true}
        ></Form_Signup>
        <Form_Signup
          sx={{ mb: "-10em" }}
          title="휴대폰 번호"
          place_text="010-8495-9515"
          helper_text="올바른 휴대폰 번호를 입력해 주세요."
          but_exis={false}
        ></Form_Signup>
        <Form_Signup
          sx={{ mb: "-10em" }}
          title="이메일"
          but_text="이메일 인증"
          place_text="jean1030@naver.com"
          but_exis={true}
        ></Form_Signup>
      </Grid>

      <Grid container xs direcion="row" mt="2vw">
        <Form_Signup title="비밀번호" place_text="*****"></Form_Signup>
        <Form_Signup
          title="새 비밀번호 변경"
          place_text="******"
          helper_text="영문, 숫자, 특수문자 포함 8~15자로 작성해주세요."
        ></Form_Signup>
        <Form_Signup
          title="새 비밀번호 확인"
          place_text="******"
          helper_text="비밀번호가 일치하지 않습니다."
        ></Form_Signup>
      </Grid>

      {/* 변경버튼 */}
      <Grid item xs={12} mt="3vw" mb="0.5vw">
        <Button_contain_style>변경</Button_contain_style>
      </Grid>

      {/* 회원 탈퇴 버튼 */}
      <Grid item xs={12} mt="3vw" mb="0.5vw">
        <Withdrawl />
      </Grid>
    </Box>
  );
};

export default Profile;
