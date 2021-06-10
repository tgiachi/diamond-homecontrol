package com.github.tgiachi.diamond.homecontrol.components.categories.tracking;

import com.github.tgiachi.diamond.homecontrol.api.annotations.EventEntity;
import com.github.tgiachi.diamond.homecontrol.api.entities.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@EventEntity(name = "owntracks_events")
public class OwnTrackEntity extends BaseEntity {

    private String deviceName;
    private double latitude;
    private double longitude;
    private double altitude;

    private double distanceFromHome;
}
