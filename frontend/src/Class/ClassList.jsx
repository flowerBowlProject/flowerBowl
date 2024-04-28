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
    const params = new URLSearchParams(location.search);
    const keyword = params.get('keyword');

    useEffect(() => {
        if (keyword !== null) {
            if (accessToken === '') {
                axios.get(`${url}/api/search/lessons?keyword=${keyword}&page=1&size=10`)
                    .then(res => {
                        setListData(res.data.lesson);
                        //setPageInfo(res.data.pageInfo);
                        console.log(res);
                    })
                    .catch(err => {
                        console.log(err);
                    })
            } else {
                console.log(keyword)
                axios.get(`${url}/api/user/search/lessons?keyword=${keyword}&page=1&size=10`, {
                    headers: {
                        Authorization: `Bearer ${accessToken}`
                    }
                })
                    .then(res => {
                        setListData(res.data.lessons);
                        //setPageInfo(res.data.pageInfo);
                        console.log(res);
                    })
                    .catch(err => {
                        console.log(err);
                    })
            }
        } else {
            {/* 로그인에 따른 조회 */ }
            if (accessToken == '') {
                axios.get(`${url}/api/guest/lessons?page=1&size=10`)
                    .then(res => {
                        console.log(res);
                        setListData(res.data.lessons);
                    })
                    .catch(err => {
                        console.log(err);
                    })
            } else {
                axios.get(`${url}/api/user/lessons?page=1&size=10`, {
                    headers: {
                        Authorization: `Bearer ${accessToken}`
                    }
                })
                    .then(res => {
                        console.log(res);
                        setListData(res.data.lessons);
                    })
                    .catch(err => {
                        console.log(err);
                    })
            }
        }
    }, [keyword, accessToken])

    const clickBookmark = (e, lesson_no, lesson_like_status, index) => {
        console.log("북마크 클릭");
        if (accessToken === '') {
            console.log('로그인 후 이용해 주세요.');
        } else {
            if (lesson_like_status) {
                console.log('북마크 해제')
                axios.delete(`${url}/api/user/lessons/like/${lesson_no}`, {
                    headers: {
                        Authorization: `Bearer ${accessToken}`
                    }
                })
                    .then(res => {
                        console.log('북마크 해제 성공')
                        setListData((listData) => {
                            const updatedList = { ...listData[index], lesson_like_status: false };
                            const newListData = [...listData.slice(0, index), updatedList, ...listData.slice(index + 1)];
                            return newListData;
                        })
                    })
                    .catch(err => {
                        console.log(err);
                        console.log('북마크 해제 실패')
                    })
            } else {
                console.log('북마크 등록')
                axios.post(`${url}/api/user/lessons/like`, {
                    "lesson_no": lesson_no
                }, {
                    headers: {
                        Authorization: `Bearer ${accessToken}`
                    }
                })
                    .then(res => {
                        console.log('북마크 등록 성공');
                        setListData((listData) => {
                            const updatedList = { ...listData[index], lesson_like_status: true };
                            const newListData = [...listData.slice(0, index), updatedList, ...listData.slice(index + 1)];
                            return newListData;
                        })
                    })
                    .catch(err => {
                        console.log(err);
                        console.log('북마크 등록 실패');
                    })
            }
        }
    }

    const clickRegister = () => {
        navigator(`/registerClass`);
    }

    const clickDetail = (e, lesson_no) => {
        navigator(`/classDetail/${lesson_no}`);
    }

    return (
        <div className="viewList-Box">
            <div className="sortList">
                <div className="sortList-left">
                    <Button sx={{ color: 'main.or' }}> 최신순 </Button>
                    <Button sx={{ color: 'main.or' }}> 인기순 </Button>
                    <Button sx={{ color: 'main.or' }}> 댓글순 </Button>
                </div>
                <div className="sortList-right">
                    <ButtonOutlinedStyle className="view-register" variant="outlined" onClick={clickRegister}> 클래스 등록 </ButtonOutlinedStyle>
                </div>
            </div>
            <div className="viewList">
                {/* 리스트 출력*/}
                {listData.length !== 0 && listData.map((data, index) =>
                    <div style={{ position: 'relative' }} key={index} >
                        <Bookmark sx={{ cursor: 'point' }} onClick={(e) => clickBookmark(e, data.lesson_no, data.lesson_like_status, index)} check={data.lesson_like_status} />
                        <RecipeReviewCard onClick={(e) => clickDetail(e, data.lesson_no)}
                            title={data.lesson_title} like_count={data.lesson_like_cnt} comment_count={0} sname={data.lesson_sname} date={data.lesson_date} type={false} />
                    </div>)}
            </div>
            <Button className="moreButton"> 더보기 </Button>
        </div>
    );
}

export default ViewList;