import React, { useCallback, useState } from 'react';
import './FileDropArea.css';

function FileDropArea() {
  const [dragActive, setDragActive] = useState(false);

  // 드래그 이벤트 핸들러
  const handleDrag = useCallback((event) => {
    event.preventDefault();
    event.stopPropagation();
    if (event.type === 'dragenter' || event.type === 'dragover') {
      setDragActive(true);
    } else if (event.type === 'dragleave') {
      setDragActive(false);
    }
  }, []);

  // 드롭 이벤트 핸들러
  const handleDrop = useCallback((event) => {
    event.preventDefault();
    event.stopPropagation();
    setDragActive(false);
    const files = event.dataTransfer.files;
    // 파일 처리 로직을 여기에 추가하세요.
    console.log(files);
  }, []);

  // 파일 선택 핸들러
  const handleFileSelect = useCallback((event) => {
    const files = event.target.files;
    // 파일 처리 로직을 여기에 추가하세요.
    console.log(files);
  }, []);

  return (
    <div
      className={`drop-area ${dragActive ? 'active' : ''}`}
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
        style={{ display: 'none' }}
      />
      <label htmlFor="fileinput">파일 선택</label>
    </div>
  );
}

export default FileDropArea;