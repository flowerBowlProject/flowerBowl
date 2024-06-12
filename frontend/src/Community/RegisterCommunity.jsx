import React, { useEffect, useState } from "react";
import './RegisterCommunityStyle.css';
import ToastEditor from "../Component/ToastEditor";
import { url } from "../url";
import { useDispatch, useSelector } from 'react-redux';
import axios from "axios";
import ButtonContain from "../Component/ButtonContain";
import ButtonOutlined from "../Component/ButtonOutlined";
import { useNavigate } from "react-router-dom";
import { editErrorType, openError } from "../persistStore";
import ErrorConfirm from "../Hook/ErrorConfirm";

const RegisterCommunity = () => {
    const accessToken = useSelector(state => state.accessToken);
    const navigator = useNavigate();
    const dispatch = useDispatch();

    const [registerData, setRegisterData] = useState({ community_title: '', community_content: '', community_file_oname:[], community_file_sname:[] });

    useEffect(()=>{
        axios.get(`${url}/api/users/info`,{
            headers:{
                Authorization : `Bearer ${accessToken}`
            }
        })
        .catch(err=>{
            dispatch(editErrorType(err.response.data.code));
            dispatch(openError());
            navigator('/communityList');
        })
    })

    {/* 토스트 에디터 값 받아와 저장 */ }
    const getToastEditor = content => {
        setRegisterData((registerData) => ({ ...registerData, community_content: content }));
    }

    const getToastImg = contentImg =>{
        setRegisterData(prevState => ({...prevState, community_file_oname: [...prevState.community_file_oname, contentImg.oname]}));
        setRegisterData(prevState => ({...prevState, community_file_sname: [...prevState.community_file_sname, contentImg.sname]}));
    }

    {/* 변경사항 적용 */ }
    const setValue = (e) => {
        const value = e.target.value;
        const name = e.target.name;
        setRegisterData((registerData) => ({ ...registerData, [name]: value }));
    }

    {/* 커뮤니티 등록 */ }
    const handleRegister = () => {

        if(registerData.community_title.trim() === ''){
            dispatch(editErrorType('TITLE'));
            dispatch(openError());
        }else if(registerData.community_content === ''){
            dispatch(editErrorType('CONTENT'));
            dispatch(openError());
        }else{
            axios.post(`${url}/api/communities`, registerData, {
            headers: {
                Authorization: `Bearer ${accessToken}`
            }
            })
            .then(res => {
                console.log(res);
                dispatch(editErrorType('REGISTER'));
                dispatch(openError());
                navigator('/communityList');
            })
            .catch(err => {
                console.log(err);
                dispatch(editErrorType(err.response.data.code));
                dispatch(openError());
            })
        }
    }

    {/* 취소 버튼 클릭 */ }
    const handleCancel = () => {
        // 뒤로가기 - 리스트 페이지로 이동
        navigator('/communityList');
    }

    return (
        <div className="community-Box">
            <ErrorConfirm error={useSelector(state => state.errorType)} />

            <input className="communityregister-title" type='text' placeholder="제목을 작성해 주세요." name="community_title" onChange={(e)=>setValue(e)}/>

            <div className="communityText-Box">
                <ToastEditor getToastEditor={getToastEditor} getToastImg={getToastImg} setContent={''}/>
            </div>

            {/* 등록 + 취소 버튼 컴포넌트 위치 */}
            <div className="register_button" style={{ marginTop: "2%" }}>
            <ButtonOutlined size='large' text='등록' handleClick={handleRegister}/> &nbsp;
            <ButtonContain size='large' text='취소' handleClick={handleCancel}/>
            </div>
        </div>
    );
}

export default RegisterCommunity;