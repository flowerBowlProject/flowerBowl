import { Outlet, Link, useNavigate } from "react-router-dom";
import ButtonContain from "../../Component/ButtonContain";
import ButtonOutlined from "../../Component/ButtonOutlined";
import { Grid } from "@mui/material";
import { useState, useEffect } from "react";

const PaymentDetail = () => {
  const navigate = useNavigate();
  const [buttonClick, setButtonClick] = useState([false, false, false]);
  const handleClickPaymentDetail = () => {
    setButtonClick([true, false, false]);
  };
  const handleClickReview = () => {
    setButtonClick([false, true, false]);
  };
  const handleClickReviewWrite = () => {
    setButtonClick([false, false, true]);
  };
  useEffect(() => {
    handleClickPaymentDetail();
    navigate("/mypage/paymentDetail/checkPaidList");
  }, []);

  return (
    <Grid container direction="row" mt="3vw">
      <Grid item xs={0.9} ml="6vw">
        <Link to="/mypage/paymentDetail/checkPaidList">
          {buttonClick[0] ? (
            <ButtonContain
              size="medium"
              text="결제 내역"
              handleClick={handleClickPaymentDetail}
            />
          ) : (
            <ButtonOutlined
              size="medium"
              text="결제 내역"
              handleClick={handleClickPaymentDetail}
            />
          )}
        </Link>
      </Grid>
      <Grid item xs={0.9}>
        <Link to="/mypage/paymentDetail/checkReview">
          {buttonClick[1] ? (
            <ButtonContain
              size="medium"
              text="리뷰 조회"
              handleClick={handleClickReview}
            />
          ) : (
            <ButtonOutlined
              size="medium"
              text="리뷰 조회"
              handleClick={handleClickReview}
            />
          )}
        </Link>
      </Grid>
      <Grid item xs={0.9}>
        <Link to="/mypage/paymentDetail/registerReview">
          {buttonClick[2] ? (
            <ButtonContain
              size="medium"
              text="리뷰 작성"
              handleClick={handleClickReviewWrite}
            />
          ) : (
            <ButtonOutlined
              size="medium"
              text="리뷰 작성"
              handleClick={handleClickReviewWrite}
            />
          )}
        </Link>
      </Grid>
      <Grid item xs={12} mt="1vw">
        <Outlet />
      </Grid>
    </Grid>
  );
};
export default PaymentDetail;
