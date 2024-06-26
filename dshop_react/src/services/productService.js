import api from "./api";

export const productsEndpoint = "/products";

export const getProducts = async (cachekey) => {
  const response = await api.get(cachekey.url, {
    params: cachekey.params.filter,
  });
  return response.data;
};

export const getProductById = async (url) => {
  const response = await api.get(url);
  return response.data;
};

export const createProduct = async (product) => {
  const response = await api.post(productsEndpoint, product);
  return response.data;
};

export const updateProduct = async (id, product) => {
  const response = await api.put(`${productsEndpoint}/${id}`, product);
  return response.data;
};

export const deleteProduct = async (id) => {
  const response = await api.delete(`${productsEndpoint}/${id}`);
  return response.data;
};

export const buyProduct = async (id, quantity) => {
  return await api.post(`${productsEndpoint}/buy`, {
    id: id,
    quantity: quantity,
  });
};
