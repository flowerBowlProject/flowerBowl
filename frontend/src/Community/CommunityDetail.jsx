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
import { Viewer } from "@toast-ui/react-editor";

const CommunityDetail = () => {
    const [communityData, setCommunityData] = useState({});
    const { community_no } = useParams();
    const writer= useSelector((state)=>state.nickname);
    const location = useLocation();
    const navigator = useNavigate();
    const accessToken = useSelector(state => state.accessToken);

    {/* 데이터 불러오기 */}
    useEffect(()=>{
        axios.get(`${url}/api/communities/detail/${community_no}`)
        .then(res=>{
            setCommunityData(res.data.data);
        })
        .catch(err=>{
            console.log(err);
        })
    },[])

    {/* 커뮤니티 수정 */}
    const handleModify = () =>{
        navigator('/modifyCommunity/' + community_no);
    }

    {/* 커뮤니티 삭제 */}
    const handleDelete = () =>{
        console.log(accessToken)
        {/* alert 창 띄우기 */}
        axios.delete(`${url}/api/communities/${community_no}`,
        {
            headers:{
                Authorization: `Bearer ${accessToken}`
            }
        })
        .then(res=>{
            console.log(res);
            {/* 삭제 alert 창 띄우기 */}
            navigator('/communityList');
        })
        .catch(err=>{
            console.log(err);
        })
    }

    return (
        <>
            <div className="communityDetail-Box">
                <div className="community-title"> {communityData.community_title} </div>
                <div className='community-body'>{communityData.community_content && <Viewer initialValue={communityData.community_content} />}</div>
                {/* 수정/삭제 버튼 - 작성자인 경우에만 true로 버튼 표시 */}
                <div className="community-change">
                    {writer === communityData.community_writer &&  <ButtonOutlined size='large' text='수정' handleClick={handleModify}/>} &nbsp;
                    {writer === communityData.community_writer && <ButtonContain size='large' text='삭제'handleClick={handleDelete}/>}
                </div>
            </div>
            <div>
                <Comment typeString={2} no={community_no}/>
            </div>
        </>
    );
}

export default CommunityDetail;