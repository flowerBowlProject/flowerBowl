import React from "react";
import './CommentStyle.css';
import Inputbutton from "../Input/Inputbutton";
import SubdirectoryArrowRightIcon from '@mui/icons-material/SubdirectoryArrowRight';

const CommentChild = ({ data, isLast }) => {
    {/* 대댓글 클릭 시 */}
    const registerReply = () =>{

    }

    {/* 수정 버튼 클릭 시 */}
    const changeComment = () =>{
        
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
                    <textarea className="comment-content" id="comment-content" disabled>
                        {data.comment_content}
                    </textarea>
                </div>
                <div className="commentButtonChild-Box">
                    <Inputbutton text="수정" i={true} w="small" onClick={changeComment}/>&nbsp;
                    <Inputbutton text="삭제" i={false} w="small" />
                </div>
            </div>
            {isLast && <div style={{ border: "0.5px solid #B0A695", width: "90%", margin: "1vw auto 1vw auto" }} />}
        </>
    );
}

export default CommentChild;