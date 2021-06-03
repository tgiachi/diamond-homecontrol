package com.github.tgiachi.diamond.homecontrol.api.components;


import com.github.tgiachi.diamond.homecontrol.api.annotations.DiamondComponent;
import com.github.tgiachi.diamond.homecontrol.api.annotations.ScheduledComponent;
import com.github.tgiachi.diamond.homecontrol.api.data.ComponentPollResult;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.components.IDiamondComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@ScheduledComponent(seconds = 10)
@DiamondComponent(name = "Test component", version = "1.0", description = "Test", category = "TEST")
public class TestComponent implements IDiamondComponent {
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public boolean isPoll() {
        return true;
    }

    @Override
    public ComponentPollResult<?> poll() {
        logger.info("Boom");
        return new ComponentPollResult<>();
    }
}
