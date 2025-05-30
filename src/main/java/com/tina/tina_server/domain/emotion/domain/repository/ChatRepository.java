package com.tina.tina_server.domain.emotion.domain.repository;

import com.tina.tina_server.domain.emotion.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findAllByEmotionIdAndType(Long emotionId, String type);

}