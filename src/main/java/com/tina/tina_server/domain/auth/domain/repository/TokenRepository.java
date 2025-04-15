package com.tina.tina_server.domain.auth.domain.repository;

import com.tina.tina_server.domain.auth.domain.Token;
import com.tina.tina_server.domain.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByUser(Users users);

    Optional<Token> findByRefreshToken(String refreshToken);
}
