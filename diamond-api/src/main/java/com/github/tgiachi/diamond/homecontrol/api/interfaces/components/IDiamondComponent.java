package com.github.tgiachi.diamond.homecontrol.api.interfaces.components;

import com.github.tgiachi.diamond.homecontrol.api.data.ComponentPollResult;

public interface IDiamondComponent {

    boolean isPoll();

    ComponentPollResult<?> poll();

}
