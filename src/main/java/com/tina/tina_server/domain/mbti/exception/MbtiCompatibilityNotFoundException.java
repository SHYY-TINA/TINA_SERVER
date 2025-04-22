package com.tina.tina_server.domain.mbti.exception;

import com.tina.tina_server.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class MbtiCompatibilityNotFoundException extends BaseException {
    public MbtiCompatibilityNotFoundException() {
        super(HttpStatus.NOT_FOUND, "MBTI_COMPATIBILITY_NOT_FOUND", "해당 MBTI 궁합 정보가 없습니다.");
    }
}