import axios from "axios";
import api from "./api";

export const imagesEndpoint = "/images";

export const uploadImage = async (formData) => {
    
    const response = await api.post(imagesEndpoint, formData,{
        headers: {
            "Content-Type": "multipart/form-data",
        },
    });
    return response.data;
}

export const getImage = async (url) => {
    const response = await axios.get(url);
    return response.data;
}