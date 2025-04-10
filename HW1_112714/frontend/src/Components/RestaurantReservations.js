import React, { useState, useEffect } from 'react';
import {
  AppBar,
  Box,
  Tabs,
  Tab,
  Typography,
  Paper,
  List,
  ListItem,
  ListItemText,
  Divider,
} from '@mui/material';

const RestaurantReservations = () => {
  const [restaurants, setRestaurants] = useState([]);
  const [selectedRestaurant, setSelectedRestaurant] = useState(0);
  const [reservations, setReservations] = useState([]);
  const [error, setError] = useState(null);

  // Fetch restaurants on component mount
  useEffect(() => {
    const fetchRestaurants = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/restaurants');
        if (response.ok) {
          const data = await response.json();
          setRestaurants(data);
          if (data.length > 0) {
            fetchReservationsByRestaurantId(data[0].id); // Fetch reservations for the first restaurant
          }
        } else {
          const errorMessage = await response.text();
          setError(errorMessage);
        }
      } catch (err) {
        console.error('Error fetching restaurants:', err);
        setError('An error occurred while fetching restaurants.');
      }
    };

    fetchRestaurants();
  }, []);

  // Fetch reservations by restaurant ID
  const fetchReservationsByRestaurantId = async (restaurantId) => {
    try {
      const response = await fetch(`http://localhost:8080/api/reservations/restaurant?restaurantId=${restaurantId}`);
      if (response.ok) {
        const data = await response.json();
        setReservations(data);
        setError(null);
      } else {
        const errorMessage = await response.text();
        setError(errorMessage);
        setReservations([]);
      }
    } catch (err) {
      console.error('Error fetching reservations:', err);
      setError('An error occurred while fetching reservations.');
      setReservations([]);
    }
  };

  const handleTabChange = (event, newValue) => {
    setSelectedRestaurant(newValue);
    fetchReservationsByRestaurantId(restaurants[newValue].id);
  };

  return (
    <Box sx={{ p: 3 }}>
      <Typography variant="h5" sx={{ mb: 2, fontWeight: 'bold' }}>
        Reservations by Restaurant
      </Typography>
      {error && (
        <Typography variant="body1" color="error" sx={{ mb: 2 }}>
          {error}
        </Typography>
      )}
      {restaurants.length > 0 && (
        <>
          <AppBar position="static" color="primary" sx={{ borderRadius: 1 }}>
            <Tabs
              value={selectedRestaurant}
              onChange={handleTabChange}
              indicatorColor="secondary"
              textColor="inherit"
              variant="scrollable"
              scrollButtons="auto"
            >
              {restaurants.map((restaurant, index) => (
                <Tab key={restaurant.id} label={restaurant.name} />
              ))}
            </Tabs>
          </AppBar>
          <Box sx={{ mt: 3 }}>
            {reservations.length > 0 ? (
              <Paper elevation={3} sx={{ p: 2 }}>
                <Typography variant="h6" sx={{ mb: 2 }}>
                  Reservations:
                </Typography>
                <List>
                  {reservations.map((reservation) => (
                    <React.Fragment key={reservation.id}>
                      <ListItem>
                        <ListItemText
                          primary={`Student ID: ${reservation.studentId}`}
                          secondary={`Dish Type: ${reservation.dishType}, Time Slot: ${reservation.timeSlot}, Date: ${new Date(
                            reservation.date
                          ).toLocaleDateString('en-GB')}, Status: ${reservation.status}`}
                        />
                      </ListItem>
                      <Divider />
                    </React.Fragment>
                  ))}
                </List>
              </Paper>
            ) : (
              <Typography variant="body1" color="text.secondary">
                No reservations found for this restaurant.
              </Typography>
            )}
          </Box>
        </>
      )}
    </Box>
  );
};

export default RestaurantReservations;