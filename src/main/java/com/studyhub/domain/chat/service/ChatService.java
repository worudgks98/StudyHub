package com.studyhub.domain.chat.service;

import com.studyhub.domain.chat.dto.ChatMessageResponse;
import com.studyhub.domain.chat.dto.ChatRoomResponse;
import com.studyhub.domain.chat.entity.ChatMember;
import com.studyhub.domain.chat.entity.ChatMessage;
import com.studyhub.domain.chat.entity.ChatRoom;
import com.studyhub.domain.chat.repository.ChatMemberRepository;
import com.studyhub.domain.chat.repository.ChatMessageRepository;
import com.studyhub.domain.chat.repository.ChatRoomRepository;
import com.studyhub.domain.member.entity.Member;
import com.studyhub.domain.member.repository.MemberRepository;
import com.studyhub.domain.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMemberRepository chatMemberRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void createChatRoom(Post post) {

        if(chatRoomRepository.findByPostId(post.getId()).isPresent()){
            return;
        }

        ChatRoom chatRoom = ChatRoom.builder()
                .post(post)
                .createdAt(LocalDateTime.now())
                .build();

        ChatRoom saveRoom =  chatRoomRepository.save(chatRoom);

        ChatMember host = ChatMember.builder()
                .chatRoom(saveRoom)
                .member(post.getMember())
                .build();

        chatMemberRepository.save(host);
    }

    @Transactional
    public void addMemberToChatRoom(Post post, Member member) {

        ChatRoom chatRoom =
                chatRoomRepository.findByPostId(post.getId())
                        .orElseThrow();

        ChatMember chatMember =
                ChatMember.builder()
                        .chatRoom(chatRoom)
                        .member(member)
                        .build();

        chatMemberRepository.save(chatMember);
    }
    
    @Transactional
    public void sendMessage(Long roomId,Long memberId,String message){
        
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방 없음"));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new IllegalArgumentException("회원 없음"));
        
        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoom(room)
                .sender(member)
                .message(message)
                .createdAt(LocalDateTime.now())
                .build();
        
        chatMessageRepository.save(chatMessage);
    }

    @Transactional(readOnly = true)
    public List<ChatMessageResponse> getMessages(Long roomId,Long memberId){

        if(!chatMemberRepository
                .existsByChatRoomIdAndMemberId(roomId, memberId)){

            throw new IllegalArgumentException("채팅방 접근 권한이 없습니다.");
        }

        List<ChatMessage> messages =
                chatMessageRepository
                        .findByChatRoomIdOrderByCreatedAtAsc(roomId);

        return messages.stream()
                .map(message ->
                        ChatMessageResponse.builder()
                                .sender(message.getSender().getNickname())
                                .message(message.getMessage())
                                .build())
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ChatRoomResponse> getMyChatRooms(
            Long memberId){

        List<ChatMember> chatMembers =
                chatMemberRepository
                        .findByMemberId(memberId);

        return chatMembers.stream()
                .map(chatMember ->
                        ChatRoomResponse.builder()
                                .roomId(
                                        chatMember.getChatRoom().getId()
                                )
                                .postTitle(
                                        chatMember.getChatRoom()
                                                .getPost()
                                                .getTitle()
                                )
                                .build()
                )
                .toList();
    }
}
