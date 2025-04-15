package com.tina.tina_server.domain.user.domain.repository;

import com.tina.tina_server.domain.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findBySocialAccountUid(String socialAccountUid);

}
