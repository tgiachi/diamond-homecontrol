package com.github.tgiachi.diamond.homecontrol.server.services;

import com.github.tgiachi.diamond.homecontrol.api.data.ComponentPollResult;
import com.github.tgiachi.diamond.homecontrol.api.impl.services.AbstractDiamondService;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.entities.IBaseEntity;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.events.IEventListener;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.services.IEventService;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.services.INoSqlService;
import lombok.Getter;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

@Service
public class EventService extends AbstractDiamondService implements IEventService {

    @Getter
    private Map<String, List<IEventListener>> eventListeners = new HashMap<>();

    private static final String ALL_EVENTS = "*";

    private final INoSqlService noSqlService;
    private final Executor threadPoolExecutor;

    public EventService(INoSqlService noSqlService, @Qualifier("generalExecutor") Executor executor) {
        this.noSqlService = noSqlService;
        this.threadPoolExecutor = executor;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onPoolResult(ComponentPollResult<? extends IBaseEntity> message) {
        logger.debug("Received new event: {}", message.getEntityClass().getSimpleName());
        noSqlService.insert(message.getData(), (Class<IBaseEntity>) message.getEntityClass());
        dispatchEvents(message);
    }

    private void dispatchEvents(ComponentPollResult<? extends IBaseEntity> message) {
        if (eventListeners.containsKey(message.getEntityClass().getSimpleName())) {
            eventListeners.get(message.getEntityClass().getSimpleName()).stream().forEach(listener -> threadPoolExecutor.execute(() -> {
                listener.onEvent(message.getData());
            }));
        }
        if (eventListeners.containsKey(ALL_EVENTS)) {
            eventListeners.get(ALL_EVENTS).stream().forEach(listener -> threadPoolExecutor.execute(() -> {
                listener.onEvent(message.getData());
            }));
        }
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void addEventListener(String eventName, IEventListener listener) {
        if (!eventListeners.containsKey(eventName)) {
            eventListeners.put(eventName, new ArrayList<>());
        }
        eventListeners.get(eventName).add(listener);
    }
}
