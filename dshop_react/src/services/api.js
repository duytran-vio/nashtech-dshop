import axios from "axios";

const api = axios.create({
    baseURL: "http://localhost:8080/api/",
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
      return Promise.reject(error.response.data);
    }
  );

export default api;