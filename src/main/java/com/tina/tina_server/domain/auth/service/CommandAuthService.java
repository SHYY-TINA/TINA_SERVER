package com.tina.tina_server.domain.auth.service;

import com.tina.tina_server.common.jwt.dto.TokenResponse;
import com.tina.tina_server.domain.auth.domain.Token;
import com.tina.tina_server.domain.auth.infra.dto.SocialPlatformUserInfo;
import com.tina.tina_server.domain.auth.infra.oauth.KakaoClient;
import com.tina.tina_server.domain.auth.presentation.dto.DetailRequest;
import com.tina.tina_server.domain.auth.presentation.dto.LoginResponse;
import com.tina.tina_server.domain.auth.presentation.dto.TokenRefreshRequest;
import com.tina.tina_server.domain.auth.service.implementation.AuthDeleter;
import com.tina.tina_server.domain.auth.service.implementation.AuthReader;
import com.tina.tina_server.domain.auth.service.implementation.AuthUpdater;
import com.tina.tina_server.domain.user.domain.Users;
import com.tina.tina_server.domain.user.service.implementation.UserCreator;
import com.tina.tina_server.domain.user.service.implementation.UserDeleter;
import com.tina.tina_server.domain.user.service.implementation.UserReader;
import com.tina.tina_server.domain.user.service.implementation.UserUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommandAuthService {
    private final KakaoClient kakaoClient;
    private final UserReader userReader;
    private final UserUpdater userUpdater;
    private final UserCreator userCreator;
    private final UserDeleter userDeleter;
    private final AuthReader authReader;
    private final AuthUpdater authUpdater;
    private final AuthDeleter authDeleter;

    public LoginResponse login(String code){
        SocialPlatformUserInfo userInfo = kakaoClient.getUserInfo(kakaoClient.getAccessToken(code));

        Optional<Users> user = userReader.findBySocialAccountUid(userInfo.userId());

        if(user.isPresent()) {
            Users existingUser = user.get();
            Token token = authReader.findByUser(user);
            TokenResponse tokenResponse = authUpdater.refreshToken(token);

            boolean detail = existingUser.getMbti() != null;

            return new LoginResponse(tokenResponse, detail);
        } else {
            Users signupUser = userCreator.signup(userInfo.userId(), userInfo.profileImageUrl());
            TokenResponse tokenResponse = authUpdater.publishToken(signupUser, null);

            return new LoginResponse(tokenResponse, false);
        }
    }

    public void logout(TokenRefreshRequest request){
        Token existingToken = authReader.findByRefreshToken(request.refreshToken());
        authDeleter.deleteRefreshToken(existingToken);
    }

    public void submitAdditionalInfo(DetailRequest req, Long id){
        Users user = userReader.findById(id);
        userUpdater.updateDetail(user, req.nickname(), req.mbti());
    }

    public void quitUser(Long id){
        Users user = userReader.findById(id);
        userDeleter.delete(user);
    }
}
