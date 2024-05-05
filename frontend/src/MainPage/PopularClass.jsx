import React from 'react'
import {Grid,Typography,Skeleton,Container} from '@mui/material';
const ClassContent=({m,title,content})=>{
    return(
    <Grid container direction='column' xs>
        <Grid item ml={m} mb='1vw'>
            <Typography variant='h6' align='center' >
                {title}
            </Typography>
        </Grid>
        <Grid item ml={m} mb='1vw'>
            <Typography align='center' style={{whiteSpace: 'pre-wrap'}}>
                {content}
            </Typography>
        </Grid>
        <Grid item ml={m}>
            <Typography color='main.or' align='center'  >
                Learn more
            </Typography>
        </Grid>
    </Grid>
    );
}
const PopularClass=()=>{
    return(
        <Grid container my='3vw' direction='column'>
            <Grid  item  alignContent='center'>
                <Typography variant='h6' ml='5vw'>
                    인기 클래스
                </Typography>
            </Grid>
            <Grid container my='2vw' direction='row' alignItems='flex-end' justifyContent='space-around'>
                <Grid item >
                    <Skeleton variant='circular' width='15vw' height='15vw' />
                </Grid>
                <Grid item  >
                    <Skeleton variant='circular' width='20vw' height='20vw' />
                </Grid>
                <Grid item >
                    <Skeleton variant='circular' width='15vw' height='15vw' />
                </Grid>
            </Grid>
            <Grid container direction='row' justifyContent='space-around' >
                <ClassContent m='-1vw' title='클래스입니다.' content={`내용입니다.내용입니다.내용입니다.\n내용입니다.내용입니다.내용입니다.\n내용입니다.내용입니다.내용입니다.`}/>
                <ClassContent m='0.5vw' title='클래스입니다.' content={`내용입니다.내용입니다.내용입니다.\n내용입니다.내용입니다.내용입니다.\n내용입니다.내용입니다.내용입니다.`}/>
                <ClassContent m='2vw' title='클래스입니다.' content={`내용입니다.내용입니다.내용입니다.\n내용입니다.내용입니다.내용입니다.\n내용입니다.내용입니다.내용입니다.`}/>
            </Grid>
        </Grid>
        

        
    );


}
export default PopularClass;