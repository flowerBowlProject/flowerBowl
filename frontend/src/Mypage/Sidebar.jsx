import * as React from "react";
import PropTypes from "prop-types";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Divider from "@mui/material/Divider";
import CssBaseline from "@mui/material/CssBaseline";
import Drawer from "@mui/material/Drawer";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import Toolbar from "@mui/material/Toolbar";

// https://mui.com/material-ui/react-drawer/

const drawerWidth = 240;

function Sidebar(props) {
  const { window } = props;
  const [mobileOpen, setMobileOpen] = React.useState(false);
  const [isClosing, setIsClosing] = React.useState(false);

  const handleDrawerClose = () => {
    setIsClosing(true);
    setMobileOpen(false);
  };

  const handleDrawerTransitionEnd = () => {
    setIsClosing(false);
  };

  const handleDrawerToggle = () => {
    if (!isClosing) {
      setMobileOpen(!mobileOpen);
    }
  };

  const drawer = (
    <div>
      <Toolbar />
      <List>
        <Divider sx={{ borderColor: "main.or", borderWidth: "1px" }} />
        {[
          "프로필",
          "북마크",
          "마이 클래스",
          "마이 레시피",
          "결제내역",
          "클래스 신청내역",
        ].map((text, index) => (
          <React.Fragment key={text}>
            <ListItem
              key={text}
              disablePadding
              sx={{
                color: "main.br",
                textAlign: "center",
                "&:hover": { backgroundColor: "main.or" },
                "&:active": { backgroundColor: "main.or" },
              }}
            >
              <ListItemButton
                sx={{
                  justifyContent: "center",
                  width: "100%",
                  height: "60px",
                  "&:hover": { backgroundColor: "main.or", color: "white" },
                  "&:active": { backgroundColor: "main.or", color: "white" },
                }}
              >
                <ListItemIcon></ListItemIcon>
                <ListItemText
                  primary={text}
                  sx={{
                    "&:hover": { color: "white" },
                    "&:active": { color: "white" },
                  }}
                />
              </ListItemButton>
            </ListItem>

            {index <
              [
                "프로필",
                "북마크",
                "마이 클래스",
                "마이 레시피",
                "결제내역",
                "클래스 신청내역",
                "",
              ].length -
                1 && (
              <Divider sx={{ borderColor: "main.or", borderWidth: "1px" }} />
            )}
          </React.Fragment>
        ))}
      </List>
    </div>
  );

  const container =
    window !== undefined ? () => window().document.body : undefined;

  return (
    <Box sx={{ display: "flex" }}>
      <CssBaseline />
      <AppBar
        position="fixed"
        sx={{
          width: { sm: `calc(100% - ${drawerWidth}px)` },
          ml: { sm: `${drawerWidth}px` },
        }}
      ></AppBar>
      <Box
        component="nav"
        sx={{ width: { sm: drawerWidth }, flexShrink: { sm: 0 } }}
        aria-label="mailbox folders"
      >
        {/* The implementation can be swapped with js to avoid SEO duplication of links. */}
        <Drawer
          container={container}
          variant="temporary"
          open={mobileOpen}
          onTransitionEnd={handleDrawerTransitionEnd}
          onClose={handleDrawerClose}
          ModalProps={{
            keepMounted: true, // Better open performance on mobile.
          }}
          sx={{
            display: { xs: "block", sm: "none" },
            "& .MuiDrawer-paper": {
              boxSizing: "border-box",
              width: drawerWidth,
              borderRight: "2px solid",
              borderLeft: "2px solid",
              borderColor: "main.or",
            },
          }}
        >
          {drawer}
        </Drawer>
        <Drawer
          variant="permanent"
          sx={{
            display: { xs: "none", sm: "block" },
            "& .MuiDrawer-paper": {
              boxSizing: "border-box",
              width: drawerWidth,
              borderLeft: "2px solid",
              borderRight: "2px solid",
              borderColor: "main.or",
            },
          }}
          open
        >
          {drawer}
        </Drawer>
      </Box>
      {/* 메인콘텐츠 영역 정의 */}
      <Box
        component="main"
        sx={{
          flexGrow: 1,
          p: 3,
          width: { sm: `calc(100% - ${drawerWidth}px)` },
        }}
      >
        <Toolbar />
      </Box>
    </Box>
  );
}

Sidebar.propTypes = {
  /**
   * Injected by the documentation to work in an iframe.
   * Remove this when copying and pasting into your project.
   */
  window: PropTypes.func,
};

export default Sidebar;
