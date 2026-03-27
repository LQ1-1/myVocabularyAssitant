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

    @Override
    public String toString() {
        return "LiveActivitySession{" +
                "activityId='" + activityId + '\'' +
                ", uId='" + uId + '\'' +
                ", pushToken='" + pushToken + '\'' +
                ", status=" + status +
                ", startedAt=" + startedAt +
                ", lastPushedAt=" + lastPushedAt +
                ", expiresAt=" + expiresAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public LocalDateTime getLastPushedAt() {
        return lastPushedAt;
    }

    public void setLastPushedAt(LocalDateTime lastPushedAt) {
        this.lastPushedAt = lastPushedAt;
    }

    public String getPushToken() {
        return pushToken;
    }

    public void setPushToken(String pushToken) {
        this.pushToken = pushToken;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
