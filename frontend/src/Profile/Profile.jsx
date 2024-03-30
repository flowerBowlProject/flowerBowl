import React from 'react'
import Input from './Input/Input';
import Inputbutton from './Input/Inputbutton';
import PersonIcon from '@mui/icons-material/Person';
import "./ProfileImage.css"


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
      <Inputbutton text="사진 변경" i= {true}/>
        <div className="ProfileImage">
           <PersonIcon sx={{ color: 'black', width: '100px', height: '100px', position: 'absolute'}}/>{/* '#FFFF00'은 노란색을 나타냄 */}
        </div>
      <Inputbutton text="쉐프 신청" i= {true}/>
    </div>


    {/* 폼형태 */}
    <div className = 'form'>
        아이디
        <Input/>
        <Inputbutton text="변경" i= {true} />
   
        비밀번호
        <Input/>
        <Inputbutton text="변경"  i={true}/>
        비밀번호 확인
        <Input/>
        닉네임
        <Input/>
        <Inputbutton text="변경" i= {true}/>
        이메일
        <Input/>
        <Inputbutton text="변경" i= {true}/>
        휴대폰 번호
        <Input/>
        <Inputbutton text="변경" i= {true}/>
    </div>


    </>
  );
}


export default Profile;