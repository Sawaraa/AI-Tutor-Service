package com.tutor.service.Impl;

import com.google.api.client.util.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleDriveService {

    @Value("${google.api.key}")
    private String apiKey;

    public String readDocumentContent(String fileUrl) {
        try {
            String fileId = extractFileId(fileUrl);
            if (fileId == null) {
                return null;
            }

            String exportUrl = String.format(
                    "https://www.googleapis.com/drive/v3/files/%s/export?mimeType=text/plain&key=%s",
                    fileId, apiKey
            );

            RestTemplate restTemplate = new RestTemplate();
            String content = restTemplate.getForObject(exportUrl, String.class);

            return content;

        } catch (Exception e) {
            return null;
        }
    }

    private String extractFileId(String url) {
        if (url == null) return null;

        try {
            if (url.contains("/d/")) {
                String[] parts = url.split("/d/");
                String afterId = parts[1];
                return afterId.split("/")[0];
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
