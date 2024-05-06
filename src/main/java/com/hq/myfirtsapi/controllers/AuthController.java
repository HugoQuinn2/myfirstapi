package com.hq.myfirtsapi.controllers;

import com.hq.myfirtsapi.models.AuthModel;
import com.hq.myfirtsapi.models.LoginModel;
import com.hq.myfirtsapi.models.RegisterModel;
import com.hq.myfirtsapi.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "login")
    public ResponseEntity<AuthModel> login(@RequestBody LoginModel request){

        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthModel> register(@RequestBody RegisterModel request){

        return ResponseEntity.ok(authService.register(request));
    }

}
