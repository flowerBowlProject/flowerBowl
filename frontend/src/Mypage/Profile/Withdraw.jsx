import * as React from "react";
import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import Slide from "@mui/material/Slide";
import axios from "axios";
import { url } from "../../url";
import { useSelector } from "react-redux";

const Transition = React.forwardRef(function Transition(props, ref) {
  return <Slide direction="up" ref={ref} {...props} />;
});

export default function Withdrawl() {
  const accessToken = useSelector((state) => state.accessToken);
  const [open, setOpen] = React.useState(false);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  return (
    <React.Fragment>
      <Button
        variant="outlined"
        onClick={handleClickOpen}
        sx={{
          color: "main.or",
          borderColor: "main.or",
          width: "140px",
          height: "30px",
          position: "absolute",
          "&:hover": {
            color: "main.or",
            border: "none",
            backgroundColor: "transparent",
          },
        }}
      >
        회원 탈퇴하러가기
      </Button>
      <Dialog
        open={open}
        TransitionComponent={Transition}
        keepMounted
        onClose={handleClose}
        aria-describedby="alert-dialog-slide-description"
      >
        <DialogTitle sx={{ color: "main.br" }}>
          {"저희 화반을 정말 탈퇴하시겠습니까?"}
        </DialogTitle>
        <DialogContent>
          <DialogContentText
            id="저희 화반을 정말 탈퇴하시겠습니까?"
            sx={{ color: "main.gr" }}
          >
            저희 화반에서는 쉐프들의 다양한 클래스와 유용한 레시피들이 있습니다.
            서비스를 그만 이용하시고싶으시면, 예를 눌러주세요.
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} sx={{ color: "main.br" }}>
            예, 화반 탈퇴할래요
          </Button>
          <Button onClick={handleClose} sx={{ color: "main.br" }}>
            아니요, 화반 이용할래요
          </Button>
        </DialogActions>
      </Dialog>
    </React.Fragment>
  );
}
