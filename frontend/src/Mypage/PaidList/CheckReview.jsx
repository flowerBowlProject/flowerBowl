import { React, useState, useEffect } from "react";
import "./CheckReview.css";
import ButtonContain from "../../Component/ButtonContain";
import ButtonOutlined from "../../Component/ButtonOutlined";
import { Link } from "react-router-dom";
import axios from "axios";
import { url } from "../../url";
import { useSelector } from "react-redux";

const CheckReview = () => {
  // 정렬기능
  const [sortDirection, setSortDirection] = useState("asc");
  const [sortDirectionRating, setSortDirectionRating] = useState("asc");
  const [listData, setListData] = useState([]);
  const accessToken = useSelector((state) => state.accessToken);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`${url}/api/reviews`, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        });
        setListData(response.data.writtenReviews);
        //코드 확인
        // console.log(response.data.likeRecipes);
      } catch (error) {
        console.error("Error fetching data:", error);
        setListData([]);
      }
    };
    fetchData();
  }, [accessToken]);

  //   날짜정렬
  const sortTableDataByDate = (direction = "asc") => {
    const sortedData = [...listData].sort((a, b) => {
      const dateA = new Date(a.review_date);
      const dateB = new Date(b.review_date);
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
  const toggleSortDirectionRating = () => {
    setSortDirectionRating((prevDirection) => {
      const newDirection = prevDirection === "asc" ? "desc" : "asc";
      sortDataByAttribute("review_score", newDirection); // Sort data after updating the direction
      return newDirection;
    });
  };
  const sortDataByAttribute = (attribute, direction) => {
    const sortedData = [...listData].sort((a, b) => {
      const valueA = a[attribute];
      const valueB = b[attribute];

      if (direction === "asc") {
        return valueB - valueA; // Ascending order
      } else {
        return valueA - valueB; // Descending order
      }
    });

    setListData(sortedData);
  };

  return (
    <>
      {/* 버튼들 */}
      <section className="buttons">
        <span className="paidlist">
          <Link to="/mypage/checkPaidList">
            <ButtonOutlined size="medium" text="결제 내역" />
          </Link>
        </span>
        <span className="review">
          <Link to="/mypage/checkReview">
            <ButtonContain size="medium" text="리뷰 조회" />
          </Link>
        </span>
        <span className="register">
          <Link to="/mypage/registerReview">
            <ButtonOutlined size="medium" text="리뷰 작성" />
          </Link>
        </span>
      </section>

      {/* 내용 */}
      <section className="table-content">
        <table className="custom-table">
          <thead>
            <tr>
              <th>No</th>
              <th>
                작성날짜
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
              <th></th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {[...listData, ...Array(8 - listData.length)].map((data, index) => (
              <tr key={index}>
                <td>{data ? index + 1 : ""}</td>
                <td>{data ? data.review_date : ""}</td>
                <td>{data ? data.lesson_title : ""}</td>
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
                <td>
                  {data ? <ButtonOutlined size="verySmall" text="삭제" /> : ""}
                </td>
                <td>
                  {data ? <ButtonContain size="verySmall" text="수정" /> : ""}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </section>

      {/* 더보기 버튼    */}
      <section className="bottom-add">
        <ButtonContain size="medium" text="더보기" />
      </section>
    </>
  );
};

export default CheckReview;
