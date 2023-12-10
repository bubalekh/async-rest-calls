package ru.vsk.async.rest.service.aggregator;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Slf4j
public abstract class AggregatorService<T> {
    public List<T> aggregateData() {
        Queue<CompletableFuture<T>> integrations = new LinkedList<>();
        List<T> result = new ArrayList<>();
        fetchData(integrations, result);
        integrations.forEach(CompletableFuture::join);
        integrations.clear();
        return result;
    }

    public abstract void fetchData(Queue<CompletableFuture<T>> integrations, List<T> result);

    protected void asyncCallIntegration(Queue<CompletableFuture<T>> integrations, Supplier<T> integrationCall, Consumer<T> resultList, int timeout, Runnable timeoutCallback) {
        CompletableFuture<T> integrationCallData = CompletableFuture.supplyAsync(integrationCall);
        integrations.add(integrationCallData);
        integrationCallData
                .completeOnTimeout(null, timeout, TimeUnit.SECONDS)
                .whenComplete((data, ex) -> {
                    if (ex != null) {
                        log.error("Exception occurred while async service call. Error: {}", ex.getMessage());
                    }
                    if (data != null) {
                        resultList.accept(data);
                    }
                    else {
                        timeoutCallback.run();
                    }
                });
    }
}
