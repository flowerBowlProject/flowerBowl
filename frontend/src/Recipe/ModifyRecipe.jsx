import React, { useEffect, useState } from "react";
import './RegisterRecipeStyle.css';
import Category from "../Component/Category";
import ToastEditor from "../Component/ToastEditor";
import axios from "axios";
import { url } from "../url";
import { useNavigate, useParams } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import RecipeStuff from "./RecipeStuff";
import ButtonOutlined from "../Component/ButtonOutlined";
import ButtonContain from "../Component/ButtonContain";
import { editErrorType, openError } from "../persistStore";
import ErrorConfirm from "../Hook/ErrorConfirm";

const RegisterRecipe = () => {
    {/* 등록 레시피 데이터 + 썸네일 + 썸네일 선택 여부 */ }
    const [registerData, setRegisterData] = useState({});
    const [thumbnail, setThumbnail] = useState(null);
    const [selectedFile, setSelectedFile] = useState(null);
    const { recipe_no } = useParams();
    const accessToken = useSelector((state) => state.accessToken);
    const navigator = useNavigate();
    const dispatch = useDispatch();

    useEffect(() => {
        axios.get(`${url}/api/recipes/${recipe_no}`, {
            headers: {
                Authorization: `Bearer ${accessToken}`
            }
        })
            .then(res => {
                console.log(res);
                setRegisterData(res.data.data);
            })
            .catch(err => {
                console.log(err);
                dispatch(editErrorType(err.response.data.code));
                dispatch(openError());
            })
    }, [recipe_no])

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

    {/* 토스트 에디터 값 받아와 저장 */ }
    const getToastEditor = content => {
        setRegisterData((registerData) => ({ ...registerData, lesson_content: content }));
    }

    {/* 재료 입력 값 받아와서 저장 */ }
    const getStuff = stuff => {
        console.log(stuff);
        setRegisterData((registerData) => ({ ...registerData, recipe_stuff: stuff }));
    }

    {/* 그 외의 변경사항 적용 */ }
    const setValue = (e) => {
        const value = e.target.value;
        const name = e.target.name;
        setRegisterData((registerData) => ({ ...registerData, [name]: value }));
    }

    const handleModify = () => {
        console.log(registerData);
        {/* 등록 내용 작성 여부 확인 후 alert */ }
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
            axios.put(`${url}/recipes/${recipe_no}`, registerData, {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            })
                .then(res => {
                    console.log(res);
                    dispatch(editErrorType('MODIFY'));
                    dispatch(openError());  
                    navigator(`/recipeDetail/${recipe_no}`);
                })
                .catch(err => {
                    console.log(err);
                    dispatch(editErrorType(err.response.data.code));
                    dispatch(openError());
                })
        }
    }

    const handleCancel = () => {
        navigator(`/recipeDetail/${recipe_no}`);
    }

    return (
        <div className="registerRecipe-Box">
            <ErrorConfirm error={useSelector(state => state.errorType)} />

            {/* 썸네일 선택 - 다시 클릭 시 재선택 */}
            <div style={{ display: 'flex', alignItems: 'flex-end' }}>
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
                <input className="register-Recipetitle" type='text' placeholder="제목을 작성해 주세요." name='recipe_title' value={registerData.recipe_title} onChange={(e) => setValue(e)} />

                {/* 카테고리 선택 */}
                <Category getCategory={getCategory} setCategory={registerData.recipe_category} />
            </div>

            <div className="recipeText-Box">
                {/* 재료 작성란 */}
                <div className="materialInput-Box" style={{ display: 'flex' }}>
                    <RecipeStuff getStuff={getStuff} setStuff={registerData.recipe_stuff} />
                </div>
                {/* 레시피 || 클래스 상세 내용 작성란 */}
                <ToastEditor getToastEditor={getToastEditor} setContent={registerData.recipe_content} />
            </div>

            <div className="register_button" style={{ marginTop: "2%" }}>
                <ButtonOutlined size='large' text='수정' handleClick={handleModify} /> &nbsp;
                <ButtonContain size='large' text='취소' handleClick={handleCancel} />
            </div>
        </div>
    );
}

export default RegisterRecipe;