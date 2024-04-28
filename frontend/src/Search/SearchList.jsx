import React, { useEffect, useState } from "react";
import './SearchListStyle.css';
import RecipeReviewCard from "../Component/CardComp";
import { styled } from '@mui/material/styles';
import Bookmark from "../Component/Bookmark";
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell, { tableCellClasses } from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from "axios";
import { url } from "../url";
import { useSelector } from "react-redux";

const SearchList = () => {
    const navigator = useNavigate();
    const location = useLocation();
    const params = new URLSearchParams(location.search);
    const keyword = params.get('keyword');
    const accessToken = useSelector(state => state.accessToken);

    const [searchRecipeList, setSearchRecipeList] = useState([]);
    const [searchClassList, setSearchClassList] = useState([]);
    const [searchCommunityList, setCommunityList] = useState([]);

    const StyledTableCell = styled(TableCell)(() => ({
        [`&.${tableCellClasses.head}`]: {
            color: '#B9835C',
        },
    }));

    const StyledTableRow = styled(TableRow)(() => ({
        '&:css-1pvw05l-MuiTableRow-root': {
            backgroundColor: '#B9835C',
            color: 'white'
        },
    }));

    useEffect(() => {
        if (accessToken === '') {
            axios.get(`${url}/api/search?keyword=${keyword}&size=4`)
                .then(res => {
                    setCommunityList(res.data.community);
                    setSearchRecipeList(res.data.recipes);
                    setSearchClassList(res.data.lessons);
                })
                .catch(err => {
                    console.log(err);
                })
        }else{
            axios.get(`${url}/api/user/search?keyword=${keyword}&size=4`,{
                headers:{
                    Authorization: `Bearer ${accessToken}`
                }
            })
                .then(res => {
                    setCommunityList(res.data.community);
                    setSearchRecipeList(res.data.recipes);
                    setSearchClassList(res.data.lessons);
                })
                .catch(err => {
                    console.log(err);
                })
        }

    }, [keyword])

    {/* 레시피 디테일 페이지 조회 */ }
    const handleRecipeDetail = (recipe_no, e) => {
        e.preventDefault();
        navigator(`/recipeDetail/${recipe_no}`);
    }

    {/* 클래스 디테일 페이지 조회 */ }
    const handleClassDetail = (lesson_no, e) => {
        e.preventDefault();
        navigator(`/classDetail/${lesson_no}`);
    }

    {/* 커뮤니티 디테일 페이지 조회 */ }
    const handleCommunityDetail = (community_no, e) => {
        e.preventDefault();
        navigator(`/communityDetail/${community_no}`);
    }

    {/* 더보기 버튼 클릭 */ }
    const handleMoreRecipe = () => {
        navigator('/recipeList?keyword='+keyword);
    }

    const handleMoreClass = () => {
        navigator('/classList?keyword='+keyword);
    }

    const handleMoreCommunity = () => {
        navigator('/communityList?keyword='+keyword);
    }

    {/* 즐겨찾기 클릭 */}
    const clickRecipeBookmark = (e, index, recipe_no) =>{
        if (accessToken === "") {
          {
            /* 로그인이 되어있지 않은 경우 - 로그인 후 이용 가능 alrt - 변수명 수정 후 확인 필요*/
          }
          console.log("로그인 후 이용 가능");
        } else {
          axios
            .post(`${url}/api/recipes/like/${recipe_no}`, null, {
              headers: {
                Authorization: `Bearer ${accessToken}`,
              }
            })
            .then((res) => {
              const check = res.data.code=='SU' ? false : true;
              setSearchRecipeList((searchRecipeList) => {
                const updatedList = { ...searchRecipeList[index], recipe_like_status: check };
                const newListData = [...searchRecipeList.slice(0, index), updatedList, ...searchRecipeList.slice(index + 1)];
                return newListData;
              })
            })
        }
    }

    const clickClassBookmark = (e, index, lesson_no, lesson_like_status) =>{
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
                        setSearchClassList((searchClassList) => {
                            const updatedList = { ...searchClassList[index], lesson_like_status: false };
                            const newListData = [...searchClassList.slice(0, index), updatedList, ...searchClassList.slice(index + 1)];
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
                        setSearchClassList((searchClassList) => {
                            const updatedList = { ...searchClassList[index], lesson_like_status: true };
                            const newListData = [...searchClassList.slice(0, index), updatedList, ...searchClassList.slice(index + 1)];
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


    return (
        <div className="searchList-Box">
            <div className="searchRecipe">
                <div className="searchList-title">
                    레시피 <a className="title-right" style={{ cursor: 'pointer' }} onClick={handleMoreRecipe}> 더보기 &gt; </a>
                </div>
                <div className="searchList-body">
                    {searchRecipeList.length !== 0 ? searchRecipeList.map((data, index) =>
                        <div style={{ position: 'relative', cursor: 'pointer' }} key={data.recipe_no}>
                            <Bookmark check={data.recipe_like_status}  onClick={(e) => clickRecipeBookmark(e, index, data.recipe_no)}/>
                            <div onClick={(e) => handleRecipeDetail(data.recipe_no, e)}>
                                <RecipeReviewCard
                                    title={data.recipe_title} like_count={data.recipe_like_cnt} comment_count={data.comment_cnt} sname={data.recipe_sname} date={data.recipe_date} type={true} />
                            </div>
                        </div>
                    ) : <div style={{ margin: "5% auto" }}> "조회된 게시글이 없습니다"</div>}
                </div>
            </div>
            <div className="searchClass">
                <div className="searchList-title">
                    클래스 <a className="title-right" style={{ cursor: 'pointer' }} onClick={handleMoreClass}> 더보기 &gt; </a>
                </div>
                <div className="searchList-body">
                    {searchClassList.length !== 0 ? searchClassList.map((data, index) =>
                        <div style={{ position: 'relative', cursor: 'pointer' }} key={index}>
                            <Bookmark check={data.lesson_like_status} onClick={(e)=>clickClassBookmark(e, index, data.lesson_no, data.lesson_like_status)}/>
                            <div onClick={(e) => handleClassDetail(data.lesson_no, e)}>
                                <RecipeReviewCard key={data.lesson_no}
                                    title={data.lesson_title} like_count={data.lesson_like_cnt} sname={data.lesson_sname} date={data.lesson_date} type={false} />
                            </div>
                        </div>
                    ) : <div style={{ margin: "5% auto" }}> "조회된 게시글이 없습니다"</div>}
                </div>
            </div>
            <div className="searchCommunity">
                <div className="searchList-title">
                    커뮤니티 <a className="title-right" style={{ cursor: 'pointer' }} onClick={handleMoreCommunity}> 더보기 &gt; </a>
                </div>

                {searchCommunityList.length !== 0 ?
                    <TableContainer component={Paper} sx={{ boxShadow: 'none' }}>

                        <Table sx={{ border: "none" }}>
                            <TableHead>
                                <TableRow>
                                    <StyledTableCell align="center" style={{ width: "10%" }}>No</StyledTableCell>
                                    <StyledTableCell align="center" style={{ width: "40%" }}>제목</StyledTableCell>
                                    <StyledTableCell align="center" style={{ width: "20%" }}>글쓴이</StyledTableCell>
                                    <StyledTableCell align="center" style={{ width: "20%" }}>작성시간</StyledTableCell>
                                    <StyledTableCell align="center" style={{ width: "10%" }}>조회수</StyledTableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody >
                                {searchCommunityList.map((searchCommunityList, index) => (
                                    <StyledTableRow key={index} hover sx={{ cursor: 'pointer' }} onClick={(e) => handleCommunityDetail(searchCommunityList.community_no, e)}>
                                        <StyledTableCell align="center">{index + 1}</StyledTableCell>
                                        <StyledTableCell align="center">{searchCommunityList.community_title}</StyledTableCell>
                                        <StyledTableCell align="center">{searchCommunityList.community_writer}</StyledTableCell>
                                        <StyledTableCell align="center">{searchCommunityList.community_date}</StyledTableCell>
                                        <StyledTableCell align="center">{searchCommunityList.community_views}</StyledTableCell>
                                    </StyledTableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                    : <div style={{ textAlign: "center", margin: "5% auto 10% auto" }}> "조회된 게시글이 없습니다"</div>}
            </div>
        </div>
    );
}

export default SearchList;