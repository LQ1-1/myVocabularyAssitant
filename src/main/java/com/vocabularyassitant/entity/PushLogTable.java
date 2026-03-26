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
}
