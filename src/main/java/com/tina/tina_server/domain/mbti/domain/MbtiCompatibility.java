package com.tina.tina_server.domain.mbti.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MbtiCompatibility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 4)
    private String firstMbti;

    @Column(nullable = false, length = 4)
    private String secondMbti;

    private String title;

    private Integer heart;

    @Column(columnDefinition = "TEXT")
    private String communicationStyle;

    @Column(columnDefinition = "TEXT")
    private String strengthInRelationship;

    @Column(columnDefinition = "TEXT")
    private String caution;

    @Column(columnDefinition = "TEXT")
    private String tip;
}
