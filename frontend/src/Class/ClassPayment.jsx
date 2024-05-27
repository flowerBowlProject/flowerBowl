import React, { useEffect, useState } from "react";
import axios from "axios";
import { url } from "../url";
import { useDispatch, useSelector } from "react-redux";
import ButtonContain from "../Component/ButtonContain";
import { editErrorType, openError } from "../persistStore";

const { IMP } = window;

const ClassPayment = ({ lesson_no }) => {
    const [paymentData, setPaymentData] = useState({
        amount: 123,
        buyer_email: "khj4209k@naver.com",
        buyer_name : "일반회원테스트",
        buyer_tel: "01012345678",
        merchant_uid: "2024-05-27_5",
        name: "클래스제목",
        pay_method: "card",
        pg: "kakaopay.TC0ONETIME"
    });
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
            console.log(res);
                window.IMP.init([res.data.store_id]); // 결제 데이터 정의
                window.IMP.request_pay(res.data?.request_pay, callback);
            })
        .catch(err=>{
            console.log(err);
            if(err.response?.data.code==='FA'){
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
            if(error_msg === "[결제포기] 사용자가 결제를 취소하셨습니다"){
                axios.get(`${url}/api/mypage/pays`, {
                    headers: {
                      Authorization: `Bearer ${accessToken}`,
                    },
                })
                .then(res=>{
                    console.log(res.data.pays[0].pay_no);
                    axios.delete(`${url}/api/mypage/pays/${res.data.pays[0].pay_no}`,{
                        headers:{
                            Authorization : `Bearer ${accessToken}`,
                        }
                    })
                    .then(res=>{
                        dispatch(editErrorType('BUYCANCEL'));
                        dispatch(openError());
                    })
                    .catch(err=>{
                        console.log(err);
                    })
                })
                .catch(err=>{
                    console.log(err);
                })
                
            }else{
                console.log('실패');
            }
            
        }
    };

    return (
        <>
            <ButtonContain size='large' text='구매' handleClick={buyClass}/>
        </>
    ); // 이 컴포넌트는 실제로 렌더링할 필요가 없으므로 null을 반환합니다.
};

export default ClassPayment;