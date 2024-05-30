import api from "./api";

export const productsEndpoint = "/products";

export const getProducts = async (cachekey) => {
    const response = await api.get(cachekey.url, {
        params: cachekey.params.filter
    });
    return response.data;
}

export const deleteProduct = async (id) => {
    const response = await api.delete(`${productsEndpoint}/${id}`);
    console.log(response);
    return response.data;
}