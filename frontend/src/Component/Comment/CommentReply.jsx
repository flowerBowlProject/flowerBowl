import React, { useState } from "react";
import './CommentStyle.css';
import SubdirectoryArrowRightIcon from '@mui/icons-material/SubdirectoryArrowRight';
import axios from "axios";
import { url } from "../../url";
import ButtonContain from "../ButtonContain";
import ButtonOutlined from "../ButtonOutlined";
import { useSelector } from "react-redux";

const CommentReply = ({ registerReply, typeString, no, parent_no,isLast, setChange, change }) => {
    const writer = useSelector((state) => state.nickname);
    const accessToken = useSelector(state => state.accessToken);

    const [registerData, setRegisterData] = useState({ type: typeString, post_no: no, comment_content: "", parent_no: parent_no });

    {/* 등록 버튼 클릭 시 */ }
    const registerComment = () => {
        if (registerData.comment_content.trim() === '') {
            console.log('대댓글을 작성해주세요');
        } else {
            axios.post(`${url}/api/comments`, registerData, {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            })
                .then(res => {
                    console.log(res);
                    registerReply();
                    setChange(!change);
                })
                .catch(err => {
                    console.log(err);
                })
        }
    }

    {/* 취소 버튼 클릭 시 */ }
    const handleCancle = () => {
        registerReply();
    }

    return (
        <>
            <div className="comment-child">
                <SubdirectoryArrowRightIcon sx={{ color: "#B9835C" }} />
                <div className="commentChild-thumbnail">
                </div>
                <div className="comment-body">
                    <div className="comment-element">
                        {writer}&nbsp;&nbsp;&nbsp;
                        <div style={{ fontSize: '0.8rem', color: "#B0A695" }}>{registerData.comment_date}</div>
                    </div>
                    <textarea className="comment-content" id="comment-content" placeholder="대댓글을 작성해 주세요."
                        onChange={(e) => setRegisterData((registerData) => ({ ...registerData, comment_content: e.target.value }))}
                    />
                </div>
                <div className="commentButtonChild-Box">
                    <ButtonOutlined size='large' text='등록' handleClick={registerComment} /> &nbsp;
                    <ButtonContain size='large' text='취소' handleClick={handleCancle} />
                </div>
            </div>
            {isLast && <div style={{ border: "0.5px solid #B0A695", width: "90%", margin: "1vw auto 1vw auto" }} />}
        </>
    );
}

export default CommentReply;