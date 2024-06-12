import React, { useEffect, useState } from "react";
import './ClassDetailStyle.css';
import TurnedInNotIcon from '@mui/icons-material/TurnedInNot';
import TurnedInIcon from '@mui/icons-material/TurnedIn';
import axios from "axios";
import { url } from "../url";
import ButtonContain from "../Component/ButtonContain";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate, useParams } from 'react-router-dom';
import ButtonOutlined from "../Component/ButtonOutlined";
import { Editor, Viewer } from "@toast-ui/react-editor";
import ClassPayment from "./ClassPayment";
import ErrorConfirm from "../Hook/ErrorConfirm";
import { editErrorType, openError } from '../persistStore';
import DeleteModal from "../Hook/DeleteModal";

const { kakao } = window;

const ClassDetail = () => {
    const [classData, setClassData] = useState({});
    const { lesson_no } = useParams();
    const writer = useSelector((state) => state.nickname);
    const accessToken = useSelector(state => state.accessToken);

    const navigator = useNavigate();
    const dispatch = useDispatch();

    useEffect(() => {
        if (accessToken === '') {
            axios.get(`${url}/api/guest/lessons/${lesson_no}`)
                .then(res => {
                    console.log(res);
                    setClassData(res.data.lesson);
                })
                .catch(err => {
                    console.log(err);
                    dispatch(editErrorType(err.response.data.code));
                    dispatch(openError());
                })
        } else {
            axios.get(`${url}/api/user/lessons/${lesson_no}`, {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            })
                .then(res => {
                    console.log(res);
                    setClassData(res.data.lesson);
                })
                .catch(err => {
                    console.log(err);
                    dispatch(editErrorType(err.response.data.code));
                    dispatch(openError());
                })
        }
    }, [lesson_no])


    useEffect(() => {
        const apiKey = process.env.REACT_APP_KAKAO_MAP_API_KEY;

        const loadKakaoMap = () => {
            // 스크립트가 이미 로드된 경우, 초기화만 수행합니다.
            if (window.kakao && window.kakao.maps) {
                initializeMap();
            } else {
                const script = document.createElement('script');
                script.src = `https://dapi.kakao.com/v2/maps/sdk.js?appkey=${apiKey}&autoload=false`;
                script.async = true;
                script.onload = () => {
                    if (window.kakao && window.kakao.maps) {
                        window.kakao.maps.load(initializeMap);
                    }
                };
                document.head.appendChild(script);
            }
        };

        const initializeMap = () => {
            const { lesson_latitude, lesson_longitude } = classData;
            const mapContainer = document.getElementById('staticMap');
            const mapOption = {
                center: new window.kakao.maps.LatLng(lesson_latitude, lesson_longitude),
                level: 3,
            };

            const map = new window.kakao.maps.Map(mapContainer, mapOption);
            const markerPosition = new window.kakao.maps.LatLng(lesson_latitude, lesson_longitude);
            const marker = new window.kakao.maps.Marker({ position: markerPosition });

            marker.setMap(map);
        };

        if (document.readyState === 'complete') {
            loadKakaoMap();
        } else {
            window.addEventListener('DOMContentLoaded', loadKakaoMap);
            return () => {
                window.removeEventListener('DOMContentLoaded', loadKakaoMap);
            };
        }
    }, [classData]);

    {/* 즐겨찾기 등록 / 해제 */ }
    const clickBookmark = () => {
        if (accessToken === '') {
            console.log('로그인 후 이용')
            dispatch(editErrorType('NT'));
            dispatch(openError());
        } else {
            if (classData.lesson_like_status) {
                console.log('북마크 해제')
                axios.delete(`${url}/api/user/lessons/like/${lesson_no}`, {
                    headers: {
                        Authorization: `Bearer ${accessToken}`
                    }
                })
                    .then(res => {
                        console.log('북마크 해제 성공')
                        setClassData((classData) => ({ ...classData, lesson_like_status: false }))
                    })
                    .catch(err => {
                        console.log(err);
                        console.log('북마크 해제 실패')
                        dispatch(editErrorType(err.response.data.code));
                        dispatch(openError());
                    })
            } else {
                console.log('북마크 등록')
                axios.post(`${url}/api/user/lessons/like`, {
                    "lesson_no": lesson_no
                }, {
                    headers: {
                        Authorization: `Bearer ${accessToken}`
                    }
                })
                    .then(res => {
                        console.log('북마크 등록 성공');
                        setClassData((classData) => ({ ...classData, lesson_like_status: true }))

                    })
                    .catch(err => {
                        console.log(err);
                        console.log('북마크 등록 실패');
                        dispatch(editErrorType(err.response.data.code));
                        dispatch(openError());
                    })
            }
        }

    }

    {/* 클래스 수정 */ }
    const handleModify = () => {
        console.log('수정');
        navigator(`/modifyClass/${lesson_no}`);
    }

    return (
        <>
            <div className="classDetail-Box">
                <ErrorConfirm error={useSelector(state => state.errorType)} />

                {/* 이미지 조회 */}
                <img className="class-Img" src={classData.lesson_sname} />
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
                <div className='class-body'>{classData.lesson_contents && <Viewer initialValue={classData.lesson_contents} />}</div>

                {/* 즐겨찾기 버튼 - 즐겨찾기 여부에 따른 true / false로 아이콘 표시 */}
                <div className="class-bookmark" style={{ cursor: 'pointer' }} onClick={clickBookmark}>
                    {classData.lesson_like_status === true ? <TurnedInIcon sx={{ fontSize: '60px', color: 'main.or' }} /> :
                        <TurnedInNotIcon sx={{ fontSize: '60px', color: 'main.or' }} />} 스크랩 </div>

                {/* 수정/삭제 버튼 - 작성자인 경우에만 true로 버튼 표시 + 구매하기 버튼 - 작성자가 아닌 경우 노출 */}
                <div className="class-change">
                    가격 : {classData.lesson_price} &nbsp;&nbsp;&nbsp;
                    {writer !== classData.lesson_writer && <ClassPayment lesson_no={lesson_no} />}
                    {writer === classData.lesson_writer && <ButtonOutlined size='large' text='수정' handleClick={(e) => handleModify(e)} />} &nbsp;&nbsp;
                    {writer === classData.lesson_writer && <DeleteModal checkType={'class'} no={lesson_no} />}
                </div>
            </div>
        </>
    );
}

export default ClassDetail;