package com.tina.tina_server.domain.emotion.presentation.dto.response;

import com.tina.tina_server.domain.emotion.domain.ObservedEmotions;

public record ObserverEmotionResponse(
        Long id,
        String userNickname,
        String partnerName,
        String partnerMbti,
        int charmScore
) {
    public static ObserverEmotionResponse from(ObservedEmotions entity, String userNickname) {
        return new ObserverEmotionResponse(
                entity.getId(),
                userNickname,
                entity.getPartnerName(),
                entity.getPartnerMbti(),
                entity.getCharmScore()
        );
    }
}
