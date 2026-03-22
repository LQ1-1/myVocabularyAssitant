package com.vocabularyassitant.mapper;

import com.vocabularyassitant.entity.RefreshToken;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

@Mapper
public interface RefreshTokenMapper {
    int insert(RefreshToken refreshToken);

    RefreshToken findByRefreshTokenHash(@Param("refreshTokenHash") String refreshTokenHash);

    int updateRefreshTokenByRId(RefreshToken refreshToken);

    int revokeByRId(@Param("rId") String rId, @Param("revokedAt") LocalDateTime revokedAt);
}
