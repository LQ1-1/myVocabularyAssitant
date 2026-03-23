package com.vocabularyassitant.util;

import com.vocabularyassitant.mapper.InviteCodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class InviteCodeUtil
{
    @Value("${invitecode.available_characters}")
    private String CHARS;
    @Value("${invitecode.length}")
    private int LENGTH;
    private SecureRandom RANDOM = new SecureRandom();

    @Autowired
    private InviteCodeMapper inviteCodeMapper;

    public String generateInviteCode()
    {
        StringBuilder res=new StringBuilder(LENGTH);
        for(int i=0;i<LENGTH;i++)
        {
            res.append(CHARS.charAt(RANDOM.nextInt(CHARS.length())));
        }
        return res.toString();
    }

    public Integer checkInviteCodeValidity(String code)
    {
        return inviteCodeMapper.checkInviteCodeValidity(code);
    }
}
