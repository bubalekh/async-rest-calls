package ru.vsk.async.rest.service.aggregator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vsk.async.rest.service.ApiService;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Slf4j
@Service
public class AsyncAggregatorServiceImpl extends AggregatorService<String> {

    private final ApiService firstService;
    private final ApiService secondService;
    private final ApiService thirdService;

    public AsyncAggregatorServiceImpl(Executor executor, ApiService firstService, ApiService secondService, ApiService thirdService) {
        super(executor);
        this.firstService = firstService;
        this.secondService = secondService;
        this.thirdService = thirdService;
    }

    @Override
    public void fetchData(Queue<CompletableFuture<String>> integrations, List<String> result) {
        asyncCallIntegration(integrations, firstService::getData, result::add, 3, () -> log.error("First service didn't respond in time"));
        asyncCallIntegration(integrations, secondService::getData, result::add, 3, () -> log.error("Second service didn't respond in time"));
        asyncCallIntegration(integrations, thirdService::getData, result::add, 6, () -> log.error("Third service didn't respond in time"));
        CompletableFuture.allOf().join();
    }
}
