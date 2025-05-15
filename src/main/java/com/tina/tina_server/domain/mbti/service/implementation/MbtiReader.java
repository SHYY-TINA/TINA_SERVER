package com.tina.tina_server.domain.mbti.service.implementation;

import com.tina.tina_server.domain.mbti.domain.MbtiCompatibility;
import com.tina.tina_server.domain.mbti.domain.repository.MbtiCompatibilityRepository;
import com.tina.tina_server.domain.mbti.exception.MbtiCompatibilityNotFoundException;
import com.tina.tina_server.domain.mbti.presentation.dto.response.MbtiCompatibilityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MbtiReader {
    private final MbtiCompatibilityRepository mbtiCompatibilityRepository;

    public MbtiCompatibilityResponse findCompatibility(String firstMbti, String secondMbti) {
        MbtiCompatibility data = mbtiCompatibilityRepository
                .findByFirstMbtiAndSecondMbtiOrSecondMbtiAndFirstMbti(firstMbti, secondMbti, firstMbti, secondMbti)
                .orElseThrow(MbtiCompatibilityNotFoundException::new);

        return new MbtiCompatibilityResponse(
                data.getTitle(),
                data.getHeart(),
                data.getCommunicationStyle(),
                data.getStrengthInRelationship(),
                data.getCaution(),
                data.getTip()
        );
    }
}
