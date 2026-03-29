package com.vocabularyassitant.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AndroidPushDevice {
    private String deviceId;
    private String uId;
    private String fcmToken;
    private Integer status;
    private LocalDateTime lastPushedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
