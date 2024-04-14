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
import {Link, Outlet} from "react-router-dom";

const Row = ({ icon, name, isHovered, isFixed, link }) => {
  const color = isFixed || isHovered ? "main.wh" : "main.or";
  return (
    <Link to={link} className="underlink">
      <ListItem
        sx={{
          backgroundColor: isFixed || isHovered ? "main.or" : "transparent",
          color: color,
          "&:hover": {
            backgroundColor: "main.or",
            color: "main.wh",
          },
        }}
      >
        <ListItemButton
          sx={{ "&:hover": { border: "none", backgroundColor: "transparent" } }}
        >
          <ListItemIcon sx={{ color: color }}>{icon}</ListItemIcon>
          <ListItemText primary={name} sx={{ color: color }} />
        </ListItemButton>
      </ListItem>
    </Link>
  );
};

const Sidebar = ({ fixedItem = "" }) => {
  const items = [
    { name: "프로필", icon: <AccountCircleIcon />, link: "/Mypage/profile" },
    { name: "북마크", icon: <BookmarkIcon />, link: "/Mypage/bookmarkRecipe" },
    {
      name: "마이 클래스",
      icon: <ImportContactsIcon />,
      link: "/Mypage/checkteachingclass",
    },
    {
      name: "마이 레시피",
      icon: <BlenderIcon />,
      link: "/Mypage/checkmakingrecipe",
    },
    { name: "결제 내역", icon: <PaymentIcon />, link: "/Mypage/checkPaidList" },
    {
      name: "클래스 신청 내역",
      icon: <ListIcon />,
      link: "/Mypage/checkClassList",
    },
  ];

  const initialHoverIndex = items.findIndex((item) => item.name === fixedItem);
  const [hoverIndex, setHoverIndex] = useState(initialHoverIndex);
  useEffect(() => {
    setHoverIndex(initialHoverIndex);
  }, [initialHoverIndex]);

  return (
    <Grid container direction="column" mt="5vw">
      {items.map((item, i) => {
        return (
          <Grid
            item
            key={i}
            width="18vw"
            onMouseEnter={() => setHoverIndex(i)}
            onMouseLeave={() => setHoverIndex(initialHoverIndex)}
            sx={{
              borderTop:
                i === 1 || i === 3 || i === 5 ? "none" : "2px solid #F6C47B",
              borderRight: "2px solid #F6C47B",
              borderBottom: i === 1 || i === 3 ? "none" : "2px solid #F6C47B",
              "&:hover": { backgroundColor: "main.or", color: "main.wh" },
            }}
          >
            <Row
              name={item.name}
              icon={item.icon}
              isFixed={i === initialHoverIndex}
              isHovered={hoverIndex === i}
              link={item.link}
            />
          </Grid>
        );
      })}
      <Outlet />
    </Grid>
  );
};

export default Sidebar;
