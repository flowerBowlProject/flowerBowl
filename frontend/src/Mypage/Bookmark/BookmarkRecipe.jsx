import React from "react";
import CardComp from "../../Component/CardComp";
import Bookmark from "../../Component/Bookmark";
import Button_contain_style from "../../Component/Button_contain_style";
import Button_outlined_style from "../../Component/Button_outlined_style";
import "./BookmarkRecipe.css";

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
        <Button_contain_style>레시피</Button_contain_style>
        <Button_outlined_style>클래스</Button_outlined_style>
      </div>

      {/* 구분선 */}
      <div class="division-line"></div>

      <div className="bookmark-content">{bookmarkComponents}</div>

      <div class="division-line"></div>

      <div className="add">
        <Button_contain_style>더보기</Button_contain_style>
      </div>
    </>
  );
};

export default BookmarkRecipe;
