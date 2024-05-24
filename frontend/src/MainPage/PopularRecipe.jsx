import React, { useEffect ,useState} from 'react';
import {Grid,Typography,Skeleton } from '@mui/material';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import axios from 'axios';
import { url } from '../url';
import { useNavigate } from "react-router-dom";
const PopularRecipe=()=>{
    const [popularRecipe,setPopularRecipe]=useState(Array(5).fill({recipe_no:'',recipe_sname:''}))
    const navigator = useNavigate();
    const handleClick=(event)=>{
        const target=event.target.id
        console.log(target);
        switch(target){
            case 'img1':
                navigator(`/recipeDetail/${popularRecipe[0].recipe_no}`);
                break;
            case 'img2':
                navigator(`/recipeDetail/${popularRecipe[1].recipe_no}`);
                break;
            case 'img3':                
                navigator(`/recipeDetail/${popularRecipe[2].recipe_no}`);
                break;
            case 'img4':
                navigator(`/recipeDetail/${popularRecipe[3].recipe_no}`);
                break;
            case 'img5':
                navigator(`/recipeDetail/${popularRecipe[4].recipe_no}`);
                break;
        }
    }
    
    useEffect(()=>{
        const fetchData=async()=>{
            try{
                const response=await axios.get(`${url}/api/recipes/popular`)
                console.log(response.data.posts)
                setPopularRecipe(response.data.posts)
            }catch(error){
                console.log(error.response.data)
            }
        }
        fetchData();
    },[])
    
    return(
        <Grid container my='5vw' direction='row' alignContent='center' >
            <Grid item sm={6}> 
                <Typography sx={{marginLeft:'2vw'}} variant='h6'>인기 레시피</Typography>
            </Grid>
            <Grid  justifyContent='flex-end'  sm={6} container  item>  
                <Typography noWrap   color='main.or'>더보기 </Typography>
                <ArrowForwardIosIcon  sx={{marginRight:'2vw',color:'main.or'}}></ArrowForwardIosIcon>
            </Grid>
            <Grid direction="row" sm={12} container  justifyContent="center" my='2vw' >
                <Grid item sm ml='3vw'>
                    {popularRecipe[0].recipe_sname?<img alt={popularRecipe[0].recipe_sname} onClick={handleClick} id='img1' src={popularRecipe[0].recipe_sname} style={{width:'16vw' ,height:'16vw', cursor: 'pointer'}}/>:<Skeleton variant="rectangular" width='16vw' height='16vw' />}
                </Grid> 
                <Grid item sm>
                {popularRecipe[1].recipe_sname?<img alt={popularRecipe[1].recipe_sname}   onClick={handleClick} id='img2' src={popularRecipe[1].recipe_sname} style={{width:'16vw' ,height:'16vw', cursor: 'pointer'}}/>:<Skeleton variant="rectangular" width='16vw' height='16vw' />}
                </Grid> 
                <Grid item sm>
                {popularRecipe[2].recipe_sname?<img alt={popularRecipe[2].recipe_sname}  onClick={handleClick} id='img3' src={popularRecipe[2].recipe_sname} style={{width:'16vw' ,height:'16vw', cursor: 'pointer'}}/>:<Skeleton variant="rectangular" width='16vw' height='16vw' />}
                </Grid> 
                <Grid item sm>
                {popularRecipe[3].recipe_sname?<img alt={popularRecipe[3].recipe_sname}  onClick={handleClick} id='img4' src={popularRecipe[3].recipe_sname} style={{width:'16vw' ,height:'16vw', cursor: 'pointer'}}/>:<Skeleton variant="rectangular" width='16vw' height='16vw' />}
                </Grid> 
                <Grid item sm>
                {popularRecipe[4].recipe_sname?<img alt={popularRecipe[4].recipe_sname} onClick={handleClick}  id='img5' src={popularRecipe[4].recipe_sname} style={{width:'16vw' ,height:'16vw', cursor: 'pointer'}}/>:<Skeleton variant="rectangular" width='16vw' height='16vw' />}
                </Grid> 
            </Grid>
        </Grid>
        


    );

}
export default PopularRecipe;