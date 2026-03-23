package com.vocabularyassitant.entity;

import lombok.Data;

@Data
public class InviteCode
{
    private String uId;
    private String iOwnReferralCode;
    private String iAppliedReferralCode;

    public String getiAppliedReferralCode() {
        return iAppliedReferralCode;
    }

    public void setiAppliedReferralCode(String iAppliedReferralCode) {
        this.iAppliedReferralCode = iAppliedReferralCode;
    }

    public String getiOwnReferralCode() {
        return iOwnReferralCode;
    }

    public void setiOwnReferralCode(String iOwnReferralCode) {
        this.iOwnReferralCode = iOwnReferralCode;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    @Override
    public String toString() {
        return "InviteCode{" +
                "iAppliedReferralCode='" + iAppliedReferralCode + '\'' +
                ", uId='" + uId + '\'' +
                ", iOwnReferralCode='" + iOwnReferralCode + '\'' +
                '}';
    }
}
