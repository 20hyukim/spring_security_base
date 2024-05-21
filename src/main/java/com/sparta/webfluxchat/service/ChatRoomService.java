package com.sparta.webfluxchat.service;

import com.sparta.webfluxchat.entity.ChatRoom;
import com.sparta.webfluxchat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    public ResponseEntity<String> createChatRoom(String chatName) {
        Optional<ChatRoom> existingChatRoom = chatRoomRepository.findByName(chatName);
        if (existingChatRoom.isPresent()) {
            throw new IllegalArgumentException("Error: 중복된 채팅방 이름이 존재합니다.");
        }
        ChatRoom chatRoom = new ChatRoom(chatName);
        chatRoomRepository.save(chatRoom);

        return ResponseEntity.ok("성공적으로 채팅방이 생성되었습니다.");
    }
}
