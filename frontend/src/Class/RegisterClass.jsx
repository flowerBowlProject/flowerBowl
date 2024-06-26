import React, { useEffect, useState } from "react";
import './RegisterClassStyle.css';

import Category from '../Component/Category';
import ToastEditor from "../Component/ToastEditor";
import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { TextField } from "@mui/material";
import AddressSearch from "./AddressSearch";
import  axios  from "axios";
import { url } from "../url";
import { useDispatch, useSelector } from 'react-redux';
import ButtonContain from "../Component/ButtonContain";
import dayjs from "dayjs";
import ButtonOutlined from "../Component/ButtonOutlined";
import { useNavigate } from "react-router-dom";
import { editErrorType, openError } from "../persistStore";
import ErrorConfirm from "../Hook/ErrorConfirm";

const RegisterClass = () => {
    const accessToken = useSelector(state => state.accessToken);
    const navigator = useNavigate();
    const dispatch = useDispatch();

    useEffect(()=>{
        axios.get(`${url}/api/users/info`,{
            headers:{
                Authorization : `Bearer ${accessToken}`
            }
        })
        .catch(err=>{
            dispatch(editErrorType(err.response.data.code));
            dispatch(openError());
            navigator('/classList')
        })
    })

    {/* 등록 클래스 데이터 + 썸네일 + 썸네일 선택 여부 */ }
    const [registerData, setRegisterData] = useState({
        lesson_title: '', lesson_category: '', lesson_price: 0, lesson_sname:'', lesson_oname:'',
        lesson_addr: '', lesson_content: '', lesson_longitude: 0.0, lesson_latitude: 0.0, lesson_start: '', lesson_end: '', lesson_URL: '',
        lesson_file_sname:[], lesson_file_oname:[]
    });
    const [thumbnail, setThumbnail] = useState(null);
    const [selectedFile, setSelectedFile] = useState(null);

    {/* 썸네일 선택 및 변경 - 사진 업로드 과정 추가 필요 */}
    const chooseThumbnail = (e) => {
        const file = e.target.files[0];

        if (file) {
            setSelectedFile(file);
            const thumbnailURL = URL.createObjectURL(file);
            setThumbnail(thumbnailURL);
        } else {
            setThumbnail(null);
            setSelectedFile(null);
        }

        const formData = new FormData();
        formData.append('file', file);
        axios.post(`${url}/api/images/thumbnail`, formData,{
            headers:{
                Authorization : `Bearer ${accessToken}`,
                'Content-Type': 'multipart/form-data',
            }
        })
        .then(res=>{
            console.log(res);
            setRegisterData((registerData)=>({...registerData, lesson_sname:res.data.thumbnail_sname}));
            setRegisterData((registerData)=>({...registerData, lesson_oname:res.data.thumbnail_oname}))
        })
        .catch(err=>{
            console.log(err);
        })
    };

    {/* 선택한 카테고리 값 받아와 저장 */ }
    const getCategory = category => {
        setRegisterData((registerData) => ({ ...registerData, lesson_category: category }));
    }

    {/* 주소 및 위도/경도 받아와 저장 */ }
    const getAddress = address => {
        setRegisterData((registerData) => ({ ...registerData, lesson_addr: address.lesson_address }));
    }
    
    const getAddressXY = addressXY =>{
        setRegisterData((registerData) => ({ ...registerData, lesson_longitude: addressXY.lesson_longitude }));
        setRegisterData((registerData) => ({ ...registerData, lesson_latitude: addressXY.lesson_latitude }));
    }

    {/* 토스트 에디터 값 받아와 저장 */}
    const getToastEditor = contentData =>{
        setRegisterData((registerData) => ({ ...registerData, lesson_content: contentData}));
    }

    const getToastImg = contentImg =>{
        setRegisterData(prevState => ({...prevState, lesson_file_oname: [...prevState.lesson_file_oname, contentImg.oname]}));
        setRegisterData(prevState => ({...prevState, lesson_file_sname: [...prevState.lesson_file_sname, contentImg.sname]}));
    }

    {/* 그 외의 변경사항 적용 */}
    const setValue = (e) =>{
        const value = e.target.value;
        const name = e.target.name;
        setRegisterData((registerData) => ({ ...registerData, [name]: value }));
    }

    {/* 클래스 등록 */}
    const handleRegister = () =>{
        console.log(registerData);
        {/* 수정 내용 작성 여부 확인 후 alert */ }
        if (registerData.lesson_title === '') {
            dispatch(editErrorType('TITLE'));
            dispatch(openError());
        } else if (registerData.lesson_category === '') {
            dispatch(editErrorType('CATEGORY'));
            dispatch(openError());
        } else if (registerData.lesson_sname === '' || registerData.lesson_oname === '') {
            dispatch(editErrorType('THUMBNAIL'));
            dispatch(openError());
        } else if (registerData.lesson_address === null || registerData.lesson_latitude === 0.0 || registerData.lesson_longitude === 0.0) {
            dispatch(editErrorType('ADDRESS'));
            dispatch(openError());
        } else if (registerData.lesson_content && registerData.lesson_content === '') {
            dispatch(editErrorType('CONTENT'));
            dispatch(openError());
        } else if (registerData.lesson_start === '') {
            dispatch(editErrorType('STARTDATE'));
            dispatch(openError());
        } else if (registerData.lesson_end === '') {
            dispatch(editErrorType('ENDDATE'));
            dispatch(openError());
        } else if (registerData.lesson_URL.trim() === '') {
            dispatch(editErrorType('LINK'));
            dispatch(openError());
        } else {
            console.log("등록 가능");
            console.log(registerData);
            axios.post(`${url}/api/lessons`, registerData, {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            })
            .then(res=>{
                console.log(res);
                dispatch(editErrorType('REGISTER'));
                dispatch(openError());
                navigator('/classList');
            })
            .catch(err=>{
                console.log(err);
                dispatch(editErrorType(err.response.data.code));
                dispatch(openError());
            })
        }
    }

    const thumbDelete = () =>{
        setThumbnail(null);
        setRegisterData((registerData) => ({ ...registerData, lesson_sname: ""}));
        setRegisterData((registerData) => ({ ...registerData, lesson_oname: ""}));
    }

    {/* 취소 버튼 클릭 */}
    const handleCancel = () =>{
        // 뒤로가기 - 리스트 페이지로 이동 - 현재는 리스트로 이동하도록 구현
        navigator('/classList');
    }

    return (
        <div className="registerClass-Box">
            <ErrorConfirm error={useSelector(state => state.errorType)} />

            {/* 썸네일 선택 - 다시 클릭 시 재선택 */}
            <div style={{ display: 'flex', alignItems: 'flex-end' }}>
                <div className='thumbnail'>
                    {thumbnail ? (
                        <label>
                            <img className='thumbImg-preview' src={thumbnail} alt='Thumbnail Preview' onClick={thumbDelete} />
                        </label>
                    ) : (
                        <label className='register-thumbnail'>
                            썸네일 선택
                            <input type='file' name='thumbImg' onChange={chooseThumbnail} />
                        </label>)}
                </div>
                <input className="register-title" type='text' placeholder="제목을 작성해 주세요." name="lesson_title" onChange={(e)=> setValue(e)}/>

                {/* 카테고리 선택 */}
                <Category getCategory={getCategory} />
            </div>

            {/* 가격 + 날짜 작성란 */}
            <div className="classElement1-Box">
                {/* 가격 */}
                <div className="classPrice">
                    <TextField id="outlined-basic" label="가격" variant="outlined" type="number" color="secondary"
                        sx={{ width: '12vw' }} name="lesson_price" onChange={(e)=> setValue(e)} />
                </div>
                {/* 날짜 */}
                <div className="calendar-Box">
                    <LocalizationProvider dateAdapter={AdapterDayjs}>
                        <DemoContainer components={['DatePicker', 'DatePicker']}>
                            <DatePicker label="시작일"
                                onChange={(newValue) => {
                                    const formatDate = dayjs(newValue).format('YYYY-MM-DD');
                                    setRegisterData((registerData) => ({ ...registerData, lesson_start: formatDate }))}
                                }
                            />
                            <DatePicker
                                label="마감일"
                                onChange={(newValue) => {
                                    const formatDate = dayjs(newValue).format('YYYY-MM-DD');
                                    setRegisterData((registerData) => ({ ...registerData, lesson_end: formatDate }))}
                                }                            />
                        </DemoContainer>
                    </LocalizationProvider>
                </div>
            </div>

            {/* 장소 */}
            <div className="classElement2-Box">
                <AddressSearch getAddress={getAddress} getAddressXY={getAddressXY}/>
                <TextField id="outlined-basic" type="text" label="오픈 채팅" variant="outlined" readOnly sx={{ marginLeft: '1vw', width: '40vw' }} placeholder='오픈채팅 링크를 입력해 주세요.'
                    name="lesson_URL" onChange={(e)=> setValue(e)} color="secondary"/>
            </div>

            {/* 레시피 || 클래스 상세 내용 작성란 */}
            <ToastEditor getToastEditor={getToastEditor} getToastImg={getToastImg} setContent={''}/>

            <div style={{ border: "1px solid #CBA285", marginBottom: "2%" }} />
            <div className="register_button">
            <ButtonOutlined size='large' text='등록' handleClick={(e)=>handleRegister(e)}/> &nbsp;
            <ButtonContain size='large' text='취소' handleClick={handleCancel}/>
            </div>
        </div>
    );
}

export default RegisterClass;