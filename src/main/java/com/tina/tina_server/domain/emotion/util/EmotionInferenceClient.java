package com.tina.tina_server.domain.emotion.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tina.tina_server.domain.emotion.exception.AnalyzeApiCallFailedException;
import com.tina.tina_server.domain.emotion.exception.EmotionNotFoundException;
import com.tina.tina_server.domain.emotion.presentation.dto.request.ObserverEmotionRequest;
import com.tina.tina_server.domain.emotion.presentation.dto.request.UserEmotionRequest;
import com.tina.tina_server.domain.emotion.presentation.dto.response.AnalyzeMyEmotionResponse;
import com.tina.tina_server.domain.emotion.presentation.dto.response.AnalyzeOtherEmotionResponse;
import com.tina.tina_server.domain.emotion.presentation.dto.response.AnalyzedChat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmotionInferenceClient {

    private final ParsingClient parsingClient;
    private final FileUploader fileUploader;
    private final AiInferenceClient aiInferenceClient;
    private final RestTemplate restTemplate;

    @Value("${external.parsing.url}")
    private String parsingUrl;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonNode OriginAiServerClient(String nickname, String mbti, ObserverEmotionRequest req) {
        byte[] zippedCsv = parsingClient.uploadAndParse(req.file());
        List<String> fileIds = fileUploader.uploadCSVFilesFromZip(zippedCsv);
        return aiInferenceClient.requestAnalysis(nickname, mbti, req.partnerName(), req.partnerMbti(), fileIds);
    }

    public AnalyzeOtherEmotionResponse inferForObserver(String nickname, String mbti, ObserverEmotionRequest req) {
        List<Map<String, Object>> response = callAnalyzeApi(nickname, mbti, req.partnerName(), req.partnerMbti(), req.file());

        Map<String, Object> observerPart = response.stream()
                .filter(item -> "observer".equals(item.get("type")))
                .findFirst()
                .orElseThrow(() -> new EmotionNotFoundException());

        return mapToAnalyzeOtherEmotionResponse(observerPart);
    }

    public AnalyzeMyEmotionResponse inferForUser(String nickname, String mbti, UserEmotionRequest req) {
        List<Map<String, Object>> response = callAnalyzeApi(nickname, mbti, req.partnerName(), req.partnerMbti(), req.file());

        Map<String, Object> userPart = response.stream()
                .filter(item -> "user".equals(item.get("type")))
                .findFirst()
                .orElseThrow(() -> new EmotionNotFoundException());

        return mapToAnalyzeMyEmotionResponse(userPart);
    }

    private List<Map<String, Object>> callAnalyzeApi(String userNickname, String userMbti, String partnerName, String partnerMbti, MultipartFile file) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("userNickname", userNickname);
            body.add("userMBTI", userMbti);
            body.add("partnerName", partnerName);
            body.add("partnerMBTI", partnerMbti);

            if (file != null && !file.isEmpty()) {
                log.info("파일 이름: {}", file.getOriginalFilename());
                body.add("file", new ByteArrayResource(file.getBytes()) {
                    @Override
                    public String getFilename() {
                        return file.getOriginalFilename();
                    }
                });
            } else {
                log.error("업로드된 파일이 비어있거나 null입니다.");
            }

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            log.info("요청 엔티티 생성: {}", requestEntity);
            ResponseEntity<String> response = restTemplate.postForEntity(
                    parsingUrl + "/analyze",
                    requestEntity,
                    String.class
            );

            return objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        } catch (Exception e) {
            throw new AnalyzeApiCallFailedException();
        }
    }

    private AnalyzeOtherEmotionResponse mapToAnalyzeOtherEmotionResponse(Map<String, Object> map) {
        return new AnalyzeOtherEmotionResponse(
                (Integer) map.get("charmScore"),
                (String) map.get("feedbackTitle"),
                (String) map.get("feedbackContent"),
                (String) map.get("tipTitle"),
                (List<String>) map.get("tipContent"),
                (String) map.get("cautionTitle"),
                (List<String>) map.get("cautionContent"),
                objectMapper.convertValue(map.get("chat"), new TypeReference<List<AnalyzedChat>>() {})
        );
    }

    private AnalyzeMyEmotionResponse mapToAnalyzeMyEmotionResponse(Map<String, Object> map) {
        return new AnalyzeMyEmotionResponse(
                (Integer) map.get("charmScore"),
                (String) map.get("feedbackTitle"),
                (String) map.get("feedbackContent"),
                (String) map.get("charmPointTitle"),
                (List<String>) map.get("charmPointContent"),
                objectMapper.convertValue(map.get("chat"), new TypeReference<List<AnalyzedChat>>() {})
        );
    }
}