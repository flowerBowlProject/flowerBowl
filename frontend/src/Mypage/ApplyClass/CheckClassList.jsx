import { React, useState, useEffect } from "react";
import Inputbutton from "../../Component/Input/Inputbutton";
import "./CheckClassList.css";
import MyPageLayout from "../MyPageLayout";

const CheckClassList = () => {
  // 정렬기능k
  const [sortDirection, setSortDirection] = useState("asc");

  // 받아올 테이블 데이터
  const [tableData, setTableData] = useState([
    {
      date: "2024/02/20",
      description: "화이트데이 초콜릿 만들기 클래스",
      user: "@내꿈은너야",
      phone: `010-8459-8114`,
    },
    {
      date: "2023/12/25",
      description: "크리스마스 스페셜 만들기",
      user: "@메리크리스마스",
      phone: `010-5995-7519`,
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

  useEffect(() => {
    sortTableDataByDate(sortDirection);
  }, []);

  const toggleSortDirection = () => {
    setSortDirection((prevDirection) => {
      const newDirection = prevDirection === "asc" ? "desc" : "asc";
      sortTableDataByDate(newDirection); // sort the data after setting direction
      return newDirection;
    });
  };

  return (
    <MyPageLayout>
      {/* 내용 */}
      <section className="table-content">
        <table className="custom-table">
          <thead>
            <tr>
              <th>No</th>
              <th>
                구매날짜
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
              <th>구매자명</th>
              <th>핸드폰 번호</th>
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
                  <td>{item ? item.user : ""}</td>
                  <td>{item ? item.phone : ""}</td>
                  <td>
                    {item ? (
                      <Inputbutton text="취소/환불" i={true} w="medium" />
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
        <Inputbutton text="더보기" i={true} w="large" />
      </section>
    </MyPageLayout>
  );
};

export default CheckClassList;
