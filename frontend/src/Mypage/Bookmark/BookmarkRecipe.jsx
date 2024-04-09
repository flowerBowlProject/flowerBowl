import React from "react";
import CardComp from "../../Component/CardComp";
import Bookmark from "../../Component/Bookmark";
import Inputbutton from "../../Component/Input/Inputbutton";
import "./BookmarkRecipe.css";
import MyPageLayout from "../MyPageLayout";


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
    <MyPageLayout>
      <div className="bookmark-button">
        <Inputbutton text="레시피" i={true} w="medium" />
        <Inputbutton text="클래스" i={false} w="medium" />
      </div>

      {/* 구분선 */}
      <div class="division-line"></div>

      <div className="bookmark-content">
        {bookmarkComponents}
      </div>

      <div class="division-line"></div>

      <div className= "add">
      <Inputbutton text="더보기" i={true} w="medium" />
      </div>
      </MyPageLayout>
  );
};

export default BookmarkRecipe;
