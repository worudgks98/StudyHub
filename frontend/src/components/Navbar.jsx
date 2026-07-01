import { Link, useNavigate } from "react-router-dom";
import "../styles/Navbar.css";

function Navbar() {

    const navigate = useNavigate();

    const token = localStorage.getItem("token");

    const handleLogout = () => {

        localStorage.removeItem("token");
        localStorage.removeItem("memberId");
        localStorage.removeItem("nickname");

        alert("로그아웃 되었습니다.");

        navigate("/");
    };

    return (
        <nav className="navbar">

            <div className="logo">
                <Link to="/" reloadDocument>
                    StudyHub
                </Link>
            </div>

            <div className="menu">

                <Link to="/" reloadDocument>
                    게시판
                </Link>

                {token && <Link to="/write">글쓰기</Link>}

                {token && <Link to="/myposts">내 게시글</Link>}

                {token && <Link to="/myapplications">내 지원 목록</Link>}

                {token && <Link to="/chatrooms">내 채팅방</Link>}

                {!token ? (
                    <>
                        <Link to="/login">로그인</Link>
                        <Link to="/signup">회원가입</Link>
                    </>
                ) : (
                    <button onClick={handleLogout}>
                        로그아웃
                    </button>
                )}

            </div>

        </nav>
    );
}

export default Navbar;