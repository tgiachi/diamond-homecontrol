package com.github.tgiachi.diamond.homecontrol.components.categories.test;

import com.github.tgiachi.diamond.homecontrol.api.annotations.EventEntity;
import com.github.tgiachi.diamond.homecontrol.api.entities.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@EventEntity(name = "test_events")
public class TestEntity extends BaseEntity {
    private String testValue;

    private boolean testBoolean;
}
