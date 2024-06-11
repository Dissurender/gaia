import { Outlet } from "react-router-dom";
import Nav from "../components/Nav";
import Footer from "../components/Footer";

import { Container } from "@mui/material";
import { createTheme, ThemeProvider } from "@mui/material/styles";

const theme = createTheme({
  palette: {
    primary: {
      main: "#00695c", // Set primary color for buttons
    },
    secondary: {
      main: "#dd2c00", // Set secondary color for buttons
    },
    error: {
      main: "#f44336",
    },
    warning: {
      main: "#ff9800",
    },
    info: {
      main: "#2196f3",
    },
    success: {
      main: "#4caf50",
    },
    background: {
      default: "#f5f5f5",
    }
  },
});

const Layout = () => {
  return (
    <ThemeProvider theme={theme}>
      <Nav />
      <Container
        sx={{
          width: "100%",
          padding: "1rem",
          minHeight: "calc(100vh - 64px - 100px)",
        }}
        className="content"
      >
        <Outlet />
      </Container>
      <Footer />
    </ThemeProvider>
  );
};

export default Layout;
