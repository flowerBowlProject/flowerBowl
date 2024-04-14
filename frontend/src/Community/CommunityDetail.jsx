import React, { useEffect, useState } from "react";
import './CommunityDetailStyle.css';
import { useLocation } from "react-router";
import  axios  from "axios";
import { url } from "../url";
import Comment from "../Component/Comment/Comment";
import ButtonContain from "../Component/ButtonContain";

const CommunityDetail = () => {
    const [communityData, setCommunityData] = useState({ community_title: '커뮤니티 제목', community_content: '커뮤니티 내용' });

    const location = useLocation();
    const community_no = location.state?.community_no;

    {/* 데이터 불러오기 */}
    useEffect(()=>{
        axios.get(`${url}/api/communities/${community_no}`)
        .then(res=>{
            console.log(res);
        })
        .catch(err=>{
            console.log(err);
        })
    },[])

    {/* 커뮤니티 수정 */}
    const handleModify = () =>{
    }

    {/* 커뮤니티 삭제 */}
    const handleDelete = () =>{
    }

    return (
        <>
            <div className="communityDetail-Box">
                <div className="community-title"> {communityData.community_title} </div>
                <div className='community-body'>{communityData.community_content}</div>
                {/* 수정/삭제 버튼 - 작성자인 경우에만 true로 버튼 표시 */}
                <div className="community-change">
                    {true &&  <ButtonContain size='large' text='로그인'/>} &nbsp;
                    {true && <ButtonContain size='large' text='로그인'/>}
                </div>
            </div>
            <div>
                <Comment/>
            </div>
        </>
    );
}

export default CommunityDetail;