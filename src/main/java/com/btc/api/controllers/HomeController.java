package com.btc.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/home") //
public class HomeController {

    @GetMapping(value = "/test")
    public Integer test() {
        return 2;
    }

    @GetMapping(value = "/status")
    public String status() {
        return "Running";
    }
}
