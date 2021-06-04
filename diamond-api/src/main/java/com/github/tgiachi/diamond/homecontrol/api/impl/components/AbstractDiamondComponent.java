package com.github.tgiachi.diamond.homecontrol.api.impl.components;

import com.github.tgiachi.diamond.homecontrol.api.data.ComponentPollResult;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.components.IDiamondComponent;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.config.IDiamondComponentConfig;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class AbstractDiamondComponent<TConfig extends IDiamondComponentConfig> implements IDiamondComponent<TConfig> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Getter
    protected TConfig config;

    @Getter @Setter
    private boolean isPoll;

    @Override
    public ComponentPollResult<?> poll() {
        return null;
    }

    @Override
    public void initConfig(TConfig config) {
        this.config = config;
    }


}
