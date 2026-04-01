package com.vocabularyassitant.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.vocabularyassitant.config.FcmProperties;
import com.vocabularyassitant.entity.EnglishVocabulary.EnglishVocabulary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;

@Service
public class FcmPushService {
    private static final String FCM_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
    private static final Logger log = LoggerFactory.getLogger(FcmPushService.class);

    private final FcmProperties fcmProperties;
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    public FcmPushService(FcmProperties fcmProperties, ObjectMapper objectMapper) {
        this.fcmProperties = fcmProperties;
        this.objectMapper = objectMapper;
        this.httpClient = HttpClient.newHttpClient();
    }

    public boolean isConfigured() {
        return fcmProperties.isEnabled()
                && StringUtils.hasText(fcmProperties.getProjectId())
                && StringUtils.hasText(fcmProperties.getServiceAccountPath());
    }

    public PushResult pushWordNotification(String fcmToken, EnglishVocabulary vocabulary) {
        if (!isConfigured()) {
            log.warn("Skipped FCM push because FCM is not configured");
            return new PushResult(false, false, "FCM is not configured");
        }

        try {
            String accessToken = getAccessToken();
            String payload = buildPayload(fcmToken, vocabulary);
            log.info("Sending FCM push: tokenSuffix={}, vId={}, vWord={}",
                    maskToken(fcmToken), vocabulary.getvId(), vocabulary.getvWord());

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://fcm.googleapis.com/v1/projects/"
                            + fcmProperties.getProjectId() + "/messages:send"))
                    .header("Authorization", "Bearer " + accessToken)
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .POST(HttpRequest.BodyPublishers.ofString(payload, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            boolean success = response.statusCode() >= 200 && response.statusCode() < 300;
            boolean tokenInvalid = !success && containsInvalidTokenError(response.body());
            if (success) {
                log.info("FCM push accepted: tokenSuffix={}, statusCode={}, vId={}",
                        maskToken(fcmToken), response.statusCode(), vocabulary.getvId());
            } else {
                log.warn("FCM push rejected: tokenSuffix={}, statusCode={}, tokenInvalid={}, body={}",
                        maskToken(fcmToken), response.statusCode(), tokenInvalid, response.body());
            }
            return new PushResult(success, tokenInvalid, response.body());
        } catch (Exception e) {
            log.error("FCM push failed with exception: tokenSuffix={}, message={}",
                    maskToken(fcmToken), e.getMessage(), e);
            return new PushResult(false, false, e.getMessage());
        }
    }

    private String getAccessToken() throws IOException {
        try (FileInputStream inputStream = new FileInputStream(fcmProperties.getServiceAccountPath())) {
            GoogleCredentials credentials = GoogleCredentials.fromStream(inputStream)
                    .createScoped(List.of(FCM_SCOPE));
            credentials.refreshIfExpired();
            AccessToken accessToken = credentials.getAccessToken();
            if (accessToken == null || accessToken.getExpirationTime() == null
                    || accessToken.getExpirationTime().toInstant().isBefore(Instant.now())) {
                credentials.refresh();
                accessToken = credentials.getAccessToken();
            }
            if (accessToken == null) {
                throw new IOException("Unable to obtain FCM access token");
            }
            return accessToken.getTokenValue();
        }
    }

    private String buildPayload(String fcmToken, EnglishVocabulary vocabulary) throws IOException {
        ObjectNode notification = objectMapper.createObjectNode();
        notification.put("title", "Vocabulary");
        notification.put("body", vocabulary.getvWord() + " - " + vocabulary.getvZh_meaning());

        ObjectNode data = objectMapper.createObjectNode();
        data.put("vId", vocabulary.getvId());
        data.put("vWord", vocabulary.getvWord());
        data.put("vZhMeaning", vocabulary.getvZh_meaning());

        ObjectNode message = objectMapper.createObjectNode();
        message.put("token", fcmToken);
        message.set("notification", notification);
        message.set("data", data);

        ObjectNode payload = objectMapper.createObjectNode();
        payload.set("message", message);
        return objectMapper.writeValueAsString(payload);
    }

    private boolean containsInvalidTokenError(String responseBody) {
        if (!StringUtils.hasText(responseBody)) {
            return false;
        }
        String normalized = responseBody.toLowerCase();
        return normalized.contains("unregistered")
                || normalized.contains("registration-token-not-registered")
                || normalized.contains("invalid_argument");
    }

    private String maskToken(String fcmToken) {
        if (!StringUtils.hasText(fcmToken)) {
            return "empty";
        }
        int length = fcmToken.length();
        return length <= 8 ? fcmToken : fcmToken.substring(length - 8);
    }

    public record PushResult(boolean success, boolean tokenInvalid, String responseBody) {
    }
}
