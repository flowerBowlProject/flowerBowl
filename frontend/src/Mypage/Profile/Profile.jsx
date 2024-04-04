import React from "react";
import Input from "../../Component/Input/Input";
import Inputbutton from "../../Component/Input/Inputbutton";
import PersonIcon from "@mui/icons-material/Person";
import Tooltip from "@mui/material/Tooltip";
import "./ProfileImage.css";
import Withdrawl from "./Withdraw";
import Sidebar from "../Sidebar";
import "./Profile.css";

const Profile = () => {
  return (
    <div className="body">
      {/* 사이드바 */}
      <div className="sidebar">
        <Sidebar />
      </div>

      {/* 프로필 */}
      <div className="profile">
        <div className="userprofile">
          <div className="ProfileImage">
            <PersonIcon
              sx={{
                color: "black",
                width: "100px",
                height: "100px",
                position: "absolute",
              }}
            />
          </div>
          <div className="changephoto">
            <Inputbutton text="사진 변경" i={true} w="medium" />
          </div>
        </div>

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

        <Inputbutton text="쉐프 신청" i={true} w="medium" />
      </div>

      {/* 폼형태 */}
      <div className="id">
        아이디
        <div className="id-input">
          <Input placeholder="jean1030" />
        </div>
        <div className="nickname-button">
          <div className="nickname">
            <div className="duplicate-check">
              닉네임
              <span className="duplicate">
                <Inputbutton text="중복확인" i={true} w="large" />
              </span>
            </div>
            <Input placeholder="치킨나라피자공주" />
          </div>
        </div>
        휴대폰번호
        <div className="phone">
          <Input placeholder="010-8495-9515" />
        </div>
        <div className="duplicate-check">
          이메일
          <span className="email">
            <Inputbutton text="이메일 인증" i={true} w="large" />
          </span>
        </div>
        <Input placeholder="jean1030@naver.com" />
      </div>

      <div className="pw">
        비밀번호
        <div className="password">
          <Input placeholder="*******" />
        </div>
        새 비밀번호 변경
        <div className="changepassword">
          <Input placeholder="*******" />
        </div>
        새 비밀번호 확인
        <div className="checkpassword">
          <Input placeholder="*******" />
        </div>
      </div>

      {/* 변경버튼 */}

      <div className="change">
        <Inputbutton text="변경" i={true} w="medium" />
      </div>

      {/* 회원 탈퇴 버튼 */}
      <div className="withdrawl">
        <Withdrawl />
      </div>
    </div>
  );
};

export default Profile;
