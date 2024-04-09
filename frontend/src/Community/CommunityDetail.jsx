import React, { useState } from "react";
import './CommunityDetailStyle.css';
import TurnedInNotIcon from '@mui/icons-material/TurnedInNot';
import TurnedInIcon from '@mui/icons-material/TurnedIn';
import Inputbutton from "../Component/Input/Inputbutton";

const CommunityDetail = () => {
    const [communityData, setCommunityData] = useState({ community_title: '커뮤니티 제목', community_content: '커뮤니티 내용' });

    return (
        <>
            <div className="communityDetail-Box">
                <div className="community-title"> {communityData.community_title} </div>
                <div className='community-body'>{communityData.community_content}</div>
                {/* 즐겨찾기 버튼 - 즐겨찾기 여부에 따른 true / false로 아이콘 표시 */}
                <div className="community-bookmark">{true ? <TurnedInNotIcon sx={{ fontSize: '60px', color: 'main.or'}}/> : 
                <TurnedInIcon sx={{ fontSize: '60px', color: 'main.or'}}/>} 스크랩 </div>
                {/* 수정/삭제 버튼 - 작성자인 경우에만 true로 버튼 표시 */}
                <div className="community-change">
                    {true && <Inputbutton variant="outlined" text="수정" i= {false} w="medium-large"/>} &nbsp;
                    {true && <Inputbutton variant="outlined" text="삭제" i= {true} w="medium-large"/>}
                </div>
            </div>
            <div>
                {/* 댓글 */}
            </div>
        </>
    );
}

export default CommunityDetail;