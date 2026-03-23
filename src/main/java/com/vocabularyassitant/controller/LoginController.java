package com.vocabularyassitant.controller;

import com.vocabularyassitant.dto.LoginRequest;
import com.vocabularyassitant.dto.RegistrationRequest;
import com.vocabularyassitant.result.Result;
import com.vocabularyassitant.service.AuthService;
import com.vocabularyassitant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class LoginController {
    private final AuthService authService;
    @Autowired
    private UserService userService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest request) {
        return Result.success(authService.login(request));
    }

    @PostMapping("/registration")
    public Result registration(@RequestBody RegistrationRequest registrationInfo)
    {
        return Result.success(userService.addUser(registrationInfo));
    }
}
