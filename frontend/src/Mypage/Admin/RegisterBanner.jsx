import React from "react";
import FileDropArea from "./FileDropArea";
import TextInput from "./TextInput";
import Inputbutton from "../../Component/Input/Inputbutton";
import "./RegisterBanner.css";

const RegisterBanner = () => {
  return (
    <>
      <div class="division-line"></div>
      <FileDropArea />
      <TextInput />
      <div className="fileadd">
        <Inputbutton text="등록" i={true} w="large" />
      </div>
      <div class="division-line"></div>
    </>
  );
};

export default RegisterBanner;
