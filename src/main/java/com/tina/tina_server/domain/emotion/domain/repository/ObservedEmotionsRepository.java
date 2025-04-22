package com.tina.tina_server.domain.emotion.domain.repository;

import com.tina.tina_server.domain.emotion.domain.ObservedEmotions;
import com.tina.tina_server.domain.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ObservedEmotionsRepository extends JpaRepository<ObservedEmotions, Long> {

    Optional<ObservedEmotions> findByIdAndUser(Long id, Users user);

    List<ObservedEmotions> findAllByUser(Users user);
}
