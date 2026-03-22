package com.vocabularyassitant.service;

import com.vocabularyassitant.dto.AuthTokenResponse;
import com.vocabularyassitant.dto.LoginRequest;
import com.vocabularyassitant.entity.RefreshToken;
import com.vocabularyassitant.entity.User;
import com.vocabularyassitant.mapper.RefreshTokenMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HexFormat;
import java.util.UUID;

@Service
public class AuthService {
    private static final int SESSION_STATUS_ACTIVE = 1;
    private final UserService userService;
    private final JwtService jwtService;
    private final RefreshTokenMapper refreshTokenMapper;
    private final long refreshTokenTtlSeconds;

    @Value("${login_response.respond.LoginResponse_NoSuchAccount}")
    private String LoginResponse_NoSuchAccount;     //账户不存在
    @Value("${login_response.respond.LoginResponse_PasswordIncorrect}")
    private String LoginResponse_PasswordIncorrect;     //密码错误
    @Value("${login_response.respond.LoginResponse_SessionInvalid}")
    private String LoginResponse_SessionInvalid;        //会话失效
    @Value("${login_response.respond.LoginResponse_RefreshTokenInvalid}")
    private String LoginResponse_RefreshTokenInvalid;       //Refresh Token无效
    @Value("${login_response.respond.LoginResponse_RefreshTokenExpired}")
    private String LoginResponse_RefreshTokenExpired;       //Refresh Token过期

    public AuthService(
            UserService userService,
            JwtService jwtService,
            RefreshTokenMapper refreshTokenMapper,
            @Value("${security.jwt.refresh-token-ttl-seconds:2592000}") long refreshTokenTtlSeconds
    ) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.refreshTokenMapper = refreshTokenMapper;
        this.refreshTokenTtlSeconds = refreshTokenTtlSeconds;
    }

    public AuthTokenResponse login(LoginRequest request) {
        User user = userService.findByUserId(request.getuId());
        if (user == null) {
            throw new IllegalArgumentException(LoginResponse_NoSuchAccount);
        }
        if (!userService.passwordMatches(request.getuPassword(), user.getuPassword())) {
            throw new IllegalArgumentException(LoginResponse_PasswordIncorrect);
        }

        String sessionId = UUID.randomUUID().toString();
        String refreshTokenValue = randomToken();
        LocalDateTime now = LocalDateTime.now();

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setrId(sessionId);
        refreshToken.setuId(user.getuId());
        refreshToken.setRefreshTokenHash(sha256(refreshTokenValue));
        refreshToken.setDeviceId(request.getDeviceId());
        refreshToken.setDeviceType(request.getDeviceType());
        refreshToken.setStatus(SESSION_STATUS_ACTIVE);
        refreshToken.setIssuedAt(now);
        refreshToken.setExpiresAt(now.plusSeconds(refreshTokenTtlSeconds));
        refreshToken.setLastRefreshedAt(now);
        refreshTokenMapper.insert(refreshToken);

        return buildTokenResponse(user, sessionId, refreshTokenValue);
    }

    public AuthTokenResponse refresh(String refreshToken) {
        RefreshToken storedRefreshToken = refreshTokenMapper.findByRefreshTokenHash(sha256(refreshToken));
        if (storedRefreshToken == null) {
            throw new IllegalArgumentException(LoginResponse_RefreshTokenInvalid);
        }
        if (!isActive(storedRefreshToken.getStatus())) {
            throw new IllegalArgumentException(LoginResponse_SessionInvalid);
        }
        if (storedRefreshToken.getExpiresAt() != null && storedRefreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            refreshTokenMapper.revokeByRId(storedRefreshToken.getrId(), LocalDateTime.now());
            throw new IllegalArgumentException(LoginResponse_RefreshTokenExpired);
        }

        User user = userService.findByUserId(storedRefreshToken.getuId());
        if (user == null) {
            throw new IllegalArgumentException(LoginResponse_NoSuchAccount);
        }

        String rotatedRefreshToken = randomToken();
        LocalDateTime now = LocalDateTime.now();
        storedRefreshToken.setRefreshTokenHash(sha256(rotatedRefreshToken));
        storedRefreshToken.setExpiresAt(now.plusSeconds(refreshTokenTtlSeconds));
        storedRefreshToken.setLastRefreshedAt(now);
        refreshTokenMapper.updateRefreshTokenByRId(storedRefreshToken);

        return buildTokenResponse(user, storedRefreshToken.getrId(), rotatedRefreshToken);
    }

    private AuthTokenResponse buildTokenResponse(User user, String sessionId, String refreshToken) {
        AuthTokenResponse response = new AuthTokenResponse();
        response.setAccessToken(jwtService.generateAccessToken(user, sessionId));
        response.setAccessTokenExpiresIn(jwtService.getAccessTokenExpiresInSeconds());
        response.setRefreshToken(refreshToken);
        response.setRefreshTokenExpiresIn(refreshTokenTtlSeconds);
        response.setTokenType("Bearer");
        response.setSessionId(sessionId);
        return response;
    }

    private boolean isActive(Integer status) {
        return status != null && status == SESSION_STATUS_ACTIVE;
    }

    private String randomToken() {
        byte[] bytes = UUID.randomUUID().toString().concat(UUID.randomUUID().toString())
                .getBytes(StandardCharsets.UTF_8);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String sha256(String value) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(messageDigest.digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 not available", e);
        }
    }
}
