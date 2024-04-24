import { Grid, Typography } from '@mui/material'

const Footer = () => {
  
  return (
    <>
      <Grid
        container
        padding={2}
        direction={'row'}
        alignItems={'center'}
        className='footer'
      >
        <Typography>
          &copy; {new Date().getFullYear()}
        </Typography>
      </Grid>
    </>
  )
}

export default Footer;