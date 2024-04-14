import React, { useState } from "react";
import './RegisterRecipeStyle.css';
import Category from "../Component/Category";
import ToastEditor from "../Component/ToastEditor";

const RegisterRecipe = () => {
    {/* 등록 레시피 데이터 + 썸네일 + 썸네일 선택 여부 */ }
    const [registerData, setRegisterData] = useState({ recipe_title: '', recipe_category: '', recipe_stuff: [], recipe_content: '' });
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
        setRegisterData((registerData) => ({ ...registerData, recipe_category: category }));
    }

    {/* 토스트 에디터 값 받아와 저장 */}
    const getToastEditor = content =>{
        setRegisterData((registerData) => ({ ...registerData, lesson_content: content}));
    }

    return (
        <div className="registerRecipe-Box">

            {/* 썸네일 선택 - 다시 클릭 시 재선택 */}
            <div style={{ display: 'flex', alignItems: 'flex-end'}}>
                <div className='recipeRegister-thumbnail'>
                    {thumbnail ? (
                        <label>
                            <img className='thumbImg-preview' src={thumbnail} alt='Thumbnail Preview' onClick={() => setThumbnail(null)} />
                        </label>
                    ) : (
                        <label className='recipeChoose-thumbnail'>
                            썸네일 선택
                            <input type='file' name='thumbImg' onChange={chooseThumbnail} />
                        </label>)}
                </div>
                <input className="register-title" type='text' placeholder="제목을 작성해 주세요."/>
            
            {/* 카테고리 선택 */}
            <Category getCategory={getCategory} />
            </div>

            <div className="recipeText-Box">
                {/* 재료 작성란 */}
                <div className="materialInput-Box" style={{borderBottom: "2px solid #CBA285", height: "2vw", width:"70%", margin : "1vw"}}>
                재료 태그 작성란 - 컴포넌트 만들어서 넣을 예정
                </div>
                {/* 레시피 || 클래스 상세 내용 작성란 */}
                <ToastEditor getToastEditor={getToastEditor} content={registerData.recipe_content}/>
            </div>
            
            {/* 조이님이 만들어주신 공통 컴포넌트 버튼 추가 필요 -> 등록 / 취소 버튼 */}
        </div>
    );
}

export default RegisterRecipe;