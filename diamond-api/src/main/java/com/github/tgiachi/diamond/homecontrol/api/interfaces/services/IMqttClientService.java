package com.github.tgiachi.diamond.homecontrol.api.interfaces.services;

import com.github.tgiachi.diamond.homecontrol.api.interfaces.mqtt.IMqttMessageListener;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.services.base.IDiamondService;

import java.io.Serializable;

public interface IMqttClientService extends IDiamondService {

    <TMessage extends Serializable> void sendMessage(String topic, TMessage message);

    void subscribeTopic(String topic, IMqttMessageListener listener);
}
