package com.tina.tina_server.domain.emotion.domain;

import com.tina.tina_server.domain.user.domain.Users;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ObservedEmotions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Users user;

    private String partnerName;

    private String partnerMbti;

    private Integer charmScore;

    private String feedbackTitle;

    @Column(columnDefinition = "TEXT")
    private String feedbackContent;

    private String tipTitle;

    @Column(columnDefinition = "TEXT")
    private String tipContent;

    private String cautionTitle;

    @Column(columnDefinition = "TEXT")
    private String cautionContent;
}
