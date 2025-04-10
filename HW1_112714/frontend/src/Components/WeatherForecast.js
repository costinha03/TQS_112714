import React, { useEffect, useState } from 'react';
import { Box, Typography, CircularProgress, Alert } from '@mui/material';
import axios from 'axios';
import { getWeatherIcon } from './WeatherIcons';

const WeatherForecast = ({ refresh }) => {
  const [weather, setWeather] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchWeather = async () => {
    try {
      setLoading(true);
      const response = await axios.get('http://localhost:8080/api/weather');
      setWeather(response.data);
      setError(null);
    } catch (err) {
      console.error('Failed to fetch weather data:', err);
      setError('Failed to load weather data. Please try again later.');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchWeather();
  }, [refresh]); // Re-fetch weather data when the refresh prop changes

  if (loading) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', mt: 1 }}>
        <CircularProgress size={24} />
      </Box>
    );
  }

  if (error) {
    return (
      <Alert severity="error" sx={{ mt: 1 }}>
        {error}
      </Alert>
    );
  }

  const formatDate = (dateString) => {
    const date = new Date(dateString);
    const days = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
    return `${days[date.getDay()]}, ${date.getDate()}`;
  };

  return (
    <Box sx={{ backgroundColor: '#f9f9f9', borderRadius: 1, boxShadow: 1, overflow: 'hidden', mb: 2 }}>
      <Typography variant="h6" sx={{ p: 2, fontWeight: 'bold', color: '#2196f3' }}>
        Weekly Weather Forecast
      </Typography>
      <Box sx={{ display: 'flex', flexWrap: 'wrap' }}>
        {weather.map((forecast) => (
          <Box
            key={forecast.date}
            sx={{
              width: { xs: '50%', sm: '33.33%', md: '25%', lg: `${100 / 7}%` },
              p: 1,
              textAlign: 'center',
              bgcolor: 'white',
              border: '1px solid #f0f0f0',
              '&:hover': { bgcolor: '#f5f5f5' },
            }}
          >
            <Typography variant="body2" fontWeight="bold">
              {formatDate(forecast.date)}
            </Typography>
            <Box sx={{ my: 2, fontSize: '2rem' }}>{getWeatherIcon(forecast.weatherCode)}</Box>
            <Typography variant="body2">
              {forecast.minTemperature}°C - {forecast.maxTemperature}°C
            </Typography>
          </Box>
        ))}
      </Box>
    </Box>
  );
};

export default WeatherForecast;