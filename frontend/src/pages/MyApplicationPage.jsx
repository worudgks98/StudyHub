import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import {
    getMyApplications,
    cancelApplication
} from "../api/applicationApi";

function MyApplicationPage() {

    const [applications, setApplications] = useState([]);

    useEffect(() => {

        loadApplications();

    }, []);

    const loadApplications = async () => {

        try {

            const response = await getMyApplications();

            setApplications(response.data);

        } catch (error) {

            console.error(error);

        }

    };

    const handleCancel = async (applicationId) => {

        const confirmCancel = window.confirm("지원을 취소하시겠습니까?");

        if (!confirmCancel) {
            return;
        }

        try {

            await cancelApplication(applicationId);

            alert("지원이 취소되었습니다.");

            loadApplications();

        } catch (error) {

            console.error(error);

            alert("지원 취소 실패");

        }

    };

    return (

        <div>

            <h1>내 지원 목록</h1>

            <hr />

            {applications.length === 0 ? (

                <p>지원한 게시글이 없습니다.</p>

            ) : (

                applications.map((application) => (

                    <div
                        key={application.postId}
                        style={{
                            border: "1px solid #ddd",
                            padding: "10px",
                            marginBottom: "10px",
                        }}
                    >

                        <h3>

                            <Link to={`/posts/${application.postId}`}>
                                {application.title}
                            </Link>

                        </h3>

                        <p>상태 : {application.status}</p>

                        {application.status === "PENDING" && (

                            <button
                                onClick={() =>
                                    handleCancel(application.applicationId)
                                }
                            >
                                지원 취소
                            </button>

                        )}

                    </div>

                ))

            )}

        </div>

    );
}

export default MyApplicationPage;