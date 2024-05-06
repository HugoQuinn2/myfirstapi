package com.hq.myfirtsapi.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EndPointcontroller {
    @PostMapping(value = "endpoint")
    public String welcome(){
        return "Welcome from security endpoint";
    }
}
