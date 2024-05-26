import React, { useEffect, useState } from "react";
import axios from "axios";
import { url } from "../url";
import { useDispatch, useSelector } from "react-redux";
import ButtonContain from "../Component/ButtonContain";
import { editErrorType, openError } from "../persistStore";

const { IMP } = window;

const ClassPayment = ({ lesson_no }) => {
    const [paymentData, setPaymentData] = useState({});
    const accessToken = useSelector(state => state.accessToken);
    const dispatch = useDispatch();

    const buyClass = async () => {
        if(accessToken==''){
            dispatch(editErrorType('NT'));
            dispatch(openError());
        }

        axios.post(`${url}/api/user/lessons/payments`, {
            "lesson_no": lesson_no
        }, {
            headers: {
                Authorization: `Bearer ${accessToken}`
            }
        })
        .then(res=>{
                if (window.IMP) {
                    window.IMP.init([res.data.store_id]); // 결제 데이터 정의
                }
                window.IMP.request_pay(res.data?.request_pay, callback);
            })
        .catch(err=>{
            if(err.response.data.code==='FA'){
                dispatch(editErrorType('BUYCOMPLETE'));
                dispatch(openError());
            }
        })
    };

    const callback = (response) => {
        const { success, error_msg, imp_uid, merchant_uid, pay_method, paid_amount, status } = response;
        console.log(response);
        if (success) {
            console.log('결제 완료되었습니다.');
        } else {
            console.log('실패');
        }
    };

    return (
        <>
            <ButtonContain size='large' text='구매' handleClick={buyClass}/>
        </>
    ); // 이 컴포넌트는 실제로 렌더링할 필요가 없으므로 null을 반환합니다.
};

export default ClassPayment;