package com.github.tgiachi.diamond.homecontrol.components.categories.test;

import com.github.tgiachi.diamond.homecontrol.api.annotations.DiamondComponent;
import com.github.tgiachi.diamond.homecontrol.api.annotations.ScheduledComponent;
import com.github.tgiachi.diamond.homecontrol.api.data.ComponentPollResult;
import com.github.tgiachi.diamond.homecontrol.api.data.ComponentPollResultType;
import com.github.tgiachi.diamond.homecontrol.api.data.ComponentTypes;
import com.github.tgiachi.diamond.homecontrol.api.impl.components.AbstractDiamondComponent;
import com.github.tgiachi.diamond.homecontrol.components.categories.weather.DarkSkyConfig;
import org.springframework.stereotype.Service;

@Service
@DiamondComponent(configClass = DarkSkyConfig.class, name = "Test component", category = ComponentTypes.TEST, version = "1.0", description = "Test component")
@ScheduledComponent(seconds = 10)
public class TestComponent extends AbstractDiamondComponent<TestConfig> {
    @Override
    public TestConfig getDefaultConfig() {
        return new TestConfig();
    }

    @Override
    public boolean start() {
        setPoll(true);
        return true;
    }

    @Override
    public boolean stop() {
        return true;
    }

    @Override
    public ComponentPollResult<?> poll() throws Exception {
        var testEntity = new TestEntity();
        testEntity.setTestBoolean(true);
        testEntity.setTestValue("ok");
        return ComponentPollResult.builder().entityClass(TestEntity.class).data(testEntity).status(ComponentPollResultType.SUCCESS).build();
    }
}
