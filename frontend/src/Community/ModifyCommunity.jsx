import React, { useEffect, useState } from "react";
import './RegisterCommunityStyle.css';
import ToastEditor from "../Component/ToastEditor";
import { url } from "../url";
import { useSelector } from 'react-redux';
import { useLocation } from "react-router";
import  axios  from "axios";
import ButtonContain from "../Component/ButtonContain";
import { useParams,useNavigate } from 'react-router-dom';
import ButtonOutlined from "../Component/ButtonOutlined";


const RegisterCommunity = () =>{
    const accessToken = useSelector(state => state.accessToken);
    const location = useLocation();
    const { community_no } = useParams();
    const [registerData, setRegisterData] = useState({});
    const navigator = useNavigate();


    {/* 초기 커뮤니티 값 가져오기 */}
    useEffect(()=>{
        axios.get(`${url}/api/communities/detail/${community_no}`)
        .then(res=>{
            console.log(res);
            setRegisterData(res.data.data);
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
    const handleModify = () =>{
        const isFormDataChanged = () => {
            // 모든 필드가 변경되었는지 여부를 저장할 변수
            let isChanged = false;
          
            // 모든 필드를 순회하면서 변경 여부 확인
            Object.values(registerData).forEach((value) => {
              // 빈 값이거나 초기값이 아닌 경우 변경된 것으로 간주
              if (value !== '' && value !== 0  ) {
                isChanged = true;
              }
            });
          
            return isChanged;
        };

        if(isFormDataChanged()){
            axios.put(`${url}/api/communities/${community_no}`, registerData, {
            headers: {
                Authorization: `Bearer ${accessToken}`
            }
        })
            .then(res => {
                navigator(`/communityDetail/${community_no}`);
            })
            .catch(err => {
                console.log(err);
            })
        }else{
            {/* 등록 내용 작성 여부 확인 후 alert */}
            if(registerData.community_title.trim() === ''){
                console.log('제목을 작성해 주세요.');
            }else if(registerData.community_content === ''){
                console.log('내용을 작성해 주세요');
            }else{
                console.log('관리자에게 문의해 주세요')
            }
        }
    }

    {/* 취소 버튼 클릭 */}
    const handleCancel = () =>{
        // 뒤로가기 - 리스트 페이지로 이동
        navigator(`/classDetail/${community_no}`);
    }

    return(
        <div className="community-Box">
            <input className="communityregister-title" type='text' placeholder="제목을 작성해 주세요." name="community_title" value={registerData.community_title} onChange={(e)=>setValue(e)}/>
            
            <div className="communityText-Box">
                <ToastEditor getToastEditor={getToastEditor} setContent={registerData.community_content}/>
            </div>

            {/* 등록 + 취소 버튼 컴포넌트 위치 */}
            <div className="register_button" style={{marginTop:"2%"}}>
            <ButtonOutlined size='large' text='수정' handleClick={handleModify}/> &nbsp;
            <ButtonContain size='large' text='취소' handleClick={handleCancel}/>
            </div>
        </div>
    );
}

export default RegisterCommunity;