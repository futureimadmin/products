import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/vcom/api/v1';
const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
    'Accept': 'application/json'
  },
  withCredentials: true
});

export const categoryApi = {
  getRootCategories: () =>
    api.get('/categories/root'),
  
  getCategoryTree: () =>
    api.get('/categories/tree'),
  
  getCategory: (id) =>
    api.get(`/categories/${id}`),
  
  getSubcategories: (id) =>
    api.get(`/categories/${id}/subcategories`),
  
  getCategoryPath: (id) =>
    api.get(`/categories/${id}/path`),
  
  searchCategories: (query, page = 0, size = 20) =>
    api.get(`/categories/search?query=${query}&page=${page}&size=${size}`),
};
