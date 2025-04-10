import React from 'react';
import { 
  WiDaySunny,
  WiCloudy,
  WiDayCloudy,
  WiFog,
  WiShowers,
  WiRain,
  WiSnowflakeCold,
  WiSnow,
  WiThunderstorm,
  WiHail
} from 'react-icons/wi';

// Define icon styles for consistency
const iconStyle = { fontSize: '5rem' };

// Create a mapping between Open-Meteo WMO weather codes and their corresponding icons
export const weatherIconMap = {
  // Clear sky
  '0': <WiDaySunny style={iconStyle} title="Clear sky" />,
  
  // Mainly clear, partly cloudy, and overcast
  '1': <WiDayCloudy style={iconStyle} title="Mainly clear" />,
  '2': <WiDayCloudy style={iconStyle} title="Partly cloudy" />,
  '3': <WiCloudy style={iconStyle} title="Overcast" />,
  
  // Fog and depositing rime fog
  '45': <WiFog style={iconStyle} title="Fog" />,
  '48': <WiFog style={iconStyle} title="Depositing rime fog" />,
  
  // Drizzle: Light, moderate, and dense intensity
  '51': <WiShowers style={iconStyle} title="Light drizzle" />,
  '53': <WiShowers style={iconStyle} title="Moderate drizzle" />,
  '55': <WiShowers style={iconStyle} title="Dense drizzle" />,
  
  // Freezing Drizzle: Light and dense intensity
  '56': <WiSnowflakeCold style={iconStyle} title="Light freezing drizzle" />,
  '57': <WiSnowflakeCold style={iconStyle} title="Dense freezing drizzle" />,
  
  // Rain: Slight, moderate and heavy intensity
  '61': <WiRain style={iconStyle} title="Slight rain" />,
  '63': <WiRain style={iconStyle} title="Moderate rain" />,
  '65': <WiRain style={iconStyle} title="Heavy rain" />,
  
  // Freezing Rain: Light and heavy intensity
  '66': <WiSnowflakeCold style={iconStyle} title="Light freezing rain" />,
  '67': <WiSnowflakeCold style={iconStyle} title="Heavy freezing rain" />,
  
  // Snow fall: Slight, moderate, and heavy intensity
  '71': <WiSnow style={iconStyle} title="Slight snow fall" />,
  '73': <WiSnow style={iconStyle} title="Moderate snow fall" />,
  '75': <WiSnow style={iconStyle} title="Heavy snow fall" />,
  
  // Snow grains
  '77': <WiSnow style={iconStyle} title="Snow grains" />,
  
  // Rain showers: Slight, moderate, and violent
  '80': <WiShowers style={iconStyle} title="Slight rain showers" />,
  '81': <WiShowers style={iconStyle} title="Moderate rain showers" />,
  '82': <WiShowers style={iconStyle} title="Violent rain showers" />,
  
  // Snow showers slight and heavy
  '85': <WiSnow style={iconStyle} title="Slight snow showers" />,
  '86': <WiSnow style={iconStyle} title="Heavy snow showers" />,
  
  // Thunderstorm: Slight or moderate
  '95': <WiThunderstorm style={iconStyle} title="Thunderstorm" />,
  
  // Thunderstorm with slight and heavy hail
  '96': <WiThunderstorm style={iconStyle} title="Thunderstorm with slight hail" />,
  '99': <WiThunderstorm style={iconStyle} title="Thunderstorm with heavy hail" />,
};

// Default icon for unknown weather codes
export const defaultWeatherIcon = <WiDaySunny style={iconStyle} title="Unknown weather" />;

// Helper function to get an icon by code with fallback to default
export const getWeatherIcon = (code) => {
  return weatherIconMap[code.toString()] || defaultWeatherIcon;
};