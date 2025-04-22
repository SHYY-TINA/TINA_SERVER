package com.tina.tina_server.domain.emotion.service.implementation;

import com.tina.tina_server.domain.emotion.domain.ObservedEmotions;
import com.tina.tina_server.domain.emotion.domain.UserEmotions;
import com.tina.tina_server.domain.emotion.domain.repository.ObservedEmotionsRepository;
import com.tina.tina_server.domain.emotion.domain.repository.UserEmotionsRepository;
import com.tina.tina_server.domain.emotion.exception.EmotionNotFoundException;
import com.tina.tina_server.domain.user.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmotionDeleter {

    private final UserEmotionsRepository userEmotionsRepository;
    private final ObservedEmotionsRepository observedEmotionsRepository;

    public void deleteMyEmotion(Long emotionId, Users user) {
        UserEmotions emotion = userEmotionsRepository.findByIdAndUser(emotionId, user)
                .orElseThrow(EmotionNotFoundException::new);
        userEmotionsRepository.delete(emotion);
    }

    public void deleteOtherEmotion(Long emotionId, Users user) {
        ObservedEmotions emotion = observedEmotionsRepository.findByIdAndUser(emotionId, user)
                .orElseThrow(EmotionNotFoundException::new);
        observedEmotionsRepository.delete(emotion);
    }
}