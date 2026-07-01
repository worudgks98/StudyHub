import api from "./axios";

export const getPosts = (
    page,
    keyword,
    category
) => {

    return api.get("/posts", {

        params: {
            page,
            keyword,
            category,
        },

    });

};

export const getPost = (postId) => {
    return api.get(`/posts/${postId}`);
};

export const createPost = (data) => {
    return api.post("/posts", data);
};

export const updatePost = (postId, data) => {
    return api.put(`/posts/${postId}`, data);
};

export const deletePost = (postId) => {
    return api.delete(`/posts/${postId}`);
};
