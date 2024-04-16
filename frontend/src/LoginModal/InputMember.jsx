import React from 'react'
import {Grid,Typography,Skeleton,TextField} from '@mui/material'
import InputMemberStyle from './InputMemberStyle';
const InputMember=({name,helpertext,type_pass})=>{
    return(
        <Grid container direction='row' justifyContent='center'  my='2vw'>
            <Grid item xs={5}>
                <Typography variant='h5' color='#ffffff' align='center' ml='6vw'>{name}</Typography>
            </Grid>
            <Grid item xs={4} mr='8vw' height='3vw' > 
                <InputMemberStyle type={type_pass?'password':null}  variant='standard'  helperText={helpertext} fullWidth/>
            </Grid>
        </Grid>
    )
}
export default InputMember;