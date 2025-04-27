package com.tina.tina_server.domain.emotion.exception;

import com.tina.tina_server.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class AnalyzeApiCallFailedException extends BaseException {
    public AnalyzeApiCallFailedException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "ANALYZE_API_CALL_FAILED", "감정 분석 API 호출에 실패했습니다.");
    }
}