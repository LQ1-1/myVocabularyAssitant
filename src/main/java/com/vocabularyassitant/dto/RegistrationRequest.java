package com.vocabularyassitant.dto;

import com.vocabularyassitant.entity.InviteCode;
import com.vocabularyassitant.entity.User;

public class RegistrationRequest
{
    private User userInfo;
    private InviteCode inviteCodeInfo;

    public InviteCode getInviteCodeInfo() {
        return inviteCodeInfo;
    }

    public void setInviteCodeInfo(InviteCode inviteCodeInfo) {
        this.inviteCodeInfo = inviteCodeInfo;
    }

    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "RegistrationRequest{" +
                "inviteCodeInfo=" + inviteCodeInfo.toString() +
                ", userInfo=" + userInfo.toString() +
                '}';
    }
}
