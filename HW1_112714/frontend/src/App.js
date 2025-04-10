import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { ThemeProvider, createTheme, CssBaseline } from '@mui/material';
import HomePage from './Pages/HomePage';
import Layout from './Components/Layout';
import CheckReservations from './Components/CheckReservations';
import RestaurantReservations from './Components/RestaurantReservations';
import StatsPage from './Pages/StatsPage';
// Create a custom blue theme
const theme = createTheme({
  palette: {
    primary: {
      light: '#4dabf5',
      main: '#2196f3',
      dark: '#1769aa',
      contrastText: '#fff',
    },
    secondary: {
      light: '#bbdefb',
      main: '#64b5f6',
      dark: '#1976d2',
      contrastText: '#000',
    },
    background: {
      default: '#f5f7fa',
      paper: '#ffffff',
    },
  },
  components: {
    MuiAppBar: {
      styleOverrides: {
        root: {
          boxShadow: '0 2px 10px rgba(0, 0, 0, 0.1)',
        },
      },
    },
    MuiTab: {
      styleOverrides: {
        root: {
          fontWeight: 500,
          textTransform: 'none',
          fontSize: '1rem',
          minWidth: 100,
          padding: '12px 16px',
          '&.Mui-selected': {
            backgroundColor: 'rgba(33, 150, 243, 0.08)',
          },
        },
      },
    },
    MuiPaper: {
      styleOverrides: {
        root: {
          borderRadius: 8,
          boxShadow: '0 3px 10px rgba(0, 0, 0, 0.08)',
        },
      },
    },
    MuiButton: {
      styleOverrides: {
        root: {
          textTransform: 'none',
          borderRadius: 8,
          fontWeight: 500,
        },
      },
    },
  },
  typography: {
    h4: {
      fontWeight: 600,
    },
    h6: {
      fontWeight: 500,
    },
  },
});

function App() {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <Router>
        <Routes>
          <Route path="/" element={<Layout />}>
            <Route index element={<HomePage />} />
            <Route path="/check-reservations" element={<CheckReservations />} />
            <Route path="/restaurant-reservations" element={<RestaurantReservations />} />
            <Route path="/stats" element={<StatsPage />} />
          </Route>
        </Routes>
      </Router>
    </ThemeProvider>
  );
}

export default App;