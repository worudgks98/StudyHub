import axios from "axios";

const API = "http://localhost:8080/api/chat";

const getAuthHeader = () => ({
    headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`
    }
});

export const getMessages = (roomId) => {
    return axios.get(
        `${API}/${roomId}/messages`,
        getAuthHeader()
    );
};

export const getChatRooms = () => {
    return axios.get(
        `${API}/rooms`,
        getAuthHeader()
    );
};

export const sendMessage = (roomId, message) => {
    return axios.post(
        `${API}/${roomId}/messages`,
        { message },
        getAuthHeader()
    );
};