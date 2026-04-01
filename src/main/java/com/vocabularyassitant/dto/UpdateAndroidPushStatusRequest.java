package com.vocabularyassitant.dto;

import lombok.Data;

@Data
public class UpdateAndroidPushStatusRequest {
    private String deviceId;
    private Boolean enabled;
}
