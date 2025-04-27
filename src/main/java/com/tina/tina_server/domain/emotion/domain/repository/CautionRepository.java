package com.tina.tina_server.domain.emotion.domain.repository;

import com.tina.tina_server.domain.emotion.domain.Caution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CautionRepository extends JpaRepository<Caution, Long> {
    List<Caution> findByEmotionId(Long emotionId);

}
