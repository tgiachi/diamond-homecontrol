package com.github.tgiachi.diamond.homecontrol.server.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tgiachi.diamond.homecontrol.api.impl.services.AbstractDiamondService;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.mqtt.IMqttMessageListener;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.services.IMqttClientService;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Service
public class MqttClientService extends AbstractDiamondService implements IMqttClientService {

    private static final String defaultMqttServerUrl = "tcp://mqtt.eclipseprojects.io:1883";

    private IMqttAsyncClient mqttAsyncClient;

    private final HashMap<String, List<IMqttMessageListener>> listeners = new HashMap<>();

    private boolean isConnected;

    private ThreadPoolTaskExecutor threadPool;

    @Value("${diamond.mqtt.server.url:}")
    private String mqttServerUrl;

    @Value("${diamond.mqtt.server.enabled}")
    private boolean mqttServerEnabled;

    private ObjectMapper objectMapper;

    public MqttClientService(@Qualifier("generalExecutor") Executor executor, ObjectMapper objectMapper) {
        this.threadPool = (ThreadPoolTaskExecutor) executor;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onStart() {
        super.onStart();
        connectToMqttServer();
    }

    private void connectToMqttServer() {
        if (mqttServerUrl == null || mqttServerUrl.equals("")) {
            mqttServerUrl = defaultMqttServerUrl;
        }
        if (mqttServerEnabled) {
            logger.info("Connecting to {}", mqttServerUrl);
            try {
                mqttAsyncClient = new MqttAsyncClient(mqttServerUrl, UUID.randomUUID().toString());
                var connec = mqttAsyncClient.connect(buildConnectOptions());
                connec.waitForCompletion();
                isConnected = true;
                logger.info("Connected to {}", mqttServerUrl);
            } catch (Exception ex) {
                logger.error("Error during connect to Mqtt server: {}", mqttServerUrl, ex);
            }
        }
    }

    private MqttConnectOptions buildConnectOptions() {
        var options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        return options;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public <TMessage extends Serializable> void sendMessage(String topic, TMessage tMessage) {
        var callable = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                logger.debug("Sending message {} to topic: {}", tMessage.getClass().getSimpleName(), topic);
                var json = objectMapper.writeValueAsString(tMessage);
                mqttAsyncClient.publish(topic, new MqttMessage(json.getBytes(StandardCharsets.UTF_8)));
                return null;
            }
        };
        threadPool.submit(callable);
    }

    @Override
    public void subscribeTopic(String topic, IMqttMessageListener listener) {
        var cleanTopic = topic;

        if (topic.endsWith("#")) {
            cleanTopic = cleanTopic.replace("#", "");
        }

        if (!listeners.containsKey(cleanTopic)) {
            listeners.put(cleanTopic, new ArrayList<>());
        }
        if (listeners.get(cleanTopic).size() == 0) {
            try {
                var token = mqttAsyncClient.subscribe(topic, 0, this::dispatchMessages);
                token.waitForCompletion();
            } catch (Exception ex) {
                logger.error("Error during subscribe topic {}", topic, ex);
            }
        }
        listeners.get(cleanTopic).add(listener);
    }

    private void dispatchMessages(String topic, MqttMessage message) {
        var strMessage = new String(message.getPayload());
        var listenerToNotify = parseTopic(topic);
        listenerToNotify.forEach(l -> {
            threadPool.execute(() -> {
                l.onMessage(topic, strMessage);
            });
        });
        if (listeners.containsKey("#")) {
            var notifyAll = listeners.get("#");
            notifyAll.forEach(l -> {
                threadPool.execute(() -> {
                    l.onMessage(topic, strMessage);
                });
            });
        }
    }

    private List<IMqttMessageListener> parseTopic(String inputTopic) {
        return listeners.keySet().stream().filter(inputTopic::startsWith).map(listeners::get).flatMap(Collection::stream).collect(Collectors.toList());
    }
}
