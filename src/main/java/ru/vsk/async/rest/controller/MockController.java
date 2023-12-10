package ru.vsk.async.rest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/mock")
public class MockController {

    @GetMapping("/first")
    public ResponseEntity<String> getDataFromFirstService() throws InterruptedException {
        log.info("First integration was called!");
        Thread.sleep(1000);
        return ResponseEntity.ok("Some mock data from service 1");
    }

    @GetMapping("/second")
    public ResponseEntity<String> getDataFromSecondService() throws InterruptedException {
        log.info("Second integration was called!");
        Thread.sleep(5000);
        return ResponseEntity.ok("Some mock data from service 2");
    }

    @GetMapping("/third")
    public ResponseEntity<String> getDataFromThirdService() throws InterruptedException {
        log.info("Third integration was called!");
        Thread.sleep(3000);
        return ResponseEntity.ok("Some mock data from service 3");
    }
}
