function Pagination({
    page,
    totalPages,
    onPageChange,
}) {

    return (
        <div style={{ marginTop: "20px" }}>

            <button
                disabled={page === 0}
                onClick={() => onPageChange(page - 1)}
            >
                이전
            </button>

            {Array.from(
                { length: totalPages },
                (_, index) => (
                    <button
                        key={index}
                        onClick={() => onPageChange(index)}
                        style={{
                            fontWeight: page === index ? "bold" : "normal",
                            backgroundColor: page === index ? "#4CAF50" : "white",
                            color: page === index ? "white" : "black",
                            margin: "3px",
                            padding: "6px 12px",
                            cursor: "pointer",
                        }}
                    >
                        {index + 1}
                    </button>
                )
            )}

            <button
                disabled={page === totalPages - 1}
                onClick={() => onPageChange(page + 1)}
            >
                다음
            </button>

        </div>
    );
}

export default Pagination;