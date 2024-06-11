import { Typography, Grid } from "@mui/material";
import DietaryPrefSwitchboard from "../../components/SwitchBoard";

const Settings = () => {

  return (
    <>
      <Grid container justifyContent='center' alignItems='center'>
        <Grid item>
          <Typography variant='h3' gutterBottom sx={{ p: 4 }}>
            My Dietary Preferences
          </Typography>
          <DietaryPrefSwitchboard />
        </Grid>
      </Grid>
    </>
  )
}

export default Settings;