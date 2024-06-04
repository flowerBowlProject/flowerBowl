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

         axios.get(`${url}/api/users/info`, {
              headers: {
                Authorization: `Bearer ${response.data.access_token}`,
              },
        })
        .then(res=>{
            dispatch({ type: "nickname", payload: res.data.user_nickname });
        })
        .catch (err=> {
            console.log(err);
        })

        if(isMatchNickname==='true'){
            // 중복된 닉네임이 있다면
            dispatch(editErrorType('DN'));
            dispatch(openError());
            navigator('/Mypage/profile');
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