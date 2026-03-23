package com.vocabularyassitant.mapper;

import com.vocabularyassitant.entity.InviteCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InviteCodeMapper
{
    public Integer addInviteCode(InviteCode inviteCode);
    public Integer checkInviteCodeValidity(@Param("inviteCode") String inviteCode); //检查iOwnReferralCode是否存在该邀请码
}
