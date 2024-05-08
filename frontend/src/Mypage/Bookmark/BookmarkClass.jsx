import React, { useEffect, useState } from "react";
import ClassReviewCard from "../../Component/CardComp";
import Bookmark from "../../Component/Bookmark";
import ButtonContain from "../../Component/ButtonContain";
import ButtonOutlined from "../../Component/ButtonOutlined";
import "./BookmarkRecipe.css";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import { url } from "../../url";
import ErrorConfirm from "../../Hook/ErrorConfirm";
import { useDispatch, useSelector } from "react-redux";
import { editErrorType, openError } from "../../persistStore";

const BookmarkClass = () => {
  const navigator = useNavigate();
  const [listData, setListData] = useState([]);
  const accessToken = useSelector((state) => state.accessToken);
  const dispatch = useDispatch();
  const errorType = useSelector((state) => state.errorType);
  const [slice, setSlice] = useState(8);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`${url}/api/mypage/lesson/likes`, {
          headers: { Authorization: `Bearer ${accessToken}` },
        });
        if (response.data?.likeLessons) {
          const updatedListData = response.data.likeLessons.map((item) => ({
            ...item,
            classLikeStatus: true,
          }));
          setListData(updatedListData);
        }
      } catch (error) {
        dispatch(editErrorType(error.response.data.code));
        dispatch(openError());
        setListData([]);
      }
    };
    fetchData();
  }, [accessToken]);

  const handleClickMoreDetail = () => {
    if (listData.length > slice) setSlice(slice + 8);
  };

  //북마크 해제
  const byeBookmark = async (lessonNo) => {
    console.log(listData);
    try {
      const response = await axios.delete(
        `${url}/api/user/lessons/like/${lessonNo}`,
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );
      if (response.status === 200) {
        setListData((currentListData) =>
          currentListData.filter((lesson) => lesson.lesson_no !== lessonNo)
        );
      }
    } catch (error) {
      console.error("Error in bookmark toggle:", error);
      dispatch(editErrorType(error.response.data.code));
      dispatch(openError());
    }
  };

  // 상세페이지 이동
  const clickDetail = (e, lessonNo) => {
    navigator(`/classDetail/${lessonNo}`);
  };

  return (
    <>
      <ErrorConfirm error={errorType} />
      <div className="division-line"></div>

      <div className="bookmark-content">
        {listData.length > 0 ? (
          [...listData.slice(0, slice)].map((data, index) => (
            <div key={index} style={{ position: "relative" }}>
              <Bookmark
                check={data.classLikeStatus}
                sx={{ cursor: "pointer" }}
                onClick={() => byeBookmark(data.lesson_no)}
              />
              <ClassReviewCard
                onClick={(e) => clickDetail(e, data.lesson_no)}
                title={data.lesson_title}
                like_count={data.lesson_like_cnt}
                sname={data.lesson_sname}
                date={data.lesson_date}
              />
            </div>
          ))
        ) : (
          <p>아직 북마크한 클래스가 없습니다.</p>
        )}
      </div>

      <div className="division-line"></div>

      <div className="add">
        <ButtonContain
          size="large"
          text="더보기"
          handleClick={handleClickMoreDetail}
        />
      </div>
    </>
  );
};

export default BookmarkClass;
