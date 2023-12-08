package ru.vsk.async.rest.service;

import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface ApiService {

    CompletableFuture<String> getData();
}
