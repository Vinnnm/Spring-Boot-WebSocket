package com.vinnnm.websocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/chat")
    public String getChatPage() {
        return "chat";
    }
    
    @GetMapping("/")
    public String index() {
    	return "index";
    }
}
