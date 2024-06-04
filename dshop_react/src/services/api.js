import axios from "axios";

const { REACT_APP_BASE_URL } = process.env;

const api = axios.create({
    baseURL: REACT_APP_BASE_URL,
});

api.interceptors.request.use(
    (config) => {
      const user = JSON.parse(localStorage.getItem('user'));
      if (user) {
        config.headers.Authorization = `Bearer ${user.token}`;
      }
      return config;
    }
);

api.interceptors.response.use(
    (response) => {
      // Any status code that lies within the range of 2xx cause this function to trigger
      return response;
    },
    (error) => {
      // Any status codes that falls outside the range of 2xx cause this function to trigger
      if (error.response.status === 401 || error.response.status === 403) {
        localStorage.removeItem('user');
      }
      return Promise.reject(error.response.data);
    }
  );

export default api;