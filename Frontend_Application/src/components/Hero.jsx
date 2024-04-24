import { Container, Typography } from "@mui/material";

const Hero = () => {
  return (
    <div className="hero">
      <Container
        sx={{
          padding: "2rem",
          width: "100%",
          margin: "1rem",
        }}
      >
        <Typography variant="h1">Recipe App</Typography>
        <Typography variant="h5">Find Your Perfect Recipe</Typography>

      </Container>
    </div>
  );
};

export default Hero;
