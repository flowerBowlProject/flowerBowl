import React, { useState } from "react";
import './RegisterCommunityStyle.css';
import ToastEditor from "../Component/ToastEditor";
import { url } from "../url";
import { useSelector } from 'react-redux';
import axios from "axios";
import ButtonContain from "../Component/ButtonContain";

const RegisterCommunity = () => {
    const accessToken = useSelector(state => state.persistedReducer.accessToken);

    const [registerData, setRegisterData] = useState({ community_title: '', community_content: '' });

    {/* 토스트 에디터 값 받아와 저장 */ }
    const getToastEditor = content => {
        setRegisterData((registerData) => ({ ...registerData, community_content: content }));
    }

    {/* 변경사항 적용 */ }
    const setValue = (e) => {
        const value = e.target.value;
        const name = e.target.name;
        setRegisterData((registerData) => ({ ...registerData, [name]: value }));
    }

    {/* 커뮤니티 등록 */ }
    const handleRegister = () => {
        console.log(registerData);
        axios.post(`${url}/api/communities`, registerData, {
            headers: {
                Authorization: accessToken
            }
        })
            .then(res => {
                console.log(res);
            })
            .catch(err => {
                console.log(err);
            })
    }

    {/* 취소 버튼 클릭 */ }
    const handleCancel = () => {
        // 뒤로가기 - 리스트 페이지로 이동
    }

    return (
        <div className="community-Box">
            <input className="communityregister-title" type='text' placeholder="제목을 작성해 주세요." name="community_title" onChange={(e)=>setValue(e)}/>

            <div className="communityText-Box">
                <ToastEditor getToastEditor={getToastEditor} />
            </div>

            {/* 등록 + 취소 버튼 컴포넌트 위치 */}
            <div className="register_button" style={{ marginTop: "2%" }}>
            <ButtonContain size='large' text='로그인'/> &nbsp;
            <ButtonContain size='large' text='로그인'/>
            </div>
        </div>
    );
}

export default RegisterCommunity;