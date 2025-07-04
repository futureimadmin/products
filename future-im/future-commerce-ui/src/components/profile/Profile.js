import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import '../../styles/global.css';

const Profile = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [user, setUser] = useState(null);
  const [activeSection, setActiveSection] = useState('account');

  useEffect(() => {
    const userStr = localStorage.getItem('user');
    if (!userStr) {
      navigate('/login');
      return;
    }
    
    const userData = JSON.parse(userStr);
    setUser(userData);

    // Get section from URL hash
    const hash = location.hash.replace('#', '');
    if (hash) {
      setActiveSection(hash);
      // Scroll to section
      const element = document.getElementById(hash);
      if (element) {
        element.scrollIntoView({ behavior: 'smooth' });
      }
    }
  }, [navigate, location]);

  const sections = [
    {
      id: 'account',
      title: 'My Account',
      content: (
        <div className="profile-section">
          <h3>Account Information</h3>
          {user && (
            <div className="account-details">
              <div className="detail-row">
                <span className="label">Name:</span>
                <span>{user.firstName} {user.lastName}</span>
              </div>
              <div className="detail-row">
                <span className="label">Email:</span>
                <span>{user.email}</span>
              </div>
              <div className="detail-row">
                <span className="label">Member Since:</span>
                <span>{new Date(user.createdAt).toLocaleDateString()}</span>
              </div>
            </div>
          )}
        </div>
      )
    },
    {
      id: 'orders',
      title: 'Order History',
      content: (
        <div className="profile-section">
          <h3>Your Orders</h3>
          <div className="placeholder-content">
            <p>No orders found. Start shopping to see your order history here.</p>
          </div>
        </div>
      )
    },
    {
      id: 'browsed',
      title: 'Frequently Browsed Products',
      content: (
        <div className="profile-section">
          <h3>Recently Viewed Items</h3>
          <div className="placeholder-content">
            <p>Browse products to see your viewing history here.</p>
          </div>
        </div>
      )
    },
    {
      id: 'wishlist',
      title: 'My Wishlist',
      content: (
        <div className="profile-section">
          <h3>Saved Items</h3>
          <div className="placeholder-content">
            <p>Your wishlist is empty. Save items to view them here.</p>
          </div>
        </div>
      )
    },
    {
      id: 'history',
      title: 'My History',
      content: (
        <div className="profile-section">
          <h3>Activity History</h3>
          <div className="placeholder-content">
            <p>Your recent activity will appear here.</p>
          </div>
        </div>
      )
    },
    {
      id: 'security',
      title: 'Security',
      content: (
        <div className="profile-section">
          <h3>Security Settings</h3>
          <div className="security-options">
            <div className="security-option">
              <h4>Change Password</h4>
              <button className="secondary-button">Update Password</button>
            </div>
            <div className="security-option">
              <h4>Two-Factor Authentication</h4>
              <button className="secondary-button">Enable 2FA</button>
            </div>
          </div>
        </div>
      )
    }
  ];

  return (
    <div className="profile-container">
      <div className="profile-sidebar">
        <div className="profile-nav">
          {sections.map(section => (
            <a
              key={section.id}
              href={`#${section.id}`}
              className={`profile-nav-item ${activeSection === section.id ? 'active' : ''}`}
              onClick={(e) => {
                e.preventDefault();
                setActiveSection(section.id);
                navigate(`/profile#${section.id}`);
                document.getElementById(section.id).scrollIntoView({ behavior: 'smooth' });
              }}
            >
              {section.title}
            </a>
          ))}
        </div>
      </div>
      <div className="profile-content">
        {sections.map(section => (
          <div
            key={section.id}
            id={section.id}
            className={`profile-section-container ${activeSection === section.id ? 'active' : ''}`}
          >
            {section.content}
          </div>
        ))}
      </div>
    </div>
  );
};

export default Profile;
