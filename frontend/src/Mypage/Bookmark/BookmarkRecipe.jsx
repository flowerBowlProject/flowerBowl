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
    const fetchData = async () => {
      try {
        const response = await axios.get(`${url}/api/mypage/recipe/likes`, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        });
        setListData(response.data.likeRecipes);
        //코드 확인
        // console.log(response.data.likeRecipes);
      } catch (error) {
        console.error("Error fetching data:", error);
        setListData([]);
      }
    };
    fetchData();
  }, [accessToken]);

  //북마크 해제 하는 걸로 다시 짜자!!!
  const clickBookmark = async (e, recipeNo) => {
    try {
      const response = await axios.post(
        `${url}/api/recipes/like/${recipeNo}`,
        {},
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );
      const bookmark = response.data.recipeLikeNo === undefined ? true : false;
      console.log("Bookmark state:", bookmark);
    } catch (error) {
      console.error("Error in bookmark toggle:", error);
    }
  };

  // 상세페이지 이동
  const clickDetail = (e, recipeNo) => {
    navigator(`/recipeDetail/${recipeNo}`);
  };

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

      <div className="division-line"></div>

      <div className="bookmark-content">
        {listData.length > 0 ? (
          listData.map((data, index) => (
            <div key={index} style={{ position: "relative" }}>
              <Bookmark
                check={data.recipeLikeStatus}
                sx={{ cursor: "pointer" }}
                onClick={(e) => clickBookmark(e, data.recipeNo)}
              />
              <RecipeReviewCard
                onClick={(e) => clickDetail(e, data.recipeNo)}
                title={data.recipe_title}
                like_count={data.recipe_like_cnt}
                comment_count={data.comment_cnt}
                sname={data.recipe_sname}
                date={data.recipe_date}
              />
            </div>
          ))
        ) : (
          <p>No recipes liked yet.</p>
        )}
      </div>

      <div className="division-line"></div>

      <div className="add">
        <ButtonContain size="large" text="더보기" />
      </div>
    </>
  );
};

export default BookmarkRecipe;
