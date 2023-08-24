import './App.css';
import React, { useState, useRef } from 'react';
//import { BrowserRouter, Route, Switch } from "react-router-dom";
import { BrowserRouter as Router, Routes, Route, BrowserRouter, Navigate } from 'react-router-dom';

import './registerWindow/RegisterWindow';
import './loginWindow/LoginWindow';
import './chatWindow/ChatWindow';

import RegisterWindow from './registerWindow/RegisterWindow';
import LoginWindow from './loginWindow/LoginWindow';
import ChatWindow from './chatWindow/ChatWindow';
import { useLocation } from 'react-router-dom';




function App() {
  
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  
  

  return (

    
    <BrowserRouter>
    <Routes>
      <Route path="/" element={<RegisterWindow />} />
      <Route path="/login" element={<LoginWindow setIsLoggedIn={setIsLoggedIn}   />} />
      {isLoggedIn ? (
          <Route path="/chat" element={<ChatWindow />} />
        ) : (
          <Route path="/chat" element={<Navigate to="/login" />} />
        )}
    </Routes>
   </BrowserRouter>

  );
}

export default App;
