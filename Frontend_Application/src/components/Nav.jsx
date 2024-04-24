import { AppBar, Toolbar, Box, Typography } from "@mui/material";
import { Link as RouterLink } from "react-router-dom";
import logo from "../assets/gaia_logo_white.png";

const home = {
  title: "Home",
  path: "/",
  image: logo,
  describedBy: "home-link",
};

const settings = {
  title: "Settings",
  path: "/settings",
  describedBy: "settings-link",
};

const Nav = () => {
  return (
    <Box className="nav">
      <AppBar position="static">
        <Toolbar disableGutters className="my-4 justify-between">
          <RouterLink
            key={home.title}
            to={home.path}
            id={home.title + "-link"}
            className="nav-link mx-4"
            aria-describedby={home.describedBy}
          >
            <img src={home.image} alt={home.title} style={{ height: "64px" }} />
          </RouterLink>

          <Box className="nav">
            <RouterLink
              key={settings.title}
              to={settings.path}
              id={settings.title + "-link"}
              className="nav-link mx-4"
              color="inherit"
              aria-describedby={settings.describedBy}
            >
              <Typography variant="h6" component="span">
                {settings.title}
              </Typography>
            </RouterLink>
            <RouterLink
              key="Github"
              to="https://github.com/Dissurender/gaia"
              id="Github-link"
              className="nav-link mx-4"
              color="inherit"
              aria-describedby="Github-link"
              target="_blank"
              rel="noreferrer"
            >
              <Typography variant="h6" component="span">
                Github
              </Typography>
            </RouterLink>
          </Box>
        </Toolbar>
      </AppBar>
    </Box>
  );
};

export default Nav;
