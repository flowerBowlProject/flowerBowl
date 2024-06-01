import React,{useState,useEffect} from "react";
import { Grid, Typography } from "@mui/material";
import ButtonContainStyle from "./ButtonContainStyle.jsx";
import Input_search_style from "../MainPage/InputSearchStyle.jsx";
import { validation,validationPassConfirm } from "../Hook/Validation.jsx";
import { useSelector,useDispatch } from "react-redux";
import { HideDuplication, SETMEMBEREMAIL, setMemberNewPw, setMemberPw, setMemberTel, setMermberEmail } from "../persistStore.jsx";
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
  handleBut=()=>{},
  handleCheck=()=>{},
  disable,
  valueUser,
  handleChange: handleChangeProp
}) => {
  const dispatch=useDispatch();
  const duplicationBoolean=useSelector(state=>state.duplicationBoolean[vaild])
  const duplicationText=useSelector(state=>state.duplicationText[vaild])
  const [validTest,setValidTest]= useState(false);
  const [text,setText]= useState('');
  const handleChange=(e)=>{
    const value=e.target.value;
    setText(value);
    if(setPass!=null)
    setPass(value);
    handleBut(vaild,pass_confirm?validationPassConfirm(value,vaild):validation(value,vaild))
    if(duplicationBoolean){dispatch(HideDuplication(vaild))}
    if (handleChangeProp) handleChangeProp(e);
  }
  const handleBlur=(e)=>{
    const value=e.target.value;
    setValidTest(true);
    if(vaild==='pw'){
      dispatch(setMemberPw(value))
    }else if(vaild==='tel'){
      dispatch(setMemberTel(value))
    }else if(vaild==='newPw'){
      dispatch(setMemberNewPw(value))
    }
    
  }
  useEffect(()=>{
    setText(valueUser);
  },[valueUser])
  const handleSize=()=>{
    if(size==='joy'){
        return{
          xs_1:11.1,
          ml:'2.5vw',
          height:'null',
          mt:'null',
          height_2:'3vw'
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
                onClick={()=>handleCheck(text)}
                disabled={validation(text,vaild)}
              >
                {but_text}
              </ButtonContainStyle>
            ) : null}
          </Grid>
        </Grid>
        <Grid xs item mt="0.2vw" height={form_size.height} >
          <Input_search_style
            disabled={disable}
            type={pass_exis ? "password" : "text"}
            sx={{ width: "20vw",height:form_size.height_2 }}
            variant="outlined"
            size="small"
            value={text}
            onBlur={(e)=>handleBlur(e)}
            onChange={handleChange}
            placeholder={place_text}
            helperText={duplicationBoolean?duplicationText:validTest?pass_confirm?validationPassConfirm(text,vaild)?helper_text:'':validation(text,vaild)?helper_text:'':''}
          />
        </Grid>
      </Grid>
    </Grid>
  );
};
export default FormSignup;
