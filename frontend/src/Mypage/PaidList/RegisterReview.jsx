import React, { useState, useEffect } from "react";
import Rating from "@mui/material/Rating";
import "./RegisterReview.css";
import ButtonContain from "../../Component/ButtonContain";
import ButtonOutlined from "../../Component/ButtonOutlined";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import { url } from "../../url";
import ErrorConfirm from "../../Hook/ErrorConfirm";
import { useDispatch, useSelector } from "react-redux";
import { editErrorType, openError } from "../../persistStore";

const RegisterReview = () => {
  const [value, setValue] = useState(0);
  const [expanded, setExpanded] = useState(false); // 클릭한 클래스 데이터 추가
  const [title, setTitle] = useState([]);
  const [selectedTitle, setSelectedTitle] = useState("");
  const [reviewContent, setReviewContent] = useState("");
  const accessToken = useSelector((state) => state.accessToken);
  const dispatch = useDispatch();
  const errorType = useSelector((state) => state.errorType);
  const navigate = useNavigate();

  useEffect(() => {
    const getTitle = async () => {
      try {
        const response = await axios.get(`${url}/api/reviews/list`, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        });
        setTitle(response.data.availableReviews);
      } catch (error) {
        console.error("Error fetching data:", error);
        dispatch(editErrorType(error.response.data.code));
        dispatch(openError());
      }
    };
    getTitle();
  }, [accessToken]);

  // 클래스 선택 핸들러
  // const handleClassSelect = (title) => {
  //   setSelectedTitle(title); // 선택된 타이틀 업데이트
  //   setExpanded(false); // 선택 후 목록 접기
  // };
  const handleClassSelect = (classObject) => {
    setSelectedTitle(classObject);
    setExpanded(false);
  };

  const closeModal = () => {
    setExpanded(false);
  };

  //리뷰 등록
  const submitReview = async (lesson_no, value, reviewContent) => {
    if (!lesson_no) {
      console.error("No lesson number provided");
      return;
    }

    console.log("Submitting review with data:", {
      lesson_no: lesson_no,
      review_content: reviewContent,
      review_score: value,
    });
    try {
      const response = await axios.post(
        `${url}/api/reviews`,
        {
          review_content: reviewContent,
          review_score: value,
          lesson_no: lesson_no,
        },
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );
      navigate("/mypage/paymentDetail/checkReview");
      console.log("Response:", response.data);
      dispatch(editErrorType("REVIEW UPDATE SUCCESS"));
      dispatch(openError());
    } catch (error) {
      dispatch(editErrorType(error.response.data.code));
      dispatch(openError());
      console.error("Error posting review:", error);
    }
  };

  return (
    <div className="all">
      <ErrorConfirm error={errorType} />
      {/* 클래스 목록 */}
      <section className="teachingclass">
        <div className="division-line-or"></div>
        <div className="text-title" onClick={() => setExpanded(!expanded)}>
          {selectedTitle ? selectedTitle.lesson_title : "클래스 선택"}
          &emsp;
          <span>{expanded ? "▲" : "▼"}</span>
        </div>
        {/* 선택 가능한 클래스 목록 */}
        {expanded && (
          <div className="class-list-modal">
            {title.map((classData, index) => (
              <div
                key={index}
                className="class-option"
                onClick={() => handleClassSelect(classData)}
              >
                {classData.lesson_title}
              </div>
            ))}
          </div>
        )}

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
        value={value}
        size="large"
        onChange={(event, newValue) => {
          setValue(newValue);
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
            text="등록"
            handleClick={() => {
              if (value > 0) {
                submitReview(selectedTitle.lesson_no, value, reviewContent);
              } else {
                alert("별점을 매겨주세요!");
              }
            }}
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

export default RegisterReview;
