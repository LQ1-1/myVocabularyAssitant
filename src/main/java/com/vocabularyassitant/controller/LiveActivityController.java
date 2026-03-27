package com.vocabularyassitant.controller;

import com.vocabularyassitant.mapper.LiveActivitySessionMapper;
import com.vocabularyassitant.result.Result;
import com.vocabularyassitant.service.LiveActivitySessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/LiveActivity")
public class LiveActivityController
{
    @Autowired
    private LiveActivitySessionService liveActivitySessionService;

    @PostMapping("/Register")
    public Result Register(@RequestParam("uId") String uId, @RequestParam("activityId") String activityId, @RequestParam("pushToken") String pushToken, @RequestParam("expiresAt") LocalDateTime expiresAt, Jwt jwt)
    {
        return Result.success(liveActivitySessionService.Register(uId, activityId, pushToken, expiresAt, jwt));
    }

    @PostMapping("/End")
    public Result End(@RequestParam("activityId") String activityId, Jwt jwt)
    {
        return Result.success(liveActivitySessionService.End(activityId));
    }

}
