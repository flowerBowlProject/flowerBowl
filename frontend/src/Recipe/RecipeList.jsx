import React, { useEffect, useState } from "react";
import "./ViewListStyle.css";
import Button from "@mui/material/Button";
import RecipeReviewCard from "../Component/CardComp";
import Bookmark from "../Component/Bookmark";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import { url } from "../url";
import ButtonOutlinedStyle from "../Component/ButtonOutlinedStyle";
import { useSelector } from "react-redux";
import ButtonContainStyle from "../Component/ButtonContainStyle";

const ViewList = () => {
  const [listData, setListData] = useState([]);
  const navigator = useNavigate();
  const accessToken = useSelector((state) => state.accessToken);
  const location = useLocation();
  const keyword = (location.state && location.state.keyword) || '';

  useEffect(() => {
    if (keyword !== '') {
      axios.get(`${url}/api/search/recipes?keyword=${keyword}&page=1&size=10`)
        .then(res => {
          setListData(res.data.recipes);
          //setPageInfo(res.data.pageInfo);
          console.log(res);
        })
        .catch(err => {
          console.log(err);
        })
    } else {
      { /* 로그인 여부에 따른 정보 호출 */ }
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
              Authorization: `Bearer ${accessToken}`,
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
    }
  }, [keyword, accessToken]);

  {
    /* 북마크 동작 */
  }
  const clickBookmark = (e, index, recipeNo) => {
    if (accessToken === "") {
      {
        /* 로그인이 되어있지 않은 경우 - 로그인 후 이용 가능 alrt - 변수명 수정 후 확인 필요*/
      }
      console.log("로그인 후 이용 가능");
    } else {
      axios
        .post(`${url}/api/recipes/like/${recipeNo}`, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        })
        .then((res) => {
          const bookmark = res.data.recipeLikeNo == undefined ? false : true;
          console.log(bookmark);
          setListData((listData) => {
            const updatedList = { ...listData[index], recipeLikeStatus: bookmark };
            const newListData = [...listData.slice(0, index), updatedList, ...listData.slice(index + 1)];
            return newListData;
          })
          console.log(listData)
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
                key={index}
                check={data.recipeLikeStatus}
                sx={{ cursor: "point" }}
                onClick={(e) => clickBookmark(e, index, data.recipe_no)}
              />
              <RecipeReviewCard
                onClick={(e) => clickDetail(e, data.recipe_no)}
                title={data.recipe_title}
                like_count={data.recipe_like_count}
                comment_count={data.recipe_comment_count}
                sname={data.recipe_sname}
                date={data.recipe_date}
                type={true}
              />
            </div>
          ))}
      </div>
      <ButtonContainStyle className="moreButton"> 더보기 </ButtonContainStyle>
    </div>
  );
};

export default ViewList;
