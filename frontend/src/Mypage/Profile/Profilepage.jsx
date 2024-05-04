import React, { useState, useEffect } from "react";
import FormSignup from "../../Component/FormSignup";
import PersonIcon from "@mui/icons-material/Person";
import Tooltip from "@mui/material/Tooltip";
import "./ProfileImage.css";
import Withdrawl from "./Withdraw";
import { Grid } from "@mui/material";
import ButtonContain from "../../Component/ButtonContain";
import ButtonContainStyle from "../../Component/ButtonContainStyle";
import { Link } from "react-router-dom";
import axios from "axios";
import { url } from "../../url";
import { useSelector } from "react-redux";

const Profile = () => {
  const [profileImageFile, setProfileImageFile] = useState(null);
  const [chefApplicationImageFile, setChefApplicationImageFile] =
    useState(null);
  const [imagePreviewUrl, setImagePreviewUrl] = useState(null);
  const [chefApplicationImagePreviewUrl, setChefApplicationImagePreviewUrl] =
    useState(null);
  const [passConfirm, setPassConfirm] = useState("");
  const [butDisable, setButDisable] = useState([true, true, true, true, true]);
  const accessToken = useSelector((state) => state.accessToken);
  const [userData, setUserData] = useState({
    nickname: "",
    email: "",
    phone: "",
    role: "",
    fileSname: "",
    fileOname: "",
    pwChanged: false,
    userId: "",
  });

  useEffect(() => {
    const fetchUserInfo = async () => {
      try {
        const response = await axios.get(`${url}/api/users/info`, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        });
        if (response.data.code === "SU") {
          setUserData({
            nickname: response.data.user_nickname,
            email: response.data.user_email,
            phone: response.data.user_phone,
            role: response.data.user_role,
            fileSname: response.data.user_file_sname,
            fileOname: response.data.user_file_oname,
            pwChanged: response.data.user_pw_changed,
            userId: response.data.user_id,
          });
          const savedProfileImageUrl =
            sessionStorage.getItem("profileImageUrl");
          if (savedProfileImageUrl) {
            setImagePreviewUrl(savedProfileImageUrl);
          } else if (response.data.user_file_sname) {
            const profileImageUrl = `/path/to/images/${response.data.user_file_sname}`;
            setImagePreviewUrl(profileImageUrl);
            sessionStorage.setItem("profileImageUrl", profileImageUrl);
          }
        } else {
          console.error("Failed to fetch user data:", response.data.message);
        }
      } catch (error) {
        console.error("Error fetching user data:", error);
      }
    };

    fetchUserInfo();
  }, [accessToken]);

  //수정버튼
  const handleUpdateProfile = async () => {
    const payload = {
      new_nickname: userData.nickname,
      new_phone: userData.phone,
      new_email: userData.email,
      new_pw: passConfirm,
      user_password: "현재 비밀번호",
      user_file_sname: userData.fileSname,
      user_file_oname: userData.fileOname,
    };
    console.log("payload값 확인", payload);
    try {
      const response = await axios.patch(`${url}/api/users/info`, payload, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });
      console.log("Received response:", response.data);

      if (response.data.code === "SU") {
        console.log("Update Success:", response.data.message);
        alert("변경이 완료되었습니다.");
      } else {
        console.error("Failed to update user data:", response.data.message);
        alert("변경에 실패했습니다. " + response.data.message);
      }
    } catch (error) {
      console.error("Error updating user data:", error);
      alert("Error updating profile: " + error.message);
    }
  };

  const butBoolean =
    butDisable[0] ||
    butDisable[1] ||
    butDisable[2] ||
    butDisable[3] ||
    butDisable[4];

  //프로필변경
  const handleChangeImage = (event) => {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => {
        setImagePreviewUrl(reader.result);
        setProfileImageFile(file);
        sessionStorage.setItem("profileImageUrl", reader.result);
      };
      reader.readAsDataURL(file);
      // setImageFile(file);
    }
  };

  //쉐프신청
  const handleChefApplicationImageChange = (event) => {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => {
        setChefApplicationImagePreviewUrl(reader.result);
        setChefApplicationImageFile(file);
      };
      reader.readAsDataURL(file);
    }
  };

  const handleConsole = () => {
    console.log(butDisable);
  };

  const handleBut = (type, bool) => {
    switch (type) {
      case "id":
        setButDisable((prevState) => {
          prevState[0] = bool;
          return [...prevState];
        });
        break;
      case "pw":
        setButDisable((prevState) => {
          prevState[1] = bool;
          return [...prevState];
        });
        break;
      case "email":
        setButDisable((prevState) => {
          prevState[2] = bool;
          return [...prevState];
        });
        break;
      case "tel":
        setButDisable((prevState) => {
          prevState[4] = bool;
          return [...prevState];
        });
        break;
      default:
        setButDisable((prevState) => {
          prevState[3] = bool;
          return [...prevState];
        });
        break;
    }
  };
  return (
    <>
      <Grid container direction="row" className="profileContainer">
        <Grid item xs={3}>
          <Grid container direction="column" ml="1.5em" mt="2em">
            <Grid item ml="-1em">
              <div className="ProfileImage">
                {imagePreviewUrl ? (
                  <img
                    src={imagePreviewUrl}
                    alt="Profile"
                    style={{ width: "100px", height: "100px" }}
                  />
                ) : (
                  <PersonIcon
                    sx={{ color: "black", width: "100px", height: "100px" }}
                  />
                )}
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
                  {imagePreviewUrl ? (
                    <img
                      src={chefApplicationImagePreviewUrl}
                      alt="Profile"
                      style={{ width: "100px", height: "100px" }}
                    />
                  ) : (
                    <PersonIcon
                      sx={{ color: "black", width: "100px", height: "100px" }}
                    />
                  )}
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
                  onClick={handleChefApplicationImageChange}
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
                    place_text={userData.userId}
                    helper_text="8~15자로 작성해 주세요."
                    vaild="id"
                    handleBut={handleBut}
                  />
                </Grid>
                <Grid item>
                  <FormSignup
                    size="joy"
                    title="닉네임"
                    but_text="중복확인"
                    place_text={userData.nickname}
                    but_exis={true}
                  />
                </Grid>
                <Grid item>
                  <FormSignup
                    size="joy"
                    title="휴대폰 번호"
                    place_text={userData.phone}
                    helper_text="올바른 휴대폰 번호를 입력해 주세요."
                    but_exis={false}
                    vaild="tel"
                    handleBut={handleBut}
                  />
                </Grid>
                <Grid item>
                  <FormSignup
                    size="joy"
                    title="이메일"
                    but_text="이메일 인증"
                    place_text={userData.email}
                    but_exis={true}
                    helper_text="올바른 이메일을 입력해 주세요."
                    vaild="email"
                    handleBut={handleBut}
                  />
                </Grid>
              </Grid>
            </Grid>

            {/* 모니터일 때 ml=18em */}
            <Grid item ml="15em" mb="2em" mt="2.5em">
              <div className="change">
                <ButtonContain
                  text="변경"
                  size="medium"
                  disable={butBoolean}
                  onClick={handleUpdateProfile}
                />
              </div>
            </Grid>
          </Grid>
        </Grid>

        <Grid item xs={4.5}>
          <Grid container direction="column" ml="-10em" rowGap={-5}>
            <Grid item height="7em">
              <FormSignup size="joy" title="비밀번호" place_text="********" />
            </Grid>
            <Grid item height="7em">
              <FormSignup
                size="joy"
                title="새 비밀번호 변경"
                helper_text="영문, 숫자, 특수문자 포함 8~15자로 작성해주세요."
                pass_exis={true}
                vaild="pw"
                setPass={setPassConfirm}
                handleBut={handleBut}
              />
            </Grid>
            <Grid item height="7em">
              <FormSignup
                size="joy"
                title="새 비밀번호 확인"
                helper_text="비밀번호가 일치하지 않습니다."
                pass_exis={true}
                vaild={passConfirm}
                pass_confirm={true}
                handleBut={handleBut}
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
