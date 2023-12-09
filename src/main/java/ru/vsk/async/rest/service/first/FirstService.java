package ru.vsk.async.rest.service.first;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.vsk.async.rest.service.ApiService;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class FirstService implements ApiService {

    private final RestTemplate restTemplate;

    @Override
    public String getData() {
        String url = "http://localhost:8080/mock/first";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }
}
