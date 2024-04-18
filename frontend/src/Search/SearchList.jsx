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
import { Link, useLocation, useNavigate } from 'react-router-dom';
import axios from "axios";
import { url } from "../url";


const SearchList = () => {
    const navigator = useNavigate();
    const location = useLocation();
    const params = new URLSearchParams(location.search);
    const keyword = params.get('keyword');

    const [searchRecipeList, setSearchRecipeList] = useState([]);
    const [searchClassList, setSearchClassList] = useState([]);
    const [searchCommunityList, setCommunityList] = useState([]);

    const StyledTableCell = styled(TableCell)(() => ({
        [`&.${tableCellClasses.head}`]: {
            color: '#B9835C',
        },
    }));

    {/* 호버 css 적용 필요 - 미해결 */ }
    const StyledTableRow = styled(TableRow)(() => ({
        '&:css-1pvw05l-MuiTableRow-root': {
            backgroundColor: '#B9835C',
            color: 'white'
        },
    }));

    useEffect(()=>{
        axios.get(`${url}/api/search?keyword=${keyword}&size=4`)
        .then(res=>{
            console.log(res.data);
            setCommunityList(res.data.community); // 매치 완료
            setSearchRecipeList(res.data.recipes); // 매치 필요
            setSearchClassList(res.data.lessons); // 매치 필요
        })
        .catch(err=>{
            console.log(err);
        })
    },[])

    {/* 레시피 디테일 페이지 조회 */ }
    const handleRecipeDetail = (recipe_no, e) => {
        e.preventDefault();
        navigator(`/recipeDetail/${recipe_no}`);
    }

    {/* 클래스 디테일 페이지 조회 */ }
    const handleClassDetail = (lesson_no, e) => {
        e.preventDefault();
        navigator(`/classDetail/${lesson_no}`)
    }

    {/* 커뮤니티 디테일 페이지 조회 */ }
    const handleCommunityDetail = (community_no, e) => {
        e.preventDefault();
        navigator(`/communityDetail/${community_no}`)
    }

    {/* 더보기 버튼 클릭 */}
    const handleMoreRecipe = () =>{
        
    }

    const handleMoreClass = () =>{

    }

    const handleMoreCommunity = () =>{
        navigator('/communityList', {state:{keyword:keyword}});
    }

    return (
        <div className="searchList-Box">
            <div className="searchRecipe">
                <div className="searchList-title">
                    레시피 <a className="title-right" style={{ cursor: 'pointer' }}> 더보기 &gt; </a>
                </div>
                <div className="searchList-body">
                    {searchRecipeList.length !== 0 ? searchRecipeList.map((data) =>
                    <div style={{ position: 'relative', cursor: 'pointer' }}>
                        <Bookmark />
                        <div onClick={(e)=> handleRecipeDetail(data.recipe_no, e)}>
                             <RecipeReviewCard key={data.content_no} list={data}  />
                        </div>
                    </div>
                    ) : <div style={{margin: "5% auto"}}> "조회된 게시글이 없습니다"</div>}
                </div>
            </div>
            <div className="searchClass">
                <div className="searchList-title">
                    클래스 <a className="title-right" style={{ cursor: 'pointer' }}> 더보기 &gt; </a>
                </div>
                <div className="searchList-body">
                    {searchClassList.length !== 0 ? searchClassList.map((data) =>
                        <div style={{ position: 'relative', cursor: 'pointer' }} >
                            <Bookmark />
                            <div onClick={(e)=> handleClassDetail(data.lesson_no, e)}>
                                <RecipeReviewCard key={data.content_no} list={data}/>
                            </div>
                        </div>
                    ) : <div style={{margin: "5% auto"}}> "조회된 게시글이 없습니다"</div>}
                </div>
            </div>
            <div className="searchCommunity">
                <div className="searchList-title">
                    커뮤니티 <a className="title-right" style={{ cursor: 'pointer' }} onClick={handleMoreCommunity}> 더보기 &gt; </a>
                </div>

                {searchCommunityList.length !==0 ?
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
                                <StyledTableRow key={index} hover sx={{cursor: 'pointer'}}onClick={(e) => handleCommunityDetail(searchCommunityList.community_no, e)}>
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
                : <div style={{textAlign: "center", margin: "5% auto 10% auto"}}> "조회된 게시글이 없습니다"</div>}
            </div>
        </div>
    );
}

export default SearchList;