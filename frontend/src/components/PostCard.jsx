import { Link } from "react-router-dom";
import "../styles/PostCard.css";

function PostCard({ post }) {

    const categoryName = {

        PROJECT: "프로젝트",
        STUDY: "스터디",
        INTERVIEW: "면접",

        project: "프로젝트",
        study: "스터디",
        interview: "면접",

    };

    return (

        <div className="post-card">

            <div className="post-header">

                <h3 className="post-title">

                    <Link to={`/posts/${post.id}`}>
                        {post.title}
                    </Link>

                </h3>

                <span
                    className={
                        post.closed
                            ? "status closed"
                            : "status open"
                    }
                >
                    {post.closed ? "모집완료" : "모집중"}
                </span>

            </div>

            <div className="post-body">

                <p className="post-info">
                    👤 {post.nickname}
                </p>

                <p className="post-info">
                    📂 {categoryName[post.category]}
                </p>

                <p className="post-info">
                    👥 {post.approvedCount} / {post.maxMember}명
                </p>

            </div>

        </div>

    );
}

export default PostCard;