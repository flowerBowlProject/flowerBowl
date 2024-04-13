import React from 'react'
import {Grid,Typography,Skeleton,Pagination } from '@mui/material';
const  CustomRecipe=()=>{
    return(
    
        <Grid container sx={{backgroundColor:'main.br'}} pt='5vw' >
            <Grid xs={5.1} item mt='3vw' ml='3vw'>
                <Typography  variant='h4' >  
                    자체 제작 레시피 - 관리자가 작성한 레시피
                </Typography>
                <Typography mt='1vw' >
                    화반에서 제공하는 레시피를 따라해 보세요~
                </Typography>
            </Grid>
            <Grid xs={6.5}   item container direction='column' alignItems='center'>
                <Grid item>
                    <Skeleton variant='rectangular' width='40vw' height='20vw'  />
                </Grid>
                <Grid item my='2vw'>
                    <Pagination count={5} size="small" variant='outlined'   color='secondary'/>
                </Grid>
            </Grid>
        </Grid>
    
    );
}
export default CustomRecipe;

