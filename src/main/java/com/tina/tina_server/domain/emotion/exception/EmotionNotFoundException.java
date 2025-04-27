package com.tina.tina_server.domain.emotion.exception;

import com.tina.tina_server.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class EmotionNotFoundException extends BaseException {
    public EmotionNotFoundException() {
        super(HttpStatus.NOT_FOUND, "EMOTION_NOT_FOUND", "해당 감정 데이터를 찾을 수 없습니다.");
    }
}