import React, { useEffect, useState } from "react";
import './CommunityListStyle.css';
import { styled } from '@mui/material/styles';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell, { tableCellClasses } from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import Pagination from '@mui/material/Pagination';
import { useNavigate } from 'react-router-dom';
import axios from "axios";
import { url } from "../url";
import ButtonContain from "../Component/ButtonContain";


const CommunityList = () => {
    const [listData, setListData] = useState([]);
    const [pageInfo, setPageInfo] = useState([]);
    const [curPage, setCurPage] = useState(1);
    const navigate = useNavigate();

    useEffect(() => {
        axios.get(`${url}/api/communities/list?page=1&size=10`)
            .then(res => {
                console.log(res.data);
                setListData(res.data.posts);
                setPageInfo(res.data.pageInfo);
            })
            .catch(err => {
                console.log(err);
            })
    },[])

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

    {/* 커뮤니티 글쓰기로 이동 */ }
    const handleRegister = (e) => {
        e.preventDefault();
        navigate('/registerCommunity');
    }

    {/* 디테일 페이지 조회 */ }
    const handleDetail = (community_no, e) => {
        e.preventDefault();
        navigate('/communityDetail/' + community_no);
    }

    {/* 페이지네이션 */}
    const pageChange = (e) =>{
        setCurPage(e.page);
        axios.get(`${url}/api/communities/list?page=1&size=10`)
        .then(res => {
            setListData(res.data.posts);
            setPageInfo(res.data.pageInfo);
        })
        .catch(err => {
            console.log(err);
        })
    }

    return (
        <>
            <div className="communityList-Box">
            <ButtonContain size='large' text='로그인'/>

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
                        <TableBody>
                            {listData.map((listData, index) => (
                                <StyledTableRow key={index} hover onClick={(e) => handleDetail(listData.community_no, e)}>
                                    <StyledTableCell align="center">{index + 1}</StyledTableCell>
                                    <StyledTableCell align="center">{listData.communityTitle}</StyledTableCell>
                                    <StyledTableCell align="center">{listData.communityWriter}</StyledTableCell>
                                    <StyledTableCell align="center">{listData.communityDate}</StyledTableCell>
                                    <StyledTableCell align="center">{listData.communityViews}</StyledTableCell>
                                </StyledTableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>


            </div>
            <div className="community-page">
                {/* 전체 게시물 페이지 수로 count 변경 필요 */}
                <Pagination count={Math.ceil(pageInfo.totalElement/10)}
                page = {curPage}
                onChange={pageChange} />
            </div>
        </>
    );
}

export default CommunityList;