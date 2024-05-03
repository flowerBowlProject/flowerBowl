import React from "react";
import FileDropArea from "./FileDropArea";
import TextInput from "./TextInput";
import ButtonContain from "../../Component/ButtonContain";
import "./RegisterBanner.css";

const RegisterBanner = () => {
  return (
    <>
      <div className="division-line"></div>
      <div className="dragdrop">
        <FileDropArea />
      </div>
      <div className="inputbanner">
        <TextInput />
      </div>

      <div className="fileadd">
        <ButtonContain size="doubleLarge" text="등록" />
      </div>
      <div className="division-line"></div>
    </>
  );
};

export default RegisterBanner;
