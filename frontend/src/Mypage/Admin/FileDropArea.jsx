import React, { useCallback, useState } from "react";
import "./FileDropArea.css";
import axios from "axios";
import { url } from "../../url";
import { useSelector } from "react-redux";

function FileDropArea({ onUploadSuccess }) {
  const accessToken = useSelector((state) => state?.accessToken);
  const [files, setFiles] = useState([]);

  const handleDragOver = useCallback((event) => {
    event.preventDefault();
  }, []);

  const handleFileSelect = useCallback((event) => {
    const newFiles = Array.from(event.target.files);
    setFiles(newFiles);
    uploadFiles(newFiles);
  }, []);

  const handleDrop = useCallback((event) => {
    console.log("handleDrop");
    event.preventDefault();
    const newFiles = Array.from(event.dataTransfer.files);
    setFiles(newFiles);
    uploadFiles(newFiles);
  }, []);

  //파일 업로드
  const uploadFiles = useCallback(
    (files) => {
      files.forEach((file) => {
        if (file.type.startsWith("image/")) {
          console.log(808);
          console.log(accessToken);
          const formData = new FormData();
          formData.append("file", file);

          axios
            .post(`${url}/api/images/content`, formData, {
              headers: {
                Authorization: `Bearer ${accessToken}`,
                "Content-Type": "multipart/form-data",
              },
            })
            .then((response) => {
              const { code, message, content_oname, content_sname } =
                response.data;
              console.log(
                `Upload success - Code: ${code}, Message: ${message}, Original Filename: ${content_oname}, Stored S3 URL: ${content_sname}`
              );
              onUploadSuccess(content_oname, content_sname);
            })
            .catch((error) => {
              console.error("Upload error:", error);
            });
        }
      });
    },
    [onUploadSuccess]
  );

  const filePreview = useCallback(() => {
    return files.map((file, index) => (
      <div key={index} className="file-preview">
        {file.type.startsWith("image/") ? (
          <img
            src={URL.createObjectURL(file)}
            alt={file.name}
            className="image-preview"
          />
        ) : (
          <p>{file.name}</p>
        )}
      </div>
    ));
  }, [files]);

  return (
    <div className="drop-area" onDragOver={handleDragOver} onDrop={handleDrop}>
      {files.length > 0 ? (
        <>
          <div className="preview-container">{filePreview()}</div>
          <label htmlFor="fileinput" onClick={handleDrop}>
            파일 선택
          </label>
        </>
      ) : (
        <>
          <p>파일을 드래그하거나 파일 선택을 클릭해주세요.</p>
          <label htmlFor="fileinput">파일 선택</label>
        </>
      )}
      <input
        type="file"
        id="fileinput"
        multiple
        onChange={handleFileSelect}
        style={{ display: "none" }}
      />
    </div>
  );
}

export default FileDropArea;
