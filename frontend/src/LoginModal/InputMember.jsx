import React,{useState} from 'react'
import {Grid,Typography,Skeleton,TextField} from '@mui/material'
import InputMemberStyle from './InputMemberStyle';
import { validation } from '../Hook/Validation';
const InputMember=({name,helpertext,type_pass,vaildTest,setBut=()=>{},setUser=()=>{}})=>{
    const [text,setText]= useState('');
    const handleText = (e)=>{
        const value=e.target.value;
        setText(value);
        setUser(name,value);
        const tf=validation(value,vaildTest);
        setBut(tf);
    }
    const [validTest,setValidTest]=useState(false);
    return(
        <Grid container direction='row' justifyContent='center'  my='2vw'>
            <Grid item xs={4}>
                <Typography variant='h5' color='#ffffff' align='center' ml='6vw'>{name}</Typography>
            </Grid>
            <Grid item xs={5} mr='8vw' height='3vw' > 
                <InputMemberStyle type={type_pass?'password':null}  onBlur={()=>{setValidTest(true)}}  variant='standard' onChange={handleText} helperText={validTest?validation(text,vaildTest)?helpertext:'':''}sx={{width:'17.5vw'}}  />
            </Grid>
        </Grid>
    )
}
export default InputMember;