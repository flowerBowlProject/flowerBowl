import React, { useState } from "react";
import './CommentStyle.css';
import Inputbutton from "../Input/Inputbutton";
import SubdirectoryArrowRightIcon from '@mui/icons-material/SubdirectoryArrowRight';

const CommentReply = ({registerReply}) => {
    const [data, setData] = useState({comment_no:1, comment_writer: "작성자1",
    comment_date: "2024-04-05", comment_content: "댓글 내용1", parent_no: 1, comment_thumbnail:""});

    {/* 등록 버튼 클릭 시 */}
    const changeComment = () =>{
        
    }

    {/* 취소 버튼 클릭 시 */}
    const closeReply = () =>{
        
    }

    return (
        <>
            <div className="comment-child">
            <SubdirectoryArrowRightIcon sx={{color:"#B9835C"}}/>
                <div className="commentChild-thumbnail">
                    {data.comment_thumbnail}
                </div>
                <div className="comment-body">
                    <div className="comment-element">
                        {data.comment_writer}&nbsp;&nbsp;&nbsp;
                        <div style={{ fontSize: '0.8rem', color: "#B0A695" }}>{data.comment_date}</div>
                    </div>
                    <textarea className="comment-content" id="comment-content" placeholder="대댓글을 작성해 주세요."/>
                </div>
                <div className="commentButtonChild-Box">
                    <Inputbutton text="등록" i={true} w="small" onClick={()=> changeComment()}/>&nbsp;
                    <Inputbutton text="취소" i={false} w="small" onClick={registerReply}/>
                </div>
            </div>
            {true && <div style={{ border: "0.5px solid #B0A695", width: "90%", margin: "1vw auto 1vw auto" }} />}
        </>
    );
}

export default CommentReply;