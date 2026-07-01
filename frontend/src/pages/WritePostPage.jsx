import { useNavigate } from "react-router-dom";
import { createPost } from "../api/postApi";
import PostForm from "../components/PostForm";

function WritePostPage() {

    const navigate = useNavigate();

    const handleCreate = async (data) => {

        try {

            await createPost(data);

            alert("게시글 작성 완료");

            navigate("/");

        } catch (error) {

            console.error(error);

            alert("게시글 작성 실패");
        }
    };

    return (
        <PostForm
            buttonText="게시글 작성"
            onSubmit={handleCreate}
        />
    );
}

export default WritePostPage;