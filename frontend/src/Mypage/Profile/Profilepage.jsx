import React, { useState, useEffect } from "react";
import FormSignup from "../../Component/FormSignup";
import PersonIcon from "@mui/icons-material/Person";
import Tooltip from "@mui/material/Tooltip";
import "./ProfileImage.css";
import Withdrawl from "./Withdraw";
import { Grid, Modal, Box, Typography, TextField } from "@mui/material";
import ButtonContain from "../../Component/ButtonContain";
import ButtonContainStyle from "../../Component/ButtonContainStyle";
import axios from "axios";
import { url } from "../../url";
import { useDispatch, useSelector } from "react-redux";
import {
  editErrorType,
  openError,
  setMemberName,
  ShowDuplication,
  openEmailCheck,
  setMermberEmail,
  closeEmailCheck,
  setMemberid,
  setMemberTel,
  setChefRole,
} from "../../persistStore";
import ErrorConfirm from "../../Hook/ErrorConfirm";
import ButtonOutlined from "../../Component/ButtonOutlined";
import { useTheme } from "@emotion/react";
import { useNavigate } from "react-router-dom";

const Profile = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const theme = useTheme();
  const accessToken = useSelector((state) => state.accessToken);
  const [nickNameChange, setNickNameChange] = useState(false);
  const [emailChange, setEmailChange] = useState(false);
  const [newPwChange, setNewPwChange] = useState(true);
  const [checkPwChange, setCheckPwChange] = useState(true);
  const [imageChange, setImageChange] = useState(false);
  const [imageFile, setImageFile] = useState(null);
  const [imageUrl, setImageUrl] = useState(null);
  const [profileSName, setProfileSName] = useState("");
  const [profileOName, setProfileOName] = useState("");
  const [chefImageUrl, setChefImageUrl] = useState(null);
  const [chefImageFile, setChefImageFile] = useState(null);
  const [chefDetails, setChefDetails] = useState({
    chef_oname: "",
    chef_sname: "",
  });
  const [passConfirm, setPassConfirm] = useState("");
  const [butDisable, setButDisable] = useState(true);
  const user = useSelector((state) => state.member);
  const [emailCode, setEmailCode] = useState("");
  const CheckEmailOpen = useSelector((state) => state.emailCheck);
  const isChef = useSelector((state) => state.isChef);
  const [isDisabled, setIsDisabled] = useState(false);

  //이미지 변경
  const handleChangeImage = async (event) => {
    const file = event.target.files[0];
    setImageFile(file);
    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => {
        setImageUrl(reader.result);
        //handleUploadImage(file);
      };
      reader.readAsDataURL(file);
      setImageChange(true);
      setButDisable(false);
    } else {
      setImageUrl(null);
      setImageChange(false);
    }
  };

  //프로필 이미지 업로드 api연결
  const handleUploadImage = async (file) => {
    const formData = new FormData();
    formData.append("file", file);
    console.log("Sending FormData:", formData.get("file"));
    try {
      const response = await axios.post(`${url}/api/images/profile`, formData, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
          "Content-Type": "multipart/form-data",
        },
      });
      if (response.data.code === "SU") {
        console.log("프로필 사진 변경이 완료되었습니다.", response.data);
        setButDisable(true);
        setProfileOName(response.data.profile_oname);
        setProfileSName(response.data.profile_sname);
        dispatch(editErrorType("PROFILE SUCCESS"));
        dispatch(openError());
        //로컬스토리지 저장
        localStorage.setItem("profileImageUrl", response.data.profile_sname);
        setImageUrl(response.data.profile_sname);

        return {
          profileOName: response.data.profile_oname,
          profileSName: response.data.profile_sname,
        };
      } else {
        console.error(
          "프로필 사진 변경 실패: 응답 코드가 'SU'가 아닙니다.",
          response.data
        );
      }
    } catch (error) {
      console.error("프로필 사진 변경 실패", error);
      dispatch(editErrorType(error.response.data.code));
      dispatch(openError());
    }
  };

  //쉐프 이미지 변경
  const handleUploadImageChef = async (event) => {
    const file = event.target.files[0];
    setChefImageFile(file);
    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => {
        setChefImageUrl(reader.result);
        //uploadChefImage(file);
      };
      reader.readAsDataURL(file);
    } else {
      setChefImageUrl(null);
    }
  };

  //쉐프 이미지 업로드 api연결
  const uploadChefImage = async (file) => {
    const formData = new FormData();
    formData.append("file", file);
    try {
      const response = await axios.post(`${url}/api/images/chef`, formData, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
          "Content-Type": "multipart/form-data",
        },
      });
      if (response.data.code === "SU") {
        console.log("쉐프 이미지 업로드 성공", response.data.message);
        setChefDetails({
          chef_oname: response.data.chef_oname,
          chef_sname: response.data.chef_sname,
        });
        //로컬 스토리지 저장
        localStorage.setItem("chefImageUrl", response.data.chef_sname);
        setChefImageUrl(response.data.chef_sname);
      } else {
        console.error("쉐프 이미지 업로드 실패", response.data.message);
      }
    } catch (error) {
      console.error("쉐프 이미지 업로드 실패", error);
      dispatch(editErrorType(error.response.data.code)); //기본에러
      dispatch(openError());
    }
  };

  //쉐프 신청 api 연결
  const handleApplyChef = async () => {
    console.log(
      "Sending chef application with:",
      chefDetails.chef_oname,
      chefDetails.chef_sname
    );
    // 전송되는 페이로드를 명확히 로깅합니다
    const payload = {
      chef_oname: chefDetails.chef_oname,
      chef_sname: chefDetails.chef_sname,
    };
    console.log("Payload being sent:", payload);

    try {
      const response = await axios.post(`${url}/api/mypage/chefs`, payload, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });
      dispatch(editErrorType("CHEF APPLY"));
      dispatch(openError());
      console.log("Chef application successful:", response.data);
    } catch (error) {
      console.error("Error during chef application:", error);
      console.error("Request data:", error.config.data);
      console.error("Request headers:", error.config.headers);
      dispatch(editErrorType(error.response.data.code));
      dispatch(openError());
      if (error.response) {
        console.error("Error response data:", error.response.data);
        console.error("Error response status:", error.response.status);
        console.error("Error response headers:", error.response.headers);
      }
    }
  };

  const handleChangeEmailCode = (e) => {
    const value = e.target.value;
    setEmailCode(value);
  };
  const handleCloseEmailCheck = () => {
    dispatch(closeEmailCheck());
  };
  const hanldeWithDrawlUser = async () => {
    navigate("/");
    try {
      const response = await axios.patch(`${url}/api/users/withdrawal`, null, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });
      dispatch(editErrorType("suWithdrawl"));
      dispatch(openError());
      dispatch({ type: "accessToken", payload: "" });
    } catch (error) {
      console.log(error);
      dispatch(editErrorType(error.response.data.code));
      dispatch(openError());
    }
  };

  //변경사진 마운트
  useEffect(() => {
    // Retrieve image URLs from local storage
    const savedProfileImageUrl = localStorage.getItem("profileImageUrl");
    const savedChefImageUrl = localStorage.getItem("chefImageUrl");

    if (savedProfileImageUrl) {
      setImageUrl(savedProfileImageUrl);
    }
    if (savedChefImageUrl) {
      setChefImageUrl(savedChefImageUrl);
    }

    //유저 정보
    const fetchData = async () => {
      try {
        const response = await axios.get(`${url}/api/users/info`, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        });
        dispatch(setMemberid(response.data.user_id));
        dispatch(setMemberName(response.data.user_nickname));
        dispatch(setMemberTel(response.data.user_phone));
        dispatch(setMermberEmail(response.data.user_email));
        const isChef = response.data.user_role === "ROLE_CHEF";
        dispatch(setChefRole(isChef));
        setIsDisabled(response.data?.user_type !== 'app');
      } catch (error) {
        dispatch(editErrorType(error.response?.data.code));
        dispatch(openError());
      }
    };
    fetchData();
  }, [accessToken, dispatch]);

  //프로필수정
  const handleEditUser = async () => {
    // 새로운 비밀번호 입력에 문제가 있는지 확인
    if (
      (user.memberNewPw.length >= 1 && newPwChange) ||
      (user.memberNewPw.length >= 1 && checkPwChange)
    ) {
      dispatch(editErrorType("vaildNewPw"));
      dispatch(openError());
    } else {
      // requestData 객체 초기화
      let reqeustData = {};

      // 조건에 따라 필드를 requestData에 추가
      if (nickNameChange) {
        reqeustData.new_nickname = user.memberName;
      }
      if (emailChange) {
        reqeustData.new_email = user.memberEmail;
      }
      if (user.memberTel !== "") {
        reqeustData.new_phone = user.memberTel;
      }
      if (user.memberNewPw !== "") {
        reqeustData.new_pw = user.memberNewPw;
      } else if (user.memberPw !== "") {
        reqeustData.user_password = user.memberPw;
      }
      if (imageChange) {
        // 이미지 변경 시
        const { profileOName, profileSName } = await handleUploadImage(
          imageFile
        );
        reqeustData.user_file_oname = profileOName;
        reqeustData.user_file_sname = profileSName;
      }

      console.log(reqeustData);

      try {
        const response = await axios.patch(
          `${url}/api/users/info`,
          reqeustData,
          {
            headers: {
              Authorization: `Bearer ${accessToken}`,
            },
          }
        );

        // 리덕스에 있는 닉네임도 변경
        if (nickNameChange) {
          dispatch({ type: "nickname", payload: reqeustData.new_nickname });
        }

        console.log(response);
        dispatch(editErrorType("suEdit"));
        dispatch(openError());
        setButDisable(true);

        // 비밀번호 입력란 비우기
        setTimeout(() => {
          window.location.reload();
        }, 1000); // 1초 후 새로고침
      } catch (error) {
        console.log(error);
        dispatch(editErrorType(error.response?.data.code));
        dispatch(openError());
      }
    }
  };

  const handleCheckName = async (name) => {
    try {
      const response = await axios.post(`${url}/api/auth/checkNickname`, {
        user_nickname: name,
      });
      setNickNameChange(true);
      dispatch(setMemberName(name));
      dispatch(editErrorType("SUNAME"));
      dispatch(openError());
      setButDisable(false); // 추가된 코드: 닉네임 중복 확인 후 변경 버튼 활성화
    } catch (error) {
      console.log(error);
      dispatch(editErrorType(error.response.data.code));
      dispatch(openError());
      dispatch(ShowDuplication("name"));
    }
  };

  //전화번호 수정
  const handleTelChange = (e) => {
    const newTel = e.target.value;
    dispatch(setMemberTel(newTel));
    console.log("3");
    handleBut("newTel", false); // 전화번호를 바꾸면 변경 버튼을 활성화
    console.log("1");
    setButDisable(false); // 추가된 코드: 전화번호 변경 시 버튼 활성화
    console.log("2");
  };

  const hanldeSendEmail = async (mail) => {
    if (user.memberId === "") {
      dispatch(editErrorType("idError"));
      dispatch(openError());
    } else {
      try {
        const response = await axios.post(`${url}/api/auth/sendEmail`, {
          user_email: mail,
          user_id: user.memberId,
        });
        dispatch(editErrorType("SUEMAIL"));
        dispatch(openError());

        dispatch(openEmailCheck());
        dispatch(setMermberEmail(mail));
        setButDisable(false);
      } catch (error) {
        dispatch(editErrorType(error.response.data.code));
        dispatch(openError());
      }
    }
  };
  const handleCertifiedEmail = async () => {
    try {
      const response = await axios.post(
        `${url}/api/auth/checkEmail`,
        {
          user_email: user.memberEmail,
          certification_num: emailCode,
        },
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );
      setEmailChange(true);
      dispatch(editErrorType("SUCertification"));
      dispatch(openError());
      dispatch(closeEmailCheck());
      setButDisable(false); // 추가된 코드: 이메일 인증 후 변경 버튼 활성화
    } catch (error) {
      dispatch(editErrorType(error.response.data.code));
      dispatch(openError());
    }
  };

  const handleBut = (type, bool) => {
    switch (type) {
      case "new_nickname":
        setButDisable(false);
        break;
      case "newTel":
        setButDisable(false);
        break;
      case "email":
        setButDisable(false);
        break;
      case "pw":
        setButDisable(bool);
        break;
      case "newPw":
        setNewPwChange(bool);
        break;
      default:
        setCheckPwChange(bool);
        break;
    }
  };

  return (
    <Grid container direction="row">
      <Grid item xs={3}>
        <Grid container direction="column" ml="1.5em" mt="2em">
          {isChef && ( // ROLE_CHEF일 때만 쉐프 모자 이미지 표시
            <Grid item>
              <img
                src="/images/쉐프모자.png"
                alt="쉐프 모자"
                style={{
                  width: "100px",
                  height: "100px",
                  marginLeft: "-17px",
                  marginBottom: "-20px",
                }}
              />
            </Grid>
          )}
          <Grid item ml="-1em">
            <div className="ProfileImage">
              {imageUrl ? (
                <img
                  src={imageUrl}
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
                id="file-input"
                style={{ display: "none" }}
                type="file"
                name="imageFile"
                onChange={handleChangeImage}
              />
              사진 변경
            </ButtonContainStyle>
          </Grid>
          {!isChef && (
            <>
              <Grid item mt="3em" ml="-1em">
                <div className="ChefProfileImage">
                  <Tooltip
                    title="아이콘을 클릭해 파일을 첨부해주세요."
                    followCursor
                  >
                    <label htmlFor="chef-file-input">
                      {chefImageUrl ? (
                        <img
                          src={chefImageUrl}
                          alt="Chef Profile"
                          style={{ width: "100px", height: "100px" }}
                        />
                      ) : (
                        <PersonIcon
                          sx={{
                            color: "black",
                            width: "100px",
                            height: "100px",
                          }}
                        />
                      )}
                      <input
                        id="chef-file-input"
                        style={{ display: "none" }}
                        type="file"
                        name="chefImageFile"
                        onChange={handleUploadImageChef}
                      />
                    </label>
                  </Tooltip>
                </div>
              </Grid>
              <Grid item mt="0.5em" ml="-0.5em">
                <ButtonContainStyle
                  component="label"
                  width="5vw"
                  sx={{ height: "1vw" }}
                  onClick={() =>
                    handleApplyChef(
                      chefDetails.chef_oname,
                      chefDetails.chef_sname
                    )
                  }
                >
                  쉐프 신청
                </ButtonContainStyle>
              </Grid>
            </>
          )}
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
                  helper_text="특수문자를 제외한 2~10자로 작성해 주세요."
                  vaild="name"
                  handleCheck={(name) => {
                    handleCheckName(name);
                    handleBut("new_nickname", false);
                  }}
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
                  handleChange={handleTelChange}
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
          <Grid item ml="15em" mb="2em" mt="2.5em">
            <div className="change">
              <ButtonContain
                text="변경"
                size="medium"
                disable={butDisable}
                handleClick={handleEditUser}
              />
            </div>
          </Grid>
        </Grid>
      </Grid>
      <Grid item xs={4.5}>
        <Grid container direction="column" ml="-10em" rowGap={-5}>
          <Grid item height="7em">
            <FormSignup
              size="joy"
              handleBut={handleBut}
              title="비밀번호"
              helper_text="영문,숫자,특수문자 포함 8~15자로 작성해 주세요."
              place_text="*****"
              pass_exis={true}
              disable={isDisabled}
              vaild="pw"
            />
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
              disable={isDisabled}
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
              disable={isDisabled}
              handleBut={handleBut}
            />
          </Grid>
          <Grid item>
            <div className="withdrawl">
              <Withdrawl hanldeWithDrawlUser={hanldeWithDrawlUser} />
            </div>
          </Grid>
        </Grid>
      </Grid>
      <Modal open={CheckEmailOpen} onClose={handleCloseEmailCheck}>
        <Box
          mt="15vw"
          mx="37.5vw"
          width="25vw"
          height="15vw"
          backgroundColor="main.or"
          borderRadius={1}
          border={`3px solid ${theme.palette.main.br}`}
        >
          <Grid container mt="3.5vw">
            <Grid item xs={6} textAlign="center" pt="0.7vw">
              <Typography>인증코드</Typography>
            </Grid>
            <Grid item xs={6} textAlign="left">
              <TextField
                onChange={handleChangeEmailCode}
                variant="filled"
                size="small"
                sx={{ width: "10vw" }}
              />
            </Grid>
            <Grid item xs textAlign="center" mt="4vw">
              <ButtonOutlined
                text="전송"
                size="verySmall"
                handleClick={handleCertifiedEmail}
              />
            </Grid>
          </Grid>
        </Box>
      </Modal>
      <ErrorConfirm error={useSelector((state) => state.errorType)} />
    </Grid>
  );
};

export default Profile;
