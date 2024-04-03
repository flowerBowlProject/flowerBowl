import React, { useState } from "react";
import './CommunityListStyle.css';
import { styled } from '@mui/material/styles';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell, { tableCellClasses } from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { Button } from "@mui/material";
import Pagination from '@mui/material/Pagination';


const CommunityList = () => {
    const [listData, setListData] = useState([{ community_title: '제목1', community_writer: '작성자1', community_date: '날짜1', community_views: 1 },
    { community_title: '제목2', community_writer: '작성자2', community_date: '날짜2', community_views: 2 },
    { community_title: '제목3', community_writer: '작성자3', community_date: '날짜3', community_views: 3 },
    { community_title: '제목1', community_writer: '작성자1', community_date: '날짜1', community_views: 1 },
    { community_title: '제목2', community_writer: '작성자2', community_date: '날짜2', community_views: 2 },
    { community_title: '제목3', community_writer: '작성자3', community_date: '날짜3', community_views: 3 },
    { community_title: '제목1', community_writer: '작성자1', community_date: '날짜1', community_views: 1 },
    { community_title: '제목2', community_writer: '작성자2', community_date: '날짜2', community_views: 2 },
    { community_title: '제목3', community_writer: '작성자3', community_date: '날짜3', community_views: 3 }]);

    const StyledTableCell = styled(TableCell)(() => ({
        [`&.${tableCellClasses.head}`]: {
            color: '#B9835C',
        },
    }));

    {/* 호버 css 적용 필요 - 미해결 */}
    const StyledTableRow = styled(TableRow)(() => ({
        '&:css-1pvw05l-MuiTableRow-root': {
            backgroundColor: '#B9835C',
            color : 'white'
        },
    }));

    const ColorButton = styled(Button)(() => ({
        margin: '0 0 3% 98%',
        fontSize: '1.3vw',
        width: '8vw',
        color: '#B9835C',
        borderColor: '#B9835C',
        '&:hover': {
            backgroundColor: '#B9835C',
            color: 'white',
            borderColor: '#B9835C'
        },
    }));

    return (
        <div className="communityList-Box">
            <ColorButton variant="outlined">글쓰기</ColorButton>

            <TableContainer component={Paper} sx={{boxShadow: 'none'}}>
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
                        {listData.map((listData, index) => (
                            <StyledTableRow key={index} hover >
                                <StyledTableCell>{index + 1}</StyledTableCell>
                                <StyledTableCell>{listData.community_title}</StyledTableCell>
                                <StyledTableCell>{listData.community_writer}</StyledTableCell>
                                <StyledTableCell>{listData.community_date}</StyledTableCell>
                                <StyledTableCell>{listData.community_views}</StyledTableCell>
                            </StyledTableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>

            <div className="community-page">
                {/* 전체 게시물 페이지 수로 count 변경 필요 */}
                <Pagination count={10} showFirstButton showLastButton/>
            </div>
        </div>
    );
}

export default CommunityList;