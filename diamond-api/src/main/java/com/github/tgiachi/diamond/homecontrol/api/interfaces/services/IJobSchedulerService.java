package com.github.tgiachi.diamond.homecontrol.api.interfaces.services;

import com.github.tgiachi.diamond.homecontrol.api.interfaces.services.base.IDiamondService;

public interface IJobSchedulerService extends IDiamondService {

    void addJob(int seconds, Runnable task, String name);

}
