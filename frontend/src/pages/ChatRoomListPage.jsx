import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getChatRooms } from "../api/chatApi";
import "../styles/ChatRoomListPage.css";

function ChatRoomListPage() {

    const [rooms, setRooms] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        loadRooms();
    }, []);

    const loadRooms = async () => {

        try {

            const response = await getChatRooms();

            setRooms(response.data);

        } catch (e) {

            console.error(e);

            alert("채팅방을 불러오지 못했습니다.");

        }

    };

    return (

        <div className="chatroom-container">

            <h2>내 채팅방</h2>

            {rooms.length === 0 ? (

                <p>참여중인 채팅방이 없습니다.</p>

            ) : (

                rooms.map(room => (

                    <div
                        key={room.roomId}
                        className="chatroom-card"
                        onClick={() => navigate(`/chat/${room.roomId}`)}
                    >

                        <h3>{room.postTitle}</h3>

                        <button>

                            입장하기

                        </button>

                    </div>

                ))

            )}

        </div>

    );

}

export default ChatRoomListPage;