import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getPost, deletePost } from "../api/postApi";
import {
    apply,
    getApplications,
    approve,
    reject,
    checkApplication
} from "../api/applicationApi";

function PostDetailPage() {

    const { postId } = useParams();

    const navigate = useNavigate();

    const [post, setPost] = useState(null);

    const [applications, setApplications] = useState([]);

    const [alreadyApplied, setAlreadyApplied] = useState(false);

    useEffect(() => {

        loadPost();

        loadApplications();

        loadApplicationStatus();

    }, []);

    const loadPost = async () => {

        try {

            const response = await getPost(postId);

            setPost(response.data);

        } catch (error) {

            console.error(error);

        }
    };

    const loadApplications = async () => {

        try {

            const response = await getApplications(postId);

            setApplications(response.data);

        } catch (error) {

            console.error(error);

        }
    };

    const loadApplicationStatus = async () => {

        if (!localStorage.getItem("token")) {
            return;
        }

        try {

            const response = await checkApplication(postId);

            setAlreadyApplied(response.data);

        } catch (error) {

            console.error(error);

        }
    };

    const handleDelete = async () => {

        const confirmDelete = window.confirm("정말 삭제하시겠습니까?");

        if (!confirmDelete) {
            return;
        }

        try {

            await deletePost(postId);

            alert("게시글 삭제 완료");

            navigate("/");

        } catch (error) {

            console.error(error);

            alert("게시글 삭제 실패");

        }
    };

    const handleApply = async () => {

        try {

            await apply(postId);

            alert("지원이 완료되었습니다.");

            setAlreadyApplied(true);

        } catch (error) {

            console.error(error);

            alert(error.response?.data || "지원 실패");

        }
    };

    const handleApprove = async (applicationId) => {

        try {

            await approve(applicationId);

            alert("승인되었습니다.");

            loadApplications();
            loadPost();

        } catch (error) {

            console.error(error);

            alert("승인 실패");

        }
    };

    const handleReject = async (applicationId) => {

        try {

            await reject(applicationId);

            alert("거절되었습니다.");

            loadApplications();

        } catch (error) {

            console.error(error);

            alert("거절 실패");

        }
    };

    if (!post) {
        return <h2>불러오는 중...</h2>;
    }

    const myMemberId = Number(localStorage.getItem("memberId"));

    const isWriter = myMemberId === post.memberId;


    return (
        <div>

            <h1>{post.title}</h1>

            <hr />

            <p>
                <strong>작성자 :</strong> {post.nickname}
            </p>

            <p>
                <strong>카테고리 :</strong> {post.category}
            </p>

            <p>
                <strong>모집 인원 :</strong>{" "}
                {post.approvedCount} / {post.maxMember}명
            </p>

            <hr />

            <p>{post.content}</p>

            {isWriter && (

                <div style={{ marginTop: "20px" }}>

                    <button
                        onClick={() => navigate(`/posts/${postId}/edit`)}
                    >
                        수정하기
                    </button>

                    <button
                        onClick={handleDelete}
                        style={{ marginLeft: "10px" }}
                    >
                        삭제하기
                    </button>

                </div>

            )}

            {!isWriter && localStorage.getItem("token") && (

                <div style={{ marginTop: "20px" }}>

                    <button
                        onClick={handleApply}
                        disabled={alreadyApplied}
                    >
                        {alreadyApplied ? "신청 완료" : "신청하기"}
                    </button>

                </div>

            )}

            {isWriter && (

                <div style={{ marginTop: "30px" }}>

                    <h3>신청자 목록</h3>

                    {applications.length === 0 ? (

                        <p>아직 신청자가 없습니다.</p>

                    ) : (

                        applications.map((application) => (

                            <div
                                key={application.applicationId}
                                style={{
                                    border: "1px solid #ddd",
                                    padding: "10px",
                                    marginBottom: "10px",
                                }}
                            >

                                <p>닉네임 : {application.nickname}</p>

                                <p>상태 : {application.status}</p>

                                {application.status === "PENDING" && (

                                    <>

                                        <button
                                            onClick={() =>
                                                handleApprove(application.applicationId)
                                            }
                                        >
                                            승인
                                        </button>

                                        <button
                                            onClick={() =>
                                                handleReject(application.applicationId)
                                            }
                                            style={{ marginLeft: "10px" }}
                                        >
                                            거절
                                        </button>

                                    </>

                                )}

                            </div>

                        ))

                    )}

                </div>

            )}

        </div>
    );
}

export default PostDetailPage;