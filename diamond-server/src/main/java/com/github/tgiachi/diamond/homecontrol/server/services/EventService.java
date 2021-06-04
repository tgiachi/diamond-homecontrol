package com.github.tgiachi.diamond.homecontrol.server.services;

import com.github.tgiachi.diamond.homecontrol.api.data.ComponentPollResult;
import com.github.tgiachi.diamond.homecontrol.api.impl.services.AbstractDiamondService;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.entities.IBaseEntity;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.services.IEventService;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.services.INoSqlService;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.springframework.stereotype.Service;

@Service
public class EventService extends AbstractDiamondService implements IEventService {

    private final INoSqlService noSqlService;

    public EventService(INoSqlService noSqlService) {
        this.noSqlService = noSqlService;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onPoolResult(ComponentPollResult<?> message) {
        logger.info("Gotcha!");
        noSqlService.insert(message.getData(), (Class<IBaseEntity>) message.getEntityClass());
    }
}
