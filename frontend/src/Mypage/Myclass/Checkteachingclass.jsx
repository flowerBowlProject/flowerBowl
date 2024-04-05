import { React, useState } from "react";
import Inputbutton from "../../Component/Input/Inputbutton";
import "./Checkteachingclass.css";

const Checkteachingclass = () => {
  // 정렬기능
  const [sortDirection, setSortDirection] = useState(null);
  const [sortDirectionRating, setSortDirectionRating] = useState(null);

  // 받아올 테이블 데이터
  const tableData = [
    {
      date: "2024/02/20",
      description: "화이트데이 초콜릿 만들기 클래스",
      chef: "@꽃피는아침",
      rating: 4,
    },
    {
      date: "2023/12/25",
      description: "크리스마스 스페셜 만들기",
      chef: "@메리크리스마스",
      rating: 3,
    },
  ];

  const toggleSortDirection = () => {
    setSortDirection((prevDirection) => {
      // If current direction is 'asc', toggle to 'desc', and vice versa
      return prevDirection === "asc" ? "desc" : "asc";
    });
  };
  const toggleSortDirectionRating = () => {
    setSortDirectionRating((prevDirection) => {
      return prevDirection === "asc" ? "desc" : "asc"; // Reset the other column's sort direction
    });
  };

  return (
    <>
      {/* 버튼들 */}
      <section className="buttons">
        <Inputbutton text="수강클래스 조회" i={true} w="medium" />
        <Inputbutton text="창작 클래스 조회" i={false} w="medium" />
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

export default Checkteachingclass;
