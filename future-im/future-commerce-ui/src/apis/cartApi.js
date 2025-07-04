import axios from 'axios';

const BASE_URL = 'http://localhost:8080/vcom/api/v1';

export const cartApi = {
  addToCart: (productId, quantity = 1) => 
    axios.post(`${BASE_URL}/cart/items`, {
      productId,
      quantity
    }),
  getCart: () => axios.get(`${BASE_URL}/cart`),
  updateQuantity: (itemId, quantity) => 
    axios.put(`${BASE_URL}/cart/items/${itemId}`, {
      quantity
    }),
  removeFromCart: (itemId) => 
    axios.delete(`${BASE_URL}/cart/items/${itemId}`)
};
