package com.github.tgiachi.diamond.homecontrol.api.interfaces.components;

import com.github.tgiachi.diamond.homecontrol.api.data.ComponentPollResult;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.config.IDiamondComponentConfig;

public interface IDiamondComponent<TConfig extends IDiamondComponentConfig> {

    boolean isPoll();

    ComponentPollResult<?> poll() throws Exception;

    void initConfig(TConfig config);

    TConfig getConfig();

    TConfig getDefaultConfig();

    boolean start();

    boolean stop();

}
