package com.tina.tina_server.domain.emotion.presentation;

import com.fasterxml.jackson.databind.JsonNode;
import com.tina.tina_server.domain.emotion.presentation.dto.request.ObserverEmotionRequest;
import com.tina.tina_server.domain.emotion.presentation.dto.request.UserEmotionRequest;
import com.tina.tina_server.domain.emotion.presentation.dto.response.ObserverEmotionDetailResponse;
import com.tina.tina_server.domain.emotion.presentation.dto.response.ObserverEmotionResponse;
import com.tina.tina_server.domain.emotion.presentation.dto.response.UserEmotionDetailResponse;
import com.tina.tina_server.domain.emotion.presentation.dto.response.UserEmotionResponse;
import com.tina.tina_server.domain.emotion.service.CommandEmotionService;
import com.tina.tina_server.domain.emotion.service.QueryEmotionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.tina.tina_server.common.util.AuthenticationUtil.getUserId;

@Tag(name = "감정 분석")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/emotion")
@PreAuthorize("hasAuthority('ROLE_USER')")
public class EmotionController {
    private final CommandEmotionService commandEmotionService;
    private final QueryEmotionService queryEmotionService;

    @Operation(summary = "상대 감정 분석")
    @PostMapping(value = "/observer", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ObserverEmotionDetailResponse> analyzeOtherEmotion(@ModelAttribute ObserverEmotionRequest req) {
        return ResponseEntity.ok(commandEmotionService.analyzeOtherEmotion(req,getUserId()));
    }

    @Operation(summary = "내 감정 분석")
    @PostMapping(value = "/user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserEmotionDetailResponse> analyzeMyEmotion(@ModelAttribute UserEmotionRequest req) {
        return ResponseEntity.ok(commandEmotionService.analyzeMyEmotion(req,getUserId()));
    }

    @Operation(summary = "내 감정 분석 전체 조회")
    @GetMapping("/user")
    public ResponseEntity<List<UserEmotionResponse>> getMyEmotions() {
        return ResponseEntity.ok(queryEmotionService.getAllMyEmotions(getUserId()));
    }

    @Operation(summary = "내 감정 분석 상세 조회")
    @GetMapping("/user/{emotionId}")
    public ResponseEntity<UserEmotionDetailResponse> getMyEmotionDetail(@PathVariable Long emotionId) {
        return ResponseEntity.ok(queryEmotionService.getMyEmotionDetail(emotionId, getUserId()));
    }

    @Operation(summary = "내 감정 분석 삭제")
    @DeleteMapping("/user")
    public ResponseEntity<Void> deleteMyEmotion(@RequestParam Long emotionId) {
        commandEmotionService.deleteMyEmotion(emotionId, getUserId());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "상대 감정 분석 전체 조회")
    @GetMapping("/observer")
    public ResponseEntity<List<ObserverEmotionResponse>> getOtherEmotions() {
        return ResponseEntity.ok(queryEmotionService.getAllOtherEmotions(getUserId()));
    }

    @Operation(summary = "상대 감정 분석 상세 조회")
    @GetMapping("/observer/{emotionId}")
    public ResponseEntity<ObserverEmotionDetailResponse> getOtherEmotionDetail(@PathVariable Long emotionId) {
        return ResponseEntity.ok(queryEmotionService.getOtherEmotionDetail(emotionId, getUserId()));
    }

    @Operation(summary = "상대 감정 분석 삭제")
    @DeleteMapping("/observer")
    public ResponseEntity<Void> deleteOtherEmotion(@RequestParam Long emotionId) {
        commandEmotionService.deleteOtherEmotion(emotionId, getUserId());
        return ResponseEntity.noContent().build();
    }
}