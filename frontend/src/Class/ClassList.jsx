import React, { useEffect, useState } from "react";
import '../Recipe/ViewListStyle.css';
import Button from '@mui/material/Button';
import RecipeReviewCard from "../Component/CardComp";
import Bookmark from "../Component/Bookmark";
import { useLocation, useNavigate } from 'react-router-dom';
import axios from "axios";
import { useSelector } from "react-redux";
import { url } from "../url";
import ButtonOutlinedStyle from "../Component/ButtonOutlinedStyle";

const ViewList = () => {
    const [listData, setListData] = useState([]);
    const navigator = useNavigate();
    const accessToken = useSelector(state => state.accessToken);
    const location = useLocation();
    const keyword = (location.state && location.state.keyword) || '';

    useEffect(()=>{
        if(keyword !== ''){
            axios.get(`${url}api/search/lessons?keyword=${keyword}&page=1&size=10`)
            .then(res => {
                setListData(res.data.lesson);
                //setPageInfo(res.data.pageInfo);
                console.log(res);
            })
            .catch(err => {
                console.log(err);
            })
        }else{
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
                        Authorization : `Bearer ${accessToken}`
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
        } 
    },[keyword, accessToken])

    const clickBookmark = (e, lesson_no, lesson_likes_status) =>{
        console.log("북마크 클릭");
        if(accessToken === ''){
            console.log('로그인 후 이용해 주세요.');
        }else{
            if(lesson_likes_status){
                axios.delete(`${url}/api/user/lessons/like/${lesson_no}`,{
                    headers:{
                        Authorization : `Bearer ${accessToken}`
                    }
                })
                .then(res=>{
                    console.log(res);
                })
                .catch(err=>{
                    console.log(err);
                })
            }else{
                axios.post(`${url}/api/user/lessons/like`,{
                    headers:{
                        Authorization : `Bearer ${accessToken}`
                    }
                },{
                    "lesson_no" : lesson_no
                })
                .then(res=>{
                    console.log(res);
                })
                .catch(err=>{
                    console.log(err);
                })
            }
        }
    }

    const clickRegister = () =>{
        navigator(`/registerClass`);
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
                    <ButtonOutlinedStyle className="view-register" variant="outlined" onClick={clickRegister}> 클래스 등록 </ButtonOutlinedStyle>
                </div>
            </div>
            <div className="viewList">
                {/* 리스트 출력*/}
                {listData.length !== 0 && listData.map((data, index) =>
                    <div style={{position:'relative'}} key={index} >
                        <Bookmark sx={{cursor:'point'}} onClick={(e)=>clickBookmark(e, data.lesson_no, data.lesson_likes_status)} check={data.lesson_likes_status}/>
                        <RecipeReviewCard  onClick={(e)=> clickDetail(e, data.lesson_no)}
                            title={data.lesson_title} like_count={data.lesson_likes_num} comment_count={0} sname={data.lesson_sname} date={data.lesson_date} type={false}/>
                    </div>)}
            </div>
            <Button className="moreButton"> 더보기 </Button>
        </div>
    );
}

export default ViewList;