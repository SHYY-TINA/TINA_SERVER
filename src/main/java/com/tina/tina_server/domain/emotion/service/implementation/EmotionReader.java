package com.tina.tina_server.domain.emotion.service.implementation;

import com.tina.tina_server.domain.emotion.domain.ObservedEmotions;
import com.tina.tina_server.domain.emotion.domain.UserEmotions;
import com.tina.tina_server.domain.emotion.domain.repository.ChatRepository;
import com.tina.tina_server.domain.emotion.domain.repository.ObservedEmotionsRepository;
import com.tina.tina_server.domain.emotion.domain.repository.UserEmotionsRepository;
import com.tina.tina_server.domain.emotion.domain.repository.CautionRepository;
import com.tina.tina_server.domain.emotion.domain.repository.TipRepository;
import com.tina.tina_server.domain.emotion.domain.repository.CharmPointRepository;
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
    private final CautionRepository cautionRepository;
    private final TipRepository tipRepository;
    private final CharmPointRepository charmPointRepository;

    public List<UserEmotionResponse> getAllMyEmotions(Users user) {
        return userEmotionsRepository.findAllByUser(user).stream()
                .map(emotion -> UserEmotionResponse.from(emotion, user.getNickname()))
                .toList();
    }

    public UserEmotionDetailResponse getMyEmotionDetail(Long emotionId, Users user) {
        UserEmotions emotion = userEmotionsRepository.findByIdAndUser(emotionId, user)
                .orElseThrow(EmotionNotFoundException::new);

        // type을 "User"로 설정하여 Chat을 조회
        List<AnalyzedChat> chats = chatRepository.findAllByEmotionIdAndType(emotionId, "User").stream()
                .map(chat -> new AnalyzedChat(chat.getChat(), chat.getMeaning()))
                .toList();

        List<String> charmPointContent = getCharmPointsContent(emotionId);  // charmPointContent 추가

        return UserEmotionDetailResponse.from(emotion, user.getNickname(), charmPointContent, chats);
    }

    public List<ObserverEmotionResponse> getAllOtherEmotions(Users user) {
        return observedEmotionsRepository.findAllByUser(user).stream()
                .map(emotion -> ObserverEmotionResponse.from(emotion, user.getNickname()))
                .toList();
    }

    public ObserverEmotionDetailResponse getOtherEmotionDetail(Long emotionId, Users user) {
        ObservedEmotions emotion = observedEmotionsRepository.findByIdAndUser(emotionId, user)
                .orElseThrow(EmotionNotFoundException::new);

        // type을 "Observer"로 설정하여 Chat을 조회
        List<AnalyzedChat> chats = chatRepository.findAllByEmotionIdAndType(emotionId, "Observer").stream()
                .map(chat -> new AnalyzedChat(chat.getChat(), chat.getMeaning()))
                .toList();

        List<String> tipContent = getTipsContent(emotionId);
        List<String> cautionContent = getCautionsContent(emotionId);

        return ObserverEmotionDetailResponse.from(emotion, user.getNickname(), chats, tipContent, cautionContent);
    }

    private List<String> getCautionsContent(Long emotionId) {
        return cautionRepository.findByEmotionId(emotionId).stream()
                .map(caution -> caution.getCaution())
                .toList();
    }

    private List<String> getTipsContent(Long emotionId) {
        return tipRepository.findByEmotionId(emotionId).stream()
                .map(tip -> tip.getTip())
                .toList();
    }

    private List<String> getCharmPointsContent(Long emotionId) {
        return charmPointRepository.findByEmotionId(emotionId).stream()
                .map(charmPoint -> charmPoint.getCharmPoint())
                .toList();
    }
}