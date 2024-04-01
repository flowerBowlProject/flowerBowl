import React, { useState } from "react";
import './RegisterCommunityStyle.css';
import ToastEditor from "../Component/ToastEditor";
import { Editor } from "@toast-ui/react-editor";

const RegisterCommunity = () =>{
    const [registerData, setRegisterData] = useState({community_title: '', community_content:''});

    return(
        <div className="community-Box">
            <input className="community-title" type='text' placeholder="제목을 작성해 주세요."/>
            
            <div className="communityText-Box">
                <ToastEditor/>
            </div>

            {/* 등록 + 취소 버튼 컴포넌트 위치 */}
            
        </div>
    );
}

export default RegisterCommunity;