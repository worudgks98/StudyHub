import { useState, useEffect } from "react";

function PostForm({ initialData, onSubmit, buttonText }) {

    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");
    const [category, setCategory] = useState("PROJECT");
    const [maxMember, setMaxMember] = useState(2);

    useEffect(() => {
        if (initialData) {
            setTitle(initialData.title);
            setContent(initialData.content);
            setCategory(initialData.category);
            setMaxMember(initialData.maxMember);
        }
    }, [initialData]);

    const handleSubmit = () => {
        onSubmit({
            title,
            content,
            category,
            maxMember,
        });
    };

    return (
        <div style={{ width: "600px", margin: "30px auto" }}>

            <h2>{buttonText}</h2>

            <input
                type="text"
                placeholder="제목"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                style={{ width: "100%", height: "35px", marginBottom: "10px" }}
            />

            <textarea
                placeholder="내용"
                value={content}
                onChange={(e) => setContent(e.target.value)}
                rows={8}
                style={{ width: "100%", marginBottom: "10px" }}
            />

            <select
                value={category}
                onChange={(e) => setCategory(e.target.value)}
                style={{ width: "100%", height: "35px", marginBottom: "10px" }}
            >
                <option value="PROJECT">PROJECT</option>
                <option value="STUDY">STUDY</option>
                <option value="INTERVIEW">INTERVIEW</option>
            </select>

            <input
                type="number"
                value={maxMember}
                onChange={(e) => setMaxMember(Number(e.target.value))}
                style={{ width: "100%", height: "35px", marginBottom: "20px" }}
            />

            <button
                onClick={handleSubmit}
                style={{ width: "100%", height: "40px" }}
            >
                {buttonText}
            </button>

        </div>
    );
}

export default PostForm;