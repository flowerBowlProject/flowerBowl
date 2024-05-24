import { React, useState, useEffect } from "react";
import "./CheckReview.css";
import ButtonContain from "../../Component/ButtonContain";
import ButtonOutlined from "../../Component/ButtonOutlined";
import { Link } from "react-router-dom";
import axios from "axios";
import { url } from "../../url";
import ErrorConfirm from "../../Hook/ErrorConfirm";
import { useDispatch, useSelector } from "react-redux";
import { editErrorType, openError } from "../../persistStore";

const CheckReview = () => {
  // 정렬기능
  const [sortDirection, setSortDirection] = useState("desc");
  const [sortDirectionRating, setSortDirectionRating] = useState("asc");
  const [listData, setListData] = useState([]);
  const accessToken = useSelector((state) => state.accessToken);
  const dispatch = useDispatch();
  const errorType = useSelector((state) => state.errorType);
  const [slice, setSlice] = useState(8);
  const handleClickMoreDetail = () => {
    if (listData.length > slice) setSlice(slice + 8);
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`${url}/api/reviews`, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        });
        if (response.data.writtenReviews) {
          const sortedData = response.data.writtenReviews.sort((a, b) => {
            const dateA = new Date(a.review_date);
            const dateB = new Date(b.review_date);
            return sortDirection === "asc" ? dateA - dateB : dateB - dateA;
          });
          setListData(sortedData);
        }
        //코드 확인
        // console.log(response.data.likeRecipes);
      } catch (error) {
        dispatch(editErrorType(error.response.data.code));
        dispatch(openError());
        setListData([]);
      }
    };
    fetchData();
  }, [accessToken, sortDirection]);

  const sortTableDataByDate = (direction = sortDirection) => {
    const sortedData = [...listData].sort((a, b) => {
      const dateA = new Date(a.review_date);
      const dateB = new Date(b.review_date);
      return direction === "asc" ? dateA - dateB : dateB - dateA;
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

  //리뷰 삭제
  const handleDeleteReview = async (review_no) => {
    console.log(`Attempting to delete review with ID: ${review_no}`);
    console.log(typeof review_no);
    try {
      const response = await axios.delete(`${url}/api/reviews/${review_no}`, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });
      console.log("Delete response:", response);
      const newListData = listData.filter(
        (item) => item.review_no !== review_no
      );
      setListData(newListData);
      console.log(`Updated list data after deletion:`, newListData);
    } catch (error) {
      dispatch(editErrorType(error.response.data.code));
      dispatch(openError());
      console.error("Error deleting review:", error.response || error);
    }
  };

  return (
    <>
      <ErrorConfirm error={errorType} />
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
                    {sortDirection === "asc" ? "▼" : "▲"}
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
            {[
              ...listData.slice(0, slice),
              ...Array(8 - listData.slice(slice - 8, slice).length),
            ].map((data, index) => (
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
                  {data ? (
                    <ButtonOutlined
                      handleClick={() => handleDeleteReview(data.review_no)}
                      size="verySmall"
                      text="삭제"
                    />
                  ) : (
                    ""
                  )}
                </td>
                <td>
                  <Link
                    to={`/mypage/paymentDetail/editReview/${data?.review_no}`}
                  >
                    {data ? <ButtonContain size="verySmall" text="수정" /> : ""}
                  </Link>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </section>

      {/* 더보기 버튼    */}
      <section className="bottom-add">
        <ButtonContain
          size="medium"
          text="더보기"
          handleClick={handleClickMoreDetail}
        />
      </section>
    </>
  );
};

export default CheckReview;
