package com.github.tgiachi.diamond.homecontrol.api.impl.components;

import com.github.tgiachi.diamond.homecontrol.api.data.ComponentPollResult;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.components.IDiamondComponent;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class AbstractDiamondComponent implements IDiamondComponent {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Getter
    private boolean isPoll;

    @Override
    public ComponentPollResult<?> poll() {
        return null;
    }
}
