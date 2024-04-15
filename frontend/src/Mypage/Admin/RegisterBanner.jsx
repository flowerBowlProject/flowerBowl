import React from "react";
import FileDropArea from "./FileDropArea";
import TextInput from "./TextInput";
import Button_contain_style from "../../Component/Button_contain_style";
import "./RegisterBanner.css";

const RegisterBanner = () => {
  return (
    <>
      <div class="division-line"></div>
      <FileDropArea />
      <TextInput />
      <div className="fileadd">
        <Button_contain_style>등록</Button_contain_style>
      </div>
      <div class="division-line"></div>
    </>
  );
};

export default RegisterBanner;
