import api from "./axios";

export const apply = (postId) => {
    return api.post(`/applications/${postId}`);
};

export const getApplications = (postId) => {
    return api.get(`/applications/post/${postId}`);
};

export const approve = (applicationId) => {
    return api.patch(`/applications/${applicationId}/approve`);
};

export const reject = (applicationId) => {
    return api.patch(`/applications/${applicationId}/reject`);
};

export const cancel = (applicationId) => {
    return api.delete(`/applications/${applicationId}`);
};

export const getMyApplications = () => {
    return api.get("/applications/my");
};

export const checkApplication = (postId) => {
    return api.get(`/applications/check/${postId}`);
};

export const cancelApplication = (applicationId) => {

    return api.delete(`/applications/${applicationId}`);

};