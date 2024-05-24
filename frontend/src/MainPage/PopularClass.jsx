import React, { useEffect ,useState} from 'react';
import {Grid,Typography,Skeleton,Container} from '@mui/material';
import axios from 'axios';
import { url } from '../url';
import {useNavigate,Link,NavLink} from 'react-router-dom';
const ClassContent=({m,title,content,recipeNo})=>{
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
            <Link to={`/classDetail/${recipeNo}`}  style={{textDecoration:'none'}}>
            <Typography color='main.or' align='center' >
                Learn more
            </Typography>
            </Link>
        </Grid>
    </Grid>
    );
}
const PopularClass=()=>{
    const [popularLesson,setPopularLesson]=useState(Array(3).fill({lesson_no:'',lesson_sname:'',lesson_title:'',lesson_content:''}))
    useEffect(()=>{
        const fetchData= async()=>{
            try{
                const response=await axios.get(`${url}/api/guest/lessons/like`)
                setPopularLesson(response.data)

            }catch(error){
                console.log(error)
            }
        }
        fetchData();
    },[]);
    return(
        <Grid container my='3vw' direction='column'>
            <Grid  item  alignContent='center'>
                <Typography variant='h6' ml='5vw'>
                    인기 클래스
                </Typography>
            </Grid>
            <Grid container my='2vw' direction='row' alignItems='flex-end' justifyContent='space-around'>
                <Grid item >
                    {popularLesson[0].lesson_sname?
                    <img src={popularLesson[0].lesson_sname} alt={popularLesson[0].lesson_sname}  style={{width:'15vw', height:'15vw',borderRadius:'15vw', cursor: 'pointer'}} />
                    :<Skeleton variant='circular' width='15vw' height='15vw' />
                    }
                </Grid>
                <Grid item  >
                    {popularLesson[1].lesson_sname?
                    <img src={popularLesson[1].lesson_sname} alt={popularLesson[1].lesson_sname} style={{width:'20vw', height:'20vw' ,borderRadius:'15vw', cursor: 'pointer'}}/>
                    :<Skeleton variant='circular' width='20vw' height='20vw' />
                    }
                </Grid>
                <Grid item >
                    {popularLesson[2].lesson_sname?
                    <img src={popularLesson[2].lesson_sname} alt={popularLesson[2].lesson_sname} style={{width:'15vw', height:'15vw' ,borderRadius:'15vw', cursor: 'pointer'}}/>
                    :<Skeleton variant='circular' width='15vw' height='15vw' />
                    }
                </Grid>
            </Grid>
            <Grid container direction='row' justifyContent='space-around' >
                <ClassContent m='-1vw' title={popularLesson[0].lesson_title} content={popularLesson[0].lesson_content} recipeNo={popularLesson[0].lesson_no} />
                <ClassContent m='0.5vw' title={popularLesson[1].lesson_title} content={popularLesson[1].lesson_content} recipeNo={popularLesson[1].lesson_no}/>
                <ClassContent m='2vw' title={popularLesson[2].lesson_title} content={popularLesson[2].lesson_content} recipeNo={popularLesson[2].lesson_no}/>
            </Grid>
        </Grid>
        

        
    );


}
export default PopularClass;