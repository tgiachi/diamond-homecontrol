package com.github.tgiachi.diamond.homecontrol.api.interfaces.config;

import java.io.Serializable;

public interface IDiamondComponentConfig extends Serializable {
    boolean isEnabled();
    void setEnabled(boolean enabled);
}
