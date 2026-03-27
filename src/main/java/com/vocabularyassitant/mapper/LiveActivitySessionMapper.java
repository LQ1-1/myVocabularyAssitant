package com.vocabularyassitant.mapper;

import com.vocabularyassitant.entity.LiveActivitySession;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface LiveActivitySessionMapper {
    Integer insertOrUpdate(LiveActivitySession session);

    LiveActivitySession findByActivityId(@Param("activityId") String activityId);

    List<LiveActivitySession> findAllActiveSessions();

    Integer updateLastPushedAt(@Param("activityId") String activityId,
                               @Param("lastPushedAt") LocalDateTime lastPushedAt);

    Integer endActivity(@Param("activityId") String activityId);
}
