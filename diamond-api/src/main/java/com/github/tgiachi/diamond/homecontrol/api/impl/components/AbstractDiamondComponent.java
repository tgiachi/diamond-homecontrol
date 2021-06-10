package com.github.tgiachi.diamond.homecontrol.api.impl.components;

import com.github.tgiachi.diamond.homecontrol.api.data.ComponentPollResult;
import com.github.tgiachi.diamond.homecontrol.api.data.ComponentPollResultType;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.components.IDiamondComponent;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.config.IDiamondComponentConfig;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.entities.IBaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.greenrobot.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class AbstractDiamondComponent<TConfig extends IDiamondComponentConfig> implements IDiamondComponent<TConfig> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Getter
    protected TConfig config;

    @Getter
    @Setter
    private boolean isPoll;

    @Override
    public ComponentPollResult<?> poll() throws Exception {
        return null;
    }

    @Override
    public void initConfig(TConfig config) {
        this.config = config;
    }


    protected <TEntity extends IBaseEntity> void broadcastEntityResult(TEntity entity) {
        var pollResult = new ComponentPollResult<TEntity>();
        pollResult.setEntityClass((Class<? extends TEntity>) entity.getClass());
        pollResult.setData(entity);
        pollResult.setStatus(ComponentPollResultType.SUCCESS);

        EventBus.getDefault().post(pollResult);
    }

}
