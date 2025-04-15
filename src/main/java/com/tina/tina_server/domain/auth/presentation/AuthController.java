package com.tina.tina_server.domain.auth.presentation;

import com.tina.tina_server.domain.auth.presentation.dto.DetailRequest;
import com.tina.tina_server.domain.auth.presentation.dto.LoginResponse;
import com.tina.tina_server.domain.auth.presentation.dto.TokenRefreshRequest;
import com.tina.tina_server.domain.auth.service.CommandAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.tina.tina_server.common.util.AuthenticationUtil.getUserId;


@Tag(name = "인증/인가")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final CommandAuthService commandAuthService;

    @Operation(summary = "카카오 로그인 / 가입")
    @GetMapping("/login")
    public ResponseEntity<LoginResponse> loginKakao(@RequestParam String code) {
        return ResponseEntity.ok(commandAuthService.login(code));
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<Void> logout(@Valid @RequestBody TokenRefreshRequest req) {
        commandAuthService.logout(req);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "상세 정보 요청")
    @PatchMapping("/detail")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<Void> submitAdditionalInfo(@RequestBody DetailRequest req) {
        commandAuthService.submitAdditionalInfo(req,getUserId());
        return ResponseEntity.noContent().build();
    }
}