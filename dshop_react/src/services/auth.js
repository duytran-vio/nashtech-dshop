import api from "./api";

const authEndpoint = "/auth";

export const sendLogin = async (username, password, desireRole) => {
  await api
    .post(`${authEndpoint}/login`, {
      username,
      password,
    })
    .then((response) => {
      const user = response.data.user;
      if (desireRole && user.role !== desireRole) {
        throw new Error("You don't have permission to access this page");
      }
      localStorage.setItem("user", JSON.stringify({ ...user, token: response.data.token}));
      return response.data;
    })
    .catch((error) => {
      throw new Error(error.message);
    });
};

export const sendLogout = () => {
  localStorage.removeItem("user");
};

export const sendRegister = async (userData) => {
    userData.roleId = 2;
    const response = await api.post(`${authEndpoint}/register`, userData);
    return response.data;
};

// export const getCurrentUser = async () => {
//   try {
//     const response = await api.get('/auth/me');
//     return response.data;
//   } catch (error) {
//     throw new Error('Failed to fetch current user');
//   }
// };
