import React, { useEffect } from "react";
import { useDispatch } from "react-redux";
import { useLocation, useNavigate } from "react-router-dom";

const OAuth2Success = () =>{
    const navigator = useNavigate();
    const dispatch = useDispatch();
    const location = useLocation();
    const params = new URLSearchParams(location.search);
    const token = params.get('token');

    useEffect(()=>{
        dispatch({ type: "accessToken", payload: token });
        navigator('/');
    },[token])
}

export default OAuth2Success;