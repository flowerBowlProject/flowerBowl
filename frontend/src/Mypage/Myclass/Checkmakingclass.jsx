import { React, useState, useEffect } from "react";
import ButtonContain from "../../Component/ButtonContain";
import ButtonOutlined from "../../Component/ButtonOutlined";
import "./Checkmakingclass.css";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import { url } from "../../url";
import ErrorConfirm from "../../Hook/ErrorConfirm";
import { useDispatch, useSelector } from "react-redux";
import { editErrorType, openError, closeError } from "../../persistStore"; // Assuming closeError action is defined

const Checkmakingclass = () => {
  const navigate = useNavigate();
  const accessToken = useSelector((state) => state?.accessToken);
  const dispatch = useDispatch();
  const errorType = useSelector((state) => state.errorType);

  // 정렬기능
  const [sortDirection, setSortDirection] = useState("asc");
  const [sortBookmark, setSortBookmark] = useState("asc");
  const [sortComment, setSortComment] = useState("asc");
  const [listData, setListData] = useState([]);
  const [refreshData, setRefreshData] = useState(false);
  const [slice, setSlice] = useState(8);

  const handleClickMoreDetail = () => {
    if (listData.length > slice) setSlice(slice + 8);
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`${url}/api/chef/lessons`, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        });
        setListData(response.data.myLessons);
      } catch (error) {
        dispatch(editErrorType(error.response.data.code));
        dispatch(openError());
        setListData([]);
      }
    };
    fetchData();
  }, [accessToken, refreshData]);

  // Clear error when component unmounts or navigates away
  useEffect(() => {
    return () => {
      dispatch(closeError());
    };
  }, [dispatch]);

  const clickDetail = (e, lesson_no) => {
    navigate(`/classDetail/${lesson_no}`);
  };

  //   날짜정렬
  const sortTableDataByDate = (direction = "asc") => {
    const sortedData = [...listData].sort((a, b) => {
      const dateA = new Date(a.lesson_date);
      const dateB = new Date(b.lesson_date);
      return direction === "asc" ? dateB - dateA : dateA - dateB;
    });
    setListData(sortedData);
  };

  const toggleSortDirection = () => {
    setSortDirection((prevDirection) => {
      const newDirection = prevDirection === "asc" ? "desc" : "asc";
      sortTableDataByDate(newDirection);
      return newDirection;
    });
  };

  useEffect(() => {
    sortTableDataByDate(sortDirection);
  }, []);

  // 북마크정렬
  const toggleSortBookmark = () => {
    setSortBookmark((prevDirection) => {
      const newDirection = prevDirection === "asc" ? "desc" : "asc";
      sortDataByAttribute("lesson_like_cnt", newDirection);
      return newDirection;
    });
  };

  //댓글정렬
  const toggleSortComment = () => {
    setSortComment((prevDirection) => {
      const newDirection = prevDirection === "asc" ? "desc" : "asc";
      sortDataByAttribute("review_cnt", newDirection);
      return newDirection;
    });
  };

  //북마크, 댓글  정렬
  const sortDataByAttribute = (attribute, direction) => {
    const sortedData = [...listData].sort((a, b) => {
      const valueA = Number(a[attribute]);
      const valueB = Number(b[attribute]);

      if (direction === "desc") {
        return valueA - valueB;
      } else {
        return valueB - valueA;
      }
    });

    setListData(sortedData);
  };

  const handleDelete = async (lessonNo) => {
    try {
      const response = await axios.put(
        `${url}/api/lessons`,
        {
          lesson_no: lessonNo,
        },
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
            "Content-Type": "application/json",
          },
        }
      );

      if (response.status === 200) {
        setListData((currentData) =>
          currentData.filter((lesson) => lesson.lesson_no !== lessonNo)
        );
        setRefreshData(!refreshData);
      }
    } catch (error) {
      dispatch(editErrorType(error.response.data.code));
      dispatch(openError());
    }
  };

  if (!accessToken) {
    return <div>로딩중입니다..</div>;
  }

  return (
    <>
      <ErrorConfirm error={errorType} />

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
              <th>
                북마크
                <button className="sort-button" onClick={toggleSortBookmark}>
                  <span
                    className={
                      sortBookmark === "asc" ? "arrow-up" : "arrow-down"
                    }
                  >
                    {sortBookmark === "asc" ? "▲" : "▼"}
                  </span>
                </button>
              </th>
              <th>
                댓글
                <button className="sort-button" onClick={toggleSortComment}>
                  <span
                    className={
                      sortComment === "asc" ? "arrow-up" : "arrow-down"
                    }
                  >
                    {sortComment === "asc" ? "▲" : "▼"}
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
                <td>{data ? data.lesson_date : ""}</td>
                <td onClick={(e) => clickDetail(e, data.lesson_no)}>
                  {data ? data.lesson_title : ""}
                </td>
                <td>{data ? data.lesson_like_cnt.toLocaleString() : ""}</td>
                <td>{data ? data.review_cnt.toLocaleString() : ""}</td>
                <td>
                  {data ? (
                    <ButtonOutlined
                      handleClick={handleDelete}
                      size="verySmall"
                      text="삭제"
                      data={data.lesson_no}
                    />
                  ) : (
                    ""
                  )}
                </td>
                <td>
                  <Link to={`/modifyClass/${data?.lesson_no}`}>
                    {data ? <ButtonContain size="verySmall" text="수정" /> : ""}
                  </Link>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </section>

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

export default Checkmakingclass;
