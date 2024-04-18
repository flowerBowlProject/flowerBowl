import React,{useState} from "react";
import { Grid, Typography } from "@mui/material";
import ButtonContainStyle from "./ButtonContainStyle.jsx";
import Input_search_style from "../MainPage/InputSearchStyle.jsx";
import { validation,validationPassConfirm } from "../Hook/Validation.jsx";
const FormSignup = ({
  title,
  but_text,
  place_text,
  helper_text,
  but_exis,
  pass_exis,
  size,
  vaild,
  setPass,
  pass_confirm,
  handleBut=()=>{}
}) => {
  const [validTest,setValidTest]= useState(false);
  const [text,setText]= useState('');
  const handleChange=(e)=>{
    const value=e.target.value;
    setText(value);
    if(setPass!=null)
    setPass(value);
    handleBut(vaild,pass_confirm?validationPassConfirm(value,vaild):validation(value,vaild))
  }
  const handleSize=()=>{
    if(size==='joy'){
        return{
          xs_1:11.1,
          ml:'2.5vw',
          height:'null',
          mt:'null'
        }
    }else if(size==='towel'){
        return{
          xs_1:6,
          ml:'1vw',
          height:'4vw',
          mt:'1.5vw',
          height_2:'5vw'
      }
    }
  }
  const form_size=handleSize()
  return (
    <Grid item xs={5}>
      <Grid container direction="column" mt={form_size.mt} ml={form_size.ml}>
        <Grid container xs direction="row">
          <Grid item xs={form_size.xs_1}>
            <Typography color="main.br" variant="h6">
              {title}
            </Typography>
          </Grid>
          <Grid item xs={0.9}>
            {but_exis ? (
              <ButtonContainStyle
                width="5vw"
                size="small"
                sx={{ height: "1vw", ml: "3.2vw" }}
              >
                {but_text}
              </ButtonContainStyle>
            ) : null}
          </Grid>
        </Grid>
        <Grid xs item mt="0.2vw" height={form_size.height} >
          <Input_search_style
            type={pass_exis ? "password" : "text"}
            sx={{ width: "20vw",height:form_size.height_2 }}
            variant="outlined"
            size="small"
            onBlur={()=>setValidTest(true)}
            onChange={handleChange}
            placeholder={place_text}
            helperText={validTest?pass_confirm?validationPassConfirm(text,vaild)?helper_text:'':validation(text,vaild)?helper_text:'':''}
          />
        </Grid>
      </Grid>
    </Grid>
  );
};
export default FormSignup;
