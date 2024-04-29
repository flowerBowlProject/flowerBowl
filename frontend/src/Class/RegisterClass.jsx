import React, { useState } from "react";
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
import ButtonContain from "../Component/ButtonContain";
import dayjs from "dayjs";
import ButtonOutlined from "../Component/ButtonOutlined";
import { useNavigate } from "react-router-dom";

const RegisterClass = () => {
    const accessToken = useSelector(state => state.accessToken);
    const navigator = useNavigate();

    {/* 등록 클래스 데이터 + 썸네일 + 썸네일 선택 여부 */ }
    const [registerData, setRegisterData] = useState({
        lesson_title: '', lesson_category: '', lesson_price: 0, lesson_sname:'짜장면', lesson_oname:'짜장면',
        lesson_addr: '', lesson_content: '', lesson_longitude: 0.0, lesson_latitude: 0.0, lesson_start: '', lesson_end: '', lesson_URL: ''
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
    };

    {/* 선택한 카테고리 값 받아와 저장 */ }
    const getCategory = category => {
        setRegisterData((registerData) => ({ ...registerData, lesson_category: category }));
    }

    {/* 주소 및 위도/경도 받아와 저장 */ }
    const getAddress = address => {
        setRegisterData((registerData) => ({ ...registerData, lesson_addr: address.lesson_address }));
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

    {/* 클래스 등록 */}
    const handleRegister = () =>{
       {/* 등록 내용 작성 여부 확인 후 alert */}
       if(registerData.lesson_title === ''){
            console.log('제목을 작성해 주세요.')
        }else if(registerData.lesson_category === ''){
            console.log('카테고리를 선택해 주세요')
        }else if(registerData.lesson_sname === '' || registerData.lesson_oname === ''){
            console.log('사진을 첨부해 주세요')
        }else if(registerData.lesson_addr === '' || registerData.lesson_latitude === 0.0 || registerData.lesson_longitude ===0.0){
            console.log('주소를 입력 후 주소 등록 버튼을 눌러주세요')
        }else if(registerData.lesson_content === ''){
            console.log('내용을 작성해 주세요')
        }else if(registerData.lesson_start === ''){
            console.log('시작일을 선택해 주세요')
        }else if(registerData.lesson_end === ''){
            console.log('종료일을 선택해 주세요')
        }else if(registerData.lesson_URL === ''){
            console.log('문의 채팅 링크를 입력해 주세요')
        }else{
            console.log("등록 가능");
            axios.post(`${url}/api/lessons`, registerData, {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            })
            .then(res=>{
                console.log(res);
                navigator('/classList');
            })
            .catch(err=>{
                console.log(err);
            })
        }
    }

    {/* 취소 버튼 클릭 */}
    const handleCancel = () =>{
        // 뒤로가기 - 리스트 페이지로 이동 - 현재는 리스트로 이동하도록 구현
        navigator('/classList');
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
                <AddressSearch getAddress={getAddress} />
                <TextField id="outlined-basic" type="text" label="오픈 채팅" variant="outlined" readOnly sx={{ marginLeft: '1vw', width: '40vw' }} placeholder='오픈채팅 링크를 입력해 주세요.'
                    name="lesson_URL" onChange={(e)=> setValue(e)} color="secondary"/>
            </div>

            {/* 레시피 || 클래스 상세 내용 작성란 */}
            <ToastEditor getToastEditor={getToastEditor} setContent={''}/>

            <div style={{ border: "1px solid #CBA285", marginBottom: "2%" }} />
            <div className="register_button">
            <ButtonOutlined size='large' text='등록' handleClick={(e)=>handleRegister(e)}/> &nbsp;
            <ButtonContain size='large' text='취소' handleClick={handleCancel}/>
            </div>
        </div>
    );
}

export default RegisterClass;