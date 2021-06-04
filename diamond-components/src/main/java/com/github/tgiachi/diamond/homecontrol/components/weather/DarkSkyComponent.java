package com.github.tgiachi.diamond.homecontrol.components.weather;

import com.github.tgiachi.diamond.homecontrol.api.annotations.DiamondComponent;
import com.github.tgiachi.diamond.homecontrol.api.annotations.ScheduledComponent;
import com.github.tgiachi.diamond.homecontrol.api.data.ComponentPollResult;
import com.github.tgiachi.diamond.homecontrol.api.data.ComponentTypes;
import com.github.tgiachi.diamond.homecontrol.api.impl.components.AbstractDiamondComponent;
import org.springframework.stereotype.Service;

@Service
@DiamondComponent(configClass = DarkSkyConfig.class, name = "DarkSky Weather", category = ComponentTypes.WEATHER, version = "1.0", description = "Get weather information")
@ScheduledComponent(seconds = 10)
public class DarkSkyComponent extends AbstractDiamondComponent<DarkSkyConfig> {

    public DarkSkyComponent() {
        setPoll(true);
    }

    @Override
    public DarkSkyConfig getDefaultConfig() {
        return new DarkSkyConfig();
    }

    @Override
    public boolean start() {
        return true;
    }

    @Override
    public boolean stop() {
        return false;
    }

    @Override
    public ComponentPollResult<?> poll() {
        System.gc();
        return super.poll();
    }
}
