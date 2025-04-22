package com.tina.tina_server.domain.emotion.presentation.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record ObserverEmotionRequest(
        String partnerName,
        String partnerMbti,
        MultipartFile file

) {
}
