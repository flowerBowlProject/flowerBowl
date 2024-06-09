import React, { useEffect, useState } from "react";
import './RegisterCommunityStyle.css';
import ToastEditor from "../Component/ToastEditor";
import { url } from "../url";
import { useDispatch, useSelector } from 'react-redux';
import { useLocation } from "react-router";
import  axios  from "axios";
import ButtonContain from "../Component/ButtonContain";
import { useParams,useNavigate } from 'react-router-dom';
import ButtonOutlined from "../Component/ButtonOutlined";
import ErrorConfirm from "../Hook/ErrorConfirm";
import { editErrorType, openError } from "../persistStore";


const RegisterCommunity = () =>{
    const accessToken = useSelector(state => state.accessToken);
    const location = useLocation();
    const { community_no } = useParams();
    const [registerData, setRegisterData] = useState({});
    const navigator = useNavigate();
    const dispatch = useDispatch();
    const [toastContent, setToastContent] = useState("");


    {/* 초기 커뮤니티 값 가져오기 */}
    useEffect(()=>{
        if(accessToken == ''){
            dispatch(editErrorType('NE'));
            dispatch(openError());
            navigator(`/communityDetail/${community_no}`);
        }

        axios.get(`${url}/api/users/info`,{
            headers:{
                Authorization : `Bearer ${accessToken}`
            }
        })
        .catch(err=>{
            dispatch(editErrorType(err.response.data.code));
            dispatch(openError());
            navigator(`/communityDetail/${community_no}`)
        })
        
        axios.get(`${url}/api/communities/detail/${community_no}`)
        .then(res=>{
            console.log(res);
            setRegisterData(res.data.data);
            setToastContent(res.data.data.community_content);
        })
        .catch(err=>{
            console.log(err);
            dispatch(editErrorType(err.response.data.code));
            dispatch(openError());
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

    const getToastImg = contentImg =>{
        setRegisterData(prevState => ({...prevState, community_file_oname: [...prevState.community_file_oname, contentImg.oname]}));
        setRegisterData(prevState => ({...prevState, community_file_sname: [...prevState.community_file_sname, contentImg.sname]}));
    }

    {/* 커뮤니티 등록 */}
    const handleModify = () =>{
        const isFormDataChanged = () => {
            // 모든 필드가 변경되었는지 여부를 저장할 변수
            let isChanged = false;
          
            // 모든 필드를 순회하면서 변경 여부 확인
            Object.values(registerData).forEach((value) => {
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
                dispatch(editErrorType('MODIFY'));
                dispatch(openError());
                navigator(`/communityDetail/${community_no}`);
            })
            .catch(err => {
                console.log(err);
                dispatch(editErrorType(err.response.data.code));
                dispatch(openError());
            })
        }else{
            {/* 수정 내용 작성 여부 확인 후 alert */}
            if(registerData.community_title.trim() === ''){
                dispatch(editErrorType('TITLE'));
                dispatch(openError());
            }else if(registerData.community_content === ''){
                dispatch(editErrorType('CONTENT'));
                dispatch(openError());
            }else{
                dispatch(editErrorType('error'));
                dispatch(openError());
            }
        }
    }

    {/* 취소 버튼 클릭 */}
    const handleCancel = () =>{
        // 뒤로가기 - 리스트 페이지로 이동
        navigator(`/communityDetail/${community_no}`);
    }

    return(
        <div className="community-Box">
            <ErrorConfirm  error={useSelector(state => state.errorType)} />

            <input className="communityregister-title" type='text' placeholder="제목을 작성해 주세요." name="community_title" value={registerData.community_title} onChange={(e)=>setValue(e)}/>
            
            <div className="communityText-Box">
                <ToastEditor getToastEditor={getToastEditor} getToastImg={getToastImg} setContent={toastContent}/>
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