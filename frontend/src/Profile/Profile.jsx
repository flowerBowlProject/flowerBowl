import React from 'react'
import Input from '../Component/Input/Input';
import Inputbutton from '../Component/Input/Inputbutton';
import PersonIcon from '@mui/icons-material/Person';
import Tooltip from '@mui/material/Tooltip';
import "./ProfileImage.css"
import Withdrawl from './Withdraw'


const Profile = () => {
  return (
    <>
    
    {/* 사이드바 */}
    <div className = 'sidebar'>
    </div>


    {/* 프로필 */}
    <div className = 'profile'>
        <div className="ProfileImage">
           <PersonIcon sx={{ color: 'black', width: '100px', height: '100px', position: 'absolute'}}/>{/* '#FFFF00'은 노란색을 나타냄 */}
        </div>
      <Inputbutton text="사진 변경" i= {true} w="medium"/>
        
        {/* 툴팁 */}
          <Tooltip title="파일을 첨부해주세요" followCursor>
          <div className="ProfileImage" sx={{ p: 2 }}>
          <PersonIcon sx={{ color: 'black', width: '100px', height: '100px', position: 'absolute'}}/>{/* '#FFFF00'은 노란색을 나타냄 */}
          </div>
         </Tooltip>

        <Inputbutton text="쉐프 신청" i= {true} w="medium"/>
    </div>


    {/* 폼형태 */}
    <div className = 'form'>
        아이디
        <Input placeholder="jean1030"/>
        닉네임
        <Input placeholder="치킨나라피자공주"/>
        <Inputbutton text="중복확인" i= {true} w="large" />
        휴대폰번호
        <Input placeholder="010-8495-9515"/>
        이메일
        <Input placeholder="jean1030@naver.com"/>
        <Inputbutton text="이메일 인증" i= {true} w="large" />
        비밀번호
        <Input placeholder="*******"/>
        새 비밀번호 변경
        <Input placeholder="*******"/>
        새 비밀번호 확인
        <Input placeholder="*******"/>
        </div>

        {/* 변경버튼 */}
        <Inputbutton text="변경" i= {true} w="medium" />

        {/* 회원 탈퇴 버튼 */}
        <Withdrawl/>

    </>
  );
}


export default Profile;