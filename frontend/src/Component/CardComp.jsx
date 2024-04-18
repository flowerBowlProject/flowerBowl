import * as React from "react";
import "./CardCompStyle.css";
import Card from "@mui/material/Card";
import CardMedia from "@mui/material/CardMedia";
import CardContent from "@mui/material/CardContent";
import TurnedInIcon from "@mui/icons-material/TurnedIn";
import ChatBubbleIcon from "@mui/icons-material/ChatBubble";
import { url } from "../url";

// 전체적으로 list의 element의 단어들을 api와 일치시켜야 함
const CardComp = ({title, like_count, date, comment_count, sname, onClick, type}) => {
  const handleClick = () =>{
    if(onClick) onClick();
  };

  return (
    <div onClick={handleClick}>
      <Card className="card-Box">
        {/* 이미지 요청 api로 변경 필요 */}
        <CardMedia
          component="img"
          height="250"
          width="250"
          image="../images/mookuk.jpeg"
          alt="레시피 사진"
          sx={{ borderBottom: "#F6C47B solid 2px" }}
        />
        <CardContent>
          <div className='detail-element'>
            <div className='views-date'> {date} </div>
            { type === true ?
            <div className='views-icons' style={{display:'flex', alignContent:'center'}}>
              <TurnedInIcon sx={{ color: 'main.or', marginLeft:'1vw'}}/> {like_count}
              <ChatBubbleIcon sx={{ color: 'main.or', marginLeft:'1vw'}} /> {comment_count}
            </div> :
            <div className='views-icons' style={{display:'flex', alignContent:'center'}}>
            <TurnedInIcon sx={{ color: 'main.or', marginLeft:'1vw'}}/> {like_count}
          </div>
            }
          </div>
          <div className="views-title"> {title} </div>
        </CardContent>
      </Card>
    </div>
  );
};

export default CardComp;
