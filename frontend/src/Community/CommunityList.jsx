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
import { useLocation, useNavigate } from 'react-router-dom';
import axios from "axios";
import { url } from "../url";
import ButtonOutlined from "../Component/ButtonOutlined";
import { useDispatch, useSelector } from "react-redux";
import { editErrorType, openError } from "../persistStore";
import ErrorConfirm from "../Hook/ErrorConfirm";

const CommunityList = () => {
    const [listData, setListData] = useState([]);
    const [pageInfo, setPageInfo] = useState([]);
    const [curPage, setCurPage] = useState(1);
    const navigate = useNavigate();
    const location = useLocation();
    const params = new URLSearchParams(location.search);
    const keyword = params.get('keyword');
    const dispatch = useDispatch();
    
    const accessToken = useSelector(state => state.accessToken);

    useEffect(() => {
        console.log(keyword);
        if(keyword !== null) {
            axios.get(`${url}/api/search/communities?keyword=${keyword}&page=1&size=10`)
            .then(res => {
                setListData(res.data.communities);
                setPageInfo(res.data.pageInfo);
                console.log(res);
            })
            .catch(err => {
                console.log(err);
                dispatch(editErrorType(err.response.data.code));
                dispatch(openError());
            })
        }else{
            axios.get(`${url}/api/communities/list?page=${curPage}&size=10`)
            .then(res => {
                setListData(res.data.posts);
                setPageInfo(res.data.pageInfo);
                console.log(res);
            })
            .catch(err => {
                console.log(err);
                dispatch(editErrorType(err.response.data.code));
                dispatch(openError());
            })
        }
        
    },[keyword, accessToken])

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
    const handleRegister = () => {
        if(accessToken === ''){
            dispatch(editErrorType('NE'));
            dispatch(openError());
        }else{
            navigate('/registerCommunity');
        }
        
    }

    {/* 디테일 페이지 조회 */ }
    const handleDetail = (community_no, e) => {
        e.preventDefault();
        navigate('/communityDetail/' + community_no);
    }

    {/* 페이지네이션 */}
    const pageChange = (e, value) =>{
        const checkPage = value;
        setCurPage(checkPage);
        axios.get(`${url}/api/communities/list?page=${checkPage}&size=10`)
        .then(res => {
            setListData(res.data.posts);
            setPageInfo(res.data.pageInfo);
        })
        .catch(err => {
            console.log(err);
            dispatch(editErrorType(err.response.data.code));
                dispatch(openError());
        })
    }

    return (
        <>
            <div className="communityList-Box">
            <ErrorConfirm  error={useSelector(state => state.errorType)} />

                <div style={{float:'right'}}>
                    <ButtonOutlined size='large' text='글쓰기' handleClick={handleRegister}/>
                </div>

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
                                    <StyledTableCell align="center">{(index+1)+(curPage-1)*10}</StyledTableCell>
                                    <StyledTableCell align="center">{listData.community_title}</StyledTableCell>
                                    <StyledTableCell align="center">{listData.community_writer}</StyledTableCell>
                                    <StyledTableCell align="center">{listData.community_date}</StyledTableCell>
                                    <StyledTableCell align="center">{listData.community_views}</StyledTableCell>
                                </StyledTableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </div>

            <div className="community-page">
                {pageInfo.totalElement !== 0 && <Pagination count={Math.ceil(pageInfo.totalElement/10)}
                page = {curPage}
                onChange={(e, value)=>pageChange(e, value)} />}
            </div>
        </>
    );
}

export default CommunityList;