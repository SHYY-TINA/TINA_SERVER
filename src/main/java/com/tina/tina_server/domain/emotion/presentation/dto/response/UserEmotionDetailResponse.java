package com.tina.tina_server.domain.emotion.presentation.dto.response;

import com.tina.tina_server.domain.emotion.domain.UserEmotions;

import java.util.List;

public record UserEmotionDetailResponse(
        String userNickname,
        String partnerName,
        String partnerMbti,
        int charmScore,
        String feedbackTitle,
        String feedbackContent,
        String charmPointTitle,
        String charmPointContent,
        List<AnalyzedChat> chats
) {
    public static UserEmotionDetailResponse from(UserEmotions entity, String userNickname, List<AnalyzedChat> chats) {
        return new UserEmotionDetailResponse(
                userNickname,
                entity.getPartnerName(),
                entity.getPartnerMbti(),
                entity.getCharmScore(),
                entity.getFeedbackTitle(),
                entity.getFeedbackContent(),
                entity.getCharmPointTitle(),
                entity.getCharmPointContent(),
                chats
        );
    }
}