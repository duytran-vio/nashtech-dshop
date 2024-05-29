import api from "./api";

export const categoriesEndpoint = "/categories";

export const getCategories = async () => {
    const response = await api.get(categoriesEndpoint);
    return response.data;
}

export const addCategory = async (newCategory) => {
    const response = await api.post(categoriesEndpoint, newCategory);
    return response.data;
}

export const updateCategory = async (id, updatedCategory) => {
    const response = await api.put(`${categoriesEndpoint}/${id}`, updatedCategory);
    return response.data;
}