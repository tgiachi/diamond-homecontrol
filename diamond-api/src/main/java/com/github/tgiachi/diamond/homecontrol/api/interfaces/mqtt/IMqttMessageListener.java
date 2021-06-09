package com.github.tgiachi.diamond.homecontrol.api.interfaces.mqtt;

public interface IMqttMessageListener {
    void onMessage(String topic, String message);
}
