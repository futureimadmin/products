import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import logo from '../logo.jpeg';
import '../styles/global.css';

const Header = () => {
  const [user, setUser] = useState(null);
  const [cartItemCount, setCartItemCount] = useState(0);

  const updateCartCount = () => {
    const cart = JSON.parse(localStorage.getItem('cart') || '[]');
    setCartItemCount(cart.length);
  };

  useEffect(() => {
    updateCartCount();
    window.addEventListener('cartUpdated', updateCartCount);
    
    return () => {
      window.removeEventListener('cartUpdated', updateCartCount);
    };
  }, []);

  useEffect(() => {
    const checkUser = () => {
      const userStr = localStorage.getItem('user');
      if (userStr) {
        try {
          const userData = JSON.parse(userStr);
          setUser(userData);
        } catch (error) {
          console.error('Error parsing user data:', error);
        }
      } else {
        setUser(null);
      }
    };

    checkUser();

    // Listen for both storage changes and custom login event
    window.addEventListener('storage', checkUser);
    window.addEventListener('userLogin', checkUser);

    return () => {
      window.removeEventListener('storage', checkUser);
      window.removeEventListener('userLogin', checkUser);
    };
  }, []);

  return (
    <header className="header">
      <div className="header-left">
        <Link to="/" className="logo">
          <img src={logo} alt="Future IM Logo" />
          <span className="logo-text">Future IM</span>
        </Link>
      </div>
      <nav className="nav-links">
        {user && <Link to="/marketplace">Marketplace</Link>}
        <Link to="/services">Services</Link>
        <Link to="/offers">Offers</Link>
        <Link to="/about">About</Link>
        <Link to="/contact">Contact</Link>
      </nav>
    </header>
  );
};

export default Header;
