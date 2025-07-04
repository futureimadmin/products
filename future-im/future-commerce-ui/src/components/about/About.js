import React from 'react';
import '../../styles/global.css';

const About = () => {
  return (
    <div className="about-container">
      <h1>About Future IM</h1>
      <div className="about-content">
        <div className="approach-steps">
          <div className="step">
            <h2>Our Story</h2>
            <p>
              Future IM is a cutting-edge technology company specializing in transformative solutions
              for the banking and e-commerce sectors. Founded with a vision to bridge the gap between
              traditional banking systems and modern digital commerce, we combine industry expertise
              with innovative technology to create solutions that shape the future of digital transactions.
            </p>
          </div>
          <div className="step">
            <h2>Our Mission</h2>
            <p>
              We strive to make online shopping accessible to everyone through innovative voice technology and
              user-friendly interfaces. Our mission is to create a seamless shopping experience that adapts to
              our users' needs and preferences.
            </p>
          </div>
          <div className="step">
            <h2>Our Vision</h2>
            <p>
              To be the global leader in integrated banking and e-commerce solutions, setting
              new standards for digital transformation and customer engagement.
            </p>
          </div>
          <div className="step">
            <h2>Key Features</h2>
            <ul>
              <li>Voice-enabled product search</li>
              <li>Intelligent category navigation</li>
              <li>Real-time voice recognition</li>
              <li>Secure payment processing</li>
              <li>Personalized shopping experience</li>
            </ul>
          </div>
        </div>

        <section className="values-section">
          <h2>Our Expertise</h2>
          <div className="expertise-grid">
            <div className="expertise-card">
              <h3>Banking Architecture</h3>
              <p>As certified BIAN (Banking Industry Architecture Network) implementation experts,
                we specialize in creating standardized, efficient, and future-ready banking systems.</p>
            </div>
            <div className="expertise-card">
              <h3>Voice Commerce</h3>
              <p>Pioneers in voice-enabled e-commerce solutions, making online shopping more
                intuitive and accessible through advanced voice recognition technology.</p>
            </div>
            <div className="expertise-card">
              <h3>Digital Transformation</h3>
              <p>Expert consultants helping businesses navigate their digital transformation
                journey with customized strategies and solutions.</p>
            </div>
            <div className="expertise-card">
              <h3>Innovation</h3>
              <p>Continuous investment in R&D to develop cutting-edge solutions that address
                emerging market needs and technological advances.</p>
            </div>
          </div>
        </section>
        <section className="values-section">
          <h2>Our Core Values</h2>
          <div className="values-grid">
            <div className="value-item">
              <h3>Innovation</h3>
              <p>Constantly pushing boundaries to create groundbreaking solutions</p>
            </div>
            <div className="value-item">
              <h3>Excellence</h3>
              <p>Maintaining the highest standards in everything we do</p>
            </div>
            <div className="value-item">
              <h3>Integrity</h3>
              <p>Operating with transparency and ethical principles</p>
            </div>
            <div className="value-item">
              <h3>Customer Focus</h3>
              <p>Putting our clients' success at the heart of our business</p>
            </div>
          </div>
        </section>

        <section className="why-choose-section">
          <h2>Why Choose Future IM</h2>
          <ul className="benefits-list">
            <li>
              <strong>Industry Expertise</strong>
              <p>Deep understanding of banking and e-commerce domains</p>
            </li>
            <li>
              <strong>Innovation Leadership</strong>
              <p>Cutting-edge solutions powered by latest technologies</p>
            </li>
            <li>
              <strong>Proven Track Record</strong>
              <p>Successful implementations across various industries</p>
            </li>
            <li>
              <strong>Global Reach</strong>
              <p>Serving clients worldwide with localized solutions</p>
            </li>
          </ul>
        </section>

        <section className="commitment-section">
          <h2>Our Commitment</h2>
          <p>At Future IM, we are committed to driving digital innovation while maintaining
            the highest standards of security, reliability, and customer service. Our team of
            experts works tirelessly to ensure that our solutions not only meet but exceed
            our clients' expectations, helping them stay ahead in the rapidly evolving
            digital landscape.</p>
        </section>
      </div>
    </div>
  );
};

export default About;
