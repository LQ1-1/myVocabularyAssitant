package com.vocabularyassitant.service;

import com.vocabularyassitant.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class JwtService {
    private final JwtEncoder jwtEncoder;
    private final long accessTokenExpiresInSeconds;

    public JwtService(
            JwtEncoder jwtEncoder,
            @Value("${security.jwt.access-token-ttl-seconds:900}") long accessTokenExpiresInSeconds
    ) {
        this.jwtEncoder = jwtEncoder;
        this.accessTokenExpiresInSeconds = accessTokenExpiresInSeconds;
    }

    public String generateAccessToken(User user, String sessionId) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .id(UUID.randomUUID().toString())
                .subject(user.getuId())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(accessTokenExpiresInSeconds))
                .claim("uname", user.getuName())
                .claim("sid", sessionId)
                .claim("roles", List.of("USER"))
                .build();

        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }

    public long getAccessTokenExpiresInSeconds() {
        return accessTokenExpiresInSeconds;
    }
}
