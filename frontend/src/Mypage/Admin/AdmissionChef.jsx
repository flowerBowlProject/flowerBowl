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

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`${url}/api/admin/chefs`, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        });
        setListData(response.data.candidiate);
        // // console.log(typeof response.data.candidiate);

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
              {[...listData, ...Array(8 - listData.length)].map(
                (data, index) => (
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
