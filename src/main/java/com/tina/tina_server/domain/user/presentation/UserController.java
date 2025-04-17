package com.tina.tina_server.domain.user.presentation;

import com.tina.tina_server.domain.user.presentation.dto.res.BasicInfoResponse;
import com.tina.tina_server.domain.user.presentation.dto.res.ProfileImageUrlResponse;
import com.tina.tina_server.domain.user.service.QueryUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.tina.tina_server.common.util.AuthenticationUtil.getUserId;
@Tag(name = "유저")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final QueryUserService queryUserService;

    @Operation(summary = "회원 기본 정보 조회")
    @GetMapping("/basic-info")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<BasicInfoResponse> getBasicInfo() {
        return ResponseEntity.ok(queryUserService.getBasicInfo(getUserId()));
    }

    @Operation(summary = "회원 프로필 사진 조회")
    @GetMapping("/profileImageUrl")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<ProfileImageUrlResponse> getProfileImageUrl() {
        return ResponseEntity.ok(queryUserService.getProfileImageUrl(getUserId()));
    }
}
