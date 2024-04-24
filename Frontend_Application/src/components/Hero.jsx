import { Box, Grid, Typography } from "@mui/material";
import pic from "../assets/tomatoes.png";

const Hero = () => {
  return (
    <Grid
      container
      spacing={0}
      alignItems="center"
      justifyContent="flex-start" // Align content to the left
      style={{ minHeight: "calc(100vh - 64px - 90px)" }}
      className="hero"
    >
      {/* Grid item for the content */}
      <Grid item xs={12} sm={6} md={6} lg={4}>
        <Box marginLeft={4}>
          {" "}
          {/* Add left margin for spacing */}
          <Typography variant="h1" gutterBottom align="left">
            Come Shop with us!
          </Typography>
          <Typography variant="body1" paragraph align="left">
            Simplify Your Meal Planning: Effortlessly manage your groceries and
            plan meals for the week with our intuitive app.
          </Typography>
          <Grid container spacing={2} justifyContent="flex-end">
            <Grid item marginRight={4}>
              {/*Re-add this button for About Page if have time*/}
              {/* <Button variant="contained" color="primary" size="large">
                Explore
              </Button> */}
            </Grid>
          </Grid>
        </Box>
      </Grid>
      {/* Grid item for the image */}
      <Grid item xs={12} sm={6} md={6} lg={8}>
        <img
          src={pic}
          alt="fresh veggie image"
          style={{ width: "75%", height: "75%" }}
        />
      </Grid>
    </Grid>
  );
};

export default Hero;
