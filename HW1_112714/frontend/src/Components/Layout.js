import React from 'react';
import { Outlet } from 'react-router-dom';
import { Box, AppBar, Toolbar, Typography, Button, Container } from '@mui/material';
import { Link as RouterLink } from 'react-router-dom';
import RestaurantMenuIcon from '@mui/icons-material/RestaurantMenu';
import BookOnlineIcon from '@mui/icons-material/BookOnline';
import VerifiedIcon from '@mui/icons-material/Verified';
import BarChartIcon from '@mui/icons-material/BarChart';

const Layout = () => {
  return (
    <Box sx={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
      <AppBar position="static">
        <Toolbar>
          <RestaurantMenuIcon sx={{ mr: 1 }} />
          <Typography variant="h6" component={RouterLink} to="/" sx={{ flexGrow: 1, textDecoration: 'none', color: 'inherit' }}>
            Moliceiro Campus Meals
          </Typography>
          <Button 
            color="inherit" 
            component={RouterLink} 
            to="/" 
            startIcon={<RestaurantMenuIcon />}
            sx={{ mx: 1 }}
          >
            Restaurants
          </Button>
          <Button 
            color="inherit" 
            component={RouterLink} 
            startIcon={<BookOnlineIcon />}
            sx={{ mx: 1 }}
            to='/check-reservations'
          >
            Check Reservations
          </Button>
          <Button 
            color="inherit" 
            component={RouterLink} 
            to="/restaurant-reservations" 
            startIcon={<VerifiedIcon />}
            sx={{ mx: 1 }}
          >
            Staff Verify
          </Button>
          <Button 
  color="inherit" 
  component={RouterLink} 
  to="/stats" 
  sx={{ mx: 1 }}
  startIcon={<BarChartIcon />}
>
  Stats
</Button>
        </Toolbar>
      </AppBar>
      
      <Box component="main" sx={{ flexGrow: 1, py: 2 }}>
        <Outlet />
      </Box>
      
      <Box component="footer" sx={{ py: 3, bgcolor: 'primary.dark', color: 'white', mt: 'auto' }}>
        <Container maxWidth="lg">
          <Typography variant="body2" align="center">
            © {new Date().getFullYear()} Moliceiro University Campus Meal Booking System
          </Typography>
        </Container>
      </Box>
    </Box>
  );
};

export default Layout;