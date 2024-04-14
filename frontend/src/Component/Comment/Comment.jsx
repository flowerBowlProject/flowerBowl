import React, { useState } from "react";
import './CommentStyle.css';
import { Pagination } from "@mui/material";
import CommentParent from "./CommentParent";
import CommentChild from "./CommentChild";
import ChatBubbleOutlineIcon from '@mui/icons-material/ChatBubbleOutline';
import Button_outlined_style from "../Button_outlined_style";

// props로 게시판 종류와 게시글 번호를 넘겨 받아 조회 요청 진행
const Comment = ({ props }) => {
    // 댓글/대댓글의 구분은 parent_no로 판단
    const [commentList, setCommentList] = useState([{comment_no:1, comment_writer: "작성자1",
        comment_date: "2024-04-05", comment_content: "댓글 내용1", parent_no: 1, comment_thumbnail:""},
        {comment_no:1, comment_writer: "작성자2",
        comment_date: "2024-04-05", comment_content: "댓글 내용2", parent_no: 0, comment_thumbnail:""}]);

    const [pageInfo, setPageInfo] = useState({page:0, size:0, totalPage:0, totalElement:10});

    const handleRegister = () =>{

    }

    return (
        <>
            <div className="comment-Box">
                {/* 댓글 작성란 */}
                <div className="comment-register">
                    <textarea className="comment-write" placeholder="댓글을 작성하세요."/>
                    <Button_outlined_style width='5vw' sx={{height:"4vw"}} variant='outlined' onClick={handleRegister}>등록</Button_outlined_style>
                </div>
                
                <div style={{width:"90%", margin:"2% auto", display:'flex', alignContent:'center'}}>
                    댓글 &nbsp; <ChatBubbleOutlineIcon sx={{ color: 'main.br', marginRight:"0.5vw"}} /> {pageInfo.totalElement}
                </div>
                {commentList.map((list, index) => (
                    list.parent_no === 1 ?
                        <CommentParent data={list} key={index} isLast={index !== commentList.length -1}/>
                        : <CommentChild data={list} key={index} isLast={index !== commentList.length -1}/>
                ))}
                
            </div>
            {/* 페이지네이션 */}
            <div className="comment-page">
                {/* 전체 게시물 페이지 수로 count 변경 필요 */}
                <Pagination count={10} showFirstButton showLastButton sx={{marginBottom:"5%"}}/>
            </div>
        </>
    );
}

export default Comment;