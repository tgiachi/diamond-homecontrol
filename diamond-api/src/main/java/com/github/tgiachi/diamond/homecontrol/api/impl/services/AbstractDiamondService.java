package com.github.tgiachi.diamond.homecontrol.api.impl.services;

import com.github.tgiachi.diamond.homecontrol.api.interfaces.services.base.IDiamondService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


public abstract class AbstractDiamondService implements IDiamondService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    @PostConstruct
    public void onStart() {
        logger.info("Starting service {}", getClass().getSimpleName());
    }

    @Override

    @PreDestroy
    public void onStop() {
        logger.info("Destroying service {}", getClass().getSimpleName());
    }
}
