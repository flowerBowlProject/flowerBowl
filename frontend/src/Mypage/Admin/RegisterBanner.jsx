import { React, useState } from "react";
import FileDropArea from "./FileDropArea";
import TextInput from "./TextInput";
import ButtonContain from "../../Component/ButtonContain";
import "./RegisterBanner.css";
import axios from "axios";
import { url } from "../../url";
import { useSelector } from "react-redux";

const RegisterBanner = () => {
  const accessToken = useSelector((state) => state?.accessToken);
  const [refreshData, setRefreshData] = useState(false);

  const updateBanner = async (banner_sname, banner_oname, content) => {
    const updateUrl = `${url}/api/admin/banners`;
    console.log("Attempting to update:", updateUrl); // Log the URL

    try {
      const response = await axios.put(
        updateUrl,
        {
          admin_banner_sname: banner_sname,
          admin_banner_oname: banner_oname,
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
        <FileDropArea />
      </div>
      <div className="inputbanner">
        <TextInput />
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
