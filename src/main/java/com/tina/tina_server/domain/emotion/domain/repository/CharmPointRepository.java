package com.tina.tina_server.domain.emotion.domain.repository;

import com.tina.tina_server.domain.emotion.domain.CharmPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharmPointRepository extends JpaRepository<CharmPoint, Long> {
    List<CharmPoint> findByEmotionId(Long emotionId);

}
