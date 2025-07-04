import React, { useState, useEffect, useCallback, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import { productApi, categoryApi } from '../apis/api';
import microphoneIcon from '../assets/microphone.svg';
import Carousel from './Carousel';
import ProductCard from './ProductCard';
import ProductModal from './ProductModal';
import './Marketplace.css';

const Marketplace = () => {
  const navigate = useNavigate();
  const [isRecording, setIsRecording] = useState(false);
  const [searchText, setSearchText] = useState('');
  const [selectedProduct, setSelectedProduct] = useState(null);
  const [user, setUser] = useState(null);
  const [audioUrl, setAudioUrl] = useState(null);
  const [products, setProducts] = useState([]);
  const [recordingStatus, setRecordingStatus] = useState('');
  const [categories, setCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [page, setPage] = useState(0);
  const [hasMore, setHasMore] = useState(true);
  const [categoryPath, setCategoryPath] = useState([]);
  const [cartItems, setCartItems] = useState([]);
  const [selectedProductIds, setSelectedProductIds] = useState(new Set());
  const mediaRecorder = useRef(null);
  const audioChunks = useRef([]);
  const audioRef = useRef(null);
  let pauseTimeout = null;

  const handleProductSelect = useCallback((product) => {
    setSelectedProduct(product);
  }, []);

  const handleAddToCart = useCallback((product) => {
    console.log('Adding to cart:', product);
    setCartItems(prev => {
      const newItems = [...prev, product];
      // Store in localStorage
      localStorage.setItem('cart', JSON.stringify(newItems));
      // Dispatch cartUpdated event
      window.dispatchEvent(new Event('cartUpdated'));
      console.log('Updated cart items:', newItems);
      return newItems;
    });
  }, []);

  const handleBuyNow = useCallback((product) => {
    // TODO: Implement direct purchase flow
    console.log('Buy now:', product);
  }, []);

  const handleLogout = useCallback(() => {
    localStorage.removeItem('user');
    localStorage.removeItem('accessToken');
    navigate('/login');
  }, [navigate]);

  useEffect(() => {
    const userStr = localStorage.getItem('user');
    const accessToken = localStorage.getItem('accessToken');
    
    if (!userStr || !accessToken) {
      navigate('/login');
      return;
    }

    try {
      const userData = JSON.parse(userStr);
      setUser(userData);
      loadCategories();
      loadProducts();
      
      // Initialize cart items from localStorage
      const savedCart = localStorage.getItem('cart');
      if (savedCart) {
        const parsedCart = JSON.parse(savedCart);
        console.log('Initializing cart from localStorage:', parsedCart);
        setCartItems(parsedCart);
      }
    } catch (error) {
      console.error('Error parsing user data or cart:', error);
      navigate('/login');
    }
  }, [navigate]);

  useEffect(() => {
    if (selectedCategory) {
      loadProducts(0, selectedCategory.id);
    } else {
      loadProducts();
    }
  }, [selectedCategory]);

  const startRecording = async () => {
    try {
      setRecordingStatus('Requesting microphone access...');
      const stream = await navigator.mediaDevices.getUserMedia({ audio: true });
      mediaRecorder.current = new MediaRecorder(stream);
      audioChunks.current = [];
      setRecordingStatus('Recording started... Click the microphone again to stop.');

      mediaRecorder.current.ondataavailable = (event) => {
        audioChunks.current.push(event.data);
      };

      mediaRecorder.current.onstop = () => {
        const audioBlob = new Blob(audioChunks.current, { type: 'audio/wav' });
        const url = URL.createObjectURL(audioBlob);
        setAudioUrl(url);
      };

      mediaRecorder.current.start();
      setIsRecording(true);
    } catch (error) {
      console.error('Error accessing microphone:', error);
    }
  };

  const stopRecording = () => {
    if (mediaRecorder.current && mediaRecorder.current.state !== 'inactive') {
      mediaRecorder.current.stop();
      mediaRecorder.current.stream.getTracks().forEach(track => track.stop());
    }
    setIsRecording(false);
    setRecordingStatus('Recording stopped. You can play it back below.');
  };

  const loadCategories = async () => {
    try {
      const response = await categoryApi.getCategoryTree();
      setCategories(response.data.data);
    } catch (err) {
      setError('Failed to load categories');
    }
  };

  const loadProducts = async (pageOverride = null, categoryId = null) => {
    setLoading(true);
    try {
      let response;
      if (categoryId) {
        response = await productApi.getProductsByCategory(
          categoryId,
          true,
          pageOverride !== null ? pageOverride : page,
          20
        );
      } else {
        response = await productApi.getAllProducts(pageOverride !== null ? pageOverride : page, 20);
      }
      
      const { content, last } = response.data.data;
      console.log('Products from API:', content);
      // Pass through the original URLs
      console.log('Products with original URLs:', content);
      setProducts(pageOverride === 0 ? content : [...products, ...content]);
      setHasMore(!last);
    } catch (err) {
      setError('Failed to load products');
      console.error('Error loading products:', err);
    } finally {
      setLoading(false);
    }
  };

  const loadCategoryPath = async () => {
    if (!selectedCategory) return;
    try {
      const response = await categoryApi.getCategoryPath(selectedCategory.id);
      setCategoryPath(response.data.data);
    } catch (err) {
      setError('Failed to load category path');
    }
  };

  const handleCategoryClick = (category) => {
    setSelectedCategory(category);
    setPage(0);
    setProducts([]);
  };

  const loadMore = () => {
    if (!loading && hasMore) {
      setPage(page + 1);
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    productApi.searchProducts(searchText)
      .then(response => {
        setProducts(response.data.data.content);
      })
      .catch(error => {
        console.error('Error searching products:', error);
      });
  };

  const sendAudioToServer = async () => {
    if (!audioUrl) return;

    try {
      const response = await fetch(audioUrl);
      const audioBlob = await response.blob();
      const formData = new FormData();
      formData.append('audio', audioBlob);

      const serverResponse = await fetch('http://localhost:8080/vcom/api/v1/products/audio', {
        method: 'POST',
        body: formData,
      });
      const data = await serverResponse.json();
      setSearchText(data.text || '');
      setProducts(data.products || []);
      setAudioUrl(null); // Clear the audio URL after sending
    } catch (error) {
      console.error('Error processing audio:', error);
    }
  };

  const getCategoryIcon = (categoryName) => {
    const name = categoryName.toLowerCase();
    if (name.includes('electronics')) return 'fas fa-mobile-alt';
    if (name.includes('clothing')) return 'fas fa-tshirt';
    if (name.includes('books')) return 'fas fa-book';
    if (name.includes('home')) return 'fas fa-home';
    if (name.includes('sports')) return 'fas fa-futbol';
    if (name.includes('toys')) return 'fas fa-gamepad';
    if (name.includes('beauty')) return 'fas fa-spa';
    if (name.includes('food')) return 'fas fa-utensils';
    return 'fas fa-tag';
  };

  const renderCategories = (categories) => {
    return (
      <ul className="category-list">
        {categories.map((category) => (
          <li key={category.id} className="category-item">
            <button
              onClick={() => handleCategoryClick(category)}
              className={selectedCategory?.id === category.id ? 'active' : ''}
            >
              <i className={getCategoryIcon(category.name)}></i>
              {category.name}
              {category.subcategories && category.subcategories.length > 0 && (
                <i className="fas fa-chevron-right" style={{ marginLeft: 'auto' }}></i>
              )}
            </button>
            {category.subcategories && category.subcategories.length > 0 && (
              <div className="subcategories">
                {renderCategories(category.subcategories)}
              </div>
            )}
          </li>
        ))}
      </ul>
    );
  };

  const renderProducts = () => {
    if (error) {
      return <div className="error">{error}</div>;
    }

    if (products.length === 0 && !loading) {
      return <div className="no-products">No products found</div>;
    }

    return products.map((product) => (
      <ProductCard
        key={product.id}
        product={product}
        onSelect={handleProductSelect}
        onAddToCart={handleAddToCart}
        onBuyNow={handleBuyNow}
        isSelected={selectedProductIds.has(product.id)}
        onCheckboxChange={(checked) => {
          setSelectedProductIds(prev => {
            const newSet = new Set(prev);
            if (checked) {
              newSet.add(product.id);
            } else {
              newSet.delete(product.id);
            }
            return newSet;
          });
        }}
      />
    ));
  };

  return (
    <div className="marketplace">
      <div className="marketplace-search">
        <form onSubmit={handleSubmit} className="search-box">
          <input
            type="text"
            value={searchText}
            onChange={(e) => setSearchText(e.target.value)}
            placeholder="Search products..."
          />
          <button type="submit">Search</button>
          <button
            type="button"
            className={`record-button ${isRecording ? 'recording' : ''}`}
            onClick={isRecording ? stopRecording : startRecording}
            title={isRecording ? 'Stop Recording' : 'Start Recording'}
          >
            <img src={microphoneIcon} alt="Microphone" />
          </button>
          {recordingStatus && (
            <div className="recording-status">
              {recordingStatus}
              {audioUrl && (
                <div className="audio-controls">
                  <audio ref={audioRef} src={audioUrl} controls className="audio-player" />
                  <button onClick={sendAudioToServer} className="send-button">
                    Send Search Record
                  </button>
                </div>
              )}
            </div>
          )}
        </form>
      </div>

      <div className="marketplace-content">
        <aside className="category-sidebar">
          <h2>Categories</h2>
          {renderCategories(categories)}
        </aside>

        <main className="main-content">
          {categoryPath.length > 0 && (
            <div className="breadcrumb">
              <button onClick={() => handleCategoryClick(null)}>All Products</button>
              {categoryPath.map((cat, index) => (
                <React.Fragment key={cat.id}>
                  <span className="separator">/</span>
                  <button
                    onClick={() => handleCategoryClick(cat)}
                    className={index === categoryPath.length - 1 ? 'active' : ''}
                  >
                    {cat.name}
                  </button>
                </React.Fragment>
              ))}
            </div>
          )}

          <div className="product-grid">
            {renderProducts()}
          </div>

          {loading && <div className="loading">Loading...</div>}
          {hasMore && !loading && (
            <button onClick={loadMore} className="load-more-button">
              Load More
            </button>
          )}
        </main>
      </div>

      {selectedProduct && (
        <ProductModal
          product={selectedProduct}
          onClose={() => setSelectedProduct(null)}
          onAddToCart={handleAddToCart}
          onBuyNow={handleBuyNow}
        />
      )}
    </div>
  );
};

export default Marketplace;
