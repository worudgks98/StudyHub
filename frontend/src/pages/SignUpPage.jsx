import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import "../styles/SignUpPage.css";

function SignUpPage() {

    const navigate = useNavigate();

    const [form, setForm] = useState({
        nickname: "",
        email: "",
        password: "",
        confirmPassword: ""
    });

    const handleChange = (e) => {
        setForm({
            ...form,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (form.password !== form.confirmPassword) {
            alert("비밀번호가 일치하지 않습니다.");
            return;
        }

        try {

            await axios.post("http://localhost:8080/api/members/signup", {
                nickname: form.nickname,
                email: form.email,
                password: form.password
            });

            alert("회원가입 성공");
            navigate("/login");

        } catch (error) {
            alert("회원가입 실패");
        }
    };

    return (
        <div className="signup-container">

            <form className="signup-form" onSubmit={handleSubmit}>

                <h2>회원가입</h2>

                <input
                    type="text"
                    name="nickname"
                    placeholder="닉네임"
                    value={form.nickname}
                    onChange={handleChange}
                />

                <input
                    type="email"
                    name="email"
                    placeholder="이메일"
                    onChange={handleChange}
                />

                <input
                    type="password"
                    name="password"
                    placeholder="비밀번호"
                    onChange={handleChange}
                />

                <input
                    type="password"
                    name="confirmPassword"
                    placeholder="비밀번호 확인"
                    onChange={handleChange}
                />

                <button type="submit">
                    회원가입
                </button>

                <p>
                    이미 계정이 있나요?
                    <Link to="/login"> 로그인</Link>
                </p>

            </form>

        </div>
    );
}

export default SignUpPage;