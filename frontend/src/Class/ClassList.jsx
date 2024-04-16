import React, { useEffect, useState } from "react";
import '../Recipe/ViewListStyle.css';
import Button from '@mui/material/Button';
import RecipeReviewCard from "../Component/CardComp";
import Bookmark from "../Component/Bookmark";
import { useNavigate } from 'react-router-dom';
import axios from "axios";
import { url } from "../url";

const ViewList = () => {
    const [listData, setListData] = useState([]);
    const navigator = useNavigate();

    useEffect(()=>{
            {/* 로그인 구현 이후 if문 추가 필요 - 현재는 비로그인 기준 */}
            axios.get(`${url}/api/recipes/guest`)
            .then(res=>{
                console.log(res);
                setListData(res.data.posts);
            })
            .catch(err=>{
                console.log(err);
            })
    },[])

    const clickBookmark = (e, recipeNo) =>{
        console.log("북마크 클릭");
    }

    const clickDetail = (e, recipeNo) =>{
            navigator(`/recipeDetail/${recipeNo}`);
    }

    return(
        <div className="viewList-Box">
            <div className="sortList">
                <div className="sortList-left">
                    <Button sx={{color: 'main.or'}}> 최신순 </Button>
                    <Button sx={{color: 'main.or'}}> 인기순 </Button>
                    <Button sx={{color: 'main.or'}}> 댓글순 </Button>
                </div>
                <div className="sortList-right">
                    <Button className="view-register" variant="outlined"> 레시피 등록 </Button>
                </div>
            </div>
            <div className="viewList">
                {/* 리스트 출력*/}
                {listData.length !== 0 && listData.map((data, index) =>
                    <div style={{position:'relative'}}>
                        <Bookmark sx={{cursor:'point'}} onClick={(e)=>clickBookmark(e, data.recipeNo)}/>
                        <RecipeReviewCard key={index} onClick={(e)=> clickDetail(e, data.recipeNo)}
                            title={data.recipeTitle} like_count={data.recipeLikeCount} comment_count={data.recipeCommentCount} sname={data.recipeSname} date={data.recipeDate}/>
                    </div>)}
            </div>
            <Button className="moreButton"> 더보기 </Button>
        </div>
    );
}

export default ViewList;