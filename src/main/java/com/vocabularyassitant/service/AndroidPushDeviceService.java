package com.vocabularyassitant.service;

import com.vocabularyassitant.entity.AndroidPushDevice;
import com.vocabularyassitant.mapper.AndroidPushDeviceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AndroidPushDeviceService {
    private static final Logger log = LoggerFactory.getLogger(AndroidPushDeviceService.class);

    private final AndroidPushDeviceMapper androidPushDeviceMapper;

    public AndroidPushDeviceService(AndroidPushDeviceMapper androidPushDeviceMapper) {
        this.androidPushDeviceMapper = androidPushDeviceMapper;
    }

    public Integer registerDevice(String uId, String deviceId, String fcmToken) {
        AndroidPushDevice device = new AndroidPushDevice();
        device.setDeviceId(deviceId);
        device.setuId(uId);
        device.setFcmToken(fcmToken);
        device.setStatus(1);
        Integer result = androidPushDeviceMapper.insertOrUpdate(device);
        log.info("Registered Android push device: uId={}, deviceId={}, result={}", uId, deviceId, result);
        return result;
    }

    public List<AndroidPushDevice> findAllActiveDevices() {
        return androidPushDeviceMapper.findAllActiveDevices();
    }

    public List<AndroidPushDevice> findActiveDevicesByUId(String uId) {
        return androidPushDeviceMapper.findActiveDevicesByUId(uId);
    }

    public Integer deactivateByDeviceId(String deviceId) {
        Integer result = androidPushDeviceMapper.deactivateByDeviceId(deviceId);
        log.warn("Deactivated Android push device: deviceId={}, result={}", deviceId, result);
        return result;
    }
}
