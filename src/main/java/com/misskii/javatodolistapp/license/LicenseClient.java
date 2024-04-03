package com.misskii.javatodolistapp.license;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class LicenseClient {

    private RestTemplate restTemplate;
    private ObjectMapper mapper;
    private String apiUrl = "http://localhost:8080/api";
    private Map<String, String> jsonData;
    private HttpEntity<Map<String, String>> request;

    public String createTrialLicense(String userEmail) {
        apiUrl = apiUrl + "/trial";
        jsonData = new HashMap<>();
        jsonData.put("userEmail", userEmail);
        request = new HttpEntity<>(jsonData);
        try {
            restTemplate = new RestTemplate();
            String response = restTemplate.postForObject(apiUrl, request, String.class);
            mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response);
            return jsonNode.asText();
        } catch (HttpClientErrorException.Forbidden ex) {
            String responseBody = ex.getResponseBodyAsString();
            try {
                mapper = new ObjectMapper();
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

    public String validateLicenseKey(String userEmail, String licenseKey) throws JsonProcessingException {
        apiUrl = apiUrl + "/validate";
        jsonData = new HashMap<>();
        jsonData.put("userEmail", userEmail);
        jsonData.put("licenseValue", licenseKey);
        request = new HttpEntity<>(jsonData);
        restTemplate = new RestTemplate();
        String response = restTemplate.postForObject(apiUrl, request, String.class);
        mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response);
        return jsonNode.asText();
    }

    public void getLicenseInfo(){}
}
