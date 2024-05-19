package com.sparta.webfluxchat.controller;

import com.sparta.webfluxchat.entity.Message;
import com.sparta.webfluxchat.repository.MessageRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.time.Instant;

@RestController
public class ChatController {

    private final Sinks.Many<Message> sink;
    private final MessageRepository messageRepository;

    public ChatController(MessageRepository messageRepository) {
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
        this.messageRepository = messageRepository;
    }

    @GetMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Message> streamMessages() {
        return messageRepository.findByOrderByTimestampAsc()
                .mergeWith(sink.asFlux())
                .delayElements(Duration.ofMillis(200));
    }

    @GetMapping("/send")
    public Mono<Void> sendMessage(@RequestParam String message) {
        Message msg = new Message(message, Instant.now().toEpochMilli());
        return messageRepository.save(msg)
                .doOnNext(sink::tryEmitNext)
                .then();
        //hi
    }
}
