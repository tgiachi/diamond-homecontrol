package com.github.tgiachi.diamond.homecontrol.api.components;


import com.github.tgiachi.diamond.homecontrol.api.annotations.DiamondComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;


@Component
@DiamondComponent(name = "Test component", version = "1.0", description = "Test", category = "TEST")

public class TestComponent implements Serializable {
    private Logger logger = LoggerFactory.getLogger(getClass());

    String t;

    public TestComponent() {
        logger.info("Initialized");
        t = UUID.randomUUID().toString();
    }

    @Async
    public CompletableFuture<String> testFuture(int delay) throws Exception {

        logger.info("Starting task");
        Thread.sleep(delay);

        logger.info("Task Ended");

        return CompletableFuture.completedFuture("ok");
    }

    public void start() {
        logger.info("start");

    }

    public void test() {
        logger.info("{}", t);
    }
}
