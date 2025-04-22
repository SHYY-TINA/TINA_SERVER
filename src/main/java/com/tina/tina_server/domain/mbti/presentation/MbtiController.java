package com.tina.tina_server.domain.mbti.presentation;

import com.tina.tina_server.domain.mbti.presentation.dto.response.MbtiCompatibilityResponse;
import com.tina.tina_server.domain.mbti.service.CommandMbtiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "MBTI")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mbti")
public class MbtiController {
    private final CommandMbtiService commandMbtiService;
    @Operation(summary = "MBTI 궁합 조회")
    @GetMapping("/compatibility")
    public ResponseEntity<MbtiCompatibilityResponse> getCompatibility(
            @RequestParam String firstMbti,
            @RequestParam String secondMbti) {
        return ResponseEntity.ok(
                commandMbtiService.getCompatibility(firstMbti.toUpperCase(), secondMbti.toUpperCase()));
    }
}
