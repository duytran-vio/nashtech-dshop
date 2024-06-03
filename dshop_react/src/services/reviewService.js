import api from "./api";


export const reviewsEndpoint = "/reviews";

export const getReviews = async ({url, params}) => {
    const response = await api.get(url,{
        params: params
    });
    return response.data;
}

export const createReview = async (review) => {
    const response = await api.post(reviewsEndpoint, review);
    return response.data;
}