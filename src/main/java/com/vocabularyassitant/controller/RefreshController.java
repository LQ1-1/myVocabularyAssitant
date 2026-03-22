package com.vocabularyassitant.controller;

import com.vocabularyassitant.dto.RefreshRequest;
import com.vocabularyassitant.result.Result;
import com.vocabularyassitant.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class RefreshController {
    private final AuthService authService;

    public RefreshController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/refresh")
    public Result refresh(@RequestBody RefreshRequest request) {
        return Result.success(authService.refresh(request.getRefreshToken()));
    }
}
