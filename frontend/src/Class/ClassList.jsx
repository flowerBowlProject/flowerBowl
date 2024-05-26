import React, { useEffect, useState } from "react";
import '../Recipe/ViewListStyle.css';
import Button from '@mui/material/Button';
import RecipeReviewCard from "../Component/CardComp";
import Bookmark from "../Component/Bookmark";
import { useLocation, useNavigate } from 'react-router-dom';
import axios from "axios";
import { useDispatch, useSelector } from "react-redux";
import { url } from "../url";
import ButtonOutlinedStyle from "../Component/ButtonOutlinedStyle";
import { editErrorType, openError } from '../persistStore';
import ErrorConfirm from "../Hook/ErrorConfirm";
import ButtonContainStyle from "../Component/ButtonContainStyle";

const ViewList = () => {
    const [listData, setListData] = useState([]);
    const navigator = useNavigate();
    const accessToken = useSelector(state => state.accessToken);
    const location = useLocation();
    const params = new URLSearchParams(location.search);
    const keyword = params.get('keyword');
    const [pageInfo, setPageInfo] = useState(1);
    const dispatch = useDispatch();

    {/* 정렬 구현 */ }
    const [selectButton, setSelectButton] = useState('최신순');
    const handleClick = (selectButton) => {
        let sorted;
        setSelectButton(selectButton);

        switch (selectButton) {
            case '최신순':
                sorted = [...listData].sort((a, b) => new Date(b.lesson_date) - new Date(a.lesson_date));
                setPageInfo(1);
                break;
            case "인기순":
                sorted = [...listData].sort((a, b) => b.lesson_like_cnt - a.lesson_like_cnt);
                setPageInfo(1);
                break;
            default:
                sorted = listData; // 기본값은 변경하지 않음
        }
        setListData(sorted);
    }

    useEffect(() => {
        if (keyword !== null) {
            if (accessToken === '') {
                axios.get(`${url}/api/search/lessons?keyword=${keyword}&page=1&size=${pageInfo*8}`)
                    .then(res => {
                        console.log(res);
                        let sorted;
                        switch (selectButton) {
                            case "인기순":
                                sorted = [...res.data.lesson].sort((a, b) => b.lesson_like_cnt - a.lesson_like_cnt);
                                break;
                            default:
                                sorted = res.data.lesson; // 기본값은 변경하지 않음
                        }
                        setListData(sorted);
                    })
                    .catch(err => {
                        console.log(err);
                        dispatch(editErrorType(err.response.data.code));
                        dispatch(openError());
                    })
            } else {
                console.log(keyword)
                axios.get(`${url}/api/user/search/lessons?keyword=${keyword}&page=1&size=${pageInfo*8}`, {
                    headers: {
                        Authorization: `Bearer ${accessToken}`
                    }
                })
                    .then(res => {
                        console.log(res);
                        let sorted;
                        switch (selectButton) {
                            case "인기순":
                                sorted = [...res.data.lessons].sort((a, b) => b.lesson_like_cnt - a.lesson_like_cnt);
                                break;
                            default:
                                sorted = res.data.lessons; // 기본값은 변경하지 않음
                        }
                        setListData(sorted);
                    })
                    .catch(err => {
                        console.log(err);
                        dispatch(editErrorType(err.response.data.code));
                        dispatch(openError());
                    })
            }
        } else {
            {/* 로그인에 따른 조회 */ }
            if (accessToken == '') {
                axios.get(`${url}/api/guest/lessons?page=1&size=${pageInfo*8}`)
                    .then(res => {
                        console.log(res);
                        let sorted;
                        switch (selectButton) {
                            case "인기순":
                                sorted = [...res.data.lessons].sort((a, b) => b.lesson_like_cnt - a.lesson_like_cnt);
                                break;
                            default:
                                sorted = listData; // 기본값은 변경하지 않음
                        }
                        setListData(res.data.lessons);
                    })
                    .catch(err => {
                        console.log(err);
                        dispatch(editErrorType('err.response.data.code'));
                        dispatch(openError());
                    })
            } else {
                axios.get(`${url}/api/user/lessons?page=1&size=${pageInfo*8}`, {
                    headers: {
                        Authorization: `Bearer ${accessToken}`
                    }
                })
                    .then(res => {
                        console.log(res);
                        let sorted;
                        switch (selectButton) {
                            case "인기순":
                                sorted = [...res.data.lessons].sort((a, b) => b.lesson_like_cnt - a.lesson_like_cnt);
                                break;
                            default:
                                sorted = res.data.lessons; // 기본값은 변경하지 않음
                        }
                        setListData(sorted);
                    })
                    .catch(err => {
                        console.log(err);
                        dispatch(editErrorType(err.response.data.code));
                        dispatch(openError());
                    })
            }
        }
    }, [keyword, accessToken, pageInfo])


    const clickBookmark = (e, lesson_no, lesson_like_status, index) => {
        console.log("북마크 클릭");
        if (accessToken === '') {
            dispatch(editErrorType('NT'));
            dispatch(openError());
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
                        dispatch(editErrorType(err.response.data.code));
                        dispatch(openError());
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
                        dispatch(editErrorType(err.response.data.code));
                        dispatch(openError());
                    })
            }
        }
    }

    const clickRegister = () => {
        console.log('등록 클릭');
        if (accessToken === '') {
            dispatch(editErrorType('NT'));
            dispatch(openError());
        }
        axios.get(`${url}/api/users/info`,{
            headers:{
                Authorization : `Bearer ${accessToken}`
            }
        })
        .then(res=>{
            console.log(res);
            if(res.data.user_role==='ROLE_CHEF'){
                navigator('/registerClass');
            }else{
                dispatch(editErrorType('ONLYCHEF'));
                dispatch(openError());
            }
        })
        .catch(err=>{
            dispatch(editErrorType('NN'));
            dispatch(openError());
        })
    }

    const clickDetail = (e, lesson_no) => {
        navigator(`/classDetail/${lesson_no}`);
    }

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
            <ButtonContainStyle className="moreButton" onClick={clickMore}> 더보기 </ButtonContainStyle>
        </div>
    );
}

export default ViewList;