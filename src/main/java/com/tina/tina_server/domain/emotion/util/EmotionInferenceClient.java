package com.tina.tina_server.domain.emotion.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.tina.tina_server.domain.emotion.presentation.dto.request.ObserverEmotionRequest;
import com.tina.tina_server.domain.emotion.presentation.dto.response.AnalyzeMyEmotionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmotionInferenceClient {

    private final ParsingClient parsingClient;
    private final FileUploader fileUploader;
//    private final KnowledgeManager knowledgeManager;
    private final AiInferenceClient aiInferenceClient;

    public JsonNode inferForUser(String nickname, String mbti, ObserverEmotionRequest req) {
        // 0단계: 파싱 서버에 파일 전송 & 결과로 ZIP 바이트 반환
        byte[] zippedCsv = parsingClient.uploadAndParse(req.file());

        // 1단계: 압축 해제 후 파일들을 업로드하여 file_id 목록 반환
        List<String> fileIds = fileUploader.uploadCSVFilesFromZip(zippedCsv);

//        // 2단계: file_id 들을 knowledge에 추가
//        knowledgeManager.attachFilesToKnowledge(fileIds);

        // 3단계: AI 분석 요청 (구현 예정)
        //
        return aiInferenceClient.requestAnalysis(nickname, mbti,req.partnerName(),req.partnerMbti(),fileIds);
    }

}