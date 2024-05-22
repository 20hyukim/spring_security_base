package com.sparta.webfluxchat.service;

import com.sparta.webfluxchat.entity.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    public Mono<Message> saveMessage(String message, Long roomnumber, Long userId) {
        Message msg = new Message(message, System.currentTimeMillis(), userId);
        String collectionName = "messages_room_" + roomnumber;
        return reactiveMongoTemplate.save(msg, collectionName);
    }

    public Flux<Message> findMessagesByRoomId(Long roomId) {
        String collectionName = "messages_room_" + roomId;
        logger.info("Finding messages in collection: {}", collectionName);
        return reactiveMongoTemplate.findAll(Message.class, collectionName)
                .doOnSubscribe(subscription -> logger.info("Subscription to find messages started"))
                .doOnNext(foundMessage -> logger.info("Found message: {}", foundMessage))
                .doOnError(error -> logger.error("Error occurred while finding messages: {}", error.getMessage()))
                .doOnComplete(() -> logger.info("Completed finding messages in collection: {}", collectionName));
    }
}