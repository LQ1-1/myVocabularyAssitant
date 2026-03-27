package com.vocabularyassitant.service;

import com.vocabularyassitant.entity.LiveActivitySession;
import com.vocabularyassitant.mapper.LiveActivitySessionMapper;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LiveActivitySessionService {
    private final LiveActivitySessionMapper liveActivitySessionMapper;

    public LiveActivitySessionService(LiveActivitySessionMapper liveActivitySessionMapper) {
        this.liveActivitySessionMapper = liveActivitySessionMapper;
    }

    public Integer Register(String uId, String activityId, String pushToken, LocalDateTime expiresAt, Jwt jwt) {
        if(!uId.equals(jwt.getSubject()))
        {
            return null;
        }
        LiveActivitySession session = new LiveActivitySession();
        session.setActivityId(activityId);
        session.setuId(uId);
        session.setPushToken(pushToken);
        session.setStatus(1);
        session.setStartedAt(LocalDateTime.now());
        session.setExpiresAt(expiresAt);
        return liveActivitySessionMapper.insertOrUpdate(session);
    }

    public Integer End(String activityId) {
        return liveActivitySessionMapper.endActivity(activityId);
    }
}
