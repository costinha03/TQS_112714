import React from 'react';
import { Modal, Box, Typography, Button } from '@mui/material';

const ResponseModal = ({ open, onClose, title, message }) => {
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
          {title}
        </Typography>
        <Box sx={{ mb: 2 }}>
          {message.split('\n').map((line, index) => {
            const [key, ...rest] = line.split(':');
            const value = rest.join(':').trim();
            return (
              line.trim() && (
                <Typography key={index} variant="body1" sx={{ mb: 1 }}>
                  <strong>{key}:</strong> {value}
                </Typography>
              )
            );
          })}
        </Box>
        <Box sx={{ display: 'flex', justifyContent: 'center' }}>
          <Button variant="contained" onClick={onClose}>
            Close
          </Button>
        </Box>
      </Box>
    </Modal>
  );
};

export default ResponseModal;