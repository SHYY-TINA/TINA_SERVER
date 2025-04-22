package com.tina.tina_server.domain.emotion.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.core.io.ByteArrayResource;

@Component
public class ParsingClient {

    private final RestTemplate restTemplate;

    @Value("${external.parsing.url}")
    private String parsingApiUrl;

    public ParsingClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public byte[] uploadAndParse(MultipartFile file) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            });

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<byte[]> response = restTemplate.exchange(
                    parsingApiUrl + "/parse_iphone_kakao",
                    HttpMethod.POST,
                    requestEntity,
                    byte[].class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                throw new RuntimeException("파싱 서버 요청 실패");
            }
        } catch (Exception e) {
            throw new RuntimeException("파싱 서버 요청 실패", e);
        }
    }
}