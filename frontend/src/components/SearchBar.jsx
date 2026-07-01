function SearchBar({
    keyword,
    setKeyword,
    onSearch,
}) {

    return (
        <div style={{ marginBottom: "20px" }}>

            <input
                type="text"
                placeholder="제목 검색"
                value={keyword}
                onChange={(e) => setKeyword(e.target.value)}
                onKeyDown={(e) => {
                    if (e.key === "Enter") {
                        onSearch();
                    }
                }}
            />

            <button onClick={onSearch}>
                검색
            </button>

        </div>
    );
}

export default SearchBar;