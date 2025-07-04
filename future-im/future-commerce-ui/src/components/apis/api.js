import axios from 'axios';

const API_BASE_URL = 'http://localhost:8090/vcom/api/v1';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add request interceptor to include auth token
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('accessToken');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Add response interceptor to handle auth errors
api.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    // If error is 401 and we haven't tried to refresh the token yet
    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;

      try {
        // Try to refresh the token
        const refreshToken = localStorage.getItem('refreshToken');
        if (refreshToken) {
          const response = await authApi.refreshToken(refreshToken);
          const { accessToken } = response.data.data;
          
          localStorage.setItem('accessToken', accessToken);
          
          // Retry the original request with new token
          originalRequest.headers.Authorization = `Bearer ${accessToken}`;
          return api(originalRequest);
        }
      } catch (refreshError) {
        // If refresh fails, redirect to login
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        localStorage.removeItem('user');
        window.location.href = '/login?message=Your session has expired. Please login again.';
      }
    }
    return Promise.reject(error);
  }
);

export const authApi = {
  login: (username, password) =>
    api.post('/auth/login', { username, password }),
    
  refreshToken: (refreshToken) =>
    api.post('/auth/refresh', null, {
      headers: {
        Authorization: `Bearer ${refreshToken}`
      }
    }),
};

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

export const productApi = {
  getAllProducts: (page = 0, size = 20) =>
    api.get(`/products?page=${page}&size=${size}`),
  
  getProduct: (id) =>
    api.get(`/products/${id}`),
  
  getProductsByCategory: (categoryId, includeSubcategories = false, page = 0, size = 20) =>
    api.get(`/products/category/${categoryId}?includeSubcategories=${includeSubcategories}&page=${page}&size=${size}`),
  
  searchProducts: (query, page = 0, size = 20) =>
    api.get(`/products/search?query=${query}&page=${page}&size=${size}`),
  
  getAvailableProducts: (page = 0, size = 20) =>
    api.get(`/products/available?page=${page}&size=${size}`),
  
  getAllBrands: () =>
    api.get('/products/brands'),
};

export default api;
