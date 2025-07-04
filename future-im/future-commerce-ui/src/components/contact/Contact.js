import React from 'react';
import '../../styles/global.css';

const Contact = () => {
  return (
    <div className="about-container">
      <h1>Contact Future IM</h1>
      <div className="about-content">
        <div className="approach-steps">
          <div className="step">
            <h2>Phone</h2>
            <p>
              +91 9052277992
            </p>
          </div>
          <div className="step">
            <h2>Email</h2>
            <p>
              sales@futureim.in
            </p>
          </div>
          <div className="step">
            <h2>Address</h2>
            <p>
              2/100/138/2, Plot No 139SP, Thirumala Residency Colony
              Road No 2, GSI Bandlaguda, Hyderabad, Telangana 500068
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Contact;