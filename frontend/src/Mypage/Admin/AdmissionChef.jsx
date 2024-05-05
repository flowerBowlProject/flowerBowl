import { React, useState, useEffect } from "react";
import ButtonContain from "../../Component/ButtonContain";
import ButtonOutlined from "../../Component/ButtonOutlined";
import "./AdmissionChef.css";
import axios from "axios";
import { url } from "../../url";
import { useSelector } from "react-redux";

const AdmissionChef = () => {
  // 정렬기능
  const [sortDirection, setSortDirection] = useState("asc");
  const [listData, setListData] = useState([]);
  const accessToken = useSelector((state) => state.accessToken);
  const [slice, setSlice] = useState(8);
  const handleClickMoreDetail = () => {
    if (listData.length > slice) setSlice(slice + 8);
  };
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`${url}/api/admin/chefs`, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        });
        setListData(response.data.candidiate.reverse());
        console.log(response.data.candidiate);
        // console.log(typeof response.data.candidiate);

        // const candidateData = Array.isArray(response.data?.candidiate)
        //   ? response.data.candidiate
        //   : [];
        // setListData(candidateData);

        //코드 확인
        // console.log(response.data.likeRecipes);
      } catch (error) {
        console.error("Error fetching data:", error);
        setListData([]);
      }
    };
    fetchData();
  }, [accessToken]);

  //   날짜정렬
  const extractDate = (datetime) => {
    return datetime.split("T")[0];
  };

  const sortTableDataByDate = (direction = "asc") => {
    const sortedData = [...listData].sort((a, b) => {
      const dateA = new Date(a.license_date);
      const dateB = new Date(b.license_date);
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

  const handleApproveRequest = async (userNo) => {
    try {
      const response = await axios.put(
        `${url}/api/admin/chefs`,
        { user_no: userNo },
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );
      if (response.status === 200) {
        alert(`쉐프 허가 승인 완료되었습니다: ${response.data.message}`);
        setListData(listData.filter((item) => item.user_no !== userNo));
      }
    } catch (error) {
      alert(
        `쉐프 허가 승인 오류났습니다: ${
          error.response?.data?.message || error.message
        }`
      );
    }
  };

  const handleDenialRequest = async (license_no) => {
    if (!license_no) {
      alert("Invalid license number provided.");
      console.error("Invalid license number:", license_no);
      return;
    }

    try {
      const response = await axios.delete(
        `${url}/api/admin/chefs/${license_no}`,
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );
      if (response.status === 200) {
        alert(`반려가 완료되었습니다.`);
        setListData(listData.filter((item) => item.license_no !== license_no));
      }
    } catch (error) {
      alert(`쉐프 반려가 오류났습니다.`);
    }
  };

  return (
    <>
      {/* 내용 */}
      <div className="admissionall">
        <section className="table-content">
          <table className="custom-table">
            <thead>
              <tr>
                <th className="no">No</th>
                <th className="applyDate">
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
                <th className="applyer">신청자</th>
                <th className="attachFile">첨부파일</th>
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
                  <td>{data ? extractDate(data.license_date) : ""}</td>
                  <td className="applyer">{data ? data.user_name : ""}</td>
                  <td>
                    {data && data.license_sname ? (
                      <img
                        src={data.license_sname}
                        alt="첨부파일"
                        className="attachment-image"
                      />
                    ) : (
                      ""
                    )}
                  </td>
                  <td className="button-group">
                    {data ? (
                      <>
                        <span className="adok">
                          <ButtonContain
                            size="medium"
                            text="신청 허가"
                            handleClick={() =>
                              handleApproveRequest(data.user_no)
                            }
                          />
                        </span>
                        <span className="adcl">
                          <ButtonOutlined
                            size="medium"
                            text="신청 반려"
                            handleClick={() =>
                              handleDenialRequest(data.license_no)
                            }
                          />
                        </span>
                      </>
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
      </div>
    </>
  );
};

export default AdmissionChef;
