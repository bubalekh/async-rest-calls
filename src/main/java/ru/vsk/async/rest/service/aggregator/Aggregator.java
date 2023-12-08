package ru.vsk.async.rest.service.aggregator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vsk.async.rest.service.ApiService;
import ru.vsk.async.rest.service.first.FirstService;
import ru.vsk.async.rest.service.second.SecondService;
import ru.vsk.async.rest.service.third.ThirdService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
@RequiredArgsConstructor
public class Aggregator {

    private final ApiService firstService;
    private final ApiService secondService;
    private final ApiService thirdService;

    public String aggregateData() {
        StringBuilder stringBuffer = new StringBuilder();

        try {
            stringBuffer.append(firstService.getData().get(3, TimeUnit.SECONDS)).append(", ");
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            log.error("first service didn't respond in time!");
        }
        try {
            stringBuffer.append(secondService.getData().get(3, TimeUnit.SECONDS)).append(", ");
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            log.error("second service didn't respond in time!");
        }
        try {
            stringBuffer.append(thirdService.getData().get(6, TimeUnit.SECONDS)).append(", ");
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            log.error("third service didn't respond in time!");
        }

        return stringBuffer.toString();
    }
}
