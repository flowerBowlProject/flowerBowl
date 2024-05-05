import { React, useState } from "react";
import FileDropArea from "./FileDropArea";
import ButtonContain from "../../Component/ButtonContain";
import "./RegisterBanner.css";
import axios from "axios";
import { url } from "../../url";
import { useSelector } from "react-redux";
import "./TextInput.css";

const RegisterBanner = () => {
  const accessToken = useSelector((state) => state?.accessToken);
  const [refreshData, setRefreshData] = useState(false);
  const [content_oname, setContent_oname] = useState("");
  const [content_sname, setContent_sname] = useState("");
  const [content, setContent] = useState("");

  const handleContent = (e) => {
    setContent(e.target.value);
  };

  const onUploadSuccess = (oname, sname) => {
    setContent_oname(oname);
    setContent_sname(sname);
  };

  const updateBanner = async () => {
    const updateUrl = `${url}/api/admin/banners`;
    console.log(
      `banner_sname: ${content_sname}, banner_oname: ${content_oname}, content: ${content}`
    );

    try {
      const response = await axios.put(
        updateUrl,
        {
          admin_banner_sname: content_sname,
          admin_banner_oname: content_oname,
          admin_content: content,
        },
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );

      if (response.status === 200) {
        alert("배너 등록이 완료되었습니다.");
        console.log("등록이 완료되었습니다.", response.data.message);
        setRefreshData(!refreshData); // toggle to trigger a re-fetch
      }
    } catch (error) {
      console.error("등록에 실패했습니다:", error);
      if (error.response) {
        console.log("Error response data:", error.response.data); // Log the error response data
      }
    }
  };

  return (
    <>
      <div className="division-line"></div>
      <div className="dragdrop">
        <FileDropArea onUploadSuccess={onUploadSuccess} />
      </div>
      <div className="inputbanner">
        <div className="text-input-container">
          <textarea
            className="text-input"
            placeholder="내용을 입력하세요"
            value={content}
            onChange={handleContent}
          />
        </div>
      </div>

      <div className="fileadd">
        <ButtonContain
          size="doubleLarge"
          text="등록"
          handleClick={() => updateBanner()}
        />
      </div>
      <div className="division-line"></div>
    </>
  );
};

export default RegisterBanner;
