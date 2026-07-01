import api from "./axios";

export const login = (data) => {
    return api.post("/members/login", data);
};

export const signup = (data) => {
    return api.post("/members/signup", data);
};