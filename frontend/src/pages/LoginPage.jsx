import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { login } from "../api/authApi";
import "../styles/LoginPage.css";

function LoginPage() {

    const navigate = useNavigate();

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const handleLogin = async () => {

        try {

            const response = await login({
                email,
                password,
            });

            console.log(response.data);

            localStorage.setItem("token", response.data.token);
            localStorage.setItem("memberId", response.data.memberId);
            localStorage.setItem("nickname", response.data.nickname);

            alert("로그인 성공");

            navigate("/");

        } catch (error) {

            alert("로그인 실패");

            console.error(error);

        }
    };

    return (
        <div style={{ width: "400px", margin: "50px auto" }}>

            <h2>로그인</h2>

            <input
                type="email"
                placeholder="이메일"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                style={{ width: "100%", marginBottom: "10px", height: "35px" }}
            />

            <input
                type="password"
                placeholder="비밀번호"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                style={{ width: "100%", marginBottom: "20px", height: "35px" }}
            />

            <button
                onClick={handleLogin}
                style={{ width: "100%", height: "40px" }}
            >
                로그인
            </button>

        </div>
    );
}

export default LoginPage;