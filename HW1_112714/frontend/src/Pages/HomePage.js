import React, { useState, useEffect } from 'react';
import { 
  Typography, 
  Paper, 
  Container, 
  Box, 
  CircularProgress,
  Alert
} from '@mui/material';
import RestaurantIcon from '@mui/icons-material/Restaurant';
import RestaurantTabs from '../Components/RestaurantTabs';
import axios from 'axios';

const HomePage = () => {
  const [restaurants, setRestaurants] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const getRestaurants = async () => {
      try {
        setLoading(true);
        const response = await axios.get('http://localhost:8080/api/restaurants');
        setRestaurants(response.data);
        setLoading(false);
      } catch (err) {
        console.error("Failed to fetch restaurants:", err);
        setError("Failed to load restaurants. Please try again later.");
        setLoading(false);
      }
    };

    getRestaurants();
  }, []);

  return (
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
      <Paper elevation={2} sx={{ mb: 3, p: 2 }}>
        <Typography variant="h4" component="h1" sx={{ mb: 2, color: 'primary.dark', display: 'flex', alignItems: 'center' }}>
          <RestaurantIcon fontSize="large" sx={{ mr: 1 }} />
          Campus Restaurants
        </Typography>
        <Typography variant="body1" sx={{ color: 'text.secondary' }}>
          Browse available restaurants and book your meals in advance
        </Typography>
      </Paper>

      {loading ? (
        <Box sx={{ display: 'flex', justifyContent: 'center', mt: 4 }}>
          <CircularProgress />
        </Box>
      ) : error ? (
        <Alert severity="error" sx={{ mt: 2 }}>
          {error}
        </Alert>
      ) : restaurants.length === 0 ? (
        <Alert severity="info" sx={{ mt: 2 }}>
          No restaurants available at the moment.
        </Alert>
      ) : (
        <RestaurantTabs restaurants={restaurants} />
      )}
    </Container>
  );
};

export default HomePage;