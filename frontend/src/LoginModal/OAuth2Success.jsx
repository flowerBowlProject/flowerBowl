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

    console.log(token);
    console.log(isMatchNickname);


    useEffect(()=>{
        dispatch({ type: "accessToken", payload: token });
        navigator('/');
    },[])
}

export default OAuth2Success;