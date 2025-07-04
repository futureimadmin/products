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
      <div className="header-right">
        {user ? (
          <div className="user-profile-menu">
            <div className="welcome-message">
              Welcome, <span>{user.firstName}</span>
            </div>
            <div className="cart-wrapper">
              <Link to="/cart" className="cart-icon">
                <i className="fas fa-shopping-cart"></i>
                {cartItemCount > 0 && <span className="cart-count">{cartItemCount}</span>}
              </Link>
            </div>
            <div className="profile-trigger">
              <div className="user-avatar">
                {user.firstName.charAt(0).toUpperCase()}
              </div>
            </div>
            <div className="dropdown-menu">
              <div className="menu-section">
                <div className="menu-item has-submenu">
                  <span>My Profile</span>
                  <div className="submenu">
                    <Link to="/profile#account">My Account</Link>
                    <Link to="/profile#orders">Order History</Link>
                    <Link to="/profile#browsed">Frequently Browsed</Link>
                    <Link to="/profile#wishlist">My Wishlist</Link>
                    <Link to="/profile#history">My History</Link>
                    <Link to="/profile#security">Security</Link>
                  </div>
                </div>
                <Link to="/service-requests" className="menu-item">Service Requests</Link>
                <div 
                  className="menu-item"
                  onClick={() => {
                    localStorage.removeItem('user');
                    localStorage.removeItem('accessToken');
                    localStorage.removeItem('refreshToken');
                    window.location.reload();
                  }}
                >
                  Logout
                </div>
              </div>
            </div>
          </div>
        ) : (
          <Link to="/login" className="auth-button">Login</Link>
        )}
      </div>
    </header>
  );
};

export default Header;
