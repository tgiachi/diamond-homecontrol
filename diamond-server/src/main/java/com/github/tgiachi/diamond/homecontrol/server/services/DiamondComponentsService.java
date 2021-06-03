package com.github.tgiachi.diamond.homecontrol.server.services;

import com.github.tgiachi.diamond.homecontrol.api.annotations.DiamondComponent;
import com.github.tgiachi.diamond.homecontrol.api.annotations.ScheduledComponent;
import com.github.tgiachi.diamond.homecontrol.api.data.ComponentInfo;
import com.github.tgiachi.diamond.homecontrol.api.impl.services.AbstractDiamondService;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.components.IDiamondComponent;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.services.IJobSchedulerService;
import com.github.tgiachi.diamond.homecontrol.api.utils.ReflectionUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiamondComponentsService extends AbstractDiamondService {

    private List<ComponentInfo> diamondComponents = new ArrayList<>();

    private final ApplicationContext applicationContext;
    private final IJobSchedulerService jobSchedulerService;

    public DiamondComponentsService(ApplicationContext applicationContext, IJobSchedulerService jobSchedulerService) {
        this.applicationContext = applicationContext;
        this.jobSchedulerService = jobSchedulerService;
    }

    @Override
    public void onStart() {
        super.onStart();
        scanComponents();
        buildScheduledTasks();
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
            if (d.getComponent().isPoll()) {

                var schedulerAnnotation = d.getClassz().getAnnotation(ScheduledComponent.class);
                if (schedulerAnnotation != null) {
                    jobSchedulerService.addJob(schedulerAnnotation.seconds(), parseComponentResult(d.getComponent(), d.getName()), d.getName());
                }
            }
        });
    }

    private Runnable parseComponentResult(IDiamondComponent component, String name) {
        return () -> {
            var result = component.poll();
        };
    }

}
