import axios from 'axios';

const BASE_URL = 'http://localhost:8080/vcom/api/v1';

export const productApi = {
  searchProducts: (query, page = 0, size = 20) => 
    axios.get(`${BASE_URL}/products/search`, {
      params: { query, page, size }
    }),
  getProduct: (id) => axios.get(`${BASE_URL}/products/${id}`),
  getAvailableProducts: (page = 0, size = 20) => 
    axios.get(`${BASE_URL}/products/available`, {
      params: { page, size }
    })
};
