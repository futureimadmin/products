import React, { useState, useEffect, useCallback } from 'react';
import './Carousel.css';

const Carousel = ({ images, name }) => {
  const [currentIndex, setCurrentIndex] = useState(0);
  const [isPlaying, setIsPlaying] = useState(false);

  const nextImage = useCallback(() => {
    setCurrentIndex((prevIndex) => (prevIndex + 1) % images.length);
  }, [images.length]);

  const previousImage = () => {
    setCurrentIndex((prevIndex) => (prevIndex - 1 + images.length) % images.length);
  };

  const goToImage = (index) => {
    setCurrentIndex(index);
    setIsPlaying(false);
  };

  useEffect(() => {
    let intervalId;
    if (isPlaying) {
      intervalId = setInterval(nextImage, 3000);
    }
    return () => {
      if (intervalId) {
        clearInterval(intervalId);
      }
    };
  }, [isPlaying, nextImage]);

  return (
    <div className="carousel">
      <div className="carousel-image-container">
        <img
          src={images[currentIndex]}
          alt={`${name} - Image ${currentIndex + 1}`}
          className="carousel-image"
          onError={(e) => {
            e.target.onerror = null;
            e.target.src = '/images/placeholders/placeholder-product.svg';
          }}
        />
        <button
          className="carousel-control prev"
          onClick={previousImage}
          aria-label="Previous image"
        >
          ‹
        </button>
        <button
          className="carousel-control next"
          onClick={nextImage}
          aria-label="Next image"
        >
          ›
        </button>
      </div>
      <div className="carousel-controls">
        <button
          className={`carousel-control ${isPlaying ? 'playing' : ''}`}
          onClick={() => setIsPlaying(!isPlaying)}
          aria-label={isPlaying ? 'Pause slideshow' : 'Play slideshow'}
        >
          {isPlaying ? '⏸' : '▶'}
        </button>
      </div>
      <div className="carousel-dots">
        {images.map((_, index) => (
          <button
            key={index}
            className={`carousel-dot ${index === currentIndex ? 'active' : ''}`}
            onClick={() => goToImage(index)}
            aria-label={`Go to image ${index + 1}`}
          />
        ))}
      </div>
    </div>
  );
};

export default Carousel;
