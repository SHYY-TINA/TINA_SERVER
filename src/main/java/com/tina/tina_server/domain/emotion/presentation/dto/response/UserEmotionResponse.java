package com.tina.tina_server.domain.emotion.presentation.dto.response;

import com.tina.tina_server.domain.emotion.domain.UserEmotions;

public record UserEmotionResponse(
        Long id,
        String userNickname,
        String partnerName,
        String partnerMbti,
        int charmScore
) {
    public static UserEmotionResponse from(UserEmotions entity, String userNickname) {
        return new UserEmotionResponse(
                entity.getId(),
                userNickname,
                entity.getPartnerName(),
                entity.getPartnerMbti(),
                entity.getCharmScore()
        );
    }
}
