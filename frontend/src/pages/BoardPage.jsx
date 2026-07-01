import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getPosts } from "../api/postApi";
import SearchBar from "../components/SearchBar";
import Pagination from "../components/Pagination";
import PostCard from "../components/PostCard";
import "../styles/BoardPage.css";

function BoardPage() {

    const [posts, setPosts] = useState([]);
    const [keyword, setKeyword] = useState("");
    const [category, setCategory] = useState("");
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);

    useEffect(() => {
        loadPosts();
    }, [page,category]);

    const loadPosts = async () => {

        try {

            const response = await getPosts(
                page,
                keyword,
                category
            );

            setPosts(response.data.content);
            setTotalPages(response.data.totalPages);

        } catch (error) {

            console.error(error);

        }
    };

    const handleSearch = () => {

        if (page !== 0) {
            setPage(0);
        } else {
            loadPosts();
        }

    };

    const handleCategory = (selectedCategory) => {

        setCategory(selectedCategory);

        setPage(0);

    };

    const movePage = (pageNumber) => {

        setPage(pageNumber);

    };

    return (

    <div className="board-container">

            <h1 className="board-title">

                StudyHub 게시판

            </h1>

            <SearchBar
                keyword={keyword}
                setKeyword={setKeyword}
                onSearch={handleSearch}
            />

            <div style={{ marginTop: "15px", marginBottom: "15px" }}>

                <button onClick={() => handleCategory("")}>
                    전체
                </button>

                <button
                    onClick={() => handleCategory("PROJECT")}
                    style={{ marginLeft: "10px" }}
                >
                    프로젝트
                </button>

                <button
                    onClick={() => handleCategory("STUDY")}
                    style={{ marginLeft: "10px" }}
                >
                    스터디
                </button>

                <button
                    onClick={() => handleCategory("INTERVIEW")}
                    style={{ marginLeft: "10px" }}
                >
                    면접
                </button>

            </div>

            <hr />

            {posts.map((post) => (
                <PostCard
                    key={post.id}
                    post={post}
                />
            ))}

            <Pagination
                page={page}
                totalPages={totalPages}
                onPageChange={movePage}
            />

        </div>
    );
}

export default BoardPage;