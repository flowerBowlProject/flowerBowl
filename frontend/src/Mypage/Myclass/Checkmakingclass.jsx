import { React, useState, useEffect } from "react";
import ButtonContain from "../../Component/ButtonContain";
import ButtonOutlined from "../../Component/ButtonOutlined";
import "./Checkmakingclass.css";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import { url } from "../../url";
import { useSelector } from "react-redux";

const Checkmakingclass = () => {
  const navigate = useNavigate();
  const accessToken = useSelector((state) => state?.accessToken);

  // 정렬기능
  const [sortDirection, setSortDirection] = useState("asc");
  const [sortBookmark, setSortBookmark] = useState("asc");
  const [sortComment, setSortComment] = useState("asc");
  const [listData, setListData] = useState([]);
  const [refreshData, setRefreshData] = useState(false);
  //액세스토큰 확인
  // console.log("Access Token:", accessToken);

  useEffect(() => {
    const fetchData = async () => {
      try {
        console.log("데이터 리로드");
        const response = await axios.get(`${url}/api/chef/lessons`, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        });
        setListData(response.data.myLessons);
        //코드 확인
        // console.log(response.data.payLessons);
      } catch (error) {
        console.error("Error fetching data:", error);
        setListData([]);
      }
    };
    fetchData();
  }, [accessToken, refreshData]);

  // 상세페이지 이동
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
      sortTableDataByDate(newDirection); // sort the data after setting direction
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
      sortDataByAttribute("bookmark_cnt", newDirection); // Sort data after updating the direction
      return newDirection;
    });
  };

  //댓글정렬
  const toggleSortComment = () => {
    setSortComment((prevDirection) => {
      const newDirection = prevDirection === "asc" ? "desc" : "asc";
      sortDataByAttribute("review_cnt", newDirection); // Sort data after updating the direction
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

  // 삭제버튼 api연결
  const handleDelete = async (lessonNo) => {
    console.log("Delete button clicked", lessonNo); // 로그: 함수 호출과 수업 번호 확인

    try {
      const response = await axios.put(`${url}/api/lessons`, {
        lesson_no: lessonNo,
        headers: { Authorization: `Bearer ${accessToken}` },
      });

      console.log("API Response", response); // 로그: API 응답 전체 확인

      if (response.status === 200) {
        console.log("Success:", response.data.message); // 로그: 성공 메시지 확인
        setRefreshData(!refreshData);
      }
    } catch (error) {
      console.error("Failed to delete lesson:", error); // 로그: 에러 상황에서의 오류 메시지
    }
  };

  if (!accessToken) {
    return <div>로딩중입니다..</div>;
  }

  return (
    <>
      {/* 버튼들 */}
      <section className="buttons">
        <span className="checktclass">
          <Link to="/mypage/checkteachingclass">
            <ButtonOutlined size="doubleLarge" text="수강클래스 조회" />
          </Link>
        </span>
        <span className="checkmclass">
          <Link to="/mypage/checkmakingclass">
            <ButtonContain size="doubleLarge" text="창작클래스 조회" />
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
            {[...listData, ...Array(8 - listData.length)].map((data, index) => (
              <tr key={index}>
                <td>{data ? index + 1 : ""}</td>
                <td>{data ? data.lesson_date : ""}</td>
                <td onClick={(e) => clickDetail(e, data.lesson_no)}>
                  {data ? data.lesson_title : ""}
                </td>
                <td>{data ? data.bookmark_cnt.toLocaleString() : ""}</td>
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

export default Checkmakingclass;
