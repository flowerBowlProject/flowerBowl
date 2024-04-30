import React from "react";
import "./TextInput.css";

function TextInput() {
  return (
    <div className="text-input-container">
      <textarea className="text-input" placeholder="내용을 입력하세요" />
    </div>
  );
}

export default TextInput;
