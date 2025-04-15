package com.tina.tina_server.domain.user.domain;

import com.tina.tina_server.domain.auth.domain.Token;
import com.tina.tina_server.domain.user.domain.vo.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String mbti;

    @Column(nullable = false)
    private String socialAccountUid;

    private String profileImageUrl;

    @Column(nullable = false)
    private Role role;

    public void updateDetail(String nickname, String mbti) {
        this.nickname = nickname;
        this.mbti = mbti;
    }
}
