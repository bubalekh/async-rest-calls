package ru.vsk.async.rest.service.aggregator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vsk.async.rest.service.ApiService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class Aggregator {

    private final ApiService firstService;
    private final ApiService secondService;
    private final ApiService thirdService;

    public List<String> aggregateData() {
      
        List<String> result = new ArrayList<>();

        CompletableFuture<String> firstServiceData = CompletableFuture.supplyAsync(firstService::getData);
        CompletableFuture<String> thirdServiceData = CompletableFuture.supplyAsync(thirdService::getData);
        CompletableFuture<String> secondServiceData = CompletableFuture.supplyAsync(secondService::getData);

        asyncCallIntegration(firstServiceData, result::add, 3, () -> log.error("First service didn't respond in time"));
        asyncCallIntegration(secondServiceData, result::add, 3, () -> log.error("Second service didn't respond in time"));
        asyncCallIntegration(thirdServiceData, result::add, 6, () -> log.error("Third service didn't respond in time"));

        CompletableFuture.allOf(firstServiceData, secondServiceData, thirdServiceData).join();

        return result;
    }

    private <T> void asyncCallIntegration(CompletableFuture<T> integrationCall, Consumer<T> resultList, int timeout, Runnable timeoutCallback) {
        integrationCall
                .completeOnTimeout(null, timeout, TimeUnit.SECONDS)
                .whenComplete((data, ex) -> {
                    if (ex != null) {
                        log.error("Exception occurred while async service call: {}", ex.getMessage());
                    }
                    if (data != null)
                        resultList.accept(data);
                    else
                        timeoutCallback.run();
                });
    }
}
