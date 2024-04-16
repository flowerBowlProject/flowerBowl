import React from "react";
import CardComp from "../../Component/CardComp";
import Bookmark from "../../Component/Bookmark";
import ButtonContain from "../../Component/ButtonContain";
import ButtonOutlined from "../../Component/ButtonOutlined";
import "./BookmarkRecipe.css";
import { Link } from "react-router-dom";

const BookmarkRecipe = () => {
  const sampleRecipeData = {
    date: "2020-01-01",
    comment_count: 123,
    like_count: 456,
    title: "시원한 소고기 무국",
  };

  const bookmarkComponents = Array.from({ length: 8 }, (_, index) => (
    <div key={index} className="bookmark-wrapper">
      <Bookmark />
      <CardComp list={sampleRecipeData} />
    </div>
  ));

  return (
    <>
      <div className="bookmark-button">
        <Link to="/mypage/bookmarkRecipe">
          <ButtonContain size="medium" text="레시피" />
        </Link>
        <span className="classbutton">
          <Link to="/mypage/bookmarkClass">
            <ButtonOutlined
              size="medium"
              text="클래스"
              className="classbutton"
            />
          </Link>
        </span>
      </div>

      {/* 구분선 */}
      <div class="division-line"></div>

      <div className="bookmark-content">{bookmarkComponents}</div>

      <div class="division-line"></div>

      <div className="add">
        <ButtonContain size="large" text="더보기" />
      </div>
    </>
  );
};

export default BookmarkRecipe;
