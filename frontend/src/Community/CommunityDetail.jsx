import React, { useEffect, useState } from "react";
import './CommunityDetailStyle.css';
import TurnedInNotIcon from '@mui/icons-material/TurnedInNot';
import TurnedInIcon from '@mui/icons-material/TurnedIn';
import { useLocation } from "react-router";
import  axios  from "axios";
import Button_outlined_style from "../Component/Button_outlined_style";
import Button_contain_style from "../Component/Button_contain_style";
import { url } from "../url";
import Comment from "../Component/Comment/Comment";

const CommunityDetail = () => {
    const [communityData, setCommunityData] = useState({ community_title: '커뮤니티 제목', community_content: '커뮤니티 내용' });

    const location = useLocation();
    const community_no = location.state?.community_no;

    {/* 데이터 불러오기 */}
    useEffect(()=>{
        axios.get(`${url}/api/communities/${community_no}`)
        .then(res=>{

        })
        .catch(err=>{

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
                    {true &&  <Button_outlined_style width='5vw' sx={{height:"2vw"}} variant='outlined' onClick={handleModify}>수정</Button_outlined_style>} &nbsp;
                    {true && <Button_contain_style width='5vw' variant='contained' sx={{height:"2vw"}} onClick={handleDelete}>삭제</Button_contain_style>}
                </div>
            </div>
            <div>
                <Comment/>
            </div>
        </>
    );
}

export default CommunityDetail;