package com.sparta.webfluxchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.sparta.webfluxchat")
public class WebfluxChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebfluxChatApplication.class, args);
    }

}
