import React, { useState } from "react";
import './ClassDetailStyle.css';
import TurnedInNotIcon from '@mui/icons-material/TurnedInNot';
import TurnedInIcon from '@mui/icons-material/TurnedIn';
import Inputbutton from "../Component/Input/Inputbutton";

const ClassDetail = () => {
    const [classData, setClassData] = useState({
        lesson_title: '클래스 제목', lesson_sname: '', lesson_writer: '작성자1', lesson_price: 1000, lesson_URL: 'http://kakao.chat/102552',
        lesson_start: '2024-03-19', lesson_end: '2024-04-19', lesson_category: '밥', lesson_content: '클래스 내용', lesson_like_status: false
    });
    return (
        <>
            <div className="classDetail-Box">
                {/* 이미지 조회 */}
                <div className="class-Img">
                    {/* 썸네일 첨부 필요 */}
                </div>
                <div className="class-element">
                    <div className="class-title"> {classData.lesson_title} </div>
                    <div className="class-writerDateURL">
                        <div>
                            by {classData.lesson_writer} &nbsp; {classData.lesson_start} ~ {classData.lesson_end}
                        </div>
                        <a className="class-url" href={classData.lesson_URL}>문의 : {classData.lesson_URL}</a>
                    </div>
                </div>

                {/* 가격 + 오픈채팅 링크 + 장소 (지도 api)*/}
                <div className="class-addr">
                    지도 구현
                </div>
                <div className='class-body'>{classData.lesson_content}</div>

                {/* 즐겨찾기 버튼 - 즐겨찾기 여부에 따른 true / false로 아이콘 표시 */}
                <div className="class-bookmark">{classData.lesson_like_status ? <TurnedInNotIcon sx={{ fontSize: '60px', color: 'main.or' }} /> :
                    <TurnedInIcon sx={{ fontSize: '60px', color: 'main.or' }} />} 스크랩 </div>

                {/* 수정/삭제 버튼 - 작성자인 경우에만 true로 버튼 표시 + 구매하기 버튼 - 작성자가 아닌 경우 노출 */}
                <div className="class-change">
                    가격 : {classData.lesson_price} &nbsp;&nbsp;&nbsp;
                    {true && <Inputbutton variant="outlined" text="구매" i={true} w="medium-large" />}
                    {false && <Inputbutton variant="outlined" text="수정" i={false} w="medium-large" />} &nbsp;&nbsp;
                    {false && <Inputbutton variant="outlined" text="삭제" i={true} w="medium-large" />}
                    
                </div>
                {/* 댓글 컴포넌트 */}
            </div>
            <div>
                {/* 댓글 */}
            </div>
        </>
    );
}

export default ClassDetail;