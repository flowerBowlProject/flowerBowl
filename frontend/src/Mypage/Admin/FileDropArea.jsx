import React, { useCallback, useState } from "react";
import "./FileDropArea.css";

function FileDropArea() {
  const [dragActive, setDragActive] = useState(false);
  const [files, setFiles] = useState([]);

  // 드래그 이벤트 핸들러
  const handleDrag = useCallback((event) => {
    event.preventDefault();
    event.stopPropagation();
    if (event.type === "dragenter" || event.type === "dragover") {
      setDragActive(true);
    } else if (event.type === "dragleave") {
      setDragActive(false);
    }
  }, []);

  // 드롭 이벤트 핸들러
  const handleDrop = useCallback((event) => {
    event.preventDefault();
    event.stopPropagation();
    setDragActive(false);
    const newFiles = Array.from(event.dataTransfer.files);
    setFiles(newFiles);
  }, []);

  // 파일 선택 핸들러
  const handleFileSelect = useCallback((event) => {
    const newFiles = Array.from(event.target.files);
    setFiles(newFiles);
  }, []);

  // 파일 미리보기 생성
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
    <div
      className={`drop-area ${dragActive ? "active" : ""}`}
      onDragEnter={handleDrag}
      onDragOver={handleDrag}
      onDragLeave={handleDrag}
      onDrop={handleDrop}
    >
      {dragActive ? (
        <p>파일을 여기에 놓으세요</p>
      ) : (
        <p>파일을 이 영역으로 드래그하거나 클릭하여 선택하세요</p>
      )}
      <input
        type="file"
        id="fileinput"
        multiple
        onInput={handleFileSelect}
        style={{ display: "none" }}
      />
      <label htmlFor="fileinput">파일 선택</label>
      <div className="preview-container">{filePreview()}</div>
    </div>
  );
}

export default FileDropArea;
