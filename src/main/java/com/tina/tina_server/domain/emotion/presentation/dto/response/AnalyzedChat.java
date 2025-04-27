package com.tina.tina_server.domain.emotion.presentation.dto.response;

public record AnalyzedChat(
        String text,
        String meaning
) {}