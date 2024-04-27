import React, { useState, useEffect } from "react";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemText from "@mui/material/ListItemText";
import ListItemIcon from "@mui/material/ListItemIcon";
import Grid from "@mui/material/Grid";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import BookmarkIcon from "@mui/icons-material/Bookmark";
import ImportContactsIcon from "@mui/icons-material/ImportContacts";
import BlenderIcon from "@mui/icons-material/Blender";
import PaymentIcon from "@mui/icons-material/Payment";
import ListIcon from "@mui/icons-material/List";
import "./Sidebar.css";
import { Link, Outlet,NavLink } from "react-router-dom";
import axios from "axios";
import { url } from "../url";
import { useSelector ,useDispatch} from "react-redux";
import { editErrorType,openError } from "../persistStore";

const Row = ({ icon, name, isHovered, isFixed, link }) => {
  const color = isFixed || isHovered ? "main.wh" : "main.or";
  
  return (
    <NavLink to={link} style={{textDecoration:'none'}} className={({isActive})=>isActive? 'selected':'unselected'} >
      <ListItem
        sx={{
          backgroundColor: isFixed || isHovered ? "main.or" : "inherit",
          color: isFixed || isHovered ? "main.wh" : "inherit",
          "&:hover": {
            backgroundColor: "main.or",
            color: "main.wh",
          },
        }}
      >
        <ListItemButton
          sx={{ "&:hover": { border: "none", backgroundColor: "transparent" } }}
        >
          <ListItemIcon sx={{ color: isFixed || isHovered ? "main.wh" : "inherit" }}>{icon}</ListItemIcon>
          <ListItemText primary={name} sx={{ color: isFixed || isHovered ? "main.wh" : "inherit" }} />
        </ListItemButton>
      </ListItem>
    </NavLink>
  );
};

const Sidebar = ({ fixedItem = "" }) => {
  const accessToken=useSelector((state)=>state.accessToken)
  const dispatch=useDispatch();
  const [items,setItems]=useState([
    { name: "프로필", icon: <AccountCircleIcon />, link: "/Mypage/profile" },
    { name: "북마크", icon: <BookmarkIcon />, link: "/Mypage/bookmark" },
    {
      name: "마이 클래스",
      icon: <ImportContactsIcon />,
      link: "/Mypage/checkClass",
    },
    {
      name: "마이 레시피",
      icon: <BlenderIcon />,
      link: "/Mypage/checkmakingrecipe",
    },
    { name: "결제 내역", icon: <PaymentIcon />, link: "/Mypage/paymentDetail" },
    {
      name: "클래스 신청 내역",
      icon: <ListIcon />,
      link: "/Mypage/checkClassList",
    }
  ])
  
    
  
  const handleCheckRole=async()=>{
    try{
      const response=await axios.get(`${url}/api/users/info`,{
        headers:{
          Authorization: `Bearer ${accessToken}`
        }
      })
      const updatedItems = [...items]; // 복사본 생성
      updatedItems[5].role = response.data.user_role;
      setItems(updatedItems); // 상태 업데이트
    }catch(error){
      dispatch(editErrorType(error.response.data.code))
      dispatch(openError())
    }

  }
  const initialHoverIndex = items.findIndex((item) => item.name === fixedItem);
  const [hoverIndex, setHoverIndex] = useState(initialHoverIndex);
  useEffect(() => {
    setHoverIndex(initialHoverIndex);
  }, [initialHoverIndex]);
  useEffect(()=>{
    handleCheckRole();
  },[])
  return (
    <Grid container direction="row">
      <Grid item xs={2.5}>
        <Grid container direction="column" mt="5vw">
          {items.map((item, i) => {
            console.log(item.role)
            if(item.role==='ROLE_CHEF'||i!==5){
              
            return (
              <Grid
                item
                key={i}
                width="18vw"
                onMouseEnter={() => setHoverIndex(i)}
                onMouseLeave={() => setHoverIndex(initialHoverIndex)}
                sx={{
                  borderTop:
                    i === 1 || i === 3 || i === 5
                      ? "none"
                      : "2px solid #F6C47B",
                  borderRight: "2px solid #F6C47B",
                  borderBottom:
                    i === 1 || i === 3 ? "none" : "2px solid #F6C47B",
                  "&:hover": { backgroundColor: "main.or", color: "main.wh" },
                }}
              >
                <Row
                  name={item.name}
                  icon={item.icon}
                  isFixed={i === initialHoverIndex}
                  isHovered={hoverIndex === i}
                  link={item.link}
                  link2={item.link2?item.link2:null}
                />
              </Grid>
            );}
          })}
        </Grid>
      </Grid>
      <Grid item xs={9.5} mt="5vw">
        <Outlet />
      </Grid>
    </Grid>
  );
};

export default Sidebar;
