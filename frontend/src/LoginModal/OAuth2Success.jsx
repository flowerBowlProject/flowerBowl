import React, { useEffect } from "react";
import { useDispatch } from "react-redux";
import { useLocation, useNavigate } from "react-router-dom";

const OAuth2Success = () =>{
    const navigator = useNavigate();
    const dispatch = useDispatch();
    const location = useLocation();
    const params = new URLSearchParams(location.search);
    const token = params.get('token');
    const isMatchNickname = params.get('isMatchNickname');

    useEffect(()=>{
        dispatch({ type: "accessToken", payload: token });

        if(isMatchNickname){
            // 중복된 닉네임이 있다면
            dispatch(editErrorType('DN'));
            dispatch(openError());
        }else{
            dispatch(editErrorType('LOGIN'));
            dispatch(openError());
            navigator('/');
        }
        
    },[]);

    return(
        <></>
    );
}

export default OAuth2Success;