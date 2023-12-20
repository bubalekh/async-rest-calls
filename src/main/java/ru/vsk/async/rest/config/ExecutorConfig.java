package ru.vsk.async.rest.config;

import org.apache.tomcat.util.threads.VirtualThreadExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class ExecutorConfig {

    @Bean
    public Executor executor() {
        return new VirtualThreadExecutor("AsyncRest-");
//        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
//        threadPoolTaskExecutor.setCorePoolSize(100);
//        threadPoolTaskExecutor.setMaxPoolSize(100);
//        threadPoolTaskExecutor.setThreadNamePrefix("AsyncRest");
//        return threadPoolTaskExecutor;
    }
}
