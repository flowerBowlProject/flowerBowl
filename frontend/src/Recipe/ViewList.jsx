import React, { useState } from "react";
import './ViewListStyle.css';
import Button from '@mui/material/Button';
import RecipeReviewCard from "../Component/CardComp";
import Bookmark from "../Component/Bookmark";



const ViewList = ({name}) => {
    const [listData, setListData] = useState([{user_id: 1, content_no : 1, title: '제목1', date: '날짜1', like_count: 1, comment_count : 1},
    {user_id: 2, content_no : 2, title: '제목2', date: '날짜2', like_count: 2, comment_count : 2}, 
    {user_id: 1, content_no : 3, title: '제목3', date: '날짜3', like_count: 3, comment_count : 3}]);

    return(
        <div className="viewList-Box">
            <div className="sortList">
                <div className="sortList-left">
                    <Button sx={{color: 'main.or'}}> 최신순 </Button>
                    <Button sx={{color: 'main.or'}}> 인기순 </Button>
                    <Button sx={{color: 'main.or'}}> 댓글순 </Button>
                </div>
                <div className="sortList-right">
                    <Button className="view-register" variant="outlined"> {name} 등록 </Button>
                </div>
            </div>
            <div className="viewList">
                {/* 리스트 출력*/}
                
                {listData.length !== 0 && listData.map((data) => <div style={{position:'relative'}}> <Bookmark/> <RecipeReviewCard key={data.content_no} list={data}/> </div>)}
            </div>
            <Button className="moreButton"> 더보기 </Button>
        </div>
    );
}

export default ViewList;