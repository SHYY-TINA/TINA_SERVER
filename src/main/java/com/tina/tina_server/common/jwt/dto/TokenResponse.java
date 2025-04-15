package com.tina.tina_server.common.jwt.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}