import api from "./api";

export const userEndPoint = "/users";

export const getUsers = async ({url, params}) => {
    const response = await api.get(url, {params});
    return response.data;
} 

export const updateUser = async (id, userData) => {
    const response = await api.patch(`${userEndPoint}/${id}`, userData);
    return response.data;
}