package com.drip.competition.client;

import com.drip.competition.dto.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class UserClient {
    private final RestTemplate restTemplate;

    @Value("${user.service.url:http://user-service:8080/api/v1/users}")
    private String userServiceUrl;

    public UserClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserDTO getUserById(UUID id) {
        try {
            return restTemplate.getForObject(userServiceUrl + "/" + id, UserDTO.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to get user with id: " + id, e);
        }
    }
}
