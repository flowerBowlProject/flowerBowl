import React, { useEffect, useRef, useState } from 'react';
import '@toast-ui/editor/dist/toastui-editor.css';
import { Editor } from '@toast-ui/react-editor';
import axios from "axios";
import { url } from "../url";
import { useSelector } from 'react-redux';

const ToastEditor = (props) => {
    const editorRef = useRef();
    const [contentData, setContentData] = useState({ content: "", oname: "", sname: "" });
    const accessToken = useSelector(state => state.accessToken);

    useEffect(() => {
        if (props.setContent !== '') {
            console.log(props.setContent)
            const htmlString = decodeURIComponent(props.setContent);
            editorRef.current?.getInstance().setHTML(htmlString);
        }
    }, [props.setContent])

    const changeContent = () => {
        const data = editorRef.current.getInstance().getHTML();
        setContentData((contentData) => ({ ...contentData, content: data }));
        props.getToastEditor(data);
    }

    return (
        <div style={{ margin: "2% auto", height: '500px' }}>
            <Editor
                onChange={changeContent}
                placeholder="내용을 입력해주세요"
                initialValue={contentData.content}
                ref={editorRef}
                previewStyle='vertical'
                height='500px'
                initialEditType='wysiwyg'
                hideModeSwitch={true}

                hooks={{
                    addImageBlobHook: (blob, callback) => {
                        let formData = new FormData();
                        formData.append('file', blob);

                        axios.post(`${url}/api/images/content`, formData, {
                            headers: {
                                Authorization: `Bearer ${accessToken}`,
                                'Content-Type': 'multipart/form-data'
                            }
                        })
                            .then(res => {
                                const decodeSname = decodeURIComponent(res.data.content_sname);
                                console.log(decodeSname);
                                setContentData(prevState => ({
                                    ...prevState,
                                    oname: [...prevState.oname, res.data.content_oname],
                                    sname: [...prevState.sname, decodeSname]
                                }));

                                props.getToastImg({
                                    oname: res.data.content_oname,
                                    sname: decodeSname
                                });
                                callback(decodeSname);
                            })
                            .catch(err => {
                                console.log(err);
                            })
                    }
                }}
            />
        </div>
    );
}

export default ToastEditor;