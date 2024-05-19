package com.sparta.webfluxchat.repository;

import com.sparta.webfluxchat.entity.Message;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface MessageRepository extends ReactiveMongoRepository<Message, String> {
    Flux<Message> findByOrderByTimestampAsc();
}
