import React, { useEffect, useState } from "react";
import './ViewListStyle.css';
import Button from '@mui/material/Button';
import RecipeReviewCard from "../Component/CardComp";
import Bookmark from "../Component/Bookmark";
import { useNavigate } from 'react-router-dom';
import axios from "axios";
import { url } from "../url";

const ViewList = ({name}) => {
    const [listData, setListData] = useState([]);

    useEffect(()=>{
        if(name == "레시피"){
            {/* 로그인 구현 이후 if문 추가 필요 - 현재는 비로그인 기준 */}
            axios.get(`${url}/api/recipes/guest`)
            .then(res=>{
                console.log(res);
            })
            .catch(err=>{
                console.log(err);
            })
        }else{

        }
    },[])

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
                {listData.length !== 0 && listData.map((data, index) => <div style={{position:'relative'}}> <Bookmark/> <RecipeReviewCard key={index} list={data}/> </div>)}
            </div>
            <Button className="moreButton"> 더보기 </Button>
        </div>
    );
}

export default ViewList;