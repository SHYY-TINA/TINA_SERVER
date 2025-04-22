package com.tina.tina_server.domain.emotion.service.implementation;

import com.tina.tina_server.domain.emotion.domain.Chat;
import com.tina.tina_server.domain.emotion.domain.ObservedEmotions;
import com.tina.tina_server.domain.emotion.domain.UserEmotions;
import com.tina.tina_server.domain.emotion.domain.repository.ChatRepository;
import com.tina.tina_server.domain.emotion.domain.repository.ObservedEmotionsRepository;
import com.tina.tina_server.domain.emotion.domain.repository.UserEmotionsRepository;
import com.tina.tina_server.domain.emotion.presentation.dto.response.AnalyzeMyEmotionResponse;
import com.tina.tina_server.domain.emotion.presentation.dto.response.AnalyzeOtherEmotionResponse;
import com.tina.tina_server.domain.emotion.presentation.dto.response.AnalyzedChat;
import com.tina.tina_server.domain.emotion.presentation.dto.response.ObserverEmotionDetailResponse;
import com.tina.tina_server.domain.emotion.presentation.dto.response.UserEmotionDetailResponse;
import com.tina.tina_server.domain.user.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmotionCreator {

    private final UserEmotionsRepository userEmotionsRepository;
    private final ObservedEmotionsRepository observedEmotionsRepository;
    private final ChatRepository chatRepository;

    public ObserverEmotionDetailResponse createOtherEmotion(Users user, AnalyzeOtherEmotionResponse ai, String partnerMbti, String partnerName) {
        ObservedEmotions emotion = observedEmotionsRepository.save(
                ObservedEmotions.builder()
                        .user(user)
                        .partnerName(partnerName)
                        .partnerMbti(partnerMbti)
                        .charmScore(ai.charmScore())
                        .feedbackTitle(ai.feedbackTitle())
                        .feedbackContent(ai.feedbackContent())
                        .tipTitle(ai.tipTitle())
                        .tipContent(ai.tipContent())
                        .cautionTitle(ai.cautionTitle())
                        .cautionContent(ai.cautionContent())
                        .build()
        );

        List<AnalyzedChat> chats = ai.chat();
        saveChats(chats, emotion.getId());

        return ObserverEmotionDetailResponse.from(emotion, user.getNickname(), chats);
    }

    public UserEmotionDetailResponse createMyEmotion(Users user, AnalyzeMyEmotionResponse ai, String partnerMbti, String partnerName) {
        UserEmotions emotion = userEmotionsRepository.save(
                UserEmotions.builder()
                        .user(user)
                        .partnerName(partnerName)
                        .partnerMbti(partnerMbti)
                        .charmScore(ai.charmScore())
                        .feedbackTitle(ai.feedbackTitle())
                        .feedbackContent(ai.feedbackContent())
                        .charmPointTitle(ai.CharmPointTitle())
                        .charmPointContent(ai.CharmPointContent())
                        .build()
        );

        List<AnalyzedChat> chats = ai.chat();
        saveChats(chats, emotion.getId());

        return UserEmotionDetailResponse.from(emotion, user.getNickname(), chats);
    }

    private void saveChats(List<AnalyzedChat> chats, Long emotionId) {
        List<Chat> chatEntities = chats.stream()
                .map(c -> Chat.builder()
                        .emotionId(emotionId)
                        .chat(c.text())
                        .meaning(c.meaning())
                        .build())
                .toList();

        chatRepository.saveAll(chatEntities);
    }
}