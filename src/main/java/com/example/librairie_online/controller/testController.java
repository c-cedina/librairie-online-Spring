package com.example.librairie_online.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(path = "test")
public class testController {
    @GetMapping(path = "string")
    public String getString() {
        return "test réussi";
    }

}
