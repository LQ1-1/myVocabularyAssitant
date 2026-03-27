package com.vocabularyassitant.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PushLogTable
{
    private String pId;
    private String uId;
    private String activityId;
    private String wordId;
    private String pushType;
    private Integer pushStatus;
    private String responseMessage;
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return "PushLogTable{" +
                "activityId='" + activityId + '\'' +
                ", pId='" + pId + '\'' +
                ", uId='" + uId + '\'' +
                ", wordId='" + wordId + '\'' +
                ", pushType='" + pushType + '\'' +
                ", pushStatus=" + pushStatus +
                ", responseMessage='" + responseMessage + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public Integer getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(Integer pushStatus) {
        this.pushStatus = pushStatus;
    }

    public String getPushType() {
        return pushType;
    }

    public void setPushType(String pushType) {
        this.pushType = pushType;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getWordId() {
        return wordId;
    }

    public void setWordId(String wordId) {
        this.wordId = wordId;
    }
}
