package com.tina.tina_server.domain.mbti.presentation.dto.response;

public record MbtiCompatibilityResponse(
        String title,
        Integer heart,
        String communicationStyle,
        String strengthInRelationship,
        String caution,
        String tip
) {}