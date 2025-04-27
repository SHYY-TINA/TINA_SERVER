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
        String sortedMbti1 = firstMbti.compareTo(secondMbti) <= 0 ? firstMbti : secondMbti;
        String sortedMbti2 = firstMbti.compareTo(secondMbti) <= 0 ? secondMbti : firstMbti;

        MbtiCompatibility data = mbtiCompatibilityRepository.findByFirstMbtiAndSecondMbti(sortedMbti1, sortedMbti2)
                .orElseThrow(() -> new MbtiCompatibilityNotFoundException());

        return new MbtiCompatibilityResponse(
                data.getCommunicationStyle(),
                data.getStrengthInRelationship(),
                data.getCaution(),
                data.getTip()
        );
    }
}
