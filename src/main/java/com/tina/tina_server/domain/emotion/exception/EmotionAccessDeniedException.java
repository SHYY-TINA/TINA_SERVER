package com.tina.tina_server.domain.emotion.exception;

import com.tina.tina_server.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class EmotionAccessDeniedException extends BaseException {
    public EmotionAccessDeniedException() {
        super(HttpStatus.FORBIDDEN, "EMOTION_ACCESS_DENIED", "해당 감정 데이터에 접근할 수 없습니다.");
    }
}