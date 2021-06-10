package com.github.tgiachi.diamond.homecontrol.components.categories.tracking;

import com.github.tgiachi.diamond.homecontrol.api.data.config.AbstractComponentConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OwnTrackConfig extends AbstractComponentConfig {

    private String mqttTopic;

    public OwnTrackConfig() {
        mqttTopic = "owntracks/#";
    }
}
