package com.tina.tina_server.domain.auth.presentation.dto.res;

import com.tina.tina_server.common.jwt.dto.TokenResponse;

public record LoginResponse(
        TokenResponse tokenResponse,
        boolean detail
) {
}
