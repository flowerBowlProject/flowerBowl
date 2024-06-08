import React, { useEffect, useState } from "react";
import "./CommentStyle.css";
import { Pagination } from "@mui/material";
import CommentParent from "./CommentParent";
import CommentChild from "./CommentChild";
import ChatBubbleOutlineIcon from "@mui/icons-material/ChatBubbleOutline";
import ButtonOutlined from "../ButtonOutlined";
import axios from "axios";
import { url } from "../../url";
import { useSelector, useDispatch } from "react-redux";
import ErrorConfirm from "../../Hook/ErrorConfirm";
import { editErrorType, openError } from "../../persistStore";

const Comment = ({ typeString, no }) => {
  const [commentList, setCommentList] = useState([]);
  const [pageInfo, setPageInfo] = useState({
    page: 1,
    size: 10,
    totalPage: 0,
    totalElement: 0,
  });
  const [registerData, setRegisterData] = useState({
    type: typeString,
    post_no: no,
    parent_no: null,
    comment_content: "",
  });
  const accessToken = useSelector((state) => state.accessToken);
  const [curPage, setCurPage] = useState(1);
  const [change, setChange] = useState(false);
  const dispatch = useDispatch();

  const fetchData = async () => {
    try {
      const response = await axios.get(
        `${url}/api/comments?type=${typeString}&post_no=${no}&page=${curPage}&size=${pageInfo.size}`
      );
      setCommentList(response.data.comments);
      setPageInfo(response.data.pageInfo);
    } catch (err) {
      console.log(err);
      dispatch(editErrorType(err.response.data.code));
      dispatch(openError());
    }
  };

  useEffect(() => {
    fetchData();
  }, [change, curPage]);

  const handleRegister = async () => {
    if (accessToken === "") {
      dispatch(editErrorType("NT"));
      dispatch(openError());
    } else if (registerData.comment_content.trim() === "") {
      dispatch(editErrorType("COMMENT"));
      dispatch(openError());
    } else {
      try {
        await axios.post(`${url}/api/comments`, registerData, {
          headers: { Authorization: `Bearer ${accessToken}` },
        });
        setRegisterData({
          type: typeString,
          post_no: no,
          parent_no: null,
          comment_content: "",
        });
        setChange(!change);
      } catch (err) {
        console.log(err);
        dispatch(editErrorType(err.response.data.code));
        dispatch(openError());
      }
    }
  };

  const pageChange = (e, value) => {
    setCurPage(value);
    setChange(!change);
  };

  return (
    <>
      <div className="comment-Box">
        <ErrorConfirm error={useSelector((state) => state.errorType)} />
        <div className="comment-register">
          <textarea
            className="comment-write"
            placeholder="댓글을 작성하세요."
            value={registerData.comment_content}
            onChange={(e) =>
              setRegisterData({
                ...registerData,
                comment_content: e.target.value,
              })
            }
          />
          <ButtonOutlined
            size="heightLarge"
            text="등록"
            handleClick={handleRegister}
          />
        </div>
        <div
          style={{
            width: "90%",
            margin: "2% auto",
            display: "flex",
            alignContent: "center",
          }}
        >
          댓글 &nbsp;{" "}
          <ChatBubbleOutlineIcon
            sx={{ color: "main.br", marginRight: "0.5vw" }}
          />{" "}
          {pageInfo.totalElement}
        </div>
        {commentList.length === 0 && (
          <div
            style={{
              display: "flex",
              justifyContent: "center",
              marginBottom: "2vw",
            }}
          >
            조회된 댓글이 없습니다
          </div>
        )}
        {commentList.map((list, index) =>
          list.parent_no === null ? (
            <CommentParent
              typeString={typeString}
              no={no}
              data={list}
              key={list.comment_no}
              isLast={index !== commentList.length - 1}
              change={change}
              setChange={setChange}
            />
          ) : (
            <CommentChild
              data={list}
              key={list.comment_no}
              isLast={index !== commentList.length - 1}
              change={change}
              setChange={setChange}
            />
          )
        )}
      </div>
      <div className="comment-page">
        {pageInfo.totalElement !== 0 && (
          <Pagination
            count={Math.ceil(pageInfo.totalElement / pageInfo.size)}
            page={curPage}
            onChange={pageChange}
          />
        )}
      </div>
    </>
  );
};

export default Comment;
