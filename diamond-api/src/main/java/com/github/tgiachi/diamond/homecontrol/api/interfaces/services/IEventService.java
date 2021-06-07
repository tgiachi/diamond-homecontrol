package com.github.tgiachi.diamond.homecontrol.api.interfaces.services;

import com.github.tgiachi.diamond.homecontrol.api.interfaces.events.IEventListener;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.services.base.IDiamondService;

import java.util.List;
import java.util.Map;

public interface IEventService extends IDiamondService {

    Map<String, List<IEventListener>> getEventListeners();


    void addEventListener(String eventName, IEventListener listener);

}
