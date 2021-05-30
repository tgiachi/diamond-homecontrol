package com.github.tgiachi.diamond.homecontrol.api.impl.services;

import com.github.tgiachi.diamond.homecontrol.api.interfaces.services.base.IDiamondService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class AbstractDiamondService implements IDiamondService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public void onStart() {
        logger.info("Starting service {}", getClass().getSimpleName());
    }

    @Override

    public void onStop() {
        logger.info("Destroying service {}", getClass().getSimpleName());

    }
}
