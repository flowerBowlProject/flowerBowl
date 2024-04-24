import { React, useState, useEffect } from "react";
import "./CheckPaidList.css";
import ButtonContain from "../../Component/ButtonContain";
import ButtonOutlined from "../../Component/ButtonOutlined";
import { Link } from "react-router-dom";
import axios from "axios";
import { url } from "../../url";
import { useSelector } from "react-redux";

const CheckPaidList = () => {
  const accessToken = useSelector((state) => state?.accessToken);

  // 정렬기능
  const [sortDirection, setSortDirection] = useState("asc");
  const [sortReceipt, setSortReceipt] = useState("asc");
  const [listData, setListData] = useState([]);
  const [refreshData, setRefreshData] = useState(false);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`${url}/api/mypage/pays`, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        });
        setListData(response.data.pays);
        //코드 확인
        // console.log(response.data.payLessons);
      } catch (error) {
        console.error("Error fetching data:", error);
        setListData([]);
      }
    };
    fetchData();
  }, [accessToken, refreshData]);

  //결제금액 천단위 붙이기
  const formatCurrency = (value) => {
    const numberValue = Number(value.replace(/￦|,/g, ""));
    return `₩${numberValue.toLocaleString()}`;
  };

  //결제금액 정렬
  const toggleSortReceipt = () => {
    setSortReceipt((prevSortReceipt) => {
      const newSortReceipt = prevSortReceipt === "asc" ? "desc" : "asc";
      sortDataByAttribute("pay_price", newSortReceipt); // 새로운 정렬 방향으로 데이터 정렬
      return newSortReceipt;
    });
  };

  // 속성(이 경우에는 결제 금액)에 따라 데이터를 정렬하는 함수
  const sortDataByAttribute = (attribute, direction) => {
    const sortedData = [...listData].sort((a, b) => {
      const valueA = parseInt(a[attribute]?.replace(/[^0-9]/g, "") || "0", 10);
      const valueB = parseInt(b[attribute]?.replace(/[^0-9]/g, "") || "0", 10);

      // 정렬 방향에 따라 숫자를 비교하여 정렬
      return direction === "asc" ? valueB - valueA : valueA - valueB;
    });

    setListData(sortedData); // 정렬된 데이터로 상태 업데이트
  };

  useEffect(() => {
    sortDataByAttribute("receipt", sortReceipt);
  }, []);

  //   날짜정렬
  const sortTableDataByDate = (direction = "asc") => {
    const sortedData = [...listData].sort((a, b) => {
      const dateA = new Date(a.pay_date);
      const dateB = new Date(b.pay_date);
      return direction === "asc" ? dateB - dateA : dateA - dateB;
    });
    setListData(sortedData);
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

  // 취소/환불 api연결
  const handleDelete = async (payNo) => {
    try {
      const response = await axios.delete(`${url}/api/mypage/pays/${payNo}`, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });

      if (response.status === 200) {
        console.log("Success:", response.data.message);
        setRefreshData(!refreshData); // toggle to trigger a re-fetch
      }
    } catch (error) {
      console.error("Failed to delete lesson:", error);
    }
  };

  return (
    <>
      {/* 버튼들 */}
      <section className="buttons">
        <span className="paidlist">
          <Link to="/mypage/checkPaidList">
            <ButtonContain size="medium" text="결제 내역" />
          </Link>
        </span>
        <span className="review">
          <Link to="/mypage/checkReview">
            <ButtonOutlined size="medium" text="리뷰 조회" />
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
                결제금액
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
            {[...listData, ...Array(8 - listData.length)].map((data, index) => (
              <tr key={index}>
                <td>{data ? index + 1 : ""}</td>
                <td>{data ? data.pay_date : ""}</td>
                <td>{data ? data.lesson_title : ""}</td>
                <td>{data ? data.lesson_writer : ""}</td>
                <td>{data ? formatCurrency(data.pay_price) : ""}</td>
                <td>
                  {data ? (
                    <ButtonContain
                      handleClick={handleDelete}
                      size="small"
                      text="취소/환불"
                    />
                  ) : (
                    ""
                  )}
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

export default CheckPaidList;
