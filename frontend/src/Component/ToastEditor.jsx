import React, { useRef } from 'react';
import '@toast-ui/editor/dist/toastui-editor.css';
import '@toast-ui/editor-plugin-code-syntax-highlight/dist/toastui-editor-plugin-code-syntax-highlight.css';
import { Editor } from '@toast-ui/react-editor';
import 'prismjs/themes/prism.css';

const ToastEditor = () => {
    const editorRef = useRef();

    return (
        <div className='editor' style={{margin: "1% auto"}}>
            <Editor
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