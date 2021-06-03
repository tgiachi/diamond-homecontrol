package com.github.tgiachi.diamond.homecontrol.server.services;

import com.github.tgiachi.diamond.homecontrol.api.impl.services.AbstractDiamondService;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.services.IJobSchedulerService;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class JobSchedulerService extends AbstractDiamondService implements IJobSchedulerService {

    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);


    @Override
    public void addJob(int seconds, Runnable task, String name) {
        logger.info("Adding Job {} every {}", name, seconds);
        scheduledExecutorService.scheduleAtFixedRate(buildJob(task, name), seconds, seconds, TimeUnit.SECONDS);
    }

    private Runnable buildJob(Runnable runnable, String name) {
        return () -> {
            try {
                logger.debug("Executing job: {}", name);
                runnable.run();


            } catch (Exception ex) {
                logger.error("Error during executing job: {}", name, ex);
            }
        };
    }
}
