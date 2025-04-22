package com.tina.tina_server.domain.emotion.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileUploader {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${external.ai.url}")
    private String uploadApiUrl;

    @Value("${external.ai.token}")
    private String uploadToken;

    public List<String> uploadCSVFilesFromZip(byte[] zipData) {
        List<String> fileIds = new ArrayList<>();

        try (ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(zipData))) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (!entry.getName().endsWith(".csv")) continue;

                byte[] fileBytes = zipInputStream.readAllBytes();
                String fileId = uploadSingleCSV(entry.getName(), fileBytes);
                fileIds.add(fileId);
            }
        } catch (IOException e) {
            throw new RuntimeException("ZIP 파일 처리 중 오류 발생", e);
        }

        return fileIds;
    }

    private String uploadSingleCSV(String filename, byte[] fileBytes) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBearerAuth(uploadToken);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        Resource fileResource = new ByteArrayResource(fileBytes) {
            @Override
            public String getFilename() {
                return filename;
            }
        };
        body.add("file", fileResource);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                uploadApiUrl + "/api/v1/files/",
                HttpMethod.POST,
                request,
                Map.class
        );

        return (String) response.getBody().get("id");
    }
}