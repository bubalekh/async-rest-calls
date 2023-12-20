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
public class SyncAggregatorServiceImpl extends AggregatorService<String> {

    private final ApiService firstService;
    private final ApiService secondService;
    private final ApiService thirdService;

    public SyncAggregatorServiceImpl(Executor executor, ApiService firstService, ApiService secondService, ApiService thirdService) {
        super(executor);
        this.firstService = firstService;
        this.secondService = secondService;
        this.thirdService = thirdService;
    }

    @Override
    public List<String> aggregateData() {
        return List.of(
                firstService.getData(),
                secondService.getData(),
                thirdService.getData()
        );
    }

    @Override
    public void fetchData(Queue<CompletableFuture<String>> integrations, List<String> result) {}
}
