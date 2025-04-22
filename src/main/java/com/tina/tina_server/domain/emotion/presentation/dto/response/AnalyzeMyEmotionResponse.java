package com.tina.tina_server.domain.emotion.presentation.dto.response;

import java.util.List;

public record AnalyzeMyEmotionResponse(
        Integer charmScore,
        String feedbackTitle,
        String feedbackContent,
        String CharmPointTitle,
        String CharmPointContent,
        List<AnalyzedChat> chat
) {
}
