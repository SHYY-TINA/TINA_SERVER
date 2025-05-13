package com.tina.tina_server.domain.emotion.service;

import com.tina.tina_server.domain.emotion.presentation.dto.request.GuestObserverEmotionRequest;
import com.tina.tina_server.domain.emotion.presentation.dto.request.GuestUserEmotionRequest;
import com.tina.tina_server.domain.emotion.presentation.dto.request.ObserverEmotionRequest;
import com.tina.tina_server.domain.emotion.presentation.dto.request.UserEmotionRequest;
import com.tina.tina_server.domain.emotion.presentation.dto.response.AnalyzeMyEmotionResponse;
import com.tina.tina_server.domain.emotion.presentation.dto.response.AnalyzeOtherEmotionResponse;
import com.tina.tina_server.domain.emotion.presentation.dto.response.ObserverEmotionDetailResponse;
import com.tina.tina_server.domain.emotion.presentation.dto.response.UserEmotionDetailResponse;
import com.tina.tina_server.domain.emotion.service.implementation.EmotionCreator;
import com.tina.tina_server.domain.emotion.service.implementation.EmotionDeleter;
import com.tina.tina_server.domain.emotion.util.EmotionInferenceClient;
import com.tina.tina_server.domain.user.domain.Users;
import com.tina.tina_server.domain.user.service.implementation.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommandEmotionService {

    private final EmotionInferenceClient inferenceClient;
    private final UserReader userReader;

    private final EmotionCreator emotionCreator;
    private final EmotionDeleter emotionDeleter;

    public ObserverEmotionDetailResponse analyzeOtherEmotion(ObserverEmotionRequest req, Long userId) {
        Users user = userReader.findById(userId);

        AnalyzeOtherEmotionResponse ai = inferenceClient.inferForObserver(user.getNickname(), user.getMbti(), req);

        return emotionCreator.createOtherEmotion(user, ai, req.partnerMbti(), req.partnerName());
    }

    public AnalyzeOtherEmotionResponse analyzeGuestOtherEmotion(GuestObserverEmotionRequest req) {
        ObserverEmotionRequest observerReq = toObserverRequest(req);

        AnalyzeOtherEmotionResponse ai = inferenceClient.inferForObserver(req.userName(), req.userName(), observerReq);

        return ai;
    }

    public UserEmotionDetailResponse analyzeMyEmotion(UserEmotionRequest req, Long userId) {
        Users user = userReader.findById(userId);

        AnalyzeMyEmotionResponse ai = inferenceClient.inferForUser(user.getNickname(), user.getMbti(), req);

        return emotionCreator.createMyEmotion(user, ai, req.partnerMbti(), req.partnerName());
    }

    public AnalyzeMyEmotionResponse analyzeGuestMyEmotion(GuestUserEmotionRequest req) {
        UserEmotionRequest userReq = toUserRequest(req);

        AnalyzeMyEmotionResponse ai = inferenceClient.inferForUser(req.userName(), req.userName(), userReq);

        return ai;
    }

    public void deleteMyEmotion(Long emotionId, Long userId) {
        Users user = userReader.findById(userId);
        emotionDeleter.deleteMyEmotion(emotionId, user);
    }

    public void deleteOtherEmotion(Long emotionId, Long userId) {
        Users user = userReader.findById(userId);
        emotionDeleter.deleteOtherEmotion(emotionId, user);
    }

    public ObserverEmotionRequest toObserverRequest(GuestObserverEmotionRequest guestRequest) {
        return new ObserverEmotionRequest(
                guestRequest.partnerName(),
                guestRequest.partnerMbti(),
                guestRequest.file()
        );
    }

    public UserEmotionRequest toUserRequest(GuestUserEmotionRequest guestRequest) {
        return new UserEmotionRequest(
                guestRequest.partnerName(),
                guestRequest.partnerMbti(),
                guestRequest.file()
        );
    }
}