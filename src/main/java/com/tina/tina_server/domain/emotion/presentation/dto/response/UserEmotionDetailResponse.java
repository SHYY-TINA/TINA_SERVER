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
        List<String> charmPointContent,
        List<AnalyzedChat> chat
) {
    public static UserEmotionDetailResponse from(UserEmotions entity, String userNickname, List<String> charmPointContent, List<AnalyzedChat> chats) {
        return new UserEmotionDetailResponse(
                userNickname,
                entity.getPartnerName(),
                entity.getPartnerMbti(),
                entity.getCharmScore(),
                entity.getFeedbackTitle(),
                entity.getFeedbackContent(),
                entity.getCharmPointTitle(),
                charmPointContent,
                chats
        );
    }
}