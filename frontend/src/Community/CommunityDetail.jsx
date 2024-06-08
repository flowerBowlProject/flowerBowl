import React, { useEffect, useState } from "react";
import "./CommunityDetailStyle.css";
import { useLocation } from "react-router";
import { useDispatch, useSelector } from "react-redux";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import { url } from "../url";
import Comment from "../Component/Comment/Comment";
import ButtonOutlined from "../Component/ButtonOutlined";
import { Viewer } from "@toast-ui/react-editor";
import { editErrorType, openError } from "../persistStore";
import ErrorConfirm from "../Hook/ErrorConfirm";
import DeleteModal from "../Hook/DeleteModal";

const CommunityDetail = () => {
  const [communityData, setCommunityData] = useState({});
  const { community_no } = useParams();
  const writer = useSelector((state) => state.nickname);
  const location = useLocation();
  const navigator = useNavigate();
  const accessToken = useSelector((state) => state.accessToken);
  const dispatch = useDispatch();

  {
    /* 데이터 불러오기 */
  }
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(
          `${url}/api/communities/detail/${community_no}`
        );
        setCommunityData(response.data.data);
      } catch (err) {
        console.log(err);
        dispatch(editErrorType(err.response.data.code));
        dispatch(openError());
      }
    };

    fetchData();
  }, [community_no, dispatch]);

  {
    /* 커뮤니티 수정 */
  }
  const handleModify = () => {
    navigator("/modifyCommunity/" + community_no);
  };

  return (
    <>
      <div className="communityDetail-Box">
        <ErrorConfirm error={useSelector((state) => state.errorType)} />

        <div className="community-title"> {communityData.community_title} </div>
        <div className="community-body">
          {communityData.community_content && (
            <Viewer initialValue={communityData.community_content} />
          )}
        </div>
        {/* 수정/삭제 버튼 - 작성자인 경우에만 true로 버튼 표시 */}
        <div className="community-change">
          {writer === communityData.community_writer && (
            <ButtonOutlined
              size="large"
              text="수정"
              handleClick={handleModify}
            />
          )}{" "}
          &nbsp;
          {writer === communityData.community_writer && (
            <DeleteModal checkType={"community"} no={community_no} />
          )}
        </div>
      </div>
      <div>
        <Comment typeString={2} no={community_no} />
      </div>
    </>
  );
};

export default CommunityDetail;
