package com.vocabularyassitant.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "fcm")
public class FcmProperties {
    private boolean enabled;
    private String projectId;
    private String serviceAccountPath;
    private long pushFixedRateMs = 60000L;
}
