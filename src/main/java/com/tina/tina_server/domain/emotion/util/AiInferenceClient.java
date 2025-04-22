package com.tina.tina_server.domain.emotion.util;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class AiInferenceClient {

    private final RestTemplate restTemplate;

    @Value("${external.ai.url}")
    private String aiUrl;  // AI URL

    @Value("${external.ai.token}")
    private String aiToken;  // 인증 토큰

    public AiInferenceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public JsonNode requestAnalysis(String nickname, String mbti, String partnerName, String partnerMbti, List<String> fileIds) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(aiToken);  // 인증 토큰 설정

            // 메시지 구성
            String requestBody = buildRequestBody(nickname, mbti, partnerName, partnerMbti, fileIds);  // 파일 ID들을 포함한 요청 본문

            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<JsonNode> response = restTemplate.exchange(
                    aiUrl + "/api/chat/completions",  // AI 분석 요청 URL
                    HttpMethod.POST,
                    requestEntity,
                    JsonNode.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                throw new RuntimeException("AI 분석 요청 실패");
            }

        } catch (Exception e) {
            throw new RuntimeException("AI 분석 요청 실패", e);
        }
    }

    private String buildRequestBody(String nickname, String mbti, String partnerName, String partnerMbti, List<String> fileIds) {
        // GPT 모델에 맞는 요청 본문 생성
        StringBuilder fileIdsJson = new StringBuilder();
        for (String fileId : fileIds) {
            fileIdsJson.append("{\"type\": \"file\", \"id\": \"").append(fileId).append("\"},");
        }

        if (fileIdsJson.length() > 0) {
            fileIdsJson.setLength(fileIdsJson.length() - 1);  // 마지막 쉼표 제거
        }

        return "{\n" +
                "  \"model\": \"gpt-4-turbo\",\n" +
                "  \"messages\": [\n" +
                "    {\"role\": \"user\", \"content\": \"내 MBTI는 " + mbti + "이고, 상대방의 MBTI는 " + partnerMbti + "입니다. 이 파일들을 기반으로 분석해 주세요.\"}\n" +
                "  ],\n" +
                "  \"files\": [\n" +
                fileIdsJson.toString() +
                "  ]\n" +
                "}";
    }
}