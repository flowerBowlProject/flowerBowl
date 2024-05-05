import React, { useState, useEffect } from "react";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemText from "@mui/material/ListItemText";
import ListItemIcon from "@mui/material/ListItemIcon";
import Grid from "@mui/material/Grid";
import AddCommentIcon from "@mui/icons-material/AddComment";
import PersonAddIcon from "@mui/icons-material/PersonAdd";
import { NavLink, Outlet } from "react-router-dom";
import "./Sidebar.css";

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

const AdminSidebar = ({ fixedItem = "" }) => {
  const items = [
    {
      name: "쉐프 신청 내역",
      icon: <PersonAddIcon />,
      link: "/Admin/admissionChef",
    },
    {
      name: "배너 등록",
      icon: <AddCommentIcon />,
      link: "/Admin/registerBanner",
    },
  ];

  const initialHoverIndex = items.findIndex((item) => item.name === fixedItem);
  const [hoverIndex, setHoverIndex] = useState(initialHoverIndex);
  useEffect(() => {
    setHoverIndex(initialHoverIndex);
  }, [initialHoverIndex]);

  return (
    <Grid container direction="row">
      <Grid item xs={3} >
        <Grid container direction="column" mt="5.5vw" mb="45vh">
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
                    i === 1 || i === 3 || i === 5
                      ? "none"
                      : "2px solid #F6C47B",
                  borderRight: "2px solid #F6C47B",
                  borderBottom: "2px solid #F6C47B",
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
        </Grid>
        </Grid>
        <Grid item xs={9} mt='5vw'>
          <Outlet />
        </Grid>
    </Grid>
  );
};

export default AdminSidebar;
