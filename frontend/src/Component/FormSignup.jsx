import React from 'react'
import { Grid ,Typography} from "@mui/material";
import ButtonContainStyle from './ButtonContainStyle.jsx';
import Input_search_style from '../MainPage/InputSearchStyle.jsx';
const FormSignup=({title,but_text,place_text,helper_text,but_exis,pass_exis})=>{
    return(
        <Grid item xs={5}>
        <Grid container  direction='column' mt='1vw' ml='2.5vw'>
                <Grid container xs direction='row'>
                    <Grid item xs={5}>
                        <Typography color='main.br' variant='h6'>{title}</Typography>
                    </Grid>
                    <Grid item xs={6}>
                        {but_exis?
                        <ButtonContainStyle width='5vw' size='small' sx={{height:'1vw',ml:'1vw'}}>{but_text}</ButtonContainStyle>
                        :null}
                    </Grid>
                </Grid>
                <Grid xs item mt='0.2vw'>
                    <Input_search_style type={pass_exis?"password":"text"} sx={{width:'15vw'}} variant='outlined' size='small' placeholder={place_text} helperText={helper_text} />
                </Grid>
        </Grid>
        </Grid>
    );
}
export default FormSignup;