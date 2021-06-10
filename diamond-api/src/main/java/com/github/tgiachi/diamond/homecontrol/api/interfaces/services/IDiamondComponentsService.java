package com.github.tgiachi.diamond.homecontrol.api.interfaces.services;

import com.github.tgiachi.diamond.homecontrol.api.data.ComponentInfo;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.services.base.IDiamondService;

import java.util.List;

public interface IDiamondComponentsService extends IDiamondService {

    List<ComponentInfo> getDiamondComponents();
}
