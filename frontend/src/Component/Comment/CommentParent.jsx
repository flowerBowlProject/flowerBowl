import React, { useState } from "react";
import './CommentStyle.css';
import CommentReply from "./CommentReply";
import ButtonContain from "../ButtonContain";
import ButtonOutlined from "../ButtonOutlined";
import { useDispatch, useSelector } from "react-redux";
import axios from "axios";
import { url } from "../../url";
import { editErrorType, openError } from "../../persistStore";
import ErrorConfirm from "../../Hook/ErrorConfirm";

const CommentParent = ({ data, isLast, typeString, no, change, setChange }) => {
    const [replyModal, setReplyModal] = useState(false);
    const accessToken = useSelector(state => state.accessToken);
    const [parentData, setParentData] = useState(data);
    const [commentModify, setCommentModify] = useState(true);
    const writer = useSelector((state) => state.nickname);
    const dispatch = useDispatch();

    {/* 대댓글 클릭 시 - 컴포넌트에 부모 댓글 관련 정보 넘겨주기*/}
    const registerReply = () =>{
        if(accessToken === ''){
            dispatch(editErrorType('NT'));
            dispatch(openError());
        }else{
            setReplyModal(!replyModal);
        }
    }

    {/* 수정 버튼 클릭 시 */}
    const changeComment = () =>{
        const commentContent = document.getElementById('comment-content'+data.comment_no)
        if(commentContent.disabled){
            setCommentModify(false);
            commentContent.disabled = false;
        }
        else{
            setCommentModify(true);
            commentContent.disabled = true;
        } 
    }

    {/* 수정 버튼 클릭 시 수정 요청 */}
    const modifyComment = () =>{
        const commentContent = document.getElementById('comment-content'+data.comment_no)

        axios.put(`${url}/api/comments/${parentData.comment_no}`,{
            comment_content : parentData.comment_content
        },{
            headers:{
                Authorization : `Bearer ${accessToken}`
            }
        })
        .then(res=>{
            console.log(res);
            commentContent.disabled = true;
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

    {/* 댓글 삭제 */}
    const handleDelete = () =>{
        axios.delete(`${url}/api/comments/${parentData.comment_no}`,{
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
    
    return (
        <>
            <div className="comment-parent">
                <img src={data.user_file_sname || "../../images/blankProfile.png"} className="commentParent-thumbnail"/>
                <div className="comment-body">
                    <div className="comment-element">
                        {data.comment_writer}&nbsp;&nbsp;&nbsp;
                        <div style={{ fontSize: '0.8rem', color: "#B0A695" }}>{data.comment_date}</div>
                    </div>
                    <textarea className="comment-content" id={"comment-content"+data.comment_no} placeholder="대댓글을 작성해 주세요." disabled
                         onChange={(e)=>setParentData((parentData)=>({...parentData, comment_content : e.target.value}))}>
                        {data.comment_content}
                    </textarea>
                </div>
                <div className="commentButton-Box">
                    <div className="commentReply-register" onClick={registerReply}> 대댓글 </div>
                    {writer === parentData.comment_writer && (commentModify ? 
                        <div className="commentButton">
                            <ButtonOutlined size='large' text='수정' handleClick={changeComment}/> &nbsp;
                            <ButtonContain size='large' text='삭제' handleClick={handleDelete}/>
                        </div>
                        :
                        <div className="commentButton">
                            <ButtonOutlined size='large' text='수정' handleClick={modifyComment}/> &nbsp;
                            <ButtonContain size='large' text='취소' handleClick={changeComment}/>
                        </div>
                   ) } 
                </div>
            </div>
            {isLast && <div style={{ border: "0.5px solid #B0A695", width: "90%", margin: "1vw auto 1vw auto" }} />}
            {replyModal && <CommentReply isLast={isLast} registerReply={registerReply} typeString={typeString} no={no} parent_no={parentData.comment_no} change={change} setChange={setChange}/>}
        </>
    );
}

export default CommentParent;