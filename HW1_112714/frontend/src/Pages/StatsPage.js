import React, { useState } from 'react';
import { Box, Typography, Button, Paper, Divider } from '@mui/material';
import WeatherForecast from '../Components/WeatherForecast'; // Import WeatherForecast

const StatsPage = () => {
  const [stats, setStats] = useState(null);
  const [error, setError] = useState(null);
  const [refreshWeather, setRefreshWeather] = useState(false); // State to trigger weather refresh

  const fetchStats = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/weather/cache/stats');
      if (response.ok) {
        const data = await response.json();
        setStats(data);
        setError(null);
      } else {
        const errorMessage = await response.text();
        setError(errorMessage);
      }
    } catch (err) {
      console.error('Error fetching stats:', err);
      setError('An error occurred while fetching stats.');
    }
  };

  const fetchWeather = () => {
    // Toggle the refreshWeather state to trigger a re-fetch in WeatherForecast
    setRefreshWeather((prev) => !prev);
  };

  return (
    <Box sx={{ p: 3 }}>
      <Typography variant="h5" sx={{ mb: 2, fontWeight: 'bold' }}>
        Cache Statistics
      </Typography>
      <Button variant="contained" onClick={fetchStats} sx={{ mb: 2 }}>
        Fetch Stats
      </Button>
      {error && (
        <Typography variant="body1" color="error" sx={{ mb: 2 }}>
          {error}
        </Typography>
      )}
      {stats && (
        <Paper elevation={3} sx={{ p: 2, mb: 3 }}>
          <Typography variant="h6" sx={{ mb: 2 }}>
            Stats:
          </Typography>
          <Typography variant="body1">Total Requests: {stats.totalRequests}</Typography>
          <Typography variant="body1">Hits: {stats.hits}</Typography>
          <Typography variant="body1">Misses: {stats.misses}</Typography>
        </Paper>
      )}
      <Divider sx={{ my: 3 }} />
      <Typography variant="h5" sx={{ mb: 2, fontWeight: 'bold' }}>
        Weather Forecast
      </Typography>
      <Button variant="contained" onClick={fetchWeather} sx={{ mb: 2 }}>
        Refresh Weather
      </Button>
      <WeatherForecast refresh={refreshWeather} />
    </Box>
  );
};

export default StatsPage;