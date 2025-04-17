package com.tina.tina_server.domain.user.service;

import com.tina.tina_server.domain.user.domain.Users;
import com.tina.tina_server.domain.user.presentation.dto.res.BasicInfoResponse;
import com.tina.tina_server.domain.user.presentation.dto.res.ProfileImageUrlResponse;
import com.tina.tina_server.domain.user.service.implementation.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QueryUserService {
    private final UserReader userReader;
    public BasicInfoResponse getBasicInfo(Long id) {
        Users user = userReader.findById(id);
        return new BasicInfoResponse(
                user.getNickname(),
                user.getMbti()
        );
    }

    public ProfileImageUrlResponse getProfileImageUrl(Long id) {
        Users user = userReader.findById(id);
        return new ProfileImageUrlResponse(
                user.getProfileImageUrl()
        );
    }
}
