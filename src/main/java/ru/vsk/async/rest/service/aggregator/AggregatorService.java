package ru.vsk.async.rest.service.aggregator;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
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

    /**
     * Executes all async calls performed through asyncIntegrationCall method.
     * You should place all your sync calls into this method one by one. Example:
     * <pre class="code">
     *     asyncCallIntegration(
     *         integrations,
     *         firstService::getData,
     *         result::add,
     *         3,
     *         () -> log.error("First service didn't respond in time")
     *     );
     *     asyncCallIntegration(
     *         integrations,
     *         firstService::getData,
     *         result::add,
     *         3,
     *         () -> log.error("Second service didn't respond in time")
     *     );
     *     asyncCallIntegration(
     *         integrations,
     *         thirdService::getData,
     *         result::add,
     *         6,
     *         () -> log.error("Third service didn't respond in time")
     *     );
     * </pre>
     * @param integrations the list of all operated tasks method must wait for completion
     * @param result the list tu put result of all operated tasks
     */
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
