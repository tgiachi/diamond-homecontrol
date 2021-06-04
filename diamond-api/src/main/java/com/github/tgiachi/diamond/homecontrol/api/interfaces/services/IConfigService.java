package com.github.tgiachi.diamond.homecontrol.api.interfaces.services;

import com.github.tgiachi.diamond.homecontrol.api.interfaces.components.IDiamondComponent;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.config.IDiamondComponentConfig;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.services.base.IDiamondService;

public interface IConfigService extends IDiamondService {

    <TConfig extends IDiamondComponentConfig> TConfig getConfigForComponent(IDiamondComponent<TConfig> component);

    <TConfig extends IDiamondComponentConfig> void saveConfigForComponent(IDiamondComponent<TConfig> diamondComponent);
}
