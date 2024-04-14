import React from 'react'
import { Grid ,Typography} from "@mui/material";
import Button_contain_style from './Button_contain_style';
import Input_search_style from '../MainPage/Input_search_style.jsx';
const Form_Signup=({title,but_text,place_text,helper_text,but_exis})=>{
    return(
        <Grid contianer xs={5} direction='column' mt='1vw' ml='2.5vw'>
                <Grid container xs direction='row'>
                    <Grid item xs={6}>
                        <Typography color='main.br' variant='h6'>{title}</Typography>
                    </Grid>
                    <Grid item xs={6}>
                        {but_exis?
                        <Button_contain_style width='5vw' size='small' sx={{height:'1vw'}}>{but_text}</Button_contain_style>
                        :null}
                    </Grid>
                </Grid>
                <Grid xs item mt='0.2vw'>
                <Input_search_style sx={{ width: '15vw' }}variant='outlined' size='small' placeholder={place_text} helperText={helper_text} />
                </Grid>
        </Grid>
    );
}
export default Form_Signup;