package ru.vsk.async.rest.service.third;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.vsk.async.rest.service.ApiService;

@Service
@RequiredArgsConstructor
public class ThirdService implements ApiService {

    private final RestTemplate restTemplate;

    @Override
    public String getData() {
        String url = "http://localhost:8080/mock/third";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }
}
