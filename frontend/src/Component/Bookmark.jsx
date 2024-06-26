import * as React from 'react';
import TurnedInNotIcon from '@mui/icons-material/TurnedInNot';
import TurnedInIcon from '@mui/icons-material/TurnedIn';
import { useEffect } from 'react';
const Bookmark = ({check, onClick}) => {
    const handleClick = () => {
        if (onClick) onClick();
    };

    return (
        <div>
            
            {/* 즐겨찾기 여부에 따른 채움 / 빈 아이콘 - api 연동 후 boolean 값 변경*/}
            {check ? <TurnedInIcon sx={{ fontSize: '60px', color: 'main.or', position: 'absolute', top: -3, left: -6, zIndex: 1, }} onClick={handleClick}  style={{cursor:'pointer'}}/>
                : <TurnedInNotIcon sx={{ fontSize: '60px', color: 'main.or', position: 'absolute', top: -3, left: -6, zIndex: 1, }} onClick={handleClick}  style={{cursor:'pointer'}}/>}
        </div>
    );
}

export default Bookmark;