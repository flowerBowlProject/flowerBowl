import { Box } from "@mui/material";
import React from "react";
import Sidebar from "./Sidebar";

const MyPageLayout = ({ children }) => {
  return (
    <Box display="flex" flexDirection="row" height="100vh">
      <Sidebar />
      {children}
    </Box>
  );
};

export default MyPageLayout;
