import React, { useState } from "react";
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

const SearchList = () => {
    const [searchRecipeList, setSearchRecipeList] = useState([{ user_id: 1, content_no: 1, title: '제목1', date: '날짜1', like_count: 1, comment_count: 1 },
    { user_id: 2, content_no: 2, title: '제목2', date: '날짜2', like_count: 2, comment_count: 2 },
    { user_id: 1, content_no: 3, title: '제목3', date: '날짜3', like_count: 3, comment_count: 3 },{ user_id: 1, content_no: 3, title: '제목3', date: '날짜3', like_count: 3, comment_count: 3 }]);
    const [searchClassList, setSearchClassList] = useState([{ title: '제목1', date: '날짜1', like_count: 1, comment_count: 1 }, { title: '제목1', date: '날짜1', like_count: 1, comment_count: 1 }
        , { title: '제목1', date: '날짜1', like_count: 1, comment_count: 1 }]);
    const [searchCommunityList, setCommunityList] = useState([{ community_title: '제목1', community_writer: '작성자1', community_date: '날짜1', community_views: 1 },
    { community_title: '제목2', community_writer: '작성자2', community_date: '날짜2', community_views: 2 },
    { community_title: '제목3', community_writer: '작성자3', community_date: '날짜3', community_views: 3 }]);

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

    return (
        <div className="searchList-Box">
            <div className="searchRecipe">
                <div className="searchList-title">
                    레시피 <a className="title-right"> 더보기 &gt; </a>
                </div>
                <div className="searchList-body">
                    {searchRecipeList.length !== 0 && searchRecipeList.map((data) => <div style={{ position: 'relative' }}> <Bookmark /> <RecipeReviewCard key={data.content_no} list={data} /> </div>)}

                </div>
            </div>
            <div className="searchClass">
                <div className="searchList-title">
                    클래스 <a className="title-right"> 더보기 &gt; </a>
                </div>
                <div className="searchList-body">
                    {searchClassList.length !== 0 && searchClassList.map((data) => <div style={{ position: 'relative' }}> <Bookmark /> <RecipeReviewCard key={data.content_no} list={data} /> </div>)}

                </div>
            </div>
            <div className="searchCommunity">
                <div className="searchList-title">
                    커뮤니티 <a className="title-right"> 더보기 &gt; </a>
                </div>

                <TableContainer component={Paper} sx={{ boxShadow: 'none'}}>
                    <Table sx={{ border: "none" }}>
                        <TableHead>
                            <TableRow>
                                <StyledTableCell>No</StyledTableCell>
                                <StyledTableCell>제목</StyledTableCell>
                                <StyledTableCell>글쓴이</StyledTableCell>
                                <StyledTableCell>작성시간</StyledTableCell>
                                <StyledTableCell>조회수</StyledTableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody >
                            {searchCommunityList.map((searchCommunityList, index) => (
                                <StyledTableRow key={index} hover >
                                    <StyledTableCell>{index + 1}</StyledTableCell>
                                    <StyledTableCell>{searchCommunityList.community_title}</StyledTableCell>
                                    <StyledTableCell>{searchCommunityList.community_writer}</StyledTableCell>
                                    <StyledTableCell>{searchCommunityList.community_date}</StyledTableCell>
                                    <StyledTableCell>{searchCommunityList.community_views}</StyledTableCell>
                                </StyledTableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </div>
        </div>
    );
}

export default SearchList;