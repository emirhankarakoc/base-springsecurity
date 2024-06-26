package com.karakoc.security_demosu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HelloController {

    @GetMapping("/")
    public String greeting(){
        return "Hello World!";
    }


    @GetMapping("/secured")
    public String secured(){
        return "If you see this, then you logged in.";
    }
    
}
