package com.github.tgiachi.diamond.homecontrol.api.components;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



import java.io.Serializable;
import java.util.UUID;


@Component
public class TestComponent implements Serializable {
    private Logger logger = LoggerFactory.getLogger(getClass());

    String t;

    public TestComponent() {
        logger.info("Initialized");
        t = UUID.randomUUID().toString();
    }

    public void start() {
        logger.info("start");

    }

    public void test() {
        logger.info("{}", t);
    }
}
