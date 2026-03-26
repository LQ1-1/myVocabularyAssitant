package com.vocabularyassitant.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LiveActivitySession
{
    private String activityId;
    private String uId;
    private String pushToken;
    private Integer status;
    private LocalDateTime startedAt;
    private LocalDateTime lastPushedAt;
    private LocalDateTime expiresAt;
    private LocalDateTime updatedAt;
}
