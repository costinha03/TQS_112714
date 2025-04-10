import React, { useState, lazy, Suspense } from 'react';
import { 
  AppBar, 
  Box, 
  Tabs, 
  Tab, 
  Typography, 
  Paper,
  Button,
  Grid,
  Divider
} from '@mui/material';
import { useNavigate } from 'react-router-dom';
import WeatherForecast from './WeatherForecast';

const BookingModal = lazy(() => import('./BookingModal'));
const ResponseModal = lazy(() => import('./ResponseModal'));

const reorderDays = (menus) => {
  const daysOfWeek = ["SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"];
  const todayIndex = new Date().getDay(); // Get today's index (0 = Sunday, 6 = Saturday)
  const orderedDays = [...daysOfWeek.slice(todayIndex), ...daysOfWeek.slice(0, todayIndex)];

  // Map the ordered days to the menus object
  return orderedDays.map((day) => ({
    day,
    date: new Date(new Date().setDate(new Date().getDate() + (daysOfWeek.indexOf(day) - todayIndex + 7) % 7)), // Calculate the date for each day
    menu: menus[day],
  })).filter(({ menu }) => menu); // Filter out days without menus
};

// Helper function for accessibility props
function a11yProps(index) {
  return {
    id: `restaurant-tab-${index}`,
    'aria-controls': `restaurant-tabpanel-${index}`,
  };
}

const RestaurantTabs = ({ restaurants }) => {
  const [selectedRestaurant, setSelectedRestaurant] = useState(0);
  const [modalOpen, setModalOpen] = useState(false);
  const [responseModalOpen, setResponseModalOpen] = useState(false);
  const [responseMessage, setResponseMessage] = useState('');
  const [responseTitle, setResponseTitle] = useState('');
  const [selectedDay, setSelectedDay] = useState(null);

  const navigate = useNavigate();

  const handleTabChange = (event, newValue) => {
    setSelectedRestaurant(newValue);
  };

  const handleBookDay = (restaurantId, dayObj) => {
    setSelectedDay(dayObj);
    setModalOpen(true);
  };

  const handleModalSubmit = async (reservationData) => {
    try {
      const response = await fetch('http://localhost:8080/api/reservations', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(reservationData),
      });
      console.log('Response:', response.status); // Log the response for debugging
      if (response.ok) {
        const responseData = await response.json(); // Parse the response JSON
        
        setResponseTitle('Reservation Created Successfully!');
        setResponseMessage(`
          Token: ${responseData.token}
          Restaurant: ${responseData.restaurantName}
          Dish Type: ${responseData.dishType}
          Time Slot: ${responseData.timeSlot}
          Date: ${responseData.date}
          Total Price: ${responseData.totalPrice} €
        `);
      } else {
        const error = await response.text();
        setResponseTitle('Reservation Failed');
        setResponseMessage(`Error: ${error}`);
      }
    } catch (err) {
      console.error('Error creating reservation:', err);
      setResponseTitle('Reservation Failed');
      setResponseMessage('An error occurred while creating the reservation.');
    } finally {
      setResponseModalOpen(true); // Open the response modal
    }
  };

  const selectedMenus = restaurants[selectedRestaurant]?.menus;
  const orderedMenus = selectedMenus ? reorderDays(selectedMenus) : [];

  return (
    <Paper elevation={3} sx={{ borderRadius: 4, overflow: 'hidden' }}>
      {/* Weather Forecast Component */}
      <WeatherForecast />

      <AppBar 
        position="static" 
        color="primary" 
        elevation={0} 
        sx={{ borderTopLeftRadius: 8, borderTopRightRadius: 8 }}
      >
        <Tabs
          value={selectedRestaurant}
          onChange={handleTabChange}
          indicatorColor="secondary"
          textColor="inherit"
          variant="scrollable"
          scrollButtons="auto"
          aria-label="restaurant tabs"
        >
          {restaurants.map((restaurant, index) => (
            <Tab 
              key={restaurant.id} 
              label={restaurant.name} 
              {...a11yProps(index)} 
              sx={{ fontWeight: 'bold', textTransform: 'none' }}
            />
          ))}
        </Tabs>
      </AppBar>
      <Box sx={{ mt: 3, px: 3 }}>
        {orderedMenus.length > 0 ? (
          orderedMenus.map((dayData) => (
            <Paper 
              key={dayData.day} 
              elevation={1} 
              sx={{ mb: 3, p: 3, borderRadius: 2, backgroundColor: '#f9f9f9' }}
            >
              <Typography 
                variant="h6" 
                sx={{ mb: 2, fontWeight: 'bold', color: 'primary.main' }}
              >
                {dayData.day.charAt(0).toUpperCase() + dayData.day.slice(1).toLowerCase()} - {dayData.date.toLocaleDateString('en-GB')}
              </Typography>
              <Grid container spacing={2}>
                <Grid item xs={12} sm={4}>
                  <Typography variant="body1">
                    <strong>Meat Dish:</strong> {dayData.menu.meatDish.name}
                  </Typography>
                  <Typography variant="body2" color="text.secondary">
                    {dayData.menu.meatDish.price.toFixed(2)}€
                  </Typography>
                </Grid>
                <Grid item xs={12} sm={4}>
                  <Typography variant="body1">
                    <strong>Fish Dish:</strong> {dayData.menu.fishDish.name}
                  </Typography>
                  <Typography variant="body2" color="text.secondary">
                    {dayData.menu.fishDish.price.toFixed(2)}€
                  </Typography>
                </Grid>
                <Grid item xs={12} sm={4}>
                  <Typography variant="body1">
                    <strong>Vegetarian Dish:</strong> {dayData.menu.vegetarianDish.name}
                  </Typography>
                  <Typography variant="body2" color="text.secondary">
                    {dayData.menu.vegetarianDish.price.toFixed(2)}€
                  </Typography>
                </Grid>
              </Grid>
              <Box sx={{ display: 'flex', justifyContent: 'center', mt: 2 }}>
                <Button 
                  variant="contained" 
                  color="secondary" 
                  size="medium" 
                  onClick={() => handleBookDay(restaurants[selectedRestaurant].id, dayData)}
                >
                  Book Meals for {dayData.day.charAt(0).toUpperCase() + dayData.day.slice(1).toLowerCase()} - {dayData.date.toLocaleDateString('en-GB')}
                </Button>
              </Box>
              <Divider sx={{ mt: 2 }} />
            </Paper>
          ))
        ) : (
          <Typography variant="body1" sx={{ color: 'text.secondary' }}>
            No menu available for this restaurant.
          </Typography>
        )}
      </Box>
      <Suspense fallback={<div>Loading...</div>}>
        <BookingModal
          open={modalOpen}
          onClose={() => setModalOpen(false)}
          onSubmit={handleModalSubmit}
          restaurantId={restaurants[selectedRestaurant]?.id}
          selectedDay={selectedDay}
        />
        <ResponseModal
          open={responseModalOpen}
          onClose={() => setResponseModalOpen(false)}
          title={responseTitle}
          message={responseMessage}
        />
      </Suspense>
    </Paper>
  );
};

export default RestaurantTabs;