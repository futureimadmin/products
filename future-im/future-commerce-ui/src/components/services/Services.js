import React from 'react';
import '../../styles/global.css';

const Services = () => {
  return (
    <div className="services-container">
      <h1>Our Consulting Services</h1>
      <div className="services-grid">
        <section className="banking-section">
          <h2>Banking Solutions with BIAN Architecture</h2>
          <p>Future IM specializes in implementing BIAN (Banking Industry Architecture Network)
          standards to deliver cutting-edge banking solutions.</p>

          <div className="bian-domains">
            <h3>Core Banking Domains We Cover:</h3>
            <div className="approach-steps">
              <div className="step">
                <h4>Payments & Settlements</h4>
                <p>Implementation of payment systems following BIAN service domains including Payment Execution,
                Clearing & Settlement, and Payment Order management.</p>
              </div>
              <div className="step">
                <h4>Customer Management</h4>
                <p>Customer relationship management solutions aligned with BIAN's Party Reference Data Management
                and Customer Agreement frameworks.</p>
              </div>
              <div className="step">
                <h4>Product Management</h4>
                <p>Banking product lifecycle management using BIAN's Product Design and Product Directory services.</p>
              </div>
            </div>
          </div>

          <div className="bian-benefits">
            <h3>Benefits of Our BIAN Implementation:</h3>
            <ul>
              <li>Standardized banking service architecture</li>
              <li>Reduced integration complexity</li>
              <li>Enhanced interoperability</li>
              <li>Future-proof banking solutions</li>
              <li>Accelerated digital transformation</li>
            </ul>
          </div>
        </section>

        <section className="ecommerce-section">
          <h2>E-Commerce Solutions</h2>
          <p>Our e-commerce consulting services help businesses build robust online retail platforms.</p>

          <div className="ecommerce-services">
            <h3>Key E-Commerce Services:</h3>
            <div className="approach-steps">
              <div className="step">
                <h4>Digital Payment Integration</h4>
                <p>Seamless integration of multiple payment gateways and digital wallets.</p>
              </div>
              <div className="step">
                <h4>AI-Powered Solutions</h4>
                <p>Implementation of AI for personalized shopping experiences, inventory management,
                and demand forecasting.</p>
              </div>
              <div className="step">
                <h4>Voice Commerce</h4>
                <p>Voice-enabled shopping experiences and voice-based customer service solutions.</p>
              </div>
            </div>
          </div>
        </section>

        <section className="consulting-approach">
          <h2>Our Consulting Approach</h2>
          <div className="approach-steps">
            <div className="step">
              <h4>1. Assessment</h4>
              <p>Thorough analysis of current systems and requirements</p>
            </div>
            <div className="step">
              <h4>2. Strategy Development</h4>
              <p>Custom solution design based on BIAN standards and modern e-commerce practices</p>
            </div>
            <div className="step">
              <h4>3. Implementation</h4>
              <p>Systematic execution with continuous stakeholder engagement</p>
            </div>
            <div className="step">
              <h4>4. Support & Evolution</h4>
              <p>Ongoing support and system evolution guidance</p>
            </div>
          </div>
        </section>
      </div>
    </div>
  );
};

export default Services;
