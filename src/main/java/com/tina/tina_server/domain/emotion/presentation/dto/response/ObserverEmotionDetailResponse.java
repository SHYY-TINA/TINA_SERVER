package com.tina.tina_server.domain.emotion.presentation.dto.response;

import com.tina.tina_server.domain.emotion.domain.ObservedEmotions;

import java.util.List;

public record ObserverEmotionDetailResponse(
        String userNickname,
        String partnerName,
        String partnerMbti,
        int charmScore,
        String feedbackTitle,
        String feedbackContent,
        String tipTitle,
        List<String> tipContent,
        String cautionTitle,
        List<String> cautionContent,
        List<AnalyzedChat> chats
) {
    public static ObserverEmotionDetailResponse from(ObservedEmotions entity, String userNickname, List<AnalyzedChat> chats, List<String> tipContent, List<String> cautionContent) {
        return new ObserverEmotionDetailResponse(
                userNickname,
                entity.getPartnerName(),
                entity.getPartnerMbti(),
                entity.getCharmScore(),
                entity.getFeedbackTitle(),
                entity.getFeedbackContent(),
                entity.getTipTitle(),
                tipContent,
                entity.getCautionTitle(),
                cautionContent,
                chats
        );
    }
}