import * as React from 'react';
import TurnedInNotIcon from '@mui/icons-material/TurnedInNot';
import TurnedInIcon from '@mui/icons-material/TurnedIn';

const Bookmark = () => {
    return (
        <div style={{position: 'absolute'}}>
            {/* 즐겨찾기 여부에 따른 채움 / 빈 아이콘 - api 연동 후 boolean 값 변경*/}
            {true ? <TurnedInIcon sx={{ fontSize: '60px', color: 'main.or', position: 'absolute', top: -3, left: -6, zIndex: 1, }}/>
                : <TurnedInNotIcon sx={{ fontSize: '60px', color: 'main.or', position: 'absolute', top: -3, left: -6, zIndex: 1, }}/>}
        </div>
    );
}

export default Bookmark;