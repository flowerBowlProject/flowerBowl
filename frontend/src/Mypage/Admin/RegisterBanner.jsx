import React from "react";
import FileDropArea from "./FileDropArea";
import TextInput from "./TextInput";
import ButtonContain from "../../Component/ButtonContain";
import "./RegisterBanner.css";

const RegisterBanner = () => {
  return (
    <>
      <div class="division-line"></div>
      <FileDropArea />
      <TextInput />
      <div className="fileadd">
        <ButtonContain size="medium" text="등록" />
      </div>
      <div class="division-line"></div>
    </>
  );
};

export default RegisterBanner;
