package com.tina.tina_server.domain.emotion.service;

import com.tina.tina_server.domain.emotion.presentation.dto.response.ObserverEmotionDetailResponse;
import com.tina.tina_server.domain.emotion.presentation.dto.response.ObserverEmotionResponse;
import com.tina.tina_server.domain.emotion.presentation.dto.response.UserEmotionDetailResponse;
import com.tina.tina_server.domain.emotion.presentation.dto.response.UserEmotionResponse;
import com.tina.tina_server.domain.emotion.service.implementation.EmotionReader;
import com.tina.tina_server.domain.user.domain.Users;
import com.tina.tina_server.domain.user.service.implementation.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QueryEmotionService {

    private final EmotionReader emotionReader;
    private final UserReader userReader;

    public List<UserEmotionResponse> getAllMyEmotions(Long userId) {
        Users user = userReader.findById(userId);
        return emotionReader.getAllMyEmotions(user);
    }

    public UserEmotionDetailResponse getMyEmotionDetail(Long emotionId, Long userId) {
        Users user = userReader.findById(userId);
        return emotionReader.getMyEmotionDetail(emotionId, user);
    }

    public List<ObserverEmotionResponse> getAllOtherEmotions(Long userId) {
        Users user = userReader.findById(userId);
        return emotionReader.getAllOtherEmotions(user);
    }

    public ObserverEmotionDetailResponse getOtherEmotionDetail(Long emotionId, Long userId) {
        Users user = userReader.findById(userId);
        return emotionReader.getOtherEmotionDetail(emotionId, user);
    }
}