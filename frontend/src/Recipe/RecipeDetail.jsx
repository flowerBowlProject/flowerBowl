import React, { useEffect, useState } from "react";
import './RecipeDetailStyle.css';
import TurnedInNotIcon from '@mui/icons-material/TurnedInNot';
import TurnedInIcon from '@mui/icons-material/TurnedIn';
import Comment from "../Component/Comment/Comment";
import ButtonContain from "../Component/ButtonContain";
import { useSelector } from "react-redux";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import { url } from "../url";
import ButtonOutlined from "../Component/ButtonOutlined";
import { Viewer } from "@toast-ui/react-editor";

const RecipeDetail = () => {
    const [recipeData, setRecipeData] = useState({ });
    const navigator = useNavigate();
    const accessToken = useSelector((state) => state.accessToken);
    const writer = useSelector((state)=>state.nickname);
    const { recipe_no } = useParams();

    useEffect(()=>{
        if(accessToken === ''){
            axios.get(`${url}/api/recipes/guest/${recipe_no}`)
            .then(res=>{
                console.log(res.data.data);
                setRecipeData(res.data.data);
            })
            .catch(err=>{
                console.log(err);
            })
        }else{
            axios.get(`${url}/api/recipes/${recipe_no}`,{
                headers:{
                    Authorization : `Bearer ${accessToken}`
                }
            })
            .then(res=>{
                console.log(res);
                setRecipeData(res.data.data)
            })
            .catch(err=>{
                console.log(err);
            })
        }
    },[recipe_no, accessToken])

    {/* 수정 페이지로 이동 */}
    const clickModify = () =>{
        navigator(`/modifyRecipe/${recipe_no}`)
    }

    {/* 게시글 삭제 */}
    const clickDelete = () =>{
        axios.delete(`${url}/api/recipes/${recipe_no}`,{
            headers:{
                Authorization: `Bearer ${accessToken}`
            }
        })
        .then(res=>{
            console.log(res);
            // alert 창 띄우로 리스트 페이지로 이동 navigator('/recipeList');
        })
        .catch(err=>{
            console.log(err);
        })
    }

    {/* 즐겨찾기 생성 / 삭제 */}
    const clickBookmark = () =>{
        if(accessToken === ''){
            console.log('로그인 후 사용해 주세요')
        }else{
            axios.post(`${url}/api/recipes/like/${recipe_no}`, null, {
                headers: {
                    Authorization: `Bearer ${accessToken}`,
                }
            })
            .then((res) => {
                console.log('북마크 성공');
                const check = res.data.code == 'SU' ? false : true;
                setRecipeData((recipeData) => ({...recipeData, recipe_like_status : check}))
            })
            .catch((err) => {
                /* 토큰 만료에 대한 처리 진행 */
                console.log(err);     
            });
        }
    }

    return (
        <>
            <div className="recipeDetail-Box">
                {/* 이미지 조회 */}
                <div className="recipe-Img">
                    {/* 썸네일 첨부 필요 */}
                </div>
                <div className="recipe-element">
                    <div style={{ float: "left", textAlign: "center" }}>
                        <div className="recipe-title"> {recipeData.recipe_title} </div>
                        <div className="class-category" style={{ color: "#B9835C" }}> category :  {recipeData.recipe_category}</div>
                    </div>

                    <div className="recipe-writerDate" style={{textAlign:"right"}}>
                        <div className="recipe-writer"> by {recipeData.recipe_writer} </div>
                        <div className="recipe-date">{recipeData.recipe_date}</div>
                    </div>
                </div>

                <div className="recipe-stuff">
                    재료 : {recipeData.recipe_stuff && recipeData.recipe_stuff.join(', ')}
                </div>

                <div className='recipe-body'>{recipeData.recipe_content && <Viewer initialValue={recipeData.recipe_content} />}</div>

                {/* 즐겨찾기 버튼 - 즐겨찾기 여부에 따른 true / false로 아이콘 표시 */}
                <div className="recipe-bookmark" onClick={clickBookmark} style={{cursor :'pointer'}}>{recipeData.recipe_like_status ? <TurnedInIcon sx={{ fontSize: '60px', color: 'main.or' }} /> :
                    <TurnedInNotIcon sx={{ fontSize: '60px', color: 'main.or' }} />} 스크랩 </div>
                {/* 수정/삭제 버튼 - 작성자인 경우에만 true로 버튼 표시 */}
                <div className="recipe-change">
                    {writer === recipeData.recipe_writer && <ButtonOutlined size='large' text='수정' handleClick={clickModify}/>} &nbsp;
                    {writer === recipeData.recipe_writer && <ButtonContain size='large' text='삭제' handleClick={clickDelete}/>}
                </div>
            </div>
            <div>
                {/* 댓글 */}
                <Comment typeString={1} no={recipe_no}/>
            </div>
        </>
    );
}

export default RecipeDetail;