import React, { useState } from "react";
import './RegisterClassStyle.css';

import Category from '../Component/Category';
import ToastEditor from "../Component/ToastEditor";

import dayjs from 'dayjs';
import { DemoContainer, DemoItem } from '@mui/x-date-pickers/internals/demo';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';

import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { TextField } from "@mui/material";

import axios from 'axios';
import Map from "../Map/Map";
import AddressSearch from "./AddressSearch";

const RegisterClass = () => {
    {/* 등록 클래스 데이터 + 썸네일 + 썸네일 선택 여부 */ }
    const [registerData, setRegisterData] = useState({
        lesson_title: '', lesson_category: '', lesson_price: 0,
        lesson_address: '', lesson_contents: '', lesson_longitude: 0.0, lesson_latitude: 0.0, lesson_start: '', lesson_end: ''
    });
    const [thumbnail, setThumbnail] = useState(null);
    const [selectedFile, setSelectedFile] = useState(null);

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
        console.log(category);
        setRegisterData((registerData) => ({ ...registerData, lesson_category: category }));
    }

    {/* 주소 및 위도/경도 받아와 저장 */}
    const getAddress = address => {
        console.log(address);
        setRegisterData((registerData) => ({ ...registerData, lesson_address: address.lesson_address }));
        setRegisterData((registerData) => ({ ...registerData, lesson_longitude: address.lesson_longitude }));
        setRegisterData((registerData) => ({ ...registerData, lesson_latitude: address.lesson_latitude }));
        console.log(registerData);
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
                <input className="register-title" type='text' placeholder="제목을 작성해 주세요." />

                {/* 카테고리 선택 */}
                <Category getCategory={getCategory} />
            </div>

            <div className="ClassText-Box">
                {/* 가격 + 날짜 + 장소 작성란 */}
                <div className="classElement-Box">
                    {/* 가격 */}
                    <div className="classPrice">
                        <TextField id="outlined-basic" label="가격" variant="outlined" type="number"
                            sx={{ width: '12vw' }} onChange={(value) => setRegisterData((registerData) => ({ ...registerData, lesson_price: value }))} />
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

                    {/* 장소 */}
                    <AddressSearch getAddress={getAddress}/>


                </div>

                {/* 레시피 || 클래스 상세 내용 작성란 */}
                <ToastEditor />
            </div>

            {/* 조이님이 만들어주신 공통 컴포넌트 버튼 추가 필요 -> 등록 / 취소 버튼 */}
            버튼
        </div>
    );
}

export default RegisterClass;