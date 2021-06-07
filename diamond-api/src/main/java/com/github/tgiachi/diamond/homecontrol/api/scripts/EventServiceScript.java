package com.github.tgiachi.diamond.homecontrol.api.scripts;

import com.github.tgiachi.diamond.homecontrol.api.annotations.ScriptEngineClass;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.services.IEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@ScriptEngineClass("events")
@Component
public class EventServiceScript {

    private final IEventService eventService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public EventServiceScript(IEventService eventService) {
        this.eventService = eventService;
    }

    public void subscribeEvent(String eventName, Function<Object, Void> func) {
        logger.info("Adding event watcher for {}", eventName);
        eventService.addEventListener(eventName, func::apply);
    }

}
