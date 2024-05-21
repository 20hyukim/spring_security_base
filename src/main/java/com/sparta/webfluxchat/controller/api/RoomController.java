package com.sparta.webfluxchat.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomController {

    @GetMapping("/")
    public String chatRoomPage(){
        return "chatRoom";
    }

}
