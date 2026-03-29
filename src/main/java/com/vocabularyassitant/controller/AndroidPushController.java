package com.vocabularyassitant.controller;

import com.vocabularyassitant.dto.RegisterAndroidPushTokenRequest;
import com.vocabularyassitant.entity.AndroidPushDevice;
import com.vocabularyassitant.entity.EnglishVocabulary.EnglishVocabulary;
import com.vocabularyassitant.result.Result;
import com.vocabularyassitant.service.AndroidPushDeviceService;
import com.vocabularyassitant.service.FcmPushService;
import com.vocabularyassitant.service.WordPushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/android-push")
public class AndroidPushController {
    private static final Logger log = LoggerFactory.getLogger(AndroidPushController.class);

    private final AndroidPushDeviceService androidPushDeviceService;
    private final WordPushService wordPushService;
    private final FcmPushService fcmPushService;

    public AndroidPushController(AndroidPushDeviceService androidPushDeviceService,
                                 WordPushService wordPushService,
                                 FcmPushService fcmPushService) {
        this.androidPushDeviceService = androidPushDeviceService;
        this.wordPushService = wordPushService;
        this.fcmPushService = fcmPushService;
    }

    @PostMapping("/register-token")
    public Result registerToken(@RequestBody RegisterAndroidPushTokenRequest request,
                                @AuthenticationPrincipal Jwt jwt) {
        if (jwt == null) {
            log.warn("Rejected Android push token registration because JWT is missing");
            return Result.fail("Unauthorized");
        }
        if (!StringUtils.hasText(request.getDeviceId()) || !StringUtils.hasText(request.getFcmToken())) {
            log.warn("Rejected Android push token registration because request is incomplete: uId={}", jwt.getSubject());
            return Result.fail("deviceId and fcmToken are required");
        }

        String uId = jwt.getSubject();
        log.info("Registering Android push token: uId={}, deviceId={}", uId, request.getDeviceId());
        Integer result = androidPushDeviceService.registerDevice(uId, request.getDeviceId(), request.getFcmToken());
        return Result.success(result);
    }

    @PostMapping("/push-test-word")
    public Result pushTestWord(@AuthenticationPrincipal Jwt jwt) {
        if (jwt == null) {
            log.warn("Rejected manual Android push test because JWT is missing");
            return Result.fail("Unauthorized");
        }

        String uId = jwt.getSubject();
        log.info("Starting manual Android push test: uId={}", uId);
        EnglishVocabulary vocabulary = wordPushService.pickWordForUser(uId);
        if (vocabulary == null) {
            log.warn("Manual Android push test aborted because no vocabulary is available: uId={}", uId);
            return Result.fail("No vocabulary available");
        }

        List<AndroidPushDevice> devices = androidPushDeviceService.findActiveDevicesByUId(uId);
        if (devices.isEmpty()) {
            log.warn("Manual Android push test aborted because no active device is registered: uId={}", uId);
            return Result.fail("No active Android push device found");
        }

        int successCount = 0;
        for (AndroidPushDevice device : devices) {
            FcmPushService.PushResult pushResult = fcmPushService.pushWordNotification(device.getFcmToken(), vocabulary);
            if (pushResult.success()) {
                successCount++;
                log.info("Manual Android push test success: uId={}, deviceId={}, vId={}",
                        uId, device.getDeviceId(), vocabulary.getvId());
                continue;
            }
            if (pushResult.tokenInvalid()) {
                log.warn("Manual Android push test found invalid token: uId={}, deviceId={}", uId, device.getDeviceId());
                androidPushDeviceService.deactivateByDeviceId(device.getDeviceId());
                continue;
            }
            log.warn("Manual Android push test failed: uId={}, deviceId={}, response={}",
                    uId, device.getDeviceId(), pushResult.responseBody());
        }

        Map<String, Object> data = new HashMap<>();
        data.put("uId", uId);
        data.put("vId", vocabulary.getvId());
        data.put("vWord", vocabulary.getvWord());
        data.put("sentDeviceCount", successCount);
        data.put("requestAt", LocalDateTime.now());
        log.info("Finished manual Android push test: uId={}, sentDeviceCount={}, vId={}",
                uId, successCount, vocabulary.getvId());
        return Result.success(data);
    }

    @GetMapping("/devices")
    public Result getRegisteredDevices(@AuthenticationPrincipal Jwt jwt) {
        if (jwt == null) {
            log.warn("Rejected Android device query because JWT is missing");
            return Result.fail("Unauthorized");
        }
        log.info("Querying registered Android devices: uId={}", jwt.getSubject());
        return Result.success(androidPushDeviceService.findActiveDevicesByUId(jwt.getSubject()));
    }
}
