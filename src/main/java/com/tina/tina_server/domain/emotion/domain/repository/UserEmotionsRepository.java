package com.tina.tina_server.domain.emotion.domain.repository;

import com.tina.tina_server.domain.emotion.domain.UserEmotions;
import com.tina.tina_server.domain.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserEmotionsRepository extends JpaRepository<UserEmotions, Long> {

    Optional<UserEmotions> findByIdAndUser(Long id, Users user);

    List<UserEmotions> findAllByUser(Users user);
}
