import React, { useState } from "react";
import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import ButtonContain from "../Component/ButtonContain";
import { useDispatch, useSelector } from "react-redux";
import axios from "axios";
import { url } from "../url";
import { editErrorType, openError } from "../persistStore";
import { useNavigate } from "react-router-dom";

const DeleteModal = ({checkType, no}) => {
  const accessToken = useSelector(state => state.accessToken);
  const [open, setOpen] = useState(false);
  const dispatch = useDispatch();
  const navigator = useNavigate();
  
  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleDelete = () => {
    console.log('동작 시작')
    if (checkType === 'recipe') {
      axios.delete(`${url}/api/recipes/${no}`, {
        headers: {
          Authorization: `Bearer ${accessToken}`
        }
      })
        .then(res => {
          console.log(res);
          // alert 창 띄우로 리스트 페이지로 이동 navigator('/recipeList');
          dispatch(editErrorType('DELETE'));
          dispatch(openError());
          navigator('/recipeList');
        })
        .catch(err => {
          console.log(err);
          dispatch(editErrorType(err.response.data.code));
          dispatch(openError());
        })
    } else if (checkType === 'class') {
      axios.put(`${url}/api/lessons`, {
        "lesson_no": no
      }, {
        headers: {
          Authorization: `Bearer ${accessToken}`
        }
      })
        .then(res => {
          console.log(res);
          dispatch(editErrorType('DELETE'));
          dispatch(openError());
          navigator('/classList');
        })
        .catch(err => {
          dispatch(editErrorType(err.response.data.code));
          dispatch(openError());
        })
    } else {
      axios.delete(`${url}/api/communities/${no}`,
        {
          headers: {
            Authorization: `Bearer ${accessToken}`
          }
        })
        .then(res => {
          console.log(res);
          dispatch(editErrorType('DELETE'));
          dispatch(openError());
          navigator('/communityList');
        })
        .catch(err => {
          console.log(err);
          dispatch(editErrorType(err.response.data.code));
          dispatch(openError());
        })
    }
  }

  return (
    <>
      <ButtonContain
        size='large' text='삭제' handleClick={handleClickOpen}
      />
      <Dialog
        open={open}
        onClose={handleClose}
        aria-describedby="alert-dialog-slide-description"
      >
        <DialogTitle sx={{ color: "main.br" }}>
          {"정말 삭제하시겠습니까?"}
        </DialogTitle>
        <DialogContent>
          <DialogContentText
            id="정말 삭제하시겠습니까?"
            sx={{ color: "main.gr" }}
          >
            글 삭제 시 복구할 수 없습니다.
            삭제를 원하신다면 '삭제' 버튼을 눌러주세요.
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleDelete} sx={{ color: "main.br" }} autoFocus>
            삭제
          </Button>
          <Button onClick={handleClose} sx={{ color: "main.br" }}>
            취소
          </Button>
        </DialogActions>
      </Dialog>
    </>

  );
}

export default DeleteModal;
