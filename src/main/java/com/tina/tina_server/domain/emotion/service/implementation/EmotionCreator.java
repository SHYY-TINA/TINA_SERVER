package com.tina.tina_server.domain.emotion.service.implementation;

import com.tina.tina_server.domain.emotion.domain.*;
import com.tina.tina_server.domain.emotion.domain.repository.*;
import com.tina.tina_server.domain.emotion.presentation.dto.response.AnalyzeMyEmotionResponse;
import com.tina.tina_server.domain.emotion.presentation.dto.response.AnalyzeOtherEmotionResponse;
import com.tina.tina_server.domain.emotion.presentation.dto.response.AnalyzedChat;
import com.tina.tina_server.domain.emotion.presentation.dto.response.ObserverEmotionDetailResponse;
import com.tina.tina_server.domain.emotion.presentation.dto.response.UserEmotionDetailResponse;
import com.tina.tina_server.domain.user.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Observer;

@Service
@RequiredArgsConstructor
public class EmotionCreator {
    private final UserEmotionsRepository userEmotionsRepository;
    private final ObservedEmotionsRepository observedEmotionsRepository;
    private final CautionRepository cautionRepository;
    private final TipRepository tipRepository;
    private final CharmPointRepository charmPointRepository;
    private final ChatRepository chatRepository;

    public ObserverEmotionDetailResponse createOtherEmotion(Users user, AnalyzeOtherEmotionResponse ai, String partnerMbti, String partnerName) {
        ObservedEmotions observedEmotion = observedEmotionsRepository.save(
                ObservedEmotions.builder()
                        .user(user)
                        .partnerName(partnerName)
                        .partnerMbti(partnerMbti)
                        .charmScore(ai.charmScore())
                        .feedbackTitle(ai.feedbackTitle())
                        .feedbackContent(ai.feedbackContent())
                        .tipTitle(ai.tipTitle())
                        .cautionTitle(ai.cautionTitle())
                        .build()
        );

        saveCautions(observedEmotion.getId(), ai.cautionContent());
        saveTips(observedEmotion.getId(), ai.tipContent());
        saveChats(ai.chat(), observedEmotion.getId(),"Observer");

        List<String> cautionContent = getCautionsContent(observedEmotion.getId());
        List<String> tipContent = getTipsContent(observedEmotion.getId());

        List<AnalyzedChat> chats = ai.chat();
        return ObserverEmotionDetailResponse.from(observedEmotion, user.getNickname(), chats, tipContent, cautionContent);
    }

    public UserEmotionDetailResponse createMyEmotion(Users user, AnalyzeMyEmotionResponse ai, String partnerMbti, String partnerName) {
        UserEmotions userEmotion = userEmotionsRepository.save(
                UserEmotions.builder()
                        .user(user)
                        .partnerName(partnerName)
                        .partnerMbti(partnerMbti)
                        .charmScore(ai.charmScore())
                        .feedbackTitle(ai.feedbackTitle())
                        .feedbackContent(ai.feedbackContent())
                        .charmPointTitle(ai.charmPointTitle())
                        .build()
        );

        saveCharmPoints(userEmotion.getId(), ai.charmPointContent());
        saveChats(ai.chat(), userEmotion.getId(),"User");

        List<String> charmPointContent = getCharmPointsContent(userEmotion.getId());
        List<AnalyzedChat> chats = ai.chat();
        return UserEmotionDetailResponse.from(userEmotion, user.getNickname(), charmPointContent, chats);
    }

    private void saveCautions(Long emotionId, List<String> cautions) {
        List<Caution> cautionEntities = cautions.stream()
                .map(content -> Caution.builder()
                        .emotionId(emotionId)
                        .caution(content)
                        .build())
                .toList();

        cautionRepository.saveAll(cautionEntities);
    }

    private void saveTips(Long emotionId, List<String> tips) {
        List<Tip> tipEntities = tips.stream()
                .map(content -> Tip.builder()
                        .emotionId(emotionId)
                        .tip(content)
                        .build())
                .toList();

        tipRepository.saveAll(tipEntities);
    }

    private void saveCharmPoints(Long emotionId, List<String> charmPoints) {
        List<CharmPoint> charmPointEntities = charmPoints.stream()
                .map(content -> CharmPoint.builder()
                        .emotionId(emotionId)
                        .charmPoint(content)
                        .build())
                .toList();

        charmPointRepository.saveAll(charmPointEntities);
    }

    private void saveChats(List<AnalyzedChat> chats, Long emotionId, String type) {
        List<Chat> chatEntities = chats.stream()
                .map(c -> Chat.builder()
                        .emotionId(emotionId)
                        .type(type)
                        .chat(c.text())
                        .meaning(c.meaning())
                        .build())
                .toList();

        chatRepository.saveAll(chatEntities);
    }

    private List<String> getCautionsContent(Long emotionId) {
        return cautionRepository.findByEmotionId(emotionId).stream()
                .map(Caution::getCaution)
                .toList();
    }

    private List<String> getTipsContent(Long emotionId) {
        return tipRepository.findByEmotionId(emotionId).stream()
                .map(Tip::getTip)
                .toList();
    }

    private List<String> getCharmPointsContent(Long emotionId) {
        return charmPointRepository.findByEmotionId(emotionId).stream()
                .map(CharmPoint::getCharmPoint)
                .toList();
    }
}