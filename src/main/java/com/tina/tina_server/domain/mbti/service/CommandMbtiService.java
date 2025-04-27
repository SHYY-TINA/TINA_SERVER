package com.tina.tina_server.domain.mbti.service;

import com.tina.tina_server.domain.mbti.domain.MbtiCompatibility;
import com.tina.tina_server.domain.mbti.presentation.dto.response.MbtiCompatibilityResponse;
import com.tina.tina_server.domain.mbti.service.implementation.MbtiReader;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommandMbtiService {
    private final MbtiReader mbtiReader;
    public MbtiCompatibilityResponse getCompatibility(String firstMbti, String secondMbti) {
        return mbtiReader.findCompatibility(firstMbti, secondMbti);
    }
}
