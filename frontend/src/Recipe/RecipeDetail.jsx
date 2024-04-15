import React, { useState } from "react";
import './RecipeDetailStyle.css';
import TurnedInNotIcon from '@mui/icons-material/TurnedInNot';
import TurnedInIcon from '@mui/icons-material/TurnedIn';
import Comment from "../Component/Comment/Comment";
import ButtonContain from "../Component/ButtonContain";

const RecipeDetail = () => {
    const [recipeData, setRecipeData] = useState({
        recipe_title: '레시피 제목', recipe_sname: '', recipe_writer: '작성자1',
        recipe_date: '2024-04-03', recipe_stuff: '', recipe_category: '밥', recipe_content: '레시피 내용', recipe_like_status: false
    });
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
                        <div className="class-category" style={{ color: "#B9835C" }}> category :  </div>
                    </div>

                    <div className="recipe-writerDate" style={{textAlign:"right"}}>
                        <div className="recipe-writer"> by {recipeData.recipe_writer} </div>
                        <div className="recipe-date">{recipeData.recipe_date}</div>
                    </div>
                </div>

                <div className="recipe-stuff">
                    재료 : {recipeData.recipe_stuff}
                </div>

                <div className='recipe-body'>{recipeData.recipe_content}</div>
                {/* 즐겨찾기 버튼 - 즐겨찾기 여부에 따른 true / false로 아이콘 표시 */}
                <div className="recipe-bookmark">{recipeData.recipe_like_status ? <TurnedInNotIcon sx={{ fontSize: '60px', color: 'main.or' }} /> :
                    <TurnedInIcon sx={{ fontSize: '60px', color: 'main.or' }} />} 스크랩 </div>
                {/* 수정/삭제 버튼 - 작성자인 경우에만 true로 버튼 표시 */}
                <div className="recipe-change">
                    {true && <ButtonContain size='large' text='로그인'/>} &nbsp;
                    {true && <ButtonContain size='large' text='로그인'/>}
                </div>
            </div>
            <div>
                {/* 댓글 */}
                <Comment />
            </div>
        </>
    );
}

export default RecipeDetail;