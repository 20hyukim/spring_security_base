package com.sparta.webfluxchat.service;

import com.sparta.webfluxchat.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ChatService {

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    public Mono<Message> saveMessage(String message, Long roomnumber, Long userId) {
        Message msg = new Message(message, System.currentTimeMillis(), userId);
        String collectionName = "messages_room_" + roomnumber;
        return reactiveMongoTemplate.save(msg, collectionName);
    }
}
