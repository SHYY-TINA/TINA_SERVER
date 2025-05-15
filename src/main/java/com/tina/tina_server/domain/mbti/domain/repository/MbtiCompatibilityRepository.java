package com.tina.tina_server.domain.mbti.domain.repository;

import com.tina.tina_server.domain.mbti.domain.MbtiCompatibility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface MbtiCompatibilityRepository extends JpaRepository<MbtiCompatibility, Long> {
    Optional<MbtiCompatibility> findByFirstMbtiAndSecondMbtiOrSecondMbtiAndFirstMbti(
            String firstMbti1, String secondMbti1,
            String secondMbti2, String firstMbti2
    );
}