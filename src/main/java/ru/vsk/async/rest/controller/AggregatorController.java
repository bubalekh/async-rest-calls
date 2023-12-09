package ru.vsk.async.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsk.async.rest.service.aggregator.Aggregator;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AggregatorController {

    private final Aggregator aggregator;

    @GetMapping("/aggregate")
    public ResponseEntity<List<String>> getDataFromAllServices() {
        return ResponseEntity.ok(aggregator.aggregateData());
    }
}
