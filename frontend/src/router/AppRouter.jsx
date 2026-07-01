import { Routes, Route, useLocation } from "react-router-dom";

import BoardPage from "../pages/BoardPage";
import LoginPage from "../pages/LoginPage";
import SignUpPage from "../pages/SignUpPage";
import PostDetailPage from "../pages/PostDetailPage";
import WritePostPage from "../pages/WritePostPage";
import MyPostPage from "../pages/MyPostPage";
import ChatRoomPage from "../pages/ChatRoomPage";
import ChatRoomListPage from "../pages/ChatRoomListPage";
import EditPostPage from "../pages/EditPostPage";
import ProtectedRoute from "../components/ProtectedRoute";
import MyApplicationPage from "../pages/MyApplicationPage";

function AppRouter() {

    const location = useLocation();

    return (
        <Routes>
            <Route path="/" element={<BoardPage key={location.key} />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/signup" element={<SignUpPage />} />
            <Route path="/posts/:postId" element={<PostDetailPage />} />
            <Route path="/write" element={<ProtectedRoute><WritePostPage /></ProtectedRoute>}/>
            <Route path="/myposts" element={<MyPostPage />} />
            <Route path="/chat/:roomId" element={<ChatRoomPage />} />
            <Route
                path="/posts/:postId/edit"
                element={
                    <ProtectedRoute>
                        <EditPostPage />
                    </ProtectedRoute>
                }
            />
            <Route
                path="/myapplications"
                element={
                    <ProtectedRoute>
                        <MyApplicationPage />
                    </ProtectedRoute>
                }
            />
            <Route
                path="/chatrooms"
                element={
                    <ProtectedRoute>
                        <ChatRoomListPage />
                    </ProtectedRoute>
                }
            />
        </Routes>
    );
}

export default AppRouter;