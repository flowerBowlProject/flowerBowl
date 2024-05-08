import { React, useState, useEffect } from "react";
import "./CheckClassList.css";
import ButtonContain from "../../Component/ButtonContain";
import axios from "axios";
import { url } from "../../url";
import ErrorConfirm from "../../Hook/ErrorConfirm";
import { useDispatch, useSelector } from "react-redux";
import { editErrorType, openError } from "../../persistStore";

const CheckClassList = () => {
  // 정렬기능k
  const [sortDirection, setSortDirection] = useState("asc");
  const [listData, setListData] = useState([]);
  const accessToken = useSelector((state) => state.accessToken);
  const dispatch = useDispatch();
  const errorType = useSelector((state) => state.errorType);
  const [refreshData, setRefreshData] = useState(false);
  const [slice, setSlice] = useState(8);
  const handleClickMoreDetail = () => {
    if (listData.length > slice) setSlice(slice + 8);
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`${url}/api/chef/purchasers`, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        });
        setListData(response.data.purchasers);
        //코드 확인
        // console.log(response.data.likeRecipes);
      } catch (error) {
        dispatch(editErrorType(error.response.data.code));
        dispatch(openError());
        setListData([]);
      }
    };
    fetchData();
  }, [accessToken]);

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
    console.log("이거 있는지 pay_no:", pay_no);
    // if (!pay_no) {
    //   console.error("Invalid payment number.");
    //   return; // 적절한 pay_no 값이 없으면 함수를 종료합니다.
    // }

    const deleteUrl = `${url}/api/chef/mypage/pays/${pay_no}`;
    console.log("Attempting to delete:", deleteUrl); // Log the URL

    try {
      const response = await axios.delete(deleteUrl, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });

      console.log(response.data);

      if (response.status === 200) {
        dispatch(editErrorType("CANCEL SUCCESS"));
        dispatch(openError());
        console.log("Success:", response.data.message);
        setListData((currentData) =>
          currentData.filter((item) => item.pay_no !== pay_no)
        );
        setRefreshData(!refreshData); // Optionally, toggle to trigger a re-fetch
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
              <th>구매자명</th>
              <th>휴대폰 번호</th>
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
                <td>{data ? data.user_nickname : ""}</td>
                <td>{data ? data.user_phone : ""}</td>
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

export default CheckClassList;
