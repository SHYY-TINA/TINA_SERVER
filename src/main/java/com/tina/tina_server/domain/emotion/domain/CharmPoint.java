package com.tina.tina_server.domain.emotion.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CharmPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long emotionId;

    @Column(columnDefinition = "TEXT")
    private String charmPoint;
}
