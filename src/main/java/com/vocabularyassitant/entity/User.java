package com.vocabularyassitant.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User
{
    private String uId;
    private String uPassword;
    private String uName;
    private Integer uStatus;
    private Integer uStyle;
    private LocalDateTime uUpdated_at;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public Integer getuStatus() {
        return uStatus;
    }

    public void setuStatus(Integer uStatus) {
        this.uStatus = uStatus;
    }

    public Integer getuStyle() {
        return uStyle;
    }

    public void setuStyle(Integer uStyle) {
        this.uStyle = uStyle;
    }

    public LocalDateTime getuUpdated_at() {
        return uUpdated_at;
    }

    public void setuUpdated_at(LocalDateTime uUpdated_at) {
        this.uUpdated_at = uUpdated_at;
    }

    @Override
    public String toString() {
        return "User{" +
                "uId='" + uId + '\'' +
                ", uPassword='" + uPassword + '\'' +
                ", uName='" + uName + '\'' +
                ", uStatus=" + uStatus +
                ", uStyle=" + uStyle +
                ", uUpdated_at=" + uUpdated_at +
                '}';
    }
}
