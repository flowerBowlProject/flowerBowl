import React, { useState } from "react";
import Rating from "@mui/material/Rating";
import "./RegisterReview.css";
import ButtonContain from "../../Component/ButtonContain";
import ButtonOutlined from "../../Component/ButtonOutlined";

const RegisterReview = () => {
  const [value, setValue] = useState(0);
  const [expanded, setExpanded] = useState(false); // 클릭한 클래스 데이터 추가
  const [selectedTitle, setSelectedTitle] = useState("");

  //  클래스 데이터
  const classList = [
    { title: "겉바속촉 타르트 쿠킹 클래스" },
    { title: "화이트데이 루피 초콜릿 만들기" },
    { title: "스위스 쉐프에서 스위스 초콜릿 만들기" },
  ];

  // 클래스 선택 핸들러
  const handleClassSelect = (title) => {
    setSelectedTitle(title); // 선택된 타이틀 업데이트
    setExpanded(false); // 선택 후 목록 접기
  };

  const closeModal = () => {
    setExpanded(false);
  };

  return (
    <div className="all">
      {/* 버튼들 */}
      <section className="buttons">
        <span className="write-review">
          <ButtonOutlined size="medium" text="결제 내역" />
        </span>
        <ButtonOutlined size="medium" text="리뷰 조회" />
        <ButtonContain size="medium" text="리뷰 작성" />
      </section>
      <div className="division-line-br"></div>

      {/* 클래스 목록 */}
      <section className="teachingclass">
        <div className="division-line-or"></div>
        <div className="text-title" onClick={() => setExpanded(!expanded)}>
          {selectedTitle || "클래스 / 레시피 선택"}&emsp;
          <span>{expanded ? "▲" : "▼"}</span>
        </div>
        {/* 선택 가능한 클래스 목록 */}
        {expanded && (
          <div className="class-list-modal">
            {classList.map((cls, index) => (
              <div
                key={index}
                className="class-option"
                onClick={() => handleClassSelect(cls.title)}
              >
                {cls.title}
              </div>
            ))}
          </div>
        )}

        <div className="division-line-or"></div>
        <div className="text-content">
          클래스 / 레시피는 어떠셨나요?
          <br />
          솔직한 리뷰를 남겨주세요.
        </div>
      </section>

      {/* 평점기능 */}
      <Rating
        name="size-large"
        value={value}
        size="large"
        onChange={(event, newValue) => {
          setValue(newValue);
        }}
        sx={{
          fontSize: "6em",
          color: "main.or",
          display: "flex",
          justifyContent: "center",
          "& .MuiRating-iconEmpty": {
            color: "main.gr",
          },
        }}
      />
      <section />
      <section className="input-content">
        <div className="division-line"></div>
        {/* 인풋창 */}
        <textarea
          className="styled-input"
          placeholder="피드백을 남겨주세요."
        ></textarea>
      </section>
      {/* 하단부 */}
      <div className="division-line-br"></div>
      <div className="bottom-buttons">
        <ButtonOutlined size="medium" text="등록" />
        <ButtonContain size="medium" text="취소" />
      </div>
    </div>
  );
};

export default RegisterReview;
