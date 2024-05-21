package com.sparta.webfluxchat.controller;

import com.sparta.webfluxchat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping("/chatroom")
    public ResponseEntity<String> createChatRoom(@RequestParam String chatName) {

        return chatRoomService.createChatRoom(chatName);
    }
}
