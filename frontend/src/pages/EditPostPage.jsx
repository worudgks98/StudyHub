import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { getPost, updatePost } from "../api/postApi";
import PostForm from "../components/PostForm";

function EditPostPage() {

    const { postId } = useParams();

    const navigate = useNavigate();

    const [post, setPost] = useState(null);

    useEffect(() => {
        loadPost();
    }, []);

    const loadPost = async () => {

        try {

            const response = await getPost(postId);

            setPost(response.data);

        } catch (error) {

            console.error(error);

        }
    };

    const handleUpdate = async (data) => {

        try {

            await updatePost(postId, data);

            alert("게시글 수정 완료");

            navigate(`/posts/${postId}`);

        } catch (error) {

            console.error(error);

            alert("게시글 수정 실패");

        }
    };

    if (!post) {
        return <h2>불러오는 중...</h2>;
    }

    return (
        <PostForm
            initialData={post}
            onSubmit={handleUpdate}
            buttonText="게시글 수정"
        />
    );
}

export default EditPostPage;