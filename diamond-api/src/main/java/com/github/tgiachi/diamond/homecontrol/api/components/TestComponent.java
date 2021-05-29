package com.github.tgiachi.diamond.homecontrol.api.components;

import com.github.tgiachi.diamond.homecontrol.api.annotations.DiamondComponent;
import jakarta.enterprise.context.RequestScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

import java.util.UUID;


@RequestScoped
public class TestComponent {
    private Logger logger = LoggerFactory.getLogger(getClass());

    String t;
    @PostConstruct
    public void start(){
        logger.info("start");
        t = UUID.randomUUID().toString();
    }
    public void test(){
        logger.info("{}", t);
    }
}
