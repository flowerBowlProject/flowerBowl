import Reac, { useEffect } from 'react';
import {Grid,Typography,Skeleton } from '@mui/material';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import axios from 'axios';
import { url } from '../url';
const PopularRecipe=()=>{
    useEffect(()=>{
        const fetchData=async()=>{
            try{
                const response=await axios.get(`${url}/api/recipes/guest`)
                console.log(response.data.posts)

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
                    <Skeleton variant="rectangular" width='16vw' height='16vw' />
                </Grid> 
                <Grid item sm>
                    <Skeleton variant="rectangular" width='16vw' height='16vw' />
                </Grid> 
                <Grid item sm>
                    <Skeleton variant="rectangular" width='16vw' height='16vw' />
                </Grid> 
                <Grid item sm>
                    <Skeleton variant="rectangular" width='16vw' height='16vw' />
                </Grid> 
                <Grid item sm>
                    <Skeleton variant="rectangular" width='16vw' height='16vw' />
                </Grid> 
            </Grid>
        </Grid>
        


    );

}
export default PopularRecipe;