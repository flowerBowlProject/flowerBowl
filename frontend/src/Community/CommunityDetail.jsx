import React, { useEffect, useState } from "react";
import './CommunityDetailStyle.css';
import { useLocation } from "react-router";
import { useSelector } from 'react-redux';
import { useParams,useNavigate } from 'react-router-dom';
import  axios  from "axios";
import { url } from "../url";
import Comment from "../Component/Comment/Comment";
import ButtonContain from "../Component/ButtonContain";
import ButtonOutlined from "../Component/ButtonOutlined";

const CommunityDetail = () => {
    const [communityData, setCommunityData] = useState({});

    const location = useLocation();
    const navigate = useNavigate();
    const accessToken = useSelector(state => state.accessToken);
    const { community_no } = useParams();

    {/* 데이터 불러오기 */}
    useEffect(()=>{
        axios.get(`${url}/api/communities/detail/${community_no}`)
        .then(res=>{
            console.log(res);
            setCommunityData()
        })
        .catch(err=>{
            console.log(err);
        })
    },[])

    {/* 커뮤니티 수정 */}
    const handleModify = (e) =>{
        e.preventDefault();
        navigate('/modifyCommunity/' + community_no);
    }

    {/* 커뮤니티 삭제 */}
    const handleDelete = () =>{
        {/* alert 창 띄우기 */}
        axios.delete(`${url}/api/communities/${community_no}`,
        {
            headers:{
                Authorization: accessToken
            }
        })
        .then(res=>{

        })
        .catch(err=>{

        })
    }

    return (
        <>
            <div className="communityDetail-Box">
                <div className="community-title"> {communityData.communityTitle} </div>
                <div className='community-body'>{communityData.communityContent}</div>
                {/* 수정/삭제 버튼 - 작성자인 경우에만 true로 버튼 표시 */}
                <div className="community-change">
                    {true &&  <ButtonOutlined size='large' text='수정' handleClick={handleModify}/>} &nbsp;
                    {true && <ButtonContain size='large' text='삭제'handleClick={handleDelete}/>}
                </div>
            </div>
            <div>
                <Comment/>
            </div>
        </>
    );
}

export default CommunityDetail;