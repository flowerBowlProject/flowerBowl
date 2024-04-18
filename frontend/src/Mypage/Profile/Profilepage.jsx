import React, { useState } from "react";
import FormSignup from "../../Component/FormSignup";
import PersonIcon from "@mui/icons-material/Person";
import Tooltip from "@mui/material/Tooltip";
import "./ProfileImage.css";
import Withdrawl from "./Withdraw";
import { Grid } from "@mui/material";
import ButtonContain from "../../Component/ButtonContain";
import ButtonContainStyle from "../../Component/ButtonContainStyle";

const Profile = () => {
  const [imageFile, setImageFile] = useState(null);

  const handleChangeImage = (event) => {
    setImageFile(event.target.files[0]);
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
              <Grid container direction="column" ml="-10em" rowGap={2}>
                <Grid item>
                  <FormSignup
                    size="joy"
                    title="아이디"
                    place_text="jean1030"
                    helper_text="8~15자로 작성해 주세요. 중복된 아이디입니다."
                  />
                </Grid>
                <Grid item>
                  <FormSignup
                    size="joy"
                    title="닉네임"
                    but_text="중복확인"
                    place_text="치킨나라피자공주"
                    helper_text="중복된 닉네임입니다."
                    but_exis={true}
                  />
                </Grid>
                <Grid item>
                  <FormSignup
                    size="joy"
                    title="휴대폰 번호"
                    place_text="010-8495-9515"
                    helper_text="올바른 휴대폰 번호를 입력해 주세요."
                    but_exis={false}
                  />
                </Grid>
                <Grid item>
                  <FormSignup
                    size="joy"
                    title="이메일"
                    but_text="이메일 인증"
                    place_text="jean1030@naver.com"
                    but_exis={true}
                  />
                </Grid>
              </Grid>
            </Grid>

            {/* 모니터일 때 ml=18em */}
            <Grid item ml="15em" mb="2em" mt="2.5em">
              <div className="change">
                <ButtonContain text="변경" size="medium" />
              </div>
            </Grid>
          </Grid>
        </Grid>

        <Grid item xs={4.5}>
          <Grid container direction="column" ml="-10em" rowGap={2}>
            <Grid item height="7em">
              <FormSignup size="joy" title="비밀번호" place_text="*****" />
            </Grid>
            <Grid item height="7em">
              <FormSignup
                size="joy"
                title="새 비밀번호 변경"
                place_text="******"
                helper_text="영문, 숫자, 특수문자 포함 8~15자로 작성해주세요."
              />
            </Grid>
            <Grid item height="7em">
              <FormSignup
                size="joy"
                title="새 비밀번호 확인"
                place_text="******"
                helper_text="비밀번호가 일치하지 않습니다."
              />
            </Grid>
            <Grid item>
              <div className="withdrawl">
                <Withdrawl />
              </div>
            </Grid>
          </Grid>
        </Grid>
      </Grid>
    </>
  );
};

export default Profile;
