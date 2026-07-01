import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "../styles/MyPostPage.css";

function MyPostPage() {

    const [posts, setPosts] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {

        const fetchMyPosts = async () => {

            try {

                const token = localStorage.getItem("token");

                const response = await axios.get(
                    "http://localhost:8080/api/posts/my",
                    {
                        headers: {
                            Authorization: `Bearer ${token}`
                        }
                    }
                );

                setPosts(response.data);

            } catch (error) {
                console.error(error);
                alert("내 게시글을 불러오지 못했습니다.");
            }

        };

        fetchMyPosts();

    }, []);

    return (

        <div className="mypost-container">

            <h2>내가 작성한 게시글</h2>

            {posts.length === 0 ? (

                <p className="empty-message">
                    작성한 게시글이 없습니다.
                </p>

            ) : (

                <div className="mypost-list">

                    {posts.map((post) => (

                        <div
                            key={post.id}
                            className="mypost-card"
                            onClick={() => navigate(`/posts/${post.id}`)}
                        >

                            <h3>{post.title}</h3>

                            <p>
                                <strong>카테고리</strong> : {post.category}
                            </p>

                            <p>
                                <strong>모집 인원</strong> :
                                {" "}
                                {post.currentMember} / {post.maxMember}
                            </p>

                            <p>
                                <strong>상태</strong> :
                                {" "}
                                {post.closed ? "🔴 모집 마감" : "🟢 모집 중"}
                            </p>

                            {post.createdAt && (
                                <small>
                                    작성일 : {post.createdAt.substring(0, 10)}
                                </small>
                            )}

                        </div>

                    ))}

                </div>

            )}

        </div>

    );

}

export default MyPostPage;