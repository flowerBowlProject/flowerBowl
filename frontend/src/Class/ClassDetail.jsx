import React, { useEffect, useState } from "react";
import './ClassDetailStyle.css';
import TurnedInNotIcon from '@mui/icons-material/TurnedInNot';
import TurnedInIcon from '@mui/icons-material/TurnedIn';
import  axios  from "axios";
import { useLocation } from "react-router";
import { url } from "../url";
import ButtonContain from "../Component/ButtonContain";

const { kakao } = window;

const ClassDetail = () => {
    const [classData, setClassData] = useState({
        lesson_title: '클래스 제목', lesson_sname: '', lesson_writer: '작성자1', lesson_price: 1000, lesson_URL: 'http://kakao.chat/102552',
        lesson_start: '2024-03-19', lesson_end: '2024-04-19', lesson_category: '밥', lesson_content: '클래스 내용', lesson_like_status: false,
        lesson_addr: '서울특별시 강서구', lesson_longitude: 126.9779692, lesson_latitude: 37.566535
    });

    const location = useLocation();
    const lesson_no = location.state?.lesson_no;

    useEffect(() => {
        // 이미지 지도에서 마커가 표시될 위치입니다 
        var markerPosition = new kakao.maps.LatLng(classData.lesson_latitude,classData.lesson_longitude);

        // 이미지 지도에 표시할 마커입니다
        // 이미지 지도에 표시할 마커는 Object 형태입니다
        var marker = {
            position: markerPosition
        };

        var staticMapContainer = document.getElementById('staticMap'), // 이미지 지도를 표시할 div  
            staticMapOption = {
                center: new kakao.maps.LatLng(classData.lesson_latitude,classData.lesson_longitude), // 이미지 지도의 중심좌표
                level: 3, // 이미지 지도의 확대 레벨
                marker: marker // 이미지 지도에 표시할 마커 
            };

        // 이미지 지도를 생성합니다
        var staticMap = new kakao.maps.StaticMap(staticMapContainer, staticMapOption);
    }, [])

    useEffect(()=>{
        {/* 정보 가져오기 - 로그인 여부에 따라 요청 axios 호출 조건문 필요 */}
        axios.get(`${url}/lesson/user/${lesson_no}`)
        .then(res=>{

        })
        .catch(err=>{

        })
    },[])

    {/* 클래스 구매 */}
    const buyClass = () =>{
    }

    {/* 클래스 수정 */}
    const handleModify = () =>{
    }

    {/* 클래스 삭제 */}
    const handleDelete = () =>{
    }

    return (
        <>
            <div className="classDetail-Box">
                {/* 이미지 조회 */}
                <div className="class-Img">
                    {/* 썸네일 첨부 필요 */}
                </div>
                <div className="class-element">
                    <div style={{float:"left", textAlign:"center"}}>
                        <div className="class-title"> {classData.lesson_title} </div>
                        <div className="class-category" style={{color:"#B9835C"}}> category : {classData.lesson_category} </div>
                    </div>
                    <div className="class-writerDateURL">
                        <div>
                            by {classData.lesson_writer} &nbsp; {classData.lesson_start} ~ {classData.lesson_end}
                        </div>
                        <a className="class-url" href={classData.lesson_URL}>문의 : {classData.lesson_URL}</a>
                    </div>
                </div>

                {/* 가격 + 오픈채팅 링크 + 장소 (지도 api)*/}
                <div className="class-map">
                    <div id="staticMap" style={{ width: '100%', height: '20vw' }}></div>
                </div>
                <div className="class-addr">
                    장소 : {classData.lesson_addr}
                </div>
                <div className='class-body'>{classData.lesson_content}</div>

                {/* 즐겨찾기 버튼 - 즐겨찾기 여부에 따른 true / false로 아이콘 표시 */}
                <div className="class-bookmark">{classData.lesson_like_status ? <TurnedInNotIcon sx={{ fontSize: '60px', color: 'main.or' }} /> :
                    <TurnedInIcon sx={{ fontSize: '60px', color: 'main.or' }} />} 스크랩 </div>

                {/* 수정/삭제 버튼 - 작성자인 경우에만 true로 버튼 표시 + 구매하기 버튼 - 작성자가 아닌 경우 노출 */}
                <div className="class-change">
                    가격 : {classData.lesson_price} &nbsp;&nbsp;&nbsp;
                    {false && <ButtonContain size='large' text='로그인'/>}
                    {true &&  <ButtonContain size='large' text='로그인'/>} &nbsp;&nbsp;
                    {true &&  <ButtonContain size='large' text='로그인'/>}
                </div>
            </div>
        </>
    );
}

export default ClassDetail;