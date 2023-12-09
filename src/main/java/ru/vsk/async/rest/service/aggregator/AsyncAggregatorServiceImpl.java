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
import java.util.function.Supplier;

@Slf4j
@Service
@RequiredArgsConstructor
public class AsyncAggregatorServiceImpl implements AggregatorService {

    private final ApiService firstService;
    private final ApiService secondService;
    private final ApiService thirdService;

    @SuppressWarnings("rawtypes")
    private final List<CompletableFuture> integrations = new ArrayList<>();

    @Override
    public List<String> aggregateData() {
      
        List<String> result = new ArrayList<>();

        asyncCallIntegration(firstService::getData, result::add, 3, () -> log.error("First service didn't respond in time"));
        asyncCallIntegration(secondService::getData, result::add, 6, () -> log.error("Second service didn't respond in time"));
        asyncCallIntegration(thirdService::getData, result::add, 6, () -> log.error("Third service didn't respond in time"));

        integrations.forEach(CompletableFuture::join);

        return result;
    }

    private <T> void asyncCallIntegration(Supplier<T> integrationCall, Consumer<T> resultList, int timeout, Runnable timeoutCallback) {
        CompletableFuture<T> integrationCallData = CompletableFuture.supplyAsync(integrationCall);
        integrations.add(integrationCallData);
        integrationCallData
                .completeOnTimeout(null, timeout, TimeUnit.SECONDS)
                .whenComplete((data, ex) -> {
                    if (ex != null) {
                        log.error("Exception occurred while async service call. Error: {}", ex.getMessage());
                    }
                    if (data != null)
                        resultList.accept(data);
                    else
                        timeoutCallback.run();
                });
    }
}
