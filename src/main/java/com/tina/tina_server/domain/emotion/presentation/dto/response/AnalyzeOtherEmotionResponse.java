package com.tina.tina_server.domain.emotion.presentation.dto.response;

import java.util.List;

public record AnalyzeOtherEmotionResponse(
        Integer charmScore,
        String feedbackTitle,
        String feedbackContent,
        String tipTitle,
        String tipContent,
        String cautionTitle,
        String cautionContent,
        List<AnalyzedChat> chat
) {}