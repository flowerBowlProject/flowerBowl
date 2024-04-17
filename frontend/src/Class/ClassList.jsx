import React, { useEffect, useState } from "react";
import '../Recipe/ViewListStyle.css';
import Button from '@mui/material/Button';
import RecipeReviewCard from "../Component/CardComp";
import Bookmark from "../Component/Bookmark";
import { useNavigate } from 'react-router-dom';
import axios from "axios";
import { useSelector } from "react-redux";
import { url } from "../url";

const ViewList = () => {
    const [listData, setListData] = useState([]);
    const navigator = useNavigate();
    const accessToken = useSelector(state => state.persistedReducer.accessToken);

    useEffect(()=>{
            {/* 로그인에 따른 조회 */}
            if(accessToken == ''){
                 axios.get(`${url}/api/guest/lessons?page=1&size=10`)
                .then(res=>{
                    console.log(res);
                    setListData(res.data.lessons);
                })
                .catch(err=>{
                    console.log(err);
                })
            }else{
                axios.get(`${url}/api/user/lessons?page=1&size=10`,{
                    headers:{
                        Authorization : accessToken
                    }
                })
                .then(res=>{
                    console.log(res);
                    setListData(res.data.lessons);
                })
                .catch(err=>{
                    console.log(err);
                })
            }
           
    },[])

    const clickBookmark = (e, lesson_no) =>{
        console.log("북마크 클릭");
    }

    const clickDetail = (e, lesson_no) =>{
            navigator(`/classDetail/${lesson_no}`);
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
                    <Button className="view-register" variant="outlined"> 클래스 등록 </Button>
                </div>
            </div>
            <div className="viewList">
                {/* 리스트 출력*/}
                {listData.length !== 0 && listData.map((data, index) =>
                    <div style={{position:'relative'}}>
                        <Bookmark sx={{cursor:'point'}} onClick={(e)=>clickBookmark(e, data.lesson_no)}/>
                        <RecipeReviewCard key={index} onClick={(e)=> clickDetail(e, data.lesson_no)}
                            title={data.lesson_title} like_count={data.lesson_likes_num} comment_count={0} sname={data.lesson_sname} date={data.lesson_date}/>
                    </div>)}
            </div>
            <Button className="moreButton"> 더보기 </Button>
        </div>
    );
}

export default ViewList;