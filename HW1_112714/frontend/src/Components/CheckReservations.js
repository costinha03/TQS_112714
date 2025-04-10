import React, { useState } from 'react';
import {
  Box,
  Typography,
  TextField,
  Button,
  Paper,
  List,
  ListItem,
  ListItemText,
  Divider,
} from '@mui/material';

const CheckReservations = () => {
  const [studentId, setStudentId] = useState('');
  const [reservations, setReservations] = useState([]);
  const [error, setError] = useState(null);

  const handleFetchReservations = async () => {
    if (!studentId.trim()) {
      setError('Please enter a valid Student ID.');
      return;
    }

    try {
      const response = await fetch(`http://localhost:8080/api/reservations/student/${studentId}`);
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

  const handleCheckIn = async (token) => {
    try {
      const response = await fetch(`http://localhost:8080/api/reservations/${token}/check-in`, {
        method: 'POST',
      });
      if (response.ok) {
        const updatedReservation = await response.json();
        setReservations((prev) =>
          prev.map((res) => (res.token === token ? updatedReservation : res))
        );
        alert('Reservation checked in successfully!');
      } else {
        const errorMessage = await response.text();
        alert(`Failed to check in: ${errorMessage}`);
      }
    } catch (err) {
      console.error('Error checking in reservation:', err);
      alert('An error occurred while checking in the reservation.');
    }
  };

  const handleCancel = async (token) => {
    try {
      const response = await fetch(`http://localhost:8080/api/reservations/${token}/cancel`, {
        method: 'POST',
      });
      if (response.ok) {
        const updatedReservation = await response.json();
        setReservations((prev) =>
          prev.map((res) => (res.token === token ? updatedReservation : res))
        );
        alert('Reservation canceled successfully!');
      } else {
        const errorMessage = await response.text();
        alert(`Failed to cancel: ${errorMessage}`);
      }
    } catch (err) {
      console.error('Error canceling reservation:', err);
      alert('An error occurred while canceling the reservation.');
    }
  };

  return (
    <Box sx={{ p: 3 }}>
      <Typography variant="h5" sx={{ mb: 2, fontWeight: 'bold' }}>
        Check Reservations by Student ID
      </Typography>
      <Box sx={{ display: 'flex', alignItems: 'center', mb: 3 }}>
        <TextField
          label="Student ID"
          value={studentId}
          onChange={(e) => setStudentId(e.target.value)}
          sx={{ mr: 2 }}
        />
        <Button variant="contained" onClick={handleFetchReservations}>
          Fetch Reservations
        </Button>
      </Box>
      {error && (
        <Typography variant="body1" color="error" sx={{ mb: 2 }}>
          {error}
        </Typography>
      )}
      {!error && reservations.length === 0 && studentId.trim() && (
        <Typography variant="body1" color="text.secondary" sx={{ mb: 2 }}>
          No reservations found for the provided Student ID.
        </Typography>
      )}
      {reservations.length > 0 && (
        <Paper elevation={3} sx={{ p: 2 }}>
          <Typography variant="h6" sx={{ mb: 2 }}>
            Reservations:
          </Typography>
          <List>
            {reservations.map((reservation) => (
              <React.Fragment key={reservation.id}>
                <ListItem>
                  <ListItemText
                    primary={`Restaurant: ${reservation.restaurantName}`}
                    secondary={`Dish Type: ${reservation.dishType}, Time Slot: ${reservation.timeSlot}, Date: ${new Date(
                      reservation.date
                    ).toLocaleDateString('en-GB')}`}
                  />
                  <Button
                    variant="outlined"
                    color="success"
                    sx={{ mr: 1 }}
                    onClick={() => handleCheckIn(reservation.token)}
                  >
                    Check In
                  </Button>
                  <Button
                    variant="outlined"
                    color="error"
                    onClick={() => handleCancel(reservation.token)}
                  >
                    Cancel
                  </Button>
                </ListItem>
                <Divider />
              </React.Fragment>
            ))}
          </List>
        </Paper>
      )}
    </Box>
  );
};

export default CheckReservations;