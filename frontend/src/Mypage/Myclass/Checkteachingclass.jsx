import { React, useState, useEffect } from "react";
import ButtonContain from "../../Component/ButtonContain";
import ButtonOutlined from "../../Component/ButtonOutlined";
import "./Checkteachingclass.css";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import { url } from "../../url";
import { useSelector } from "react-redux";

const Checkteachingclass = () => {
  const navigate = useNavigate();

  // 정렬기능
  const [sortDirection, setSortDirection] = useState("asc");
  const [sortDirectionRating, setSortDirectionRating] = useState("asc");
  const [listData, setListData] = useState([]);
  const accessToken = useSelector((state) => state.accessToken);
  const [slice,setSlice]= useState(8);
  //액세스토큰 확인
  // console.log("Access Token:", accessToken);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`${url}/api/mypage/lessons`, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        });
        setListData(response.data.payLessons);
        //코드 확인
        // console.log(response.data.payLessons);
      } catch (error) {
        console.error("Error fetching data:", error);
        setListData([]);
      }
    };
    fetchData();
  }, [accessToken]);

  // 상세페이지 이동
  const clickDetail = (e, lesson_no) => {
    navigate(`/classDetail/${lesson_no}`);
  };

  //   날짜 정렬
  const sortTableDataByDate = (direction = "asc") => {
    const sortedData = [...listData].sort((a, b) => {
      const dateA = new Date(a.pay_date);
      const dateB = new Date(b.pay_date);
      return direction === "asc" ? dateB - dateA : dateA - dateB;
    });
    setListData(sortedData);
  };

  const toggleSortDirection = () => {
    setSortDirection((prevDirection) => {
      const newDirection = prevDirection === "asc" ? "desc" : "asc";
      sortTableDataByDate(newDirection); // sort the data after setting direction
      return newDirection;
    });
  };

  useEffect(() => {
    sortTableDataByDate(sortDirection);
  }, []);

  //별점정렬
  const sortDataByAttribute = (attribute, direction) => {
    const sortedData = [...listData].sort((a, b) => {
      const valueA = parseInt(a[attribute]);
      const valueB = parseInt(b[attribute]);

      if (direction === "asc") {
        return valueA - valueB;
      } else {
        return valueB - valueA;
      }
    });

    setListData(sortedData);
  };

  const toggleSortDirectionRating = () => {
    setSortDirectionRating((prevDirection) => {
      const newDirection = prevDirection === "asc" ? "desc" : "asc";
      sortDataByAttribute("review_score", newDirection); // Use 'review_score' instead of 'rating'
      return newDirection;
    });
  };
  const handleClickMoreDetail=()=>{
    if(listData.length>slice)
    setSlice(slice+8)
  }
  return (
    <>
      
      {/* 내용 */}
      <section className="table-content">
        <table className="custom-table">
          <thead>
            <tr>
              <th>No</th>
              <th>
                수강날짜
                <button className="sort-button" onClick={toggleSortDirection}>
                  <span
                    className={
                      sortDirection === "asc" ? "arrow-up" : "arrow-down"
                    }
                  >
                    {sortDirection === "asc" ? "▲" : "▼"}
                  </span>
                </button>
              </th>
              <th>클래스명</th>
              <th>쉐프</th>
              <th>
                별점
                <button
                  className="sort-button"
                  onClick={toggleSortDirectionRating}
                >
                  <span
                    className={
                      sortDirectionRating === "asc" ? "arrow-up" : "arrow-down"
                    }
                  >
                    {sortDirectionRating === "asc" ? "▲" : "▼"}
                  </span>
                </button>
              </th>
            </tr>
          </thead>
          <tbody>
            {[...listData.slice(0,slice), ...Array(8-listData.slice(slice-8,slice).length)].map((data, index) => (
              <tr key={index}>
                <td>{data ? index + 1 : ""}</td>
                <td>{data ? data.pay_date : ""}</td>
                <td onClick={(e) => clickDetail(e, data.lesson_no)}>
                  {data ? data.lesson_title : ""}
                </td>
                <td>{data ? data.lesson_writer : ""}</td>
                <td>
                  {data ? (
                    <>
                      <span className="star-filled">
                        {"★".repeat(data.review_score)}
                      </span>
                      <span className="star-empty">
                        {"☆".repeat(5 - data.review_score)}
                      </span>
                    </>
                  ) : (
                    ""
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </section>

      {/* 더보기 버튼    */}
      <section className="bottom-add">
        <ButtonContain size="medium" text="더보기" handleClick={handleClickMoreDetail} />
      </section>
    </>
  );
};

export default Checkteachingclass;
