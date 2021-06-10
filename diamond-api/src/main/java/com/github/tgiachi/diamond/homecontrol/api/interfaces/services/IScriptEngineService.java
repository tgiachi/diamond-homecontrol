package com.github.tgiachi.diamond.homecontrol.api.interfaces.services;

import com.github.tgiachi.diamond.homecontrol.api.interfaces.services.base.IDiamondService;

public interface IScriptEngineService extends IDiamondService {

    void runThreadScript(String content);

    Object runSyncScript(String script);
}
