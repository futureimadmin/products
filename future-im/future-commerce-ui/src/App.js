import React from 'react';
import { Route, Routes } from 'react-router-dom';
import Header from './components/Header';
import Welcome from './components/welcome/Welcome';
import About from './components/about/About';
import Contact from './components/contact/Contact';
import Services from './components/services/Services';
import Offers from './components/offers/Offers';
import Login from './components/auth/Login';
import Register from './components/auth/Register';
import Marketplace from './components/marketplace/Marketplace';
import Profile from './components/profile/Profile';
import './App.css';

function App() {
  return (
    <div className="App">
      {/*Header />*/}
      <Routes>
        <Route path="/" element={<Welcome />} />
        <Route path="/about" element={<About />} />
        <Route path="/contact" element={<Contact />} />
        <Route path="/services" element={<Services />} />
        <Route path="/offers" element={<Offers />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/marketplace" element={<Marketplace />} />
        <Route path="/profile" element={<Profile />} />
      </Routes>
      <footer className="footer">
        <p>&copy; 2025 Future IM. All rights reserved.</p>
      </footer>
    </div>
  );
}

export default App;
