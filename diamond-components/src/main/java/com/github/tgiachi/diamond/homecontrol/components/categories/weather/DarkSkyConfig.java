package com.github.tgiachi.diamond.homecontrol.components.categories.weather;

import com.github.tgiachi.diamond.homecontrol.api.data.config.AbstractComponentConfig;
import lombok.Data;

@Data
public class DarkSkyConfig extends AbstractComponentConfig {
    private String apiKey;
}
