import { Box } from "@mui/material";
import React from "react";
import AdminSidebar from "../Mypage/Admin/AdminSidebar";

const MyPageAdminLayout = ({ children }) => {
  return (
    <Box display="flex" flexDirection="row" height="100vh">
      <AdminSidebar/>
      {children}
    </Box>
  );
};

export default MyPageAdminLayout;
