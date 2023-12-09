package ru.vsk.async.rest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsk.async.rest.service.aggregator.AggregatorService;
import ru.vsk.async.rest.service.aggregator.AsyncAggregatorServiceImpl;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/aggregate")
@RequiredArgsConstructor
public class AggregatorController {

    private final AggregatorService asyncAggregatorServiceImpl;
    private final AggregatorService syncAggregatorServiceImpl;

    @GetMapping("/async")
    public ResponseEntity<List<String>> getDataAsync() {
        return ResponseEntity.ok(invokeGetData(asyncAggregatorServiceImpl));
    }

    @GetMapping("/sync")
    public ResponseEntity<List<String>> getDataSync() {
        return ResponseEntity.ok(invokeGetData(syncAggregatorServiceImpl));
    }

    private List<String> invokeGetData(AggregatorService aggregatorService) {
        long startTime = System.nanoTime();
        List<String> result = aggregatorService.aggregateData();
        log.info("{} milliseconds has passed", System.nanoTime() - startTime);
        return result;
    }
}
