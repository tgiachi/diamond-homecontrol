package com.github.tgiachi.diamond.homecontrol.api.data.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
@ConfigurationProperties(prefix = "diamond")
public class DiamondConfig implements Serializable {

    private double homeLatitude;
    private double homeLongitude;

    private String test;
}
