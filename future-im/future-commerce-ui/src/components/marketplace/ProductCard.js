import React from 'react';
import './ProductCard.css';

const ProductCard = ({
  product,
  onSelect,
  onAddToCart,
  onBuyNow,
  isSelected,
  onCheckboxChange
}) => {
  const handleImageClick = () => {
    onSelect(product);
  };

  const handleAddToCart = (e) => {
    e.stopPropagation();
    onAddToCart(product);
  };

  const handleBuyNow = (e) => {
    e.stopPropagation();
    onBuyNow(product);
  };

  return (
    <div className="product-card">
      <div className="product-selection">
        <input
          type="checkbox"
          checked={isSelected}
          onChange={(e) => onCheckboxChange(e.target.checked)}
          onClick={(e) => e.stopPropagation()}
        />
      </div>

      <div className="product-image-container" onClick={handleImageClick}>
        {product.imageUrls && product.imageUrls.length > 0 ? (
          <img
            src={product.imageUrls[0]}
            alt={product.name}
            className="product-image"
            onError={(e) => {
              e.target.onerror = null;
              e.target.src = '/images/placeholders/placeholder-product.svg';
            }}
          />
        ) : (
          <img
            src="/images/placeholders/placeholder-product.svg"
            alt={product.name}
            className="product-image"
          />
        )}
      </div>

      <div className="product-info">
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
        <h3 className="product-name">{product.name}</h3>
        <p className="product-description">{product.description}</p>
        <div className="product-footer">
          <div className="product-price">
            {product.discountPrice ? (
              <>
                <span className="original-price">₹{product.price}</span>
                <span className="discount-price">₹{product.discountPrice}</span>
              </>
            ) : (
              <span>₹{product.price}</span>
            )}
          </div>
          <div className="product-actions">
            {product.stockQuantity > 0 ? (
              <>
                <button
                  className="add-to-cart-button"
                  onClick={() => onAddToCart(product)}
                >
                  <i className="fas fa-shopping-cart"></i>
                  Cart
                </button>
                <button
                  className="buy-now-button"
                  onClick={() => onBuyNow(product)}
                >
                  <i className="fas fa-bolt"></i>
                  Buy
                </button>
              </>
            ) : (
              <span className="out-of-stock">Out of Stock</span>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductCard;
