import React from 'react';
import '../../styles/global.css';

const Products = () => {
  const products = [
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
      name: 'PaymentHub',
      tag: 'Payment Solution',
      shortDescription: 'Unified Payment Processing Platform',
      description: 'A comprehensive payment processing solution that supports multiple payment methods and protocols.',
      features: [
        {
          title: 'Multi-Protocol Support',
          description: 'Support for various payment protocols including ISO20022, SWIFT, and domestic payment systems.'
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
    },
    {
      id: 3,
      name: 'DigitalCore',
      tag: 'Core Banking',
      shortDescription: 'Modern Core Banking Platform',
      description: 'Next-generation core banking platform built for the digital age.',
      features: [
        {
          title: 'Cloud-Native',
          description: 'Built from the ground up for cloud deployment with containerization and orchestration.'
        },
        {
          title: 'Event-Driven',
          description: 'Event-driven architecture enabling real-time processing and integration.'
        },
        {
          title: 'Scalable Design',
          description: 'Horizontally scalable architecture supporting millions of accounts and transactions.'
        }
      ]
    },
    {
      id: 4,
      name: 'VoiceCommerce',
      tag: 'Voice-Enabled E-Commerce',
      shortDescription: 'Next-Generation Voice Shopping Experience',
      description: 'VoiceCommerce revolutionizes online shopping by integrating advanced voice recognition technology with existing e-commerce platforms, making shopping as natural as having a conversation.',
      features: [
        {
          title: 'Voice Search Integration',
          description: 'Natural language processing for intuitive product search and filtering.'
        },
        {
          title: 'Voice-Enabled Shopping Cart',
          description: 'Add, remove, and modify cart items using voice commands.'
        },
        {
          title: 'Voice Payment Processing',
          description: 'Secure voice-authenticated payment processing and order confirmation.'
        },
        {
          title: 'Multi-Platform Support',
          description: 'Seamless integration with existing e-commerce platforms and mobile apps.'
        }
      ]
    }
  ];

  return (
    <div className="products-container">
      <h1>Our Products</h1>
      
      <div className="product-grid">
        {products.map(product => (
          <div key={product.id} className="product-card">
            <div className="product-header">
              <h2>{product.name}</h2>
              <span className="product-tag">{product.tag}</span>
            </div>
            <div className="product-content">
              <h3>{product.shortDescription}</h3>
              <p>{product.description}</p>
              
              <div className="features-section">
                <h4>Key Features</h4>
                <ul>
                  {product.features.map((feature, index) => (
                    <li key={index}>
                      <strong>{feature.title}</strong>
                      <p>{feature.description}</p>
                    </li>
                  ))}
                </ul>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Products;
