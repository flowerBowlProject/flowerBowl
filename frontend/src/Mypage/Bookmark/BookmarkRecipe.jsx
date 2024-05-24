import React, { useEffect, useState } from "react";
import RecipeReviewCard from "../../Component/CardComp";
import Bookmark from "../../Component/Bookmark";
import ButtonContain from "../../Component/ButtonContain";
import ButtonOutlined from "../../Component/ButtonOutlined";
import "./BookmarkRecipe.css";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import { url } from "../../url";
import ErrorConfirm from "../../Hook/ErrorConfirm";
import { useDispatch, useSelector } from "react-redux";
import { editErrorType, openError } from "../../persistStore";

const BookmarkRecipe = () => {
  const navigator = useNavigate();
  const [listData, setListData] = useState([]);
  const accessToken = useSelector((state) => state.accessToken);
  const dispatch = useDispatch();
  const errorType = useSelector((state) => state.errorType);
  const [slice, setSlice] = useState(8);
  const handleClickMoreDetail = () => {
    if (listData.length > slice) setSlice(slice + 8);
  };
  //액세스토큰 확인
  // console.log("Access Token:", accessToken);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`${url}/api/mypage/recipe/likes`, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        });
        const updatedData = response.data.likeRecipes.map((recipe) => ({
          ...recipe,
          recipeLikeStatus: true,
        }));
        setListData(updatedData);
      } catch (error) {
        dispatch(editErrorType(error.response.data.code));
        dispatch(openError());
        setListData([]);
      }
    };
    fetchData();
  }, [accessToken]);

  //북마크 해제
  const byeBookmark = async (recipeNo) => {
    console.log(listData);
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
      if (response.status === 200 || 201) {
        const updatedData = listData.filter(
          (recipe) => recipe.recipe_no !== recipeNo
        );
        setListData(updatedData);
      }
    } catch (error) {
      dispatch(editErrorType(error.response.data.code));
      dispatch(openError());
    }
  };

  // 상세페이지 이동
  const clickDetail = (e, recipeNo) => {
    navigator(`/recipeDetail/${recipeNo}`);
  };

  return (
    <>
      <ErrorConfirm error={errorType} />
      <div className="division-line"></div>

      <div className="bookmark-content">
        {listData.length > 0 ? (
          [...listData.slice(0, slice)].map((data, index) => (
            <div key={index} style={{ position: "relative" }}>
              <Bookmark
                check={data.recipeLikeStatus}
                sx={{ cursor: "pointer" }}
                onClick={() => byeBookmark(data.recipe_no)}
              />
              <RecipeReviewCard
                onClick={(e) => clickDetail(e, data.recipe_no)}
                title={data.recipe_title}
                like_count={data.recipe_like_cnt}
                comment_count={data.comment_cnt}
                sname={data.recipe_sname}
                date={data.recipe_date}
              />
            </div>
          ))
        ) : (
          <p>아직 북마크한 레시피가 없습니다.</p>
        )}
      </div>

      <div className="division-line"></div>

      <div className="add">
        <ButtonContain
          size="large"
          text="더보기"
          handleClick={handleClickMoreDetail}
        />
      </div>
    </>
  );
};

export default BookmarkRecipe;
