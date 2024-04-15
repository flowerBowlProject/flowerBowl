import { React, useState, useEffect } from "react";
import ButtonContain from "../../Component/ButtonContain";
import ButtonOutlined from "../../Component/ButtonOutlined";
import "./Checkteachingclass.css";

const Checkteachingclass = () => {
  // 정렬기능
  const [sortDirection, setSortDirection] = useState("asc");
  const [sortDirectionRating, setSortDirectionRating] = useState("asc");

  // 받아올 테이블 데이터
  const [tableData, setTableData] = useState([
    {
      date: "2024/02/20",
      description: "화이트데이 초콜릿 만들기 클래스",
      chef: "@내꿈은너야",
      rating: 3,
    },
    {
      date: "2023/12/25",
      description: "크리스마스 스페셜 만들기",
      chef: "@메리크리스마스",
      rating: 5,
    },
  ]);

  //   날짜정렬
  const sortTableDataByDate = (direction = "asc") => {
    const sortedData = [...tableData].sort((a, b) => {
      const dateA = new Date(a.date);
      const dateB = new Date(b.date);
      return direction === "asc" ? dateB - dateA : dateA - dateB;
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

  useEffect(() => {
    sortTableDataByDate(sortDirection);
  }, []);

  //별점정렬
  const toggleSortDirectionRating = () => {
    setSortDirectionRating((prevDirection) => {
      const newDirection = prevDirection === "asc" ? "desc" : "asc";
      sortDataByAttribute("rating", newDirection); // Sort data after updating the direction
      return newDirection;
    });
  };
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
        <ButtonContain size="medium" text="수강클래스 조회" />
        <ButtonOutlined size="medium" text="창작클래스 조회" />
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
            {[...tableData, ...Array(8 - tableData.length)].map(
              (item, index) => (
                <tr key={index}>
                  <td>{item ? index + 1 : ""}</td>
                  <td>{item ? item.date : ""}</td>
                  <td>{item ? item.description : ""}</td>
                  <td>{item ? item.chef : ""}</td>
                  <td>
                    {item ? (
                      <>
                        <span className="star-filled">
                          {"★".repeat(item.rating)}
                        </span>
                        <span className="star-empty">
                          {"☆".repeat(5 - item.rating)}
                        </span>
                      </>
                    ) : (
                      ""
                    )}
                  </td>
                </tr>
              )
            )}
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

export default Checkteachingclass;
