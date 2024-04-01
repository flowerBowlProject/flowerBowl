import * as React from 'react';
import './CardCompStyle.css';
import Card from '@mui/material/Card';
import CardMedia from '@mui/material/CardMedia';
import CardContent from '@mui/material/CardContent';
import FavoriteIcon from '@mui/icons-material/Favorite';
import ChatBubbleIcon from '@mui/icons-material/ChatBubble';

// 전체적으로 list의 element의 단어들을 api와 일치시켜야 함
const RecipeReviewCard = ({list}) => {
  return (
    <div>
      <Card className='card-Box'>
        {/* 이미지 요청 api로 변경 필요 */}
        <CardMedia
          component="img"
          height="300"
          width="300"
          image="../Img/foodImg.jpeg"
          alt="레시피 사진"
          sx={{ borderBottom: '#F6C47B solid 4px' }}
        />
        <CardContent>
          <div className='detail-element'>
            <div className='views-date'> {list.date} </div>
            <div className='views-icons'>
              <FavoriteIcon sx={{ color: 'main.or' }} /> {list.like_count}
              <ChatBubbleIcon sx={{ color: 'main.or' }} /> {list.comment_count}
            </div>
          </div>
          <div className='views-title'> {list.title} </div>
        </CardContent>
      </Card>
      </div>
  );
}

export default RecipeReviewCard;