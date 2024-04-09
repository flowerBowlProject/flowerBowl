import React from "react";
import FileDropArea from "./FileDropArea";
import TextInput from "./TextInput";
import Inputbutton from "../../Component/Input/Inputbutton";
import "./RegisterBanner.css";
import MyPageAdminLayout from "../MyPageAdminLayout";

const RegisterBanner = () => {
  return (
    <MyPageAdminLayout>
      <div class="division-line"></div>
      <FileDropArea />
      <TextInput />
      <div className="fileadd">
        <Inputbutton text="등록" i={true} w="large" />
      </div>
      <div class="division-line"></div>
    </MyPageAdminLayout>
  );
};

export default RegisterBanner;
