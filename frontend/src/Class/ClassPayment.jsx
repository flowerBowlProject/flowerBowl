import React, { useEffect, useState } from "react";
import axios from "axios";
import { url } from "../url";
import { useSelector } from "react-redux";
import ButtonContain from "../Component/ButtonContain";

const { IMP } = window;

const ClassPayment = ({ lesson_no }) => {
    const [paymentData, setPaymentData] = useState({});
    const accessToken = useSelector(state => state.accessToken);

    const buyClass = async () => {
        try {
            const response = await axios.post(`${url}/api/user/lessons/payments`, {
                "lesson_no": lesson_no
            }, {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            });
            console.log(response);
            setPaymentData(response.data.request_pay);
            if (window.IMP) {
                window.IMP.init([response.data.store_id]); // 결제 데이터 정의
            }
            window.IMP.request_pay(response.data.request_pay, callback);
        } catch (error) {
            console.log(error);
        }
    };

    const callback = (response) => {
        const { success, error_msg, imp_uid, merchant_uid, pay_method, paid_amount, status } = response;
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