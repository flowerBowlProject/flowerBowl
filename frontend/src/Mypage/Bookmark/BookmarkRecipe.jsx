import React, { useEffect, useState } from "react";
import CardComp from "../../Component/CardComp";
import Bookmark from "../../Component/Bookmark";
import ButtonContain from "../../Component/ButtonContain";
import ButtonOutlined from "../../Component/ButtonOutlined";
import "./BookmarkRecipe.css";
import { Link } from "react-router-dom";
import axios from "axios";
import { url } from "../../url";

const BookmarkRecipe = () => {
  const [listData, setListData] = useState([]);

  useEffect(() => {
    //로그인한 상태
    axios
      .get(`${url}/api/mypage/recipe/likes`)
      .then((res) => {
        console.log(res);
        setListData(res.data.likeRecipes);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  // const sampleRecipeData = {
  //   date: "2020-01-01",
  //   comment_count: 123,
  //   like_count: 456,
  //   title: "시원한 소고기 무국",
  // };

  const bookmarkComponents = Array.from({ length: 8 }, (_, index) => (
    <div key={index} className="bookmark-wrapper">
      <Bookmark check={true} />
      <CardComp list={listData} />
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
