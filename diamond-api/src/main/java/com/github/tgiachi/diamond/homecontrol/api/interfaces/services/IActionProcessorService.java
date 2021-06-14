package com.github.tgiachi.diamond.homecontrol.api.interfaces.services;

import com.github.tgiachi.diamond.homecontrol.api.interfaces.services.base.IDiamondService;

public interface IActionProcessorService extends IDiamondService {

    void executeAction(String name);
}
