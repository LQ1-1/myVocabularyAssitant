package com.vocabularyassitant.service;

import com.vocabularyassitant.dto.RegistrationRequest;
import com.vocabularyassitant.entity.InviteCode;
import com.vocabularyassitant.entity.User;
import com.vocabularyassitant.mapper.InviteCodeMapper;
import com.vocabularyassitant.mapper.UserMapper;
import com.vocabularyassitant.util.InviteCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {
    @Autowired
    private final UserMapper userMapper;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private InviteCodeMapper inviteCodeMapper;
    @Autowired
    private InviteCodeUtil inviteCodeUtil;

    @Value("${registration.result_successful}")
    private String registrationSuccessful;
    @Value("${registration.result_failed}")
    private String registrationFailed;
    @Value("${registration.result_InviteCodeInvalid}")
    private String registrationInviteCodeInvalid;

    public UserService(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByUserId(String userId) {
        return userMapper.getUserByUserId(userId);
    }

    public boolean passwordMatches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public String addUser(RegistrationRequest registrationInfo)
    {
        int checkInviteCodeValidity=inviteCodeUtil.checkInviteCodeValidity(registrationInfo.getInviteCodeInfo().getiAppliedReferralCode());
        if(checkInviteCodeValidity<=0)  //invalid invite code
        {
            return registrationInviteCodeInvalid;
        }
        else
        {
            String new_code=inviteCodeUtil.generateInviteCode();
            while(inviteCodeUtil.checkInviteCodeValidity(new_code)>0)    //new invitecode repeat
            {
                new_code=inviteCodeUtil.generateInviteCode();
            }
            //add new user record to UserTable
            User user=new User();
            user.setuId(registrationInfo.getUserInfo().getuId());
            user.setuName(registrationInfo.getUserInfo().getuName());
            user.setuPassword(passwordEncoder.encode(registrationInfo.getUserInfo().getuPassword()));
            user.setuStyle(registrationInfo.getUserInfo().getuStyle());
            user.setuStatus(registrationInfo.getUserInfo().getuStatus());
            user.setuUpdated_at(LocalDateTime.now());
            //往add new invite information to InviteCodeTable
            InviteCode NewInviteCodeRecord=new InviteCode();
            NewInviteCodeRecord.setuId(registrationInfo.getUserInfo().getuId());
            NewInviteCodeRecord.setiAppliedReferralCode(registrationInfo.getInviteCodeInfo().getiAppliedReferralCode());
            NewInviteCodeRecord.setiOwnReferralCode(new_code);
            int userAdd_result= userMapper.addUser(user);
            int inviteCodeAdd_result=inviteCodeMapper.addInviteCode(NewInviteCodeRecord);
            if(userAdd_result>0 && inviteCodeAdd_result>0)
            {
                return registrationSuccessful;
            }
            else
            {
                return registrationFailed;
            }
        }
    }
}
