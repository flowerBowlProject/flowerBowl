import React, { useEffect, useState } from "react";
import './ClassDetailStyle.css';
import TurnedInNotIcon from '@mui/icons-material/TurnedInNot';
import TurnedInIcon from '@mui/icons-material/TurnedIn';
import axios from "axios";
import { url } from "../url";
import ButtonContain from "../Component/ButtonContain";
import { useSelector } from "react-redux";
import { useNavigate, useParams } from 'react-router-dom';
import ButtonOutlined from "../Component/ButtonOutlined";
import { Viewer } from "@toast-ui/react-editor";


const { kakao } = window;

const ClassDetail = () => {
    const [classData, setClassData] = useState({});
    const { lesson_no } = useParams();
    const [writer, setWriter] = useState(false);
    const accessToken = useSelector(state => state.accessToken);

    const navigator = useNavigate();

    useEffect(() => {
        if (accessToken === '') {
            axios.get(`${url}/api/guest/lessons/${lesson_no}`)
                .then(res => {
                    console.log(res);
                    setClassData(res.data.lesson);
                })
                .catch(err => {
                    console.log(err);
                })
        } else {
            axios.get(`${url}/api/user/lessons/${lesson_no}`,{
                headers:{
                    Authorization : `Bearer ${accessToken}`
                }
            })
                .then(res => {
                    console.log(res);
                    setClassData(res.data.lesson);
                })
                .catch(err => {
                    console.log(err);
                })
        }
    }, [])

    useEffect(() => {
        var mapContainer = document.getElementById('staticMap'), // 지도를 표시할 div 
            mapOption = {
                center: new kakao.maps.LatLng(classData.lesson_latitude, classData.lesson_longitude), // 지도의 중심좌표
                level: 3 // 지도의 확대 레벨
            };

        var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

        // 마커가 표시될 위치입니다 
        var markerPosition = new kakao.maps.LatLng(classData.lesson_latitude, classData.lesson_longitude);

        // 마커를 생성합니다
        var marker = new kakao.maps.Marker({
            position: markerPosition
        });

        // 마커가 지도 위에 표시되도록 설정합니다
        marker.setMap(map);

    }, [classData])

    {/* 클래스 구매 */ }
    const buyClass = () => {

    }

    {/* 클래스 수정 */ }
    const handleModify = (e) => {
        console.log('수정');
        navigator(`/modifyClass/${lesson_no}`);
    }

    {/* 클래스 삭제 */ }
    const handleDelete = () => {
        {/* alert로 삭제 여부 재확인 */ }
        console.log('삭제');
        axios.put(`${url}/api/user/lessons`,{
            "lesson_no" : lesson_no
        })
        .then(res=>{
            console.log(res);
            //navigator('/classList');
        })
        .catch(err=>{
            console.log(err);
        })
    }

    return (
        <>
            <div className="classDetail-Box">
                {/* 이미지 조회 */}
                <div className="class-Img">
                    {/* 썸네일 첨부 필요 */}
                </div>
                <div className="class-element">
                    <div style={{ float: "left", textAlign: "center" }}>
                        <div className="class-title"> {classData.lesson_title} </div>
                        <div className="class-category" style={{ color: "#B9835C" }}> category : {classData.lesson_category} </div>
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
                    <div id="staticMap" style={{ height: '20vw' }}></div>
                </div>
                <div className="class-addr">
                    장소 : {classData.lesson_addr}
                </div>
                <div className='class-body'><Viewer initialValue={classData.lesson_contents || ''} /></div>

                {/* 즐겨찾기 버튼 - 즐겨찾기 여부에 따른 true / false로 아이콘 표시 */}
                <div className="class-bookmark">{classData.lesson_likes_status === true ? <TurnedInIcon sx={{ fontSize: '60px', color: 'main.or' }} /> :
                    <TurnedInNotIcon sx={{ fontSize: '60px', color: 'main.or' }} />} 스크랩 </div>

                {/* 수정/삭제 버튼 - 작성자인 경우에만 true로 버튼 표시 + 구매하기 버튼 - 작성자가 아닌 경우 노출 */}
                <div className="class-change">
                    가격 : {classData.lesson_price} &nbsp;&nbsp;&nbsp;
                    {false && <ButtonContain size='large' text='구매' handleClick={buyClass} />}
                    {true && <ButtonOutlined size='large' text='수정' handleClick={handleModify} />} &nbsp;&nbsp;
                    {true && <ButtonContain size='large' text='삭제' handleClick={handleDelete} />}
                </div>
            </div>
        </>
    );
}

export default ClassDetail;