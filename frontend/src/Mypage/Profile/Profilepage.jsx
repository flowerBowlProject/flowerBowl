import React, { useState } from "react";
import FormSignup from "../../Component/FormSignup";
import Buttoncontain from "../../Component/ButtonContain";
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
        <Buttoncontain
          onClick={openFileSelector}
          size="small"
          text="사진 변경"
        />
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
        <Buttoncontain
          onClick={openFileSelector}
          size="small"
          text="쉐프 신청"
        />
        <input
          type="file"
          id="fileInput"
          style={{ display: "none" }}
          onChange={handleFileChange}
        />
      </Grid>
      {/* 폼형태 */}
      <Grid container xs direcion="row" mt="2vw">
        <FormSignup
          sx={{ mb: "-10em" }}
          title="아이디"
          place_text="jean1030"
          helper_text="8~15자로 작성해주세요. 중복된 아이디입니다."
          but_exis={false}
        ></FormSignup>
        <FormSignup
          sx={{ mb: "-10em" }}
          title="닉네임"
          but_text="중복확인"
          place_text="치킨나라피자공주"
          helper_text="중복된 닉네임입니다."
          but_exis={true}
        ></FormSignup>
        <FormSignup
          sx={{ mb: "-10em" }}
          title="휴대폰 번호"
          place_text="010-8495-9515"
          helper_text="올바른 휴대폰 번호를 입력해 주세요."
          but_exis={false}
        ></FormSignup>
        <FormSignup
          sx={{ mb: "-10em" }}
          title="이메일"
          but_text="이메일 인증"
          place_text="jean1030@naver.com"
          but_exis={true}
        ></FormSignup>
      </Grid>

      <Grid container xs direcion="row" mt="2vw">
        <FormSignup title="비밀번호" pass_exis={true}></FormSignup>
        <FormSignup
          title="새 비밀번호 변경"
          pass_exis={true}
          helper_text="영문, 숫자, 특수문자 포함 8~15자로 작성해주세요."
        ></FormSignup>
        <FormSignup
          title="새 비밀번호 확인"
          pass_exis={true}
          helper_text="비밀번호가 일치하지 않습니다."
        ></FormSignup>
      </Grid>

      {/* 변경버튼 */}
      <Grid item xs={12} mt="3vw" mb="0.5vw">
        <Buttoncontain size="medium" text="변경" />
      </Grid>

      {/* 회원 탈퇴 버튼 */}
      <Grid item xs={12} mt="3vw" mb="0.5vw">
        <Withdrawl />
      </Grid>
    </Box>
  );
};

export default Profile;
