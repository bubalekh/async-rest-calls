package ru.vsk.async.rest.service.aggregator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vsk.async.rest.service.ApiService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Slf4j
@Service
@RequiredArgsConstructor
public class Aggregator {

    private final ApiService firstService;
    private final ApiService secondService;
    private final ApiService thirdService;

    public List<String> aggregateData() {

        List<String> result = new ArrayList<>();

        asyncIntegrationCall(
                firstService::getData,
                result::add,
                3,
                () -> log.error("first service didn't respond in time!"));

        asyncIntegrationCall(
                secondService::getData,
                result::add,
                3,
                () -> log.error("second service didn't respond in time!"));

        asyncIntegrationCall(
                thirdService::getData,
                result::add,
                6,
                () -> log.error("third service didn't respond in time!"));

        return result;
    }

    private <T> void asyncIntegrationCall(
            Supplier<T> syncIntegrationCall,
            Consumer<T> resultList,
            int timeout,
            Runnable timeoutCallback) {
        CompletableFuture<T> responseCompletableFuture = CompletableFuture.supplyAsync(syncIntegrationCall);
        try {
            resultList.accept(responseCompletableFuture.get(timeout, TimeUnit.SECONDS));
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException timeoutException) {
            timeoutCallback.run();
        }
    }
}
