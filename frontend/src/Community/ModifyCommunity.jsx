import React, { useEffect, useState } from "react";
import './RegisterCommunityStyle.css';
import ToastEditor from "../Component/ToastEditor";
import Button_outlined_style from "../Component/Button_outlined_style";
import Button_contain_style from "../Component/Button_contain_style";
import { url } from "../url";
import { useSelector } from 'react-redux';
import { useLocation } from "react-router";
import  axios  from "axios";

const RegisterCommunity = () =>{
    const accessToken = useSelector(state => state.persistedReducer.accessToken);
    const location = useLocation();
    const community_no = location.state?.community_no;

    const [registerData, setRegisterData] = useState({community_title: '제목1', community_content:'정보1'});


    {/* 초기 커뮤니티 값 가져오기 */}
    useEffect(()=>{
        axios.get(`${url}/api/communities/${community_no}`)
        .then(res=>{
            console.log(res);
        })
        .catch(err=>{
            console.log(err);
        })
    },[])

    {/* 변경사항 적용 */}
    const setValue = (e) =>{
        const value = e.target.value;
        const name = e.target.name;
        setRegisterData((registerData) => ({ ...registerData, [name]: value }));
    }

    {/* 토스트 에디터 값 받아와 저장 */}
    const getToastEditor = content =>{
        setRegisterData((registerData) => ({ ...registerData, community_content: content}));
    }

    {/* 커뮤니티 등록 */}
    const handleRegister = () =>{
        console.log(registerData);
        axios.post(`${url}/api/communities`, registerData, {
            headers: {
                Authorization: accessToken
            }
        })
        .then(res=>{
            console.log(res);    
        })
        .catch(err=>{
            console.log(err);
        })
    }

    {/* 취소 버튼 클릭 */}
    const handleCancel = () =>{
        // 뒤로가기 - 리스트 페이지로 이동
    }

    return(
        <div className="community-Box">
            <input className="communityregister-title" type='text' placeholder="제목을 작성해 주세요." name="community_title" value={registerData.community_title} onChange={(e)=>setValue(e)}/>
            
            <div className="communityText-Box">
                <ToastEditor getToastEditor={getToastEditor} content={registerData.community_content}/>
            </div>

            {/* 등록 + 취소 버튼 컴포넌트 위치 */}
            <div className="register_button" style={{marginTop:"2%"}}>
            <Button_outlined_style width='5vw' sx={{height:"2vw"}} variant='outlined' onClick={handleRegister()}>
                    등록
                </Button_outlined_style> &nbsp;
                <Button_contain_style width='5vw' sx={{height:"2vw"}} variant='contained' onClick={handleCancel()}>
                    취소
                </Button_contain_style>
            </div>
        </div>
    );
}

export default RegisterCommunity;