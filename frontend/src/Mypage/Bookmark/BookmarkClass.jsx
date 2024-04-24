import React, { useEffect, useState } from "react";
import ClassReviewCard from "../../Component/CardComp";
import Bookmark from "../../Component/Bookmark";
import ButtonContain from "../../Component/ButtonContain";
import ButtonOutlined from "../../Component/ButtonOutlined";
import "./BookmarkRecipe.css";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import { url } from "../../url";
import { useSelector } from "react-redux";

const BookmarkClass = () => {
  const navigator = useNavigate();
  const [listData, setListData] = useState([]);
  const accessToken = useSelector((state) => state.accessToken);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`${url}/api/mypage/lesson/likes`, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        });
        setListData(response.data.likeLessons);
        //코드 확인
        // console.log(response.data.likeLessons);
      } catch (error) {
        console.error("Error fetching data:", error);
        setListData([]);
      }
    };
    fetchData();
  }, [accessToken]);

  //북마크 해제
  // const byeBookmark = async (lessonNo) => {
  //   try {
  //     const response = await axios.post(
  //       `${url}/api/user/lessons/like/${lessonNo}`,
  //       {},
  //       {
  //         headers: {
  //           Authorization: `Bearer ${accessToken}`,
  //         },
  //       }
  //     );
  //     if (response.status === 200) {
  //       // 성공적으로 처리되면 상태에서 해당 레시피의 'recipeLikeStatus'를 변경

  //    const updatedList = listData.map((item) => {
  //         if (item.lessonNo === lessonNo) {
  //           return { ...item, classLikeStatus: false }; // 'recipeLikeStatus'를 false로 설정
  //         }
  //         return item;
  //       });
  //       setListData(updatedList);
  //     }
  //   } catch (error) {
  //     console.error("Error in bookmark toggle:", error);
  //   }
  // };
  // 상세페이지 이동
  const clickDetail = (e, lesson_no) => {
    navigator(`/classDetail/${lesson_no}`);
  };

  return (
    <>
      <div className="division-line"></div>

      <div className="bookmark-content">
        {listData.length > 0 ? (
          listData.map((data, index) => (
            <div key={index} style={{ position: "relative" }}>
              <Bookmark
                check={data.recipeLikeStatus}
                sx={{ cursor: "pointer" }}
                // onClick={() => byeBookmark(data.lessonNo)}
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
        <ButtonContain size="large" text="더보기" />
      </div>
    </>
  );
};

export default BookmarkClass;
