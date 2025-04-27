package com.tina.tina_server.domain.emotion.presentation.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record UserEmotionRequest(
        String partnerName,
        String partnerMbti,
        MultipartFile file
) {
}
