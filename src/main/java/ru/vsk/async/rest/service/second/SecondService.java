package ru.vsk.async.rest.service.second;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.vsk.async.rest.service.ApiService;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class SecondService implements ApiService {

    private final RestTemplate restTemplate;

    @Override
    @Async
    public CompletableFuture<String> getData() {
        String url = "http://localhost:8080/mock/second";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return CompletableFuture.completedFuture(response.getBody());
    }
}
