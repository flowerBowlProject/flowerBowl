import ButtonOutLinedStyle from "./ButtonOutlinedStyle";
import { useState } from "react";
const ButtonOutlined = ({ text, size, handleClick, data }) => {
  const handleSize = (size) => {
    if (size === "verySmall") {
      return {
        width: "2vw",
        height: "1vw",
        size: "small",
      };
    } else if (size === "small") {
      return {
        width: "5vw",
        height: "1vw",
        size: "small",
      };
    } else if (size === "medium") {
      return {
        width: "5vw",
        height: "1.5vw",
      };
    } else if (size === "large") {
      return {
        width: "5vw",
        height: "2vw",
      };
    } else if (size === "doubleLarge") {
      return {
        width: "7vw",
        height: "2vw",
      };
    } else if (size === "veryLarge") {
      return {
        width: "15vw",
      };
    }
  };
  const sizeStyle = handleSize(size);
  return (
    <ButtonOutLinedStyle
      onClick={() => (data ? handleClick(data) : handleClick)}
      width={sizeStyle.width}
      variant="outlined"
      size={sizeStyle.size}
      sx={{ height: sizeStyle.height }}
    >
      {text}
    </ButtonOutLinedStyle>
  );
};
export default ButtonOutlined;
