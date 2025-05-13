package com.tina.tina_server.domain.emotion.presentation;

import com.tina.tina_server.domain.emotion.presentation.dto.request.GuestObserverEmotionRequest;
import com.tina.tina_server.domain.emotion.presentation.dto.request.GuestUserEmotionRequest;
import com.tina.tina_server.domain.emotion.presentation.dto.response.*;
import com.tina.tina_server.domain.emotion.service.CommandEmotionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "게스트 감정 분석")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/guest/emotion")
public class GuestGuestController {
    private final CommandEmotionService commandEmotionService;

    @Operation(summary = "게스트 상대 감정 분석")
    @PostMapping(value = "/observer", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AnalyzeOtherEmotionResponse> analyzeGuestOtherEmotion(@ModelAttribute GuestObserverEmotionRequest req) {
        return ResponseEntity.ok(commandEmotionService.analyzeGuestOtherEmotion(req));
    }

    @Operation(summary = "게스트 내 감정 분석")
    @PostMapping(value = "/user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AnalyzeMyEmotionResponse> analyzeGuestMyEmotion(@ModelAttribute GuestUserEmotionRequest req) {
        return ResponseEntity.ok(commandEmotionService.analyzeGuestMyEmotion(req));
    }
}
