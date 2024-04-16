import { React, useState, useEffect } from "react";
import ButtonContain from "../../Component/ButtonContain";
import ButtonOutlined from "../../Component/ButtonOutlined";
import "./AdmissionChef.css";

const AdmissionChef = () => {
  // 정렬기능
  const [sortDirection, setSortDirection] = useState("asc");

  // 받아올 테이블 데이터
  const [tableData, setTableData] = useState([
    {
      date: "2024/02/20",
      user: "@내꿈은너야",
      file: "https://unsplash.com/ko/%EC%82%AC%EC%A7%84/%EC%A0%91%EC%8B%9C%EC%97%90-%EC%9D%8C%EC%8B%9D%EC%9D%84-%EB%8B%B4%EB%8A%94-%EC%82%AC%EB%9E%8C-cQbOSRpElxw",
    },
    {
      date: "2023/12/25",
      user: "@메리크리스마스",
      file: "https://unsplash.com/ko/%EC%82%AC%EC%A7%84/%EC%A0%91%EC%8B%9C%EC%97%90-%EC%9D%8C%EC%8B%9D%EC%9D%84-%EC%A4%80%EB%B9%84%ED%95%98%EB%8A%94-%EB%B6%80%EC%97%8C%EC%97%90%EC%84%9C-%EB%82%A8%EC%9E%90-J1RKeY6Kv8c",
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
    <>
      {/* 내용 */}
      <div className="admissionall">
        <section className="table-content">
          <table className="custom-table">
            <thead>
              <tr>
                <th>No</th>
                <th>
                  신청일자
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
                <th>신청자</th>
                <th>첨부파일</th>
                <th></th>
              </tr>
            </thead>

            <tbody>
              {[...tableData, ...Array(8 - tableData.length)].map(
                (item, index) => (
                  <tr key={index}>
                    <td>{item ? index + 1 : ""}</td>
                    <td>{item ? item.date : ""}</td>
                    <td>{item ? item.user : ""}</td>
                    <td>
                      {item && item.file ? (
                        <img
                          src={item.file}
                          alt="첨부파일"
                          className="attachment-image"
                        />
                      ) : (
                        ""
                      )}
                    </td>
                    <td className="button-group">
                      {item ? (
                        <>
                          <span className="adok">
                            <ButtonContain size="medium" text="신청 허가" />
                          </span>
                          <span className="adcl">
                            <ButtonOutlined size="medium" text="신청 반려" />
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
      </div>
    </>
  );
};

export default AdmissionChef;
