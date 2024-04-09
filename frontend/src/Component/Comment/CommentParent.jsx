import React, { useState } from "react";
import './CommentStyle.css';
import Inputbutton from "../Input/Inputbutton";
import CommentReply from "./CommentReply";

const CommentParent = ({ data, isLast }) => {
    const [replyModal, setReplyModal] = useState(false);
    {/* 대댓글 클릭 시 - 컴포넌트에 부모 댓글 관련 정보 넘겨주기*/}
    const registerReply = () =>{
       setReplyModal(!replyModal);
    }

    {/* 수정 버튼 클릭 시 - 해결 필요 */}
    const changeComment = () =>{
        console.log("동작 진행");
        const commentContent = document.getElementById("comment-content");
        console.log(commentContent.disabled);
        if(commentContent.disabled){
            commentContent.disabled = false;
        }
        else{
            {/* 변경된 내용 반영 api 연결 필요 */}
            commentContent.disabled = true;
        } 
    }
    
    return (
        <>
            <div className="comment-parent">
                <div className="commentParent-thumbnail">
                    {data.comment_thumbnail}
                </div>
                <div className="comment-body">
                    <div className="comment-element">
                        {data.comment_writer}&nbsp;&nbsp;&nbsp;
                        <div style={{ fontSize: '0.8rem', color: "#B0A695" }}>{data.comment_date}</div>
                    </div>
                    <textarea className="comment-content" id="comment-content" placeholder="대댓글을 작성해 주세요." disabled>
                        {data.comment_content}
                    </textarea>
                </div>
                <div className="commentButton-Box">
                    <div className="commentReply-register" onClick={registerReply}> 대댓글 </div>
                    <div className="commentButton">
                        <Inputbutton text="수정" i={true} w="small" onClick={()=>changeComment()}/>&nbsp;
                        <Inputbutton text="삭제" i={false} w="small" />
                    </div>
                </div>
            </div>
            {isLast && <div style={{ border: "0.5px solid #B0A695", width: "90%", margin: "1vw auto 1vw auto" }} />}
            {replyModal && <CommentReply registerReply={registerReply}/>}
        </>
    );
}

export default CommentParent;