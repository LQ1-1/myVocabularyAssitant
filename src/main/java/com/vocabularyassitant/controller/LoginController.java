package com.vocabularyassitant.controller;

import com.vocabularyassitant.dto.LoginRequest;
import com.vocabularyassitant.result.Result;
import com.vocabularyassitant.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class LoginController {
    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest request) {
        return Result.success(authService.login(request));
    }
}
