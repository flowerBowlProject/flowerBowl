import React, { useEffect, useState } from "react";
import './RegisterRecipeStyle.css';
import Category from "../Component/Category";
import ToastEditor from "../Component/ToastEditor";
import RecipeStuff from "./RecipeStuff";
import ButtonOutlined from "../Component/ButtonOutlined";
import ButtonContain from "../Component/ButtonContain";
import { url } from "../url";
import axios from "axios";
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from "react-router-dom";
import { editErrorType, openError } from "../persistStore";
import ErrorConfirm from "../Hook/ErrorConfirm";

const RegisterRecipe = () => {
    const accessToken = useSelector(state => state.accessToken);
    const navigator = useNavigate();
    const dispatch = useDispatch();

    {/* 등록 레시피 데이터 + 썸네일 + 썸네일 선택 여부 */ }
    const [registerData, setRegisterData] = useState({ recipe_title: '', recipe_category: '', recipe_stuff: [], recipe_content: '', recipe_sname:'', recipe_oname:'',
        recipe_file_oname:[], recipe_file_sname:[] });
    const [thumbnail, setThumbnail] = useState(null);
    const [selectedFile, setSelectedFile] = useState(null);

    useEffect(()=>{
        axios.get(`${url}/api/users/info`,{
            headers:{
                Authorization : `Bearer ${accessToken}`
            }
        })
        .catch(err=>{
            dispatch(editErrorType(err.response.data.code));
            dispatch(openError());
            navigator('/recipeList')
        })
    },[])

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

        const formData = new FormData();
        formData.append('file', file);
        axios.post(`${url}/api/images/thumbnail`, formData,{
            headers:{
                Authorization : `Bearer ${accessToken}`,
                'Content-Type': 'multipart/form-data',
            }
        })
        .then(res=>{
            console.log(res);
            setRegisterData((registerData)=>({...registerData, recipe_sname:res.data.thumbnail_sname}));
            setRegisterData((registerData)=>({...registerData, recipe_oname:res.data.thumbnail_oname}))
        })
        .catch(err=>{
            console.log(err);
        })
    };

    {/* 선택한 카테고리 값 받아와 저장 */ }
    const getCategory = category => {
        setRegisterData((registerData) => ({ ...registerData, recipe_category: category }));
    }

    {/* 토스트 에디터 값 받아와 저장 */}
    const getToastEditor = contentData =>{
        setRegisterData((registerData) => ({ ...registerData, recipe_content: contentData}));
    }
    
    const getToastImg = contentImg =>{
        setRegisterData(prevState => ({...prevState, recipe_file_oname: [...prevState.recipe_file_oname, contentImg.oname]}));
        setRegisterData(prevState => ({...prevState, recipe_file_sname: [...prevState.recipe_file_sname, contentImg.sname]}));
    }

    console.log(registerData);

    {/* 재료 입력 값 받아와서 저장 */}
    const getStuff = stuff =>{
        setRegisterData((registerData) => ({...registerData, recipe_stuff: stuff}));
    }

    {/* 그 외의 변경사항 적용 */}
    const setValue = (e) =>{
        const value = e.target.value;
        const name = e.target.name;
        setRegisterData((registerData) => ({ ...registerData, [name]: value }));
    }

    const thumbDelete = () =>{
        setThumbnail(null);
        setRegisterData((registerData) => ({ ...registerData, recipe_sname: ""}));
        setRegisterData((registerData) => ({ ...registerData, recipe_oname: ""}));
    }

    const handleRegister = () =>{
        console.log(registerData);
        {/* 등록 내용 작성 여부 확인 후 alert */}
        if (registerData.recipe_title === '') {
            dispatch(editErrorType('TITLE'));
            dispatch(openError());
        } else if (registerData.recipe_category === '') {
            dispatch(editErrorType('CATEGORY'));
            dispatch(openError());
        } else if (registerData.recipe_sname === '' || registerData.recipe_oname === '') {
            dispatch(editErrorType('THUMBNAIL'));
            dispatch(openError());
        } else if (registerData.recipe_content && registerData.recipe_content === '') {
            dispatch(editErrorType('CONTENT'));
            dispatch(openError());
        } else if (registerData.recipe_stuff.length === 0) {
            dispatch(editErrorType('STUFF'));
            dispatch(openError());
        } else {
            console.log("등록 가능");
            axios.post(`${url}/api/recipes`, registerData,{
                headers:{
                    Authorization: `Bearer ${accessToken}`
                }
            })
            .then(res=>{
                dispatch(editErrorType('REGISTER'));
                dispatch(openError());
                navigator('/recipeList');
            })
            .catch(err=>{
                console.log(err);
                dispatch(editErrorType(err.response.data.code));
                dispatch(openError());
            })
        }
            
    }

    const handleCancel = () =>{
        navigator('/recipeList');
    }


    return (
        <div className="registerRecipe-Box">
            <ErrorConfirm error={useSelector(state => state.errorType)} />

            {/* 썸네일 선택 - 다시 클릭 시 재선택 */}
            <div style={{ display: 'flex', alignItems: 'flex-end'}}>
                <div className='recipeRegister-thumbnail'>
                    {thumbnail ? (
                        <label>
                            <img className='thumbImg-preview' src={thumbnail} alt='Thumbnail Preview' onClick={thumbDelete} />
                        </label>
                    ) : (
                        <label className='recipeChoose-thumbnail'>
                            썸네일 선택
                            <input type='file' name='thumbImg' onChange={chooseThumbnail} />
                        </label>)}
                </div>
                <input className="register-Recipetitle" type='text' placeholder="제목을 작성해 주세요." name="recipe_title" onChange={(e)=> setValue(e)}/>
            
            {/* 카테고리 선택 */}
            <Category getCategory={getCategory} />
            </div>

            <div className="recipeText-Box">
                {/* 재료 작성란 */}
                <div className="materialInput-Box" style={{display: 'flex'}}>
                        <RecipeStuff getStuff={getStuff}/>
                </div>
                {/* 레시피 || 클래스 상세 내용 작성란 */}
                <ToastEditor getToastEditor={getToastEditor} getToastImg={getToastImg} setContent={''}/>
            </div>
            
            <div className="register_button" style={{ marginTop: "2%" }}>
                <ButtonOutlined size='large' text='등록' handleClick={handleRegister}/> &nbsp;
                <ButtonContain size='large' text='취소' handleClick={handleCancel}/>
            </div>
        </div>
    );
}

export default RegisterRecipe;