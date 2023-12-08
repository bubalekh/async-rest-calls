package ru.vsk.async.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/mock")
public class MockController {

    @GetMapping("/first")
    public ResponseEntity<String> getDataFromFirstService() throws InterruptedException {
        Thread.sleep(1000);
        return ResponseEntity.ok("Some mock data from service 1");
    }

    @GetMapping("/second")
    public ResponseEntity<String> getDataFromSecondService() throws InterruptedException {
        Thread.sleep(5000);
        return ResponseEntity.ok("Some mock data from service 2");
    }

    @GetMapping("/third")
    public ResponseEntity<String> getDataFromThirdService() throws InterruptedException {
        Thread.sleep(3000);
        return ResponseEntity.ok("Some mock data from service 3");
    }
}
