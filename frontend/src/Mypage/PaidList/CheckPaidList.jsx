import { React, useState, useEffect } from "react";
import Inputbutton from "../../Component/Input/Inputbutton";
import "./CheckPaidList.css";

const CheckPaidList = () => {
  // 정렬기능
  const [sortDirection, setSortDirection] = useState("asc");
  const [sortReceipt, setSortReceipt] = useState("asc");

  // 받아올 테이블 데이터
  const [tableData, setTableData] = useState([
    {
      date: "2024/02/20",
      description: "화이트데이 초콜릿 만들기 클래스",
      chef: "@내꿈은너야",
      receipt: `￦35000`,
    },
    {
      date: "2023/12/25",
      description: "크리스마스 스페셜 만들기",
      chef: "@메리크리스마스",
      receipt: `￦50000`,
    },
  ]);

  //결제금액 천단위 붙이기
  const formatCurrency = (value) => {
    const numberValue = Number(value.replace(/￦|,/g, ""));
    return `₩${numberValue.toLocaleString()}`;
  };

  //결제금액 정렬
  const toggleSortReceipt = () => {
    setSortReceipt((prevSortReceipt) => {
      const newSortReceipt = prevSortReceipt === "asc" ? "desc" : "asc";
      sortDataByAttribute("receipt", newSortReceipt); // 새로운 정렬 방향으로 데이터 정렬
      return newSortReceipt;
    });
  };

  // 속성(이 경우에는 결제 금액)에 따라 데이터를 정렬하는 함수
  const sortDataByAttribute = (attribute, direction) => {
    const sortedData = [...tableData].sort((a, b) => {
      const valueA = parseInt(a[attribute].replace(/[^0-9]/g, ""), 10);
      const valueB = parseInt(b[attribute].replace(/[^0-9]/g, ""), 10);

      // 정렬 방향에 따라 숫자를 비교하여 정렬
      return direction === "asc" ? valueB - valueA : valueA - valueB;
    });

    setTableData(sortedData); // 정렬된 데이터로 상태 업데이트
  };

  useEffect(() => {
    sortDataByAttribute("receipt", sortReceipt);
  }, []);

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
      {/* 버튼들 */}
      <section className="buttons">
        <span className="write-review">
          <Inputbutton text="리뷰 작성" i={false} w="medium" />
        </span>
        <Inputbutton text="리뷰 조회" i={false} w="medium" />
        <Inputbutton text="결제 내역" i={true} w="medium" />
      </section>

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
              <th>쉐프</th>
              <th>
                결재금액
                <button className="sort-button" onClick={toggleSortReceipt}>
                  <span
                    className={
                      sortReceipt === "asc" ? "arrow-up" : "arrow-down"
                    }
                  >
                    {sortReceipt === "asc" ? "▲" : "▼"}
                  </span>
                </button>
              </th>
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
                  <td>{item ? formatCurrency(item.receipt) : ""}</td>
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
    </>
  );
};

export default CheckPaidList;
