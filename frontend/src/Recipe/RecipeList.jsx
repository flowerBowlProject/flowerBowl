import React, { useEffect, useState } from "react";
import "./ViewListStyle.css";
import Button from "@mui/material/Button";
import RecipeReviewCard from "../Component/CardComp";
import Bookmark from "../Component/Bookmark";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import { url } from "../url";
import ButtonOutlinedStyle from "../Component/ButtonOutlinedStyle";
import { useSelector } from "react-redux";
import ButtonContainStyle from "../Component/ButtonContainStyle";

const ViewList = () => {
  const [listData, setListData] = useState([]);
  const navigator = useNavigate();
  const accessToken = useSelector(
    (state) => state.persistedReducer.accessToken
  );

  useEffect(() => {
    {
      /* 로그인 여부에 따른 정보 호출 */
    }
    if (accessToken === "") {
      axios
        .get(`${url}/api/recipes/guest`)
        .then((res) => {
          console.log(res);
          setListData(res.data.posts);
        })
        .catch((err) => {
          console.log(err);
        });
    } else {
      axios
        .get(`${url}/api/recipes`, {
          headers: {
            Authorization: accessToken,
          },
        })
        .then((res) => {
          console.log(res);
          setListData(res.data.posts);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }, []);

  {
    /* 북마크 동작 */
  }
  const clickBookmark = (e, recipeNo) => {
    if (accessToken === "") {
      {
        /* 로그인이 되어있지 않은 경우 - 로그인 후 이용 가능 alrt */
      }
      console.log("로그인 후 이용 가능");
    } else {
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
    }
  };

  {
    /* 상세페이지 이동 */
  }
  const clickDetail = (e, recipeNo) => {
    navigator(`/recipeDetail/${recipeNo}`);
  };

  {
    /* 등록 페이지 이동 */
  }
  const clickRegister = () => {
    navigator("/registerRecipe");
  };

  return (
    <div className="viewList-Box">
      <div className="sortList">
        <div className="sortList-left">
          <Button sx={{ color: "main.or" }}> 최신순 </Button>
          <Button sx={{ color: "main.or" }}> 인기순 </Button>
          <Button sx={{ color: "main.or" }}> 댓글순 </Button>
        </div>
        <div className="sortList-right">
          <ButtonOutlinedStyle
            className="view-register"
            variant="outlined"
            onClick={clickRegister}
          >
            {" "}
            레시피 등록{" "}
          </ButtonOutlinedStyle>
        </div>
      </div>
      <div className="viewList">
        {/* 리스트 출력*/}
        {listData.length !== 0 &&
          listData.map((data, index) => (
            <div style={{ position: "relative" }}>
              <Bookmark
                check={data.recipeLikeStatus}
                sx={{ cursor: "point" }}
                onClick={(e) => clickBookmark(e, data.recipeNo)}
              />
              <RecipeReviewCard
                key={index}
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
      <ButtonContainStyle className="moreButton"> 더보기 </ButtonContainStyle>
    </div>
  );
};

export default ViewList;
