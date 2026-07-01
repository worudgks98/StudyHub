import { useEffect, useRef, useState } from "react";
import { useParams } from "react-router-dom";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import { getMessages } from "../api/chatApi";
import "../styles/ChatRoomPage.css";

function ChatRoomPage() {

    const { roomId } = useParams();

    const [messages, setMessages] = useState([]);
    const [message, setMessage] = useState("");

    const clientRef = useRef(null);
    const bottomRef = useRef(null);

    const memberId = Number(localStorage.getItem("memberId"));
    const nickname = localStorage.getItem("nickname");

    useEffect(() => {

        loadMessages();

        connect();

        return () => {

            if (clientRef.current) {
                clientRef.current.deactivate();
            }

        };

    }, []);

    useEffect(() => {

        bottomRef.current?.scrollIntoView({
            behavior: "smooth"
        });

    }, [messages]);

    const loadMessages = async () => {

        try {

            const response = await getMessages(roomId);

            setMessages(response.data);

        } catch (e) {

            console.error(e);

        }

    };

    const connect = () => {

        const socket = new SockJS("http://localhost:8080/ws");

        const client = new Client({

            webSocketFactory: () => socket,

            reconnectDelay: 5000,

            onConnect: () => {

                console.log("웹소켓 연결 성공");

                client.subscribe(

                    `/topic/chat/${roomId}`,

                    (msg) => {

                        const received = JSON.parse(msg.body);

                        setMessages(prev => [...prev, received]);

                    }

                );

            }

        });

        client.activate();

        clientRef.current = client;

    };

    const send = () => {

        if (!message.trim()) return;

        clientRef.current.publish({

            destination: "/app/chat",

            body: JSON.stringify({

                roomId: Number(roomId),

                memberId: memberId,

                sender: nickname,

                message: message

            })

        });

        setMessage("");

    };

    return (

        <div className="chat-container">

            <h2>채팅방</h2>

            <div className="chat-box">

                {messages.map((msg, index) => (

                    <div
                        key={index}
                        className={
                            msg.memberId === memberId
                                ? "my-message"
                                : "other-message"
                        }
                    >

                        <div className="sender">
                            {msg.sender}
                        </div>

                        <div className="bubble">
                            {msg.message}
                        </div>

                    </div>

                ))}

                <div ref={bottomRef}></div>

            </div>

            <div className="chat-input">

                <input
                    autoFocus

                    value={message}

                    onChange={(e) =>
                        setMessage(e.target.value)
                    }

                    onKeyDown={(e) => {

                        if (e.key === "Enter") {

                            send();

                        }

                    }}

                    placeholder="메시지를 입력하세요."

                />

                <button onClick={send}>

                    전송

                </button>

            </div>

        </div>

    );

}

export default ChatRoomPage;