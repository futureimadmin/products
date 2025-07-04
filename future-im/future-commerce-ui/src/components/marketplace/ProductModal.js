import React from 'react';
import Carousel from './Carousel';
import './ProductModal.css';

const ProductModal = ({ product, onClose, onAddToCart, onBuyNow }) => {
  if (!product) return null;

  const handleAddToCart = (e) => {
    e.preventDefault();
    e.stopPropagation();
    console.log('Add to cart clicked for product:', product.name);
    onAddToCart(product);
  };

  const handleBuyNow = (e) => {
    e.preventDefault();
    e.stopPropagation();
    console.log('Buy now clicked for product:', product.name);
    onBuyNow(product);
  };

  return (
    <div className="product-modal-overlay" onClick={onClose}>
      <div className="product-modal" onClick={e => e.stopPropagation()}>
        <button className="close-button" onClick={onClose}>×</button>
        
        <div className="product-modal-content">
          {product.imageUrls && product.imageUrls.length > 0 && (
            <div className="product-modal-images">
              <Carousel images={product.imageUrls} name={product.name} />
            </div>
          )}
          
          <div className="product-modal-info">
            {product.brand && (
              <div className="product-brand">
                <img
                  src={product.brand.logoUrl || '/images/placeholders/placeholder-brand.svg'}
                  alt={product.brand.name}
                  className="brand-logo"
                  onError={(e) => {
                    e.target.onerror = null;
                    e.target.src = '/images/placeholders/placeholder-brand.svg';
                  }}
                />
                <span>{product.brand.name}</span>
              </div>
            )}
            
            <h2 className="product-name">{product.name}</h2>
            <p className="product-description">{product.description}</p>
            
            <div className="product-details">
              <div className="product-price">
                {product.discountPrice ? (
                  <>
                    <span className="original-price">₹{product.price}</span>
                    <span className="discount-price">₹{product.discountPrice}</span>
                  </>
                ) : (
                  <span className="price">₹{product.price}</span>
                )}
              </div>
              
              {product.stockQuantity > 0 ? (
                <div className="stock-info in-stock">
                  In Stock ({product.stockQuantity} available)
                </div>
              ) : (
                <div className="stock-info out-of-stock">
                  Out of Stock
                </div>
              )}
            </div>

            {product.specifications && (
              <div className="product-specifications">
                <h3>Specifications</h3>
                <ul>
                  {Object.entries(product.specifications).map(([key, value]) => (
                    <li key={key}>
                      <span className="spec-label">{key}:</span>
                      <span className="spec-value">{value}</span>
                    </li>
                  ))}
                </ul>
              </div>
            )}

            <div className="product-actions">
              {product.stockQuantity > 0 ? (
                <>
                  <button className="add-to-cart-button" onClick={handleAddToCart}>
                    Add to Cart
                  </button>
                  <button className="buy-now-button" onClick={handleBuyNow}>
                    Buy Now
                  </button>
                </>
              ) : (
                <button className="notify-button">
                  Notify When Available
                </button>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductModal;
