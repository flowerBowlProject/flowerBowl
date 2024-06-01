import React, { useEffect } from "react";
import { useDispatch } from "react-redux";
import { useLocation, useNavigate } from "react-router-dom";
import { editErrorType, openError } from "../persistStore";

const OAuth2Success = () =>{
    const navigator = useNavigate();
    const dispatch = useDispatch();
    const location = useLocation();
    const params = new URLSearchParams(location.search);
    const token = params.get('token');
    const isMatchNickname = params.get('isMatchNickname');

    useEffect(()=>{
        dispatch({ type: "accessToken", payload: token });

        if(isMatchNickname==='true'){
            // 중복된 닉네임이 있다면
            dispatch(editErrorType('DN'));
            dispatch(openError());
            console.log(isMatchNickname);
            //navigator('/Mypage/profile');
        }else{
            dispatch(editErrorType('LOGIN'));
            dispatch(openError());
            console.log(isMatchNickname);
            //navigator('/');
        }
        
    },[]);

    return(
        <></>
    );
}

export default OAuth2Success;