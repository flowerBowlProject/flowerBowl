import { React, useState } from "react";
import Inputbutton from "../../Component/Input/Inputbutton";
import "./Checkmakingrecipe.css";

const Checkmakingrecipe = () => {
  // 정렬기능
  const [sortDirection, setSortDirection] = useState(null);
  const [sortBookmark, setSortBookmark] = useState(null);
  const [sortComment, setSortComment] = useState(null);

  // 받아올 테이블 데이터
  const [tableData, setTableData] = useState([
    {
      date: "2024/02/20",
      description: "화이트데이 초콜릿 만들기 클래스",
      bookmark: 800,
      comment: 4861,
    },
    {
      date: "2023/12/25",
      description: "크리스마스 스페셜 만들기",
      bookmark: 495,
      comment: 300,
    },
  ]);

  //   날짜정렬
  const sortTableDataByDate = () => {
    const sortedData = [...tableData].sort((a, b) => {
      const dateA = new Date(a.date);
      const dateB = new Date(b.date);

      if (sortDirection === "asc") {
        return dateA - dateB;
      } else {
        return dateB - dateA;
      }
    });

    setTableData(sortedData);
  };
  const toggleSortDirection = () => {
    setSortDirection((prevDirection) => {
      const newDirection = prevDirection === "asc" ? "desc" : "asc";
      sortTableDataByDate(newDirection); // sort the data after setting direction
      return newDirection;
    });
  };

  // 북마크정렬
  const toggleSortBookmark = () => {
    setSortBookmark((prevDirection) => {
      const newDirection = prevDirection === "asc" ? "desc" : "asc";
      sortDataByAttribute("bookmark", newDirection); // Sort data after updating the direction
      return newDirection;
    });
  };

  //댓글정렬
  const toggleSortComment = () => {
    setSortComment((prevDirection) => {
      const newDirection = prevDirection === "asc" ? "desc" : "asc";
      sortDataByAttribute("comment", newDirection); // Sort data after updating the direction
      return newDirection;
    });
  };

  //북마크, 댓글 정렬
  const sortDataByAttribute = (attribute, direction) => {
    const sortedData = [...tableData].sort((a, b) => {
      const valueA = a[attribute];
      const valueB = b[attribute];

      if (direction === "asc") {
        return valueB - valueA; // Ascending order
      } else {
        return valueA - valueB; // Descending order
      }
    });

    setTableData(sortedData);
  };

  return (
    <>
      {/* 버튼들 */}
      <section className="buttons">
        <Inputbutton text="수강레시피 조회" i={false} w="medium" />
        <Inputbutton text="창작레시피 조회" i={true} w="medium" />
      </section>

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
            {[...tableData, ...Array(8 - tableData.length)].map(
              (item, index) => (
                <tr key={index}>
                  <td>{item ? index + 1 : ""}</td>
                  <td>{item ? item.date : ""}</td>
                  <td>{item ? item.description : ""}</td>
                  <td>{item ? item.bookmark.toLocaleString() : ""}</td>
                  <td>{item ? item.comment.toLocaleString() : ""}</td>
                  <td>
                    {item ? (
                      <Inputbutton text="삭제" i={false} w="small" />
                    ) : (
                      ""
                    )}
                  </td>
                  <td>
                    {item ? <Inputbutton text="수정" i={true} w="small" /> : ""}
                  </td>
                </tr>
              )
            )}
          </tbody>
        </table>
      </section>

      {/* 더보기 버튼    */}
      <section className="bottom-add">
        <Inputbutton text="더보기" i={true} w="large" />
      </section>
    </>
  );
};

export default Checkmakingrecipe;
