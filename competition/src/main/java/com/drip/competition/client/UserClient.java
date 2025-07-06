package com.drip.competition.client;

import com.drip.competition.dto.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class UserClient {
    private final RestTemplate restTemplate;
    private final String userServiceUrl = "http://user-service/users/";

    public UserClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserDTO getUserById(UUID id) {
        return restTemplate.getForObject(userServiceUrl + id, UserDTO.class);
    }
}
