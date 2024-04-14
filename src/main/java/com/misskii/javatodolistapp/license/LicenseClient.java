package com.misskii.javatodolistapp.license;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LicenseClient {
    public String createTrialLicense(String userEmail) throws JsonProcessingException {
        Map<String, String> jsonData = new HashMap<>();
        jsonData.put("userEmail", userEmail);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(jsonData);
        try {
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.postForObject("https://localhost:8443/api/trial", request, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response);
            return jsonNode.get("licenseValue").asText();
        } catch (HttpClientErrorException.Forbidden ex) {
            String responseBody = ex.getResponseBodyAsString();
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(responseBody);
                return jsonNode.get("errorMessage").asText();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    public List<String> validateLicenseKey(String userEmail, String licenseKey) throws JsonProcessingException, ResourceAccessException {
        Map<String, String> jsonData = new HashMap<>();
        jsonData.put("userEmail", userEmail);
        jsonData.put("licenseValue", licenseKey);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(jsonData);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject("https://localhost:8443/api/validate", request, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response);
        List<String> jsonResult = new ArrayList<>();
        jsonResult.add(jsonNode.get("licenseStatus").asText());
        jsonResult.add(jsonNode.get("expiredDate").asText());
        return jsonResult;
    }
}
