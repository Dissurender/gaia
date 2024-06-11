import { Grid, Typography } from '@mui/material'

const Footer = () => {
  
  return (
    <>
      <Grid
        container
        padding={2}
        direction={'column'}
        alignItems={'center'}
        className='footer'
      >
        <Typography>
          &copy; {new Date().getFullYear()} GAIA
        </Typography>
      </Grid>
    </>
  )
}

export default Footer;