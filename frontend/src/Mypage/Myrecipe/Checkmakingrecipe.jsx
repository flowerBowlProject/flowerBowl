import { React, useState, useEffect } from "react";
import ButtonContain from "../../Component/ButtonContain";
import ButtonOutlined from "../../Component/ButtonOutlined";
import "./Checkmakingrecipe.css";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import { url } from "../../url";
import { useSelector } from "react-redux";

const Checkmakingrecipe = () => {
  const navigate = useNavigate();
  const accessToken = useSelector((state) => state?.accessToken);

  // 정렬기능
  const [sortDirection, setSortDirection] = useState("asc");
  const [sortBookmark, setSortBookmark] = useState("asc");
  const [sortComment, setSortComment] = useState("asc");
  const [listData, setListData] = useState([]);
  const [slice,setSlice]= useState(8);
  const handleClickMoreDetail=()=>{
    if(listData.length>slice)
    setSlice(slice+8)
  }
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`${url}/api/mypage/recipes`, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        });
        setListData(response.data.myRecipes);
        //코드 확인
         console.log(response.data.myRecipes);
      } catch (error) {
        console.error("Error fetching data:", error);
        setListData([]);
      }
    };
    fetchData();
  }, [accessToken]);

  //   날짜정렬
  const sortTableDataByDate = (direction = "asc") => {
    const sortedData = [...listData].sort((a, b) => {
      const dateA = new Date(a.recipe_date);
      const dateB = new Date(b.recipe_date);
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

  useEffect(() => {
    sortTableDataByDate(sortDirection);
  }, []);

  // 북마크정렬
  const toggleSortBookmark = () => {
    setSortBookmark((prevDirection) => {
      const newDirection = prevDirection === "asc" ? "desc" : "asc";
      sortDataByAttribute("bookmark_cnt", newDirection); // Sort data after updating the direction
      return newDirection;
    });
  };

  //댓글정렬
  const toggleSortComment = () => {
    setSortComment((prevDirection) => {
      const newDirection = prevDirection === "asc" ? "desc" : "asc";
      sortDataByAttribute("comment_cnt", newDirection); // Sort data after updating the direction
      return newDirection;
    });
  };

  //북마크, 댓글  정렬
  const sortDataByAttribute = (attribute, direction) => {
    const sortedData = [...listData].sort((a, b) => {
      const valueA = Number(a[attribute]);
      const valueB = Number(b[attribute]);

      if (direction === "desc") {
        return valueA - valueB;
      } else {
        return valueB - valueA;
      }
    });

    setListData(sortedData);
  };

  return (
    <>
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
              <th>
                북마크
                <button className="sort-button" onClick={toggleSortBookmark}>
                  <span
                    className={
                      sortBookmark === "asc" ? "arrow-up" : "arrow-down"
                    }
                  >
                    {sortBookmark === "asc" ? "▲" : "▼"}
                  </span>
                </button>
              </th>
              <th>
                댓글
                <button className="sort-button" onClick={toggleSortComment}>
                  <span
                    className={
                      sortComment === "asc" ? "arrow-up" : "arrow-down"
                    }
                  >
                    {sortComment === "asc" ? "▲" : "▼"}
                  </span>
                </button>
              </th>
              <th></th>
              <th></th>
            </tr>
          </thead>
          <tbody>
          {[...listData.slice(0,slice), ...Array(8-listData.slice(slice-8,slice).length)].map((data, index) => (
              <tr key={index}>
                <td>{data ? index + 1 : ""}</td>
                <td>{data ? data.recipe_date : ""}</td>
                <td>{data ? data.recipe_title : ""}</td>
                <td>{data ? data.recipe_like_cnt.toLocaleString() : ""}</td>
                <td>{data ? data.comment_cnt.toLocaleString() : ""}</td>
                <td>
                  {data ? <ButtonOutlined size="verySmall" text="삭제" /> : ""}
                </td>
                <td>
                  {data ? <ButtonContain size="verySmall" text="수정" /> : ""}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </section>

      {/* 더보기 버튼    */}
      <section className="bottom-add">
        <ButtonContain size="medium" text="더보기" handleClick={handleClickMoreDetail} />
      </section>
    </>
  );
};

export default Checkmakingrecipe;
