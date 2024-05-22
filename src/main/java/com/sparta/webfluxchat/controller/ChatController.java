package com.sparta.webfluxchat.controller;

import com.sparta.webfluxchat.entity.Message;
import com.sparta.webfluxchat.repository.MessageRepository;
import com.sparta.webfluxchat.security.UserDetailsImpl;
import com.sparta.webfluxchat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    private final ChatService chatService;

    public ChatController(MessageRepository messageRepository, ChatService chatService) {
        this.chatService = chatService;
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
    public Mono<Void> sendMessage(@RequestParam String message,
                                  @RequestParam Long roomnumber,
                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return chatService.saveMessage(message, roomnumber, userDetails.getUser().getId())
                .doOnNext(msg -> sink.tryEmitNext(msg))
                .then();
    }


}
