package ru.vsk.async.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.VirtualThreadTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class AsyncRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsyncRestApplication.class, args);
    }

    @Bean
    public Executor taskExecutor() {
        return new VirtualThreadTaskExecutor("AsyncCall");
    }
}
