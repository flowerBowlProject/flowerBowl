import React from "react";
import './CommentStyle.css';
import SubdirectoryArrowRightIcon from '@mui/icons-material/SubdirectoryArrowRight';
import ButtonContain from "../ButtonContain";

const CommentChild = ({ data, isLast }) => {

    {/* 수정 버튼 클릭 시 */ }
    const changeComment = () => {
        const commentContent = document.getElementById("childcomment-content");
        if (commentContent.disabled) {
            commentContent.disabled = false;
        }
        else {
            {/* 변경된 내용 반영 api 연결 필요 */ }
            commentContent.disabled = true;
        }
    }

    {/* 댓글 삭제 */ }
    const handleDelete = () => {
    }

    return (
        <>
            <div className="comment-child">
                <SubdirectoryArrowRightIcon sx={{ color: "#B9835C" }} />
                <div className="commentChild-thumbnail">
                    {data.comment_thumbnail}
                </div>
                <div className="comment-body">
                    <div className="comment-element">
                        {data.comment_writer}&nbsp;&nbsp;&nbsp;
                        <div style={{ fontSize: '0.8rem', color: "#B0A695" }}>{data.comment_date}</div>
                    </div>
                    <textarea className="comment-content" id="childcomment-content" placeholder="대댓글을 작성해 주세요." disabled>
                        {data.comment_content}
                    </textarea>
                </div>
                <div className="commentButtonChild-Box">
                <ButtonContain size='large' text='로그인'/> &nbsp;
                <ButtonContain size='large' text='로그인'/>
                </div>
            </div>
            {isLast && <div style={{ border: "0.5px solid #B0A695", width: "90%", margin: "1vw auto 1vw auto" }} />}
        </>
    );
}

export default CommentChild;