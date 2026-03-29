package com.vocabularyassitant.service;

import com.vocabularyassitant.entity.AndroidPushDevice;
import com.vocabularyassitant.entity.EnglishVocabulary.EnglishVocabulary;
import com.vocabularyassitant.mapper.AndroidPushDeviceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AndroidWordPushScheduler {
    private static final Logger log = LoggerFactory.getLogger(AndroidWordPushScheduler.class);

    private final AndroidPushDeviceService androidPushDeviceService;
    private final AndroidPushDeviceMapper androidPushDeviceMapper;
    private final WordPushService wordPushService;
    private final FcmPushService fcmPushService;

    public AndroidWordPushScheduler(AndroidPushDeviceService androidPushDeviceService,
                                    AndroidPushDeviceMapper androidPushDeviceMapper,
                                    WordPushService wordPushService,
                                    FcmPushService fcmPushService) {
        this.androidPushDeviceService = androidPushDeviceService;
        this.androidPushDeviceMapper = androidPushDeviceMapper;
        this.wordPushService = wordPushService;
        this.fcmPushService = fcmPushService;
    }

    @Scheduled(fixedRateString = "${fcm.push-fixed-rate-ms:60000}")
    public void pushWordsToAndroidDevices() {
        if (!fcmPushService.isConfigured()) {
            log.debug("Skip scheduled Android push because FCM is not configured");
            return;
        }

        List<AndroidPushDevice> devices = androidPushDeviceService.findAllActiveDevices();
        log.info("Starting scheduled Android push, activeDeviceCount={}", devices.size());
        for (AndroidPushDevice device : devices) {
            EnglishVocabulary vocabulary = wordPushService.pickWordForUser(device.getuId());
            if (vocabulary == null) {
                log.warn("No vocabulary available for scheduled push: uId={}, deviceId={}",
                        device.getuId(), device.getDeviceId());
                continue;
            }

            FcmPushService.PushResult result = fcmPushService.pushWordNotification(device.getFcmToken(), vocabulary);
            if (result.success()) {
                androidPushDeviceMapper.updateLastPushedAt(device.getDeviceId(), LocalDateTime.now());
                log.info("Scheduled Android push success: uId={}, deviceId={}, vId={}",
                        device.getuId(), device.getDeviceId(), vocabulary.getvId());
                continue;
            }

            if (result.tokenInvalid()) {
                log.warn("Scheduled Android push found invalid token: uId={}, deviceId={}",
                        device.getuId(), device.getDeviceId());
                androidPushDeviceService.deactivateByDeviceId(device.getDeviceId());
                continue;
            }

            log.warn("Scheduled Android push failed: uId={}, deviceId={}, vId={}, response={}",
                    device.getuId(), device.getDeviceId(), vocabulary.getvId(), result.responseBody());
        }
        log.info("Finished scheduled Android push");
    }
}
