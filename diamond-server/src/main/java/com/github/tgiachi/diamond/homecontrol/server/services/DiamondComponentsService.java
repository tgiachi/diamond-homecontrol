package com.github.tgiachi.diamond.homecontrol.server.services;

import com.github.tgiachi.diamond.homecontrol.api.annotations.DiamondComponent;
import com.github.tgiachi.diamond.homecontrol.api.annotations.ScheduledComponent;
import com.github.tgiachi.diamond.homecontrol.api.data.ComponentInfo;
import com.github.tgiachi.diamond.homecontrol.api.impl.services.AbstractDiamondService;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.components.IDiamondComponent;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.services.IConfigService;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.services.IDiamondComponentsService;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.services.IJobSchedulerService;
import com.github.tgiachi.diamond.homecontrol.api.utils.ReflectionUtils;
import lombok.Getter;
import org.greenrobot.eventbus.EventBus;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiamondComponentsService extends AbstractDiamondService implements IDiamondComponentsService {

    @Getter
    private List<ComponentInfo> diamondComponents = new ArrayList<>();

    private final ApplicationContext applicationContext;
    private final IJobSchedulerService jobSchedulerService;
    private final IConfigService configService;

    public DiamondComponentsService(ApplicationContext applicationContext, IJobSchedulerService jobSchedulerService, IConfigService configService) {
        this.applicationContext = applicationContext;
        this.configService = configService;
        this.jobSchedulerService = jobSchedulerService;
    }

    @Override
    public void onStart() {
        super.onStart();
        scanComponents();
        buildConfigs();
        startComponents();
        buildScheduledTasks();

    }


    private void buildConfigs() {

        diamondComponents.forEach(c -> {
            logger.info("Getting config for {}", c.getName());
            var config = configService.getConfigForComponent(c.getComponent());
            if (config != null) {
                c.setEnabled(config.isEnabled());
            }
        });
    }

    private void startComponents() {
        diamondComponents.forEach(c -> {
            if (c.isEnabled()) {
                logger.info("Starting component {}", c.getName());
                c.getComponent().start();
            }
        });
    }

    private void scanComponents() {
        var classes = ReflectionUtils.getAnnotation(DiamondComponent.class);
        classes.forEach(s -> {
            var annotation = s.getAnnotation(DiamondComponent.class);
            logger.info("Found component: {} v{} - {}", annotation.name(), annotation.version(), annotation.description());
            try {
                var cmp = (IDiamondComponent) applicationContext.getBean(s);
                var cmpInfo = new ComponentInfo();
                cmpInfo.setComponent(cmp);
                cmpInfo.setClassz(s);
                cmpInfo.setCategory(annotation.category());
                cmpInfo.setName(annotation.name());
                cmpInfo.setVersion(annotation.version());
                cmpInfo.setDescription(annotation.description());
                diamondComponents.add(cmpInfo);

            } catch (Exception ex) {
                logger.error("Error during init component: {}", annotation.name(), ex);
            }
        });
    }

    private void buildScheduledTasks() {
        diamondComponents.forEach(d -> {
            if (d.getComponent().isPoll() && d.isEnabled()) {

                var schedulerAnnotation = d.getClassz().getAnnotation(ScheduledComponent.class);
                if (schedulerAnnotation != null) {
                    jobSchedulerService.addJob(schedulerAnnotation.seconds(), parseComponentResult(d.getComponent(), d.getName()), d.getName());
                }
            }
        });
    }

    private Runnable parseComponentResult(IDiamondComponent component, String name) {
        return () -> {
            try {
                var result = component.poll();

                EventBus.getDefault().post(result);
            } catch (Exception ex) {
                logger.error("Error during poll component: {}", name, ex);
            }
        };
    }

}
