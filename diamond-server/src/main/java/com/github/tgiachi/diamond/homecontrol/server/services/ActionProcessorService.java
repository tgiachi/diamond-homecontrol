package com.github.tgiachi.diamond.homecontrol.server.services;

import com.github.tgiachi.diamond.homecontrol.api.annotations.ActionProcessor;
import com.github.tgiachi.diamond.homecontrol.api.impl.services.AbstractDiamondService;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.actions.IDiamondActionProcessor;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.services.IActionProcessorService;
import com.github.tgiachi.diamond.homecontrol.api.utils.ReflectionUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.Executor;

@Service
public class ActionProcessorService extends AbstractDiamondService implements IActionProcessorService {
    private final HashMap<String, Class<? extends IDiamondActionProcessor>> actions = new HashMap<>();

    private final ThreadPoolTaskExecutor taskExecutor;
    private final ApplicationContext applicationContext;

    public ActionProcessorService(@Qualifier("generalExecutor") Executor taskExecutor, ApplicationContext applicationContext) {
        this.taskExecutor = (ThreadPoolTaskExecutor) taskExecutor;
        this.applicationContext = applicationContext;
    }

    @Override
    public void onStart() {
        super.onStart();
        scanActions();
    }

    private void scanActions() {
        var classes = ReflectionUtils.getAnnotation(ActionProcessor.class);
        classes.forEach(c -> {
            var annotation = c.getAnnotation(ActionProcessor.class);
            actions.put(annotation.name(), (Class<? extends IDiamondActionProcessor>) c);
        });
    }

    @Override
    public void executeAction(String name) {
        if (actions.containsKey(name)) {
            logger.info("Executing action {}", name);
            var actionClass = actions.get(name);

            var action = applicationContext.getBean(actionClass);

            taskExecutor.execute(action::execute);
        }
    }
}
