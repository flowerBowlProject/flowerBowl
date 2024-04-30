import React, { useEffect, useState } from "react";
import './CommentStyle.css';
import { Pagination } from "@mui/material";
import CommentParent from "./CommentParent";
import CommentChild from "./CommentChild";
import ChatBubbleOutlineIcon from '@mui/icons-material/ChatBubbleOutline';
import ButtonContain from "../ButtonContain";
import ButtonOutlined from "../ButtonOutlined";
import axios from "axios";
import { url } from "../../url";
import { useSelector } from "react-redux";



const Comment = ({ typeString, no }) => {
    // 댓글/대댓글의 구분은 parent_no로 판단
    const [commentList, setCommentList] = useState([]);
    const [pageInfo, setPageInfo] = useState({page:1, size:10, totalPage:0, totalElement:0});
    const [registerData, setRegisterData] = useState({type:typeString, post_no:no, parent_no:null, comment_content:''});
    const accessToken = useSelector(state => state.accessToken);
    const [curPage, setCurPage] = useState(1);
    const [change, setChange] = useState(false);

    const fetchData = (typeString, no, pageInfo, setCommentList, setPageInfo) => {
        axios.get(`${url}/api/comments?type=${typeString}&post_no=${no}&page=${pageInfo.page}&size=${pageInfo.size}`)
            .then(res => {
                console.log(res);
                setCommentList(res.data.comments);
                setPageInfo(res.data.pageInfo);
            })
            .catch(err => {
                console.log(err);
            });
    };

    const changeList = () =>{
        setChange(!change);
    }

    useEffect(()=>{
        console.log('11111');
        fetchData(typeString, no, pageInfo, setCommentList, setPageInfo);
    },[change])

    {/* 댓글 등록 */}
    const handleRegister = () =>{
        console.log(registerData.comment_content);
        
        if(accessToken === ''){
            console.log('로그인 후 사용')
        }else if(registerData.comment_content.trim() === ''){
            console.log('댓글을 작성해주세요');
        }else{
            axios.post(`${url}/api/comments`,registerData,{
                headers:{
                    Authorization: `Bearer ${accessToken}`
                }
            })
            .then(res=>{
                console.log(res);
                setRegisterData((registerData)=>({...registerData, comment_content : ''}));
                setChange(!change);
                //fetchData(typeString, no, pageInfo, setCommentList, setPageInfo);
            })
            .catch(err=>{
                console.log(err);
                if(err.response.data.code === 'NE'){
                    console.log('로그인 후 이용해 주세요')
                }
            })
        }
    }

    {/* 페이지네이션 */}
    const pageChange = (e) =>{
        const checkPage = Number(e.target.outerText);
        console.log(checkPage);
        setCurPage(checkPage);
        axios.get(`${url}/api/comments?type=${typeString}&post_no=${no}&page=${curPage}&size=${pageInfo.size}`)
        .then(res => {
            setCommentList(res.data.posts);
            setPageInfo(res.data.pageInfo);
        })
        .catch(err => {
            console.log(err);
        })
    }

    return (
        <>
            <div className="comment-Box">
                {/* 댓글 작성란 */}
                <div className="comment-register">
                    <textarea className="comment-write" placeholder="댓글을 작성하세요." value={registerData.comment_content} onChange={(e)=>setRegisterData((registerData)=>({...registerData, comment_content : e.target.value}))}/>
                    <ButtonOutlined size='heightLarge' text='등록' handleClick={handleRegister}/>
                </div>
                
                <div style={{width:"90%", margin:"2% auto", display:'flex', alignContent:'center'}}>
                    댓글 &nbsp; <ChatBubbleOutlineIcon sx={{ color: 'main.br', marginRight:"0.5vw"}} /> {pageInfo.totalElement}
                </div>
                {commentList.length === 0 && <div style={{display:'flex', justifyContent:'center', marginBottom: '2vw'}}>조회된 댓글이 없습니다</div>}
                {commentList.map((list, index) => (
                    list.parent_no === null ?
                        <CommentParent typeString={typeString} no={no} data={list} key={list.comment_no} isLast={index !== commentList.length -1} change={change} setChange={setChange} />
                        : <CommentChild data={list} key={list.comment_no} isLast={index !== commentList.length -1}  change={change} setChange={setChange}/>
                ))}
                
            </div>
            {/* 페이지네이션 */}
            <div className="comment-page">
                {/* 전체 게시물 페이지 수로 count 변경 필요 */}
                <Pagination count={Math.ceil(pageInfo.totalElement/10)}
                page = {curPage}
                onChange={pageChange} />
            </div>
        </>
    );
}

export default Comment;