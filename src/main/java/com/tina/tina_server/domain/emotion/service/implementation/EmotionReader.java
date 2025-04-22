package com.tina.tina_server.domain.emotion.service.implementation;

import com.tina.tina_server.domain.emotion.domain.Chat;
import com.tina.tina_server.domain.emotion.domain.ObservedEmotions;
import com.tina.tina_server.domain.emotion.domain.UserEmotions;
import com.tina.tina_server.domain.emotion.domain.repository.ChatRepository;
import com.tina.tina_server.domain.emotion.domain.repository.ObservedEmotionsRepository;
import com.tina.tina_server.domain.emotion.domain.repository.UserEmotionsRepository;
import com.tina.tina_server.domain.emotion.presentation.dto.response.AnalyzedChat;
import com.tina.tina_server.domain.emotion.presentation.dto.response.ObserverEmotionDetailResponse;
import com.tina.tina_server.domain.emotion.presentation.dto.response.ObserverEmotionResponse;
import com.tina.tina_server.domain.emotion.presentation.dto.response.UserEmotionDetailResponse;
import com.tina.tina_server.domain.emotion.presentation.dto.response.UserEmotionResponse;
import com.tina.tina_server.domain.emotion.exception.EmotionNotFoundException;
import com.tina.tina_server.domain.user.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmotionReader {

    private final UserEmotionsRepository userEmotionsRepository;
    private final ObservedEmotionsRepository observedEmotionsRepository;
    private final ChatRepository chatRepository;

    public List<UserEmotionResponse> getAllMyEmotions(Users user) {
        return userEmotionsRepository.findAllByUser(user).stream()
                .map(emotion -> UserEmotionResponse.from(emotion, user.getNickname()))
                .toList();
    }

    public UserEmotionDetailResponse getMyEmotionDetail(Long emotionId, Users user) {
        UserEmotions emotion = userEmotionsRepository.findByIdAndUser(emotionId, user)
                .orElseThrow(EmotionNotFoundException::new);

        List<AnalyzedChat> chats = chatRepository.findAllByEmotionId(emotionId).stream()
                .map(chat -> new AnalyzedChat(chat.getChat(), chat.getMeaning()))
                .toList();

        return UserEmotionDetailResponse.from(emotion, user.getNickname(), chats);
    }

    public List<ObserverEmotionResponse> getAllOtherEmotions(Users user) {
        return observedEmotionsRepository.findAllByUser(user).stream()
                .map(emotion -> ObserverEmotionResponse.from(emotion, user.getNickname()))
                .toList();
    }

    public ObserverEmotionDetailResponse getOtherEmotionDetail(Long emotionId, Users user) {
        ObservedEmotions emotion = observedEmotionsRepository.findByIdAndUser(emotionId, user)
                .orElseThrow(EmotionNotFoundException::new);

        List<AnalyzedChat> chats = chatRepository.findAllByEmotionId(emotionId).stream()
                .map(chat -> new AnalyzedChat(chat.getChat(), chat.getMeaning()))
                .toList();

        return ObserverEmotionDetailResponse.from(emotion, user.getNickname(), chats);
    }
}