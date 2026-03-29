package com.vocabularyassitant.dto;

import lombok.Data;

@Data
public class RegisterAndroidPushTokenRequest {
    private String deviceId;
    private String fcmToken;
}
