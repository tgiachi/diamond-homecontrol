package com.github.tgiachi.diamond.homecontrol.components.weather;

import com.github.tgiachi.diamond.homecontrol.api.annotations.EventEntity;
import com.github.tgiachi.diamond.homecontrol.api.entities.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@EventEntity(name = "dark_sky_events")
public class DarkSkyEntity extends BaseEntity {
    private BigDecimal currentTemperature;
}
