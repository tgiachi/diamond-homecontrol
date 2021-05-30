package com.github.tgiachi.diamond.homecontrol.server.manager;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class DiamondServerManager {
    private Logger logger = LoggerFactory.getLogger(getClass());


    public void start(){
        logger.info("start");
    }
}
