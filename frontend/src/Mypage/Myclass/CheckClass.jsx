import ButtonContain from "../../Component/ButtonContain";
import ButtonOutlined from "../../Component/ButtonOutlined";
import { Grid } from "@mui/material";
import { useState, useEffect } from "react";
import { Outlet, Link, useNavigate } from "react-router-dom";

const CheckClass = () => {
  const navigate = useNavigate();
  const [buttonClick, setButtonClick] = useState([false, false]);
  const handleClickTeaching = () => {
    setButtonClick([true, false]);
  };
  const handleClickMaking = () => {
    setButtonClick([false, true]);
  };
  useEffect(() => {
    handleClickTeaching();
    navigate("/mypage/checkClass/teaching");
  }, []);
  return (
    <Grid container direction="row" gap={2}>
      <Grid item xs={2.1} textAlign="end">
        <Link to="/mypage/checkClass/teaching">
          {buttonClick[0] ? (
            <ButtonContain
              size="doubleLarge"
              text="수강클래스 조회"
              handleClick={handleClickTeaching}
            />
          ) : (
            <ButtonOutlined
              size="doubleLarge"
              text="수강클래스 조회"
              handleClick={handleClickTeaching}
            />
          )}
        </Link>
      </Grid>
      <Grid item xs={2}>
        <Link to="/mypage/checkClass/making">
          {buttonClick[1] ? (
            <ButtonContain
              size="doubleLarge"
              text="창작클래스 조회"
              handleClick={handleClickMaking}
            />
          ) : (
            <ButtonOutlined
              size="doubleLarge"
              text="창작클래스 조회"
              handleClick={handleClickMaking}
            />
          )}
        </Link>
      </Grid>
      <Grid item xs={12}>
        <Outlet />
      </Grid>
    </Grid>
  );
};
export default CheckClass;
