package ru.vsk.async.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.VirtualThreadTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class ExecutorConfig {

    @Bean
    public Executor taskExecutor() {
        return new VirtualThreadTaskExecutor("AsyncCall");
    }
}
