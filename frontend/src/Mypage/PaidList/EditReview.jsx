import React, { useState, useEffect } from "react";
import Rating from "@mui/material/Rating";
import "./RegisterReview.css";
import ButtonContain from "../../Component/ButtonContain";
import ButtonOutlined from "../../Component/ButtonOutlined";
import axios from "axios";
import { Link, useParams, useNavigate } from "react-router-dom";
import { url } from "../../url";
import ErrorConfirm from "../../Hook/ErrorConfirm";
import { useDispatch, useSelector } from "react-redux";
import { editErrorType, openError } from "../../persistStore";

const EditReview = () => {
  // const [value, setValue] = useState(0);
  // const [listData, setListData] = useState([]);
  const [lessonTitle, setLessonTitle] = useState([]);
  const [reviewScore, setReviewScore] = useState(0);
  const [reviewContent, setReviewContent] = useState("");
  const accessToken = useSelector((state) => state.accessToken);
  const dispatch = useDispatch();
  const errorType = useSelector((state) => state.errorType);
  const { review_no } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`${url}/api/reviews/${review_no}`, {
          headers: { Authorization: `Bearer ${accessToken}` },
        });
        setLessonTitle(response.data.lesson_title);
        setReviewScore(response.data.review_score);
        setReviewContent(response.data.review_content);
      } catch (error) {
        dispatch(editErrorType(error.response.data.code));
        dispatch(openError());
        console.error("Error fetching data:", error);
      }
    };
    fetchData();
  }, [accessToken, review_no]);

  //리뷰 수정
  const handleUpdateReview = async () => {
    try {
      const updateData = {
        review_content: reviewContent,
        review_score: reviewScore,
      };
      const response = await axios.patch(
        `${url}/api/reviews/${review_no}`,
        updateData,
        {
          headers: { Authorization: `Bearer ${accessToken}` },
        }
      );
      dispatch(editErrorType("REVIEW SUCCESS"));
      dispatch(openError());
      navigate("/mypage/paymentDetail/checkReview");
    } catch (error) {
      dispatch(editErrorType(error.response.data.code));
      dispatch(openError());
      console.error("업데이트에 실패했습니다:", error);
    }
  };

  return (
    <div className="all">
      <ErrorConfirm error={errorType} />
      {/* 클래스 목록 */}
      <section className="teachingclass">
        <div className="division-line-or"></div>
        <div className="text-title">{lessonTitle}</div>

        <div className="division-line-or"></div>
        <div className="text-content">
          클래스는 어떠셨나요?
          <br />
          솔직한 리뷰를 남겨주세요.
        </div>
      </section>

      {/* 평점기능 */}
      <Rating
        name="size-large"
        // value={reviewScore ? reviewScore : 0}
        value={reviewScore}
        size="large"
        onChange={(event, newValue) => {
          setReviewScore(newValue);
        }}
        sx={{
          fontSize: "6em",
          color: "main.or",
          display: "flex",
          justifyContent: "center",
          "& .MuiRating-iconEmpty": {
            color: "main.gr",
          },
        }}
      />
      <section />
      <section className="input-content">
        <div className="division-line"></div>
        {/* 인풋창 */}
        <textarea
          className="styled-input"
          placeholder="피드백을 남겨주세요."
          value={reviewContent}
          onChange={(e) => setReviewContent(e.target.value)}
        ></textarea>
      </section>
      {/* 하단부 */}
      <div className="division-line-br"></div>
      <div className="bottom-buttons">
        <span className="rgok">
          <ButtonOutlined
            size="medium"
            text="수정"
            handleClick={handleUpdateReview}
          />
        </span>
        <span className="rgcl">
          <Link to="/mypage/paymentDetail/checkReview">
            <ButtonContain size="medium" text="취소" />
          </Link>
        </span>
      </div>
    </div>
  );
};

export default EditReview;
