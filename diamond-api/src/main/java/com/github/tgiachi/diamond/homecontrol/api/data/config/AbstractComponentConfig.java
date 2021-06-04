package com.github.tgiachi.diamond.homecontrol.api.data.config;

import com.github.tgiachi.diamond.homecontrol.api.interfaces.config.IDiamondComponentConfig;
import lombok.Data;

@Data
public abstract class AbstractComponentConfig implements IDiamondComponentConfig {
    private boolean enabled;
}
