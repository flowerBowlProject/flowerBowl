import React, { useEffect, useState } from "react";
import RecipeReviewCard from "../../Component/CardComp";
import Bookmark from "../../Component/Bookmark";
import ButtonContain from "../../Component/ButtonContain";
import ButtonOutlined from "../../Component/ButtonOutlined";
import "./BookmarkRecipe.css";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import { url } from "../../url";
import { useSelector } from "react-redux";

const BookmarkRecipe = () => {
  const navigator = useNavigate();
  const [listData, setListData] = useState([]);
  const accessToken = useSelector(
    (state) => state.persistedReducer.accessToken
  );

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

  //북마크 동작
  const clickBookmark = (e, recipeNo) => {
    axios
      .post(`${url}/api/recipes/like/${recipeNo}`, {
        headers: {
          Authorization: accessToken,
        },
      })
      .then((res) => {
        const bookmark = res.data.recipeLikeNo == undefined ? true : false;
        console.log(bookmark);
        {
          /* recipe_like_no 여부에 따른 t/f 관리 진행 */
        }
      })
      .catch((err) => {
        {
          /* 토큰 만료에 대한 처리 진행 */
        }
      });
  };

  // 상세페이지 이동
  const clickDetail = (e, recipeNo) => {
    navigator(`/recipeDetail/${recipeNo}`);
  };

  // const sampleRecipeData = {
  //   date: "2020-01-01",
  //   comment_count: 123,
  //   like_count: 456,
  //   title: "시원한 소고기 무국",
  // };

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
      <div className="division-line"></div>

      <div className="bookmark-content">
        {/* 리스트 출력 */}
        {listData.length !== 0 &&
          listData.map((data, index) => (
            <div key={index} style={{ position: "relative" }}>
              <Bookmark
                check={data.recipeLikeStatus}
                sx={{ cursor: "pointer" }}
                onClick={(e) => clickBookmark(e, data.recipeNo)}
              />
              <RecipeReviewCard
                onClick={(e) => clickDetail(e, data.recipeNo)}
                title={data.recipeTitle}
                like_count={data.recipeLikeCount}
                comment_count={data.recipeCommentCount}
                sname={data.recipeSname}
                date={data.recipeDate}
              />
            </div>
          ))}
      </div>

      <div className="division-line"></div>

      <div className="add">
        <ButtonContain size="large" text="더보기" />
      </div>
    </>
  );
};

export default BookmarkRecipe;
