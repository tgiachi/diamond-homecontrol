package com.github.tgiachi.diamond.homecontrol.server.manager;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class DiamondServerManager {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @PostConstruct
    public void start(){
        logger.info("start");
    }
}
