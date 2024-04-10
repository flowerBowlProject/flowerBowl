import React, { useRef, useState } from 'react';
import '@toast-ui/editor/dist/toastui-editor.css';
import { Editor } from '@toast-ui/react-editor';

const ToastEditor = (props) => {
    const editorRef = useRef();
    const [content, setContent] = useState('');

    const changeContent = () =>{
        const data = editorRef.current.getInstance().getHTML();
        setContent(data);
        props.getToastEditor(data);
        console.log(data);
    }

    return (
        <div className='editor' style={{margin: "2% auto", height:'500px'}}>
            <Editor
                onChange={changeContent}
                placeholder="내용을 입력해주세요."
                initialValue=' '
                ref={editorRef}
                previewStyle='vertical'
                height='500px'
                initialEditType='wysiwyg'
                hideModeSwitch={true}
            />

            
        </div>
    );

}
export default ToastEditor;