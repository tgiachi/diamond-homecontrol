package com.github.tgiachi.diamond.homecontrol.api.impl.events;

import com.github.tgiachi.diamond.homecontrol.api.annotations.EventEntity;
import com.github.tgiachi.diamond.homecontrol.api.entities.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@EventEntity(name = "events")
public class EventTrackEntity extends BaseEntity {
    private String eventClassName;
    private Serializable data;

}
