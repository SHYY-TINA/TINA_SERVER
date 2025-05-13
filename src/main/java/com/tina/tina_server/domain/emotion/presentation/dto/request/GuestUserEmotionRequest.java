package com.tina.tina_server.domain.emotion.presentation.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record GuestUserEmotionRequest(
        String userName,
        String userMbti,
        String partnerName,
        String partnerMbti,
        MultipartFile file
) {
}
