package com.vocabularyassitant.mapper;

import com.vocabularyassitant.entity.AndroidPushDevice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface AndroidPushDeviceMapper {
    Integer insertOrUpdate(AndroidPushDevice device);

    List<AndroidPushDevice> findAllActiveDevices();

    List<AndroidPushDevice> findActiveDevicesByUId(@Param("uId") String uId);

    Integer updateLastPushedAt(@Param("deviceId") String deviceId,
                               @Param("lastPushedAt") LocalDateTime lastPushedAt);

    Integer deactivateByDeviceId(@Param("deviceId") String deviceId);
}
