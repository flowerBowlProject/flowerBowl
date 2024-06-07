import React, { useEffect, useState } from "react";
import "./ViewListStyle.css";
import Button from "@mui/material/Button";
import RecipeReviewCard from "../Component/CardComp";
import Bookmark from "../Component/Bookmark";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import { url } from "../url";
import ButtonOutlinedStyle from "../Component/ButtonOutlinedStyle";
import { useDispatch, useSelector } from "react-redux";
import ButtonContainStyle from "../Component/ButtonContainStyle";
import ErrorConfirm from "../Hook/ErrorConfirm";
import { editErrorType, openError } from "../persistStore";

const RecipeList = () => {
  const [listData, setListData] = useState([]);
  const navigator = useNavigate();
  const accessToken = useSelector((state) => state.accessToken);
  const location = useLocation();
  const params = new URLSearchParams(location.search);
  const keyword = params.get('keyword');
  const category = params.get('category');
  const [pageInfo, setPageInfo] = useState(1);
  const dispatch = useDispatch();
  const popular = params.get('popular');

  {/* 정렬 구현 */ }
  const [selectButton, setSelectButton] = useState(popular!='인기순' ? '최신순' : '인기순');
  const handleClick = (selectButton) => {
    let sorted;
    setSelectButton(selectButton);

    switch (selectButton) {
      case '최신순':
        sorted = [...listData].sort((a, b) => new Date(b.recipe_date) - new Date(a.recipe_date));
        setPageInfo(1);
        break;
      case "인기순":
        sorted = [...listData].sort((a, b) => b.recipe_like_cnt - a.recipe_like_cnt);
        setPageInfo(1);
        break;
      case "댓글순":
        sorted = [...listData].sort((a, b) => b.comment_cnt - a.comment_cnt);
        setPageInfo(1);
        break;
      default:
        sorted = listData; // 기본값은 변경하지 않음
    }
    setListData(sorted);
  }
  console.log(pageInfo);

  useEffect(() => {
    if (keyword !== null) {
      if (accessToken === '') {
        axios.get(`${url}/api/search/recipes?keyword=${keyword}&page=1&size=${pageInfo*8}`)
          .then(res => {
            setListData(res.data.recipes);
            console.log(res);
          })
          .catch(err => {
            console.log(err);
            dispatch(editErrorType(err.response.data.code));
            dispatch(openError());

          })
      } else {
        axios.get(`${url}/api/user/search/recipes?keyword=${keyword}&page=1&size=${pageInfo*8}`, {
          headers: {
            Authorization: `Bearer ${accessToken}`
          }
        })
          .then(res => {
            setListData(res.data.recipes);
            console.log(res);
          })
          .catch(err => {
            console.log(err);
            dispatch(editErrorType(err.response.data.code));
            dispatch(openError());
          })
      }
    }else if(category !== null){
      if (accessToken === '') {
        axios.get(`${url}/api/recipes/guest/list?category=${category}&page=1&size=${pageInfo*8}`)
          .then(res => {
            setListData(res.data.posts);
            console.log(res);
          })
          .catch(err => {
            console.log(err);
            dispatch(editErrorType(err.response.data.code));
            dispatch(openError());

          })
      } else {
        axios.get(`${url}/api/recipes/list?category=${category}&page=1&size=${pageInfo*8}`, {
          headers: {
            Authorization: `Bearer ${accessToken}`
          }
        })
          .then(res => {
            setListData(res.data.posts);
          })
          .catch(err => {
            console.log(err);
            dispatch(editErrorType(err.response.data.code));
            dispatch(openError());
          })
      }
    }else {
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
            dispatch(editErrorType(err.response.data.code));
            dispatch(openError());
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
            dispatch(editErrorType(err.response.data.code));
            dispatch(openError());
          });
      }
    }
  }, [keyword, accessToken, pageInfo]);

  {
    /* 북마크 동작 */
  }
  const clickBookmark = (e, index, recipe_no) => {
    console.log(recipe_no);
    if (accessToken === "") {
      {
        /* 로그인이 되어있지 않은 경우 - 로그인 후 이용 가능 alrt - 변수명 수정 후 확인 필요*/
      }
      console.log("로그인 후 이용 가능");
      dispatch(editErrorType('NT'));
      dispatch(openError());
    } else {
      axios
        .post(`${url}/api/recipes/like/${recipe_no}`, null, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          }
        })
        .then((res) => {
          const check = res.data.code == 'SU' ? false : true;
          console.log(res);
          setListData((listData) => {
            const updatedList = { ...listData[index], recipe_like_status: check };
            const newListData = [...listData.slice(0, index), updatedList, ...listData.slice(index + 1)];
            return newListData;
          })
        })
        .catch((err) => {
          {
            /* 토큰 만료에 대한 처리 진행 */
          }
          dispatch(editErrorType(err.response.data.code));
          dispatch(openError());
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
    if(accessToken === ''){
      dispatch(editErrorType('NE'));
      dispatch(openError());
  }else{
      navigator("/registerRecipe");
  }
  };

  {/* 더보기 클릭 */ }
  const clickMore = () => {
    setPageInfo(pageInfo + 1);
  }

  return (
    <div className="viewList-Box">
      <ErrorConfirm error={useSelector(state => state.errorType)} />

      <div className="sortList">
        <div className="sortList-left">
          <Button sx={{ color: selectButton === '최신순' ? "main.br" : "main.or" }} onClick={() => handleClick('최신순')}> 최신순 </Button>
          <Button sx={{ color: selectButton === '인기순' ? "main.br" : "main.or" }} onClick={() => handleClick('인기순')}> 인기순 </Button>
          <Button sx={{ color: selectButton === '댓글순' ? "main.br" : "main.or" }} onClick={() => handleClick('댓글순')}> 댓글순 </Button>
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
          listData.slice(0, pageInfo * 8).map((data, index) => (
            <div style={{ position: "relative" }}>
              <Bookmark
                key={index}
                check={data.recipe_like_status}
                sx={{ cursor: "point" }}
                onClick={(e) => clickBookmark(e, index, data.recipe_no)}
              />
              <RecipeReviewCard
                onClick={(e) => clickDetail(e, data.recipe_no)}
                title={data.recipe_title}
                like_count={data.recipe_like_cnt}
                comment_count={data.comment_cnt}
                sname={data.recipe_sname}
                date={data.recipe_date}
                type={true}
              />
            </div>
          ))}
      </div>
      <ButtonContainStyle className="moreButton" onClick={clickMore}> 더보기 </ButtonContainStyle>
    </div>
  );
};
export default RecipeList;