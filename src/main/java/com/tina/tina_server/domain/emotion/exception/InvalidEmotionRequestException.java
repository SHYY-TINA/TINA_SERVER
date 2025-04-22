package com.tina.tina_server.domain.emotion.exception;

import com.tina.tina_server.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class InvalidEmotionRequestException extends BaseException {
    public InvalidEmotionRequestException() {
        super(HttpStatus.BAD_REQUEST, "INVALID_EMOTION_REQUEST", "감정 분석 요청이 유효하지 않습니다.");
    }
}