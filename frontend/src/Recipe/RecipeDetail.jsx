import React, { useState } from "react";
import './RecipeDetailStyle.css';
import TurnedInNotIcon from '@mui/icons-material/TurnedInNot';
import TurnedInIcon from '@mui/icons-material/TurnedIn';
import Inputbutton from "../Component/Input/Inputbutton";

const RecipeDetail = () => {
    const [recipeData, setRecipeData] = useState({recipe_title: '레시피 제목', recipe_sname:'', recipe_writer:'작성자1',
        recipe_date: '2024-04-03', recipe_stuff:'', recipe_category:'밥', recipe_content: '레시피 내용', recipe_like_status:false});
    return (
        <>
            <div className="recipeDetail-Box">
                {/* 이미지 조회 */}
                <div className="recipe-Img">
                    {/* 썸네일 첨부 필요 */}
                </div>
                <div className="recipe-element">
                    <div className="recipe-title"> {recipeData.recipe_title} </div>
                    <div className="recipe-writerDate"> by {recipeData.recipe_writer} &nbsp; {recipeData.recipe_date}</div>
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
                    {true && <Inputbutton variant="outlined" text="수정" i={false} w="medium-large" />} &nbsp;
                    {true && <Inputbutton variant="outlined" text="삭제" i={true} w="medium-large" />}
                </div>

                {/* 댓글 컴포넌트 */}
            </div>
            <div>
                {/* 댓글 */}
            </div>
        </>
    );
}

export default RecipeDetail;