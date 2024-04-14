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
import { useSelector } from 'react-redux';
import { useLocation } from "react-router";
import ButtonContain from "../Component/ButtonContain";

const RegisterClass = () => {
    const accessToken = useSelector(state => state.persistedReducer.accessToken);
    const location = useLocation();
    const lesson_no = location.state?.lesson_no;

    {/* 등록 클래스 데이터 + 썸네일 + 썸네일 선택 여부 */ }
    const [registerData, setRegisterData] = useState({
        lesson_title: '제목1', lesson_category: '밥', lesson_price: 0, lesson_sname:'', lesson_oname:'',
        lesson_address: '주소1', lesson_content: '<div>내용1</div>', lesson_longitude:1.1, lesson_latitude: 1.1, lesson_start: '', lesson_end: '', lesson_URL: 'asd'
    });
    const [thumbnail, setThumbnail] = useState(null);
    const [selectedFile, setSelectedFile] = useState(null);
    

    useEffect(()=>{
        {/* 수정할 정보 가져와 세팅 */}
        axios.get(`${url}/lesson/user/${lesson_no}`)
        .then(res=>{

        })
        .catch(err=>{

        })
    })

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
    };

    {/* 선택한 카테고리 값 받아와 저장 */ }
    const getCategory = category => {
        setRegisterData((registerData) => ({ ...registerData, lesson_category: category }));
    }

    {/* 주소 및 위도/경도 받아와 저장 */ }
    const getAddress = address => {
        setRegisterData((registerData) => ({ ...registerData, lesson_address: address.lesson_address }));
        setRegisterData((registerData) => ({ ...registerData, lesson_longitude: address.lesson_longitude }));
        setRegisterData((registerData) => ({ ...registerData, lesson_latitude: address.lesson_latitude }));
    }

    {/* 토스트 에디터 값 받아와 저장 */}
    const getToastEditor = content =>{
        setRegisterData((registerData) => ({ ...registerData, lesson_content: content}));
    }

    {/* 그 외의 변경사항 적용 */}
    const setValue = (e) =>{
        const value = e.target.value;
        const name = e.target.name;
        setRegisterData((registerData) => ({ ...registerData, [name]: value }));
    }

    {/* 클래스 수정 */}
    const handleRegister = () =>{
        axios.post(`${url}/api/lessons/${lesson_no}`, registerData, {
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

    return (
        <div className="registerClass-Box">

            {/* 썸네일 선택 - 다시 클릭 시 재선택 */}
            <div style={{ display: 'flex', alignItems: 'flex-end' }}>
                <div className='thumbnail'>
                    {thumbnail ? (
                        <label>
                            <img className='thumbImg-preview' src={thumbnail} alt='Thumbnail Preview' onClick={() => setThumbnail(null)} />
                        </label>
                    ) : (
                        <label className='register-thumbnail'>
                            썸네일 선택
                            <input type='file' name='thumbImg' onChange={chooseThumbnail} />
                        </label>)}
                </div>
                <input className="register-title" type='text' placeholder="제목을 작성해 주세요." name="lesson_title" onChange={(e)=> setValue(e)} value={registerData.lesson_title}/>

                {/* 카테고리 선택 */}
                <Category getCategory={getCategory} category={registerData.lesson_category}/>
            </div>

            {/* 가격 + 날짜 작성란 */}
            <div className="classElement1-Box">
                {/* 가격 */}
                <div className="classPrice">
                    <TextField id="outlined-basic" variant="outlined" type="number" value={registerData.lesson_price}
                        sx={{ width: '12vw' }} name="lesson_price" onChange={(e)=> setValue(e)} />
                </div>
                {/* 날짜 */}
                <div className="calendar-Box">
                    <LocalizationProvider dateAdapter={AdapterDayjs}>
                        <DemoContainer components={['DatePicker', 'DatePicker']}>
                            <DatePicker label="시작일"
                                onChange={(newValue) => setRegisterData((registerData) => ({ ...registerData, lesson_start: newValue }))}
                            />
                            <DatePicker
                                label="마감일"
                                onChange={(newValue) => setRegisterData((registerData) => ({ ...registerData, lesson_end: newValue }))}
                            />
                        </DemoContainer>
                    </LocalizationProvider>
                </div>
            </div>

            {/* 장소 */}
            <div className="classElement2-Box">
                <AddressSearch getAddress={getAddress} address={registerData.lesson_address}/>
                <TextField id="outlined-basic" type="text" variant="outlined" readOnly sx={{ marginLeft: '1vw', width: '40vw' }} placeholder='오픈채팅 링크를 입력해 주세요.'
                    name="lesson_URL" onChange={(e)=> setValue(e)} value={registerData.lesson_URL} />
            </div>

            {/* 레시피 || 클래스 상세 내용 작성란 */}
            <ToastEditor getToastEditor={getToastEditor} content={registerData.lesson_content}/>

            <div style={{ border: "1px solid #CBA285", marginBottom: "2%" }} />
            <div className="register_button">
            <ButtonContain size='large' text='로그인'/> &nbsp;
            <ButtonContain size='large' text='로그인'/>
            </div>
        </div>
    );
}

export default RegisterClass;