import React, { useState } from "react";
import './CommentStyle.css';
import SubdirectoryArrowRightIcon from '@mui/icons-material/SubdirectoryArrowRight';
import ButtonContain from "../ButtonContain";
import ButtonOutlined from "../ButtonOutlined";
import { useDispatch, useSelector } from "react-redux";
import axios from "axios";
import { url } from "../../url";
import { editErrorType, openError } from "../../persistStore";

const CommentChild = ({ data, isLast, change, setChange }) => {
    const accessToken = useSelector(state => state.accessToken);
    const [commentModify, setCommentModify] = useState(true);
    const [childData, setChildData] = useState(data);
    const writer = useSelector((state) => state.nickname);
    const dispatch = useDispatch();

    {/* 수정 버튼 클릭 시 */ }
    const changeComment = () => {
        const commentContent = document.getElementById("childcomment-content"+data.comment_no);
        if (commentContent.disabled) {
            setCommentModify(false);
            commentContent.disabled = false;
        }
        else {
            setCommentModify(true);
            commentContent.disabled = true;
        }
    }

    {/* 수정 버튼 클릭 시 수정 요청 */ }
    const modifyComment = () => {
        const commentContent = document.getElementById('childcomment-content'+data.comment_no);
        console.log(commentContent);
        console.log(childData);

        axios.put(`${url}/api/comments/${childData.comment_no}`,{
            comment_content : childData.comment_content
        },{
            headers:{
                Authorization : `Bearer ${accessToken}`
            }
        })
        .then(res=>{
            console.log(res);
            commentContent.disabled = true;
            setCommentModify(true);
            dispatch(editErrorType('MODIFY'));
            dispatch(openError());
        })
        .catch(err=>{
            console.log(err);
            if(err.response.data.code === 'NCM'){
                dispatch(editErrorType('COMMENT_ERROR'));
                dispatch(openError());
            }else{
                dispatch(editErrorType(err.response.data.code));
                dispatch(openError());
            }  
        })
    }

    {/* 댓글 삭제 */ }
    const handleDelete = () => {
        axios.delete(`${url}/api/comments/${childData.comment_no}`,{
            headers:{
                Authorization : `Bearer ${accessToken}`
            }
        })
        .then(res=>{
            console.log(res);
            setChange(!change);
            dispatch(editErrorType('DELETE'));
                dispatch(openError());
        })
        .catch(err=>{
            if(err.response.data.code === 'NCM'){
                dispatch(editErrorType('COMMENT_ERROR'));
                dispatch(openError());
            }else{
                dispatch(editErrorType(err.response.data.code));
                dispatch(openError());
            }  
        })
    }

    return (
        <>
            <div className="comment-child">
                <SubdirectoryArrowRightIcon sx={{ color: "#B9835C" }} />
                <img src={childData.user_file_sname || "../../images/blankProfile.png"} className="commentChild-thumbnail"/>
                <div className="comment-body">
                    <div className="comment-element">
                        {data.comment_writer}&nbsp;&nbsp;&nbsp;
                        <div style={{ fontSize: '0.8rem', color: "#B0A695" }}>{data.comment_date}</div>
                    </div>
                    <textarea className="comment-content" id={"childcomment-content"+data.comment_no} placeholder="대댓글을 작성해 주세요." 
                    onChange={(e)=>setChildData((childData)=>({...childData, comment_content : e.target.value}))} disabled>
                        {data.comment_content}
                    </textarea>
                </div>

                {writer === childData.comment_writer && (commentModify ?
                    <div className="commentButtonChild-Box">
                        <ButtonOutlined size='large' text='수정' handleClick={changeComment} /> &nbsp;
                        <ButtonContain size='large' text='삭제' handleClick={handleDelete} />
                    </div>
                    :
                    <div className="commentButtonChild-Box">
                        <ButtonOutlined size='large' text='수정' handleClick={modifyComment} /> &nbsp;
                        <ButtonContain size='large' text='취소' handleClick={changeComment} />
                    </div>
                )}
            </div>
            {isLast && <div style={{ border: "0.5px solid #B0A695", width: "90%", margin: "1vw auto 1vw auto" }} />}
        </>
    );
}

export default CommentChild;