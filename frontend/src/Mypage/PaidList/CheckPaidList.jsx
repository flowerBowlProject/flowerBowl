import { React, useState, useEffect } from "react";
import "./CheckPaidList.css";
import ButtonContain from "../../Component/ButtonContain";
import { Link } from "react-router-dom";
import axios from "axios";
import { url } from "../../url";
import ErrorConfirm from "../../Hook/ErrorConfirm";
import { useDispatch, useSelector } from "react-redux";
import { editErrorType, openError } from "../../persistStore";

const CheckPaidList = () => {
  const accessToken = useSelector((state) => state?.accessToken);
  const dispatch = useDispatch();
  const errorType = useSelector((state) => state.errorType);
  // 정렬기능
  const [sortDirection, setSortDirection] = useState("asc");
  const [sortReceipt, setSortReceipt] = useState("asc");
  const [listData, setListData] = useState([]);
  const [refreshData, setRefreshData] = useState(false);
  // const { pay_no } = useParams();
  // console.log("pay_no 제대로 전달되지 않음:", pay_no);
  const [slice, setSlice] = useState(8);
  const handleClickMoreDetail = () => {
    if (listData.length > slice) setSlice(slice + 8);
  };

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
        // console.log(response.data.pays);
      } catch (error) {
        dispatch(editErrorType(error.response.data.code));
        dispatch(openError());
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
  const handleDelete = async (pay_no) => {
    const deleteUrl = `${url}/api/mypage/pays/${pay_no}`;
    console.log("Attempting to delete:", deleteUrl); // Log the URL

    try {
      const response = await axios.delete(deleteUrl, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });

      if (response.status === 200) {
        dispatch(editErrorType("CANCEL SUCCESS"));
        dispatch(openError());
        console.log("Success:", response.data.message);
        setRefreshData(!refreshData); // toggle to trigger a re-fetch
      }
    } catch (error) {
      dispatch(editErrorType(error.response.data.code));
      dispatch(openError());
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
            {[
              ...listData.slice(0, slice),
              ...Array(8 - listData.slice(slice - 8, slice).length),
            ].map((data, index) => (
              <tr key={index}>
                <td>{data ? index + 1 : ""}</td>
                <td>{data ? data.pay_date : ""}</td>
                <td>{data ? data.lesson_title : ""}</td>
                <td>{data ? data.lesson_writer : ""}</td>
                <td>{data ? formatCurrency(data.pay_price) : ""}</td>
                <td>
                  {data ? (
                    <ButtonContain
                      handleClick={() => handleDelete(data.pay_no)}
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
        <ButtonContain
          size="medium"
          text="더보기"
          handleClick={handleClickMoreDetail}
        />
      </section>
    </>
  );
};

export default CheckPaidList;
