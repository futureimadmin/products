import React from 'react';
import '../../styles/global.css';

const Offers = () => {
  const offers = [
    {
      id: 1,
      name: 'OpenFin',
      tag: 'BIAN Implementation',
      shortDescription: 'Reference Implementation of BIAN Specification',
      description: 'OpenFin is our flagship banking architecture solution that implements BIAN (Banking Industry Architecture Network) specifications to deliver a robust, standardized banking platform.',
      features: [
        {
          title: 'Service Domain Implementation',
          description: 'Complete implementation of BIAN service domains including Customer Management, Product Management, and Payment Processing.'
        },
        {
          title: 'Modular Architecture',
          description: 'Microservices-based architecture allowing banks to implement specific BIAN service domains incrementally.'
        },
        {
          title: 'API-First Design',
          description: 'RESTful APIs following BIAN standards for seamless integration with existing systems.'
        }
      ]
    },
    {
      id: 2,
      name: 'DigitalCore',
      tag: 'Core Banking',
      shortDescription: 'Modern Banking Platform',
      description: 'Next-generation core banking system for digital transformation.',
      features: [
        {
          title: 'Account Management',
          description: 'Comprehensive account management and processing capabilities.'
        },
        {
          title: 'API Integration',
          description: 'Open banking APIs for third-party integration.'
        },
        {
          title: 'Regulatory Compliance',
          description: 'Built-in compliance with banking regulations.'
        }
      ]
    },
    {
      id: 3,
      name: 'VoiceCommerce',
      tag: 'Voice Technology',
      shortDescription: 'Voice-Enabled Commerce',
      description: 'Revolutionary voice-controlled e-commerce platform.',
      features: [
        {
          title: 'Voice Recognition',
          description: 'Advanced voice recognition and natural language processing.'
        },
        {
          title: 'Smart Recommendations',
          description: 'AI-powered product recommendations based on voice interactions.'
        },
        {
          title: 'Multi-Platform Support',
          description: 'Seamless integration with existing e-commerce platforms and mobile apps.'
        }
      ]
    },
    {
      id: 4,
      name: 'PaymentHub',
      tag: 'Payment Solutions',
      shortDescription: 'Unified Payment Platform',
      description: 'A comprehensive payment processing and management solution.',
      features: [
        {
          title: 'Multi-Currency Support',
          description: 'Process payments in multiple currencies with real-time conversion.'
        },
        {
          title: 'Payment Analytics',
          description: 'Advanced analytics and reporting for payment transactions.'
        },
        {
          title: 'Fraud Prevention',
          description: 'AI-powered fraud detection and prevention system.'
        },
        {
          title: 'Real-time Processing',
          description: 'High-performance transaction processing with real-time settlement capabilities.'
        },
        {
          title: 'Compliance Built-in',
          description: 'Built-in compliance checks for AML, sanctions screening, and regulatory reporting.'
        }
      ]
    }
  ];

  return (
    <div className="offers-container">
      <h1>Our Offers</h1>
      <div className="offers-grid">
        {offers.map(offer => (
          <div key={offer.id} className="offer-card">
            <div className="offer-header">
              <h2>{offer.name}</h2>
              <span className="offer-tag">{offer.tag}</span>
            </div>
            <div className="offer-content">
              <h3>{offer.shortDescription}</h3>
              <p>{offer.description}</p>
              
              <div className="features-section">
                <h4>Key Features</h4>
                <div className="approach-steps">
                  {offer.features.map((feature, index) => (
                    <div className="step" key={index}>
                      <div className="feature-header">
                        <strong>{feature.title}</strong>
                      </div>
                      <p>{feature.description}</p>
                    </div>
                  ))}
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Offers;
