package com.github.tgiachi.diamond.homecontrol.components.categories.tracking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tgiachi.diamond.homecontrol.api.annotations.DiamondComponent;
import com.github.tgiachi.diamond.homecontrol.api.data.ComponentTypes;
import com.github.tgiachi.diamond.homecontrol.api.data.config.DiamondConfig;
import com.github.tgiachi.diamond.homecontrol.api.impl.components.AbstractDiamondComponent;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.mqtt.IMqttMessageListener;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.services.IMqttClientService;
import com.github.tgiachi.diamond.homecontrol.api.utils.LatLonUtils;
import com.github.tgiachi.diamond.homecontrol.components.categories.tracking.data.OwnTrackRawMessage;
import com.github.tgiachi.diamond.homecontrol.components.categories.weather.DarkSkyConfig;
import org.springframework.stereotype.Service;

@Service
@DiamondComponent(configClass = DarkSkyConfig.class, name = "OwnTrack presence", category = ComponentTypes.PRESENCE, version = "1.0", description = "Track user via MQTT queue")
public class OwnTrackComponent extends AbstractDiamondComponent<OwnTrackConfig> implements IMqttMessageListener {

    private final IMqttClientService mqttClientService;
    private final ObjectMapper objectMapper;
    private final DiamondConfig diamondConfig;

    public OwnTrackComponent(IMqttClientService mqttClientService, ObjectMapper objectMapper, DiamondConfig diamondConfig) {
        this.mqttClientService = mqttClientService;
        this.objectMapper = objectMapper;
        this.diamondConfig = diamondConfig;
    }

    @Override
    public OwnTrackConfig getDefaultConfig() {
        return new OwnTrackConfig();
    }

    @Override
    public boolean start() {
        mqttClientService.subscribeTopic(config.getMqttTopic() + "#", this);
        return true;
    }

    @Override
    public boolean stop() {
        return true;
    }

    @Override
    public void onMessage(String topic, String message) {
        try {
            var deviceName = topic.replace(config.getMqttTopic() + "/", "");

            var rawMessage = objectMapper.readValue(message, OwnTrackRawMessage.class);

            var ownData = new OwnTrackEntity();
            ownData.setDeviceName(deviceName);
            ownData.setLatitude(rawMessage.getLatitude());
            ownData.setLongitude(rawMessage.getLongitude());
            ownData.setLongitude(rawMessage.getAltitude());
            ownData.setDistanceFromHome(LatLonUtils.distance(diamondConfig.getHomeLatitude(), rawMessage.getLatitude(), diamondConfig.getHomeLongitude(), rawMessage.getLongitude(), 0, rawMessage.getAltitude()));

            logger.info("DeviceName: {} Location {} {}", deviceName, rawMessage.getLatitude(), rawMessage.getLongitude());

            broadcastEntityResult(ownData);
        } catch (Exception ex) {
            logger.error("Error during parsing ownTrack message", ex);
        }
    }
}
