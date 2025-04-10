import React, { useState } from 'react';
import {
  Modal,
  Box,
  Typography,
  TextField,
  Button,
  MenuItem,
  Select,
  InputLabel,
  FormControl,
} from '@mui/material';

const BookingModal = ({ open, onClose, onSubmit, restaurantId, selectedDay }) => {
  const [timeSlot, setTimeSlot] = useState('');
  const [dishType, setDishType] = useState('');
  const [studentId, setStudentId] = useState('');

  // Extract day name and date from selectedDay
  const dayName = selectedDay?.day || '';
  const date = selectedDay?.date || null;

  // Format date for display
  const formatDisplayDate = (date) => {
    if (!date) return '';
    return date instanceof Date ? date.toLocaleDateString('en-GB') : '';
  };

  // Format date for API in YYYY-MM-DD format
  const formatApiDate = (date) => {
    if (!date) return '';
    return date instanceof Date ? date.toISOString().split('T')[0] : '';
  };

  const handleSubmit = () => {
    if (!timeSlot || !dishType || !studentId || !date) {
      alert('Please fill in all fields.');
      return;
    }

    // Create reservation data object
    const reservationData = {
      restaurantId,
      studentId,
      date: formatApiDate(date), // Send formatted date to API
      timeSlot,
      dishType,
      dayOfWeek: dayName
    };

    onSubmit(reservationData);
    resetForm();
    onClose();
  };

  const resetForm = () => {
    setTimeSlot('');
    setDishType('');
    setStudentId('');
  };

  return (
    <Modal open={open} onClose={onClose}>
      <Box
        sx={{
          position: 'absolute',
          top: '50%',
          left: '50%',
          transform: 'translate(-50%, -50%)',
          width: 400,
          bgcolor: 'background.paper',
          borderRadius: 2,
          boxShadow: 24,
          p: 4,
        }}
      >
        <Typography variant="h6" sx={{ mb: 2 }}>
          Book a Meal
        </Typography>
        {date && (
          <Typography variant="body1" sx={{ mb: 2, fontWeight: 'bold' }}>
            {dayName} - {date.toLocaleDateString('en-GB')}
          </Typography>
        )}
        <FormControl fullWidth sx={{ mb: 2 }}>
          <InputLabel id="time-slot-label">Select Time</InputLabel>
          <Select
            labelId="time-slot-label"
            value={timeSlot}
            onChange={(e) => setTimeSlot(e.target.value)}
            label="Select Time"
          >
            <MenuItem value="LUNCH">Lunch</MenuItem>
            <MenuItem value="DINNER">Dinner</MenuItem>
          </Select>
        </FormControl>
        <FormControl fullWidth sx={{ mb: 2 }}>
          <InputLabel id="dish-type-label">Select Dish</InputLabel>
          <Select
            labelId="dish-type-label"
            value={dishType}
            onChange={(e) => setDishType(e.target.value)}
            label="Select Dish"
          >
            <MenuItem value="MEAT">Meat</MenuItem>
            <MenuItem value="FISH">Fish</MenuItem>
            <MenuItem value="VEGETARIAN">Vegetarian</MenuItem>
          </Select>
        </FormControl>
        <TextField
          fullWidth
          label="Student ID"
          value={studentId}
          onChange={(e) => setStudentId(e.target.value)}
          sx={{ mb: 2 }}
        />
        <Box sx={{ display: 'flex', justifyContent: 'space-between' }}>
          <Button variant="outlined" onClick={onClose}>
            Cancel
          </Button>
          <Button variant="contained" onClick={handleSubmit}>
            Submit
          </Button>
        </Box>
      </Box>
    </Modal>
  );
};

export default BookingModal;