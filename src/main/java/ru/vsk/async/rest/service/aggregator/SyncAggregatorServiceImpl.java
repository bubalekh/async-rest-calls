package ru.vsk.async.rest.service.aggregator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vsk.async.rest.service.ApiService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SyncAggregatorServiceImpl implements AggregatorService {

    private final ApiService firstService;
    private final ApiService secondService;
    private final ApiService thirdService;

    @Override
    public List<String> aggregateData() {
        return List.of(
                firstService.getData(),
                secondService.getData(),
                thirdService.getData()
        );
    }
}
