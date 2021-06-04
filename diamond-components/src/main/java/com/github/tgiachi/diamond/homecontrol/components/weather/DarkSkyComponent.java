package com.github.tgiachi.diamond.homecontrol.components.weather;

import ch.rasc.darksky.DsClient;
import ch.rasc.darksky.model.DsForecastRequest;
import ch.rasc.darksky.model.DsUnit;
import com.github.tgiachi.diamond.homecontrol.api.annotations.DiamondComponent;
import com.github.tgiachi.diamond.homecontrol.api.annotations.ScheduledComponent;
import com.github.tgiachi.diamond.homecontrol.api.data.ComponentPollResult;
import com.github.tgiachi.diamond.homecontrol.api.data.ComponentPollResultType;
import com.github.tgiachi.diamond.homecontrol.api.data.ComponentTypes;
import com.github.tgiachi.diamond.homecontrol.api.impl.components.AbstractDiamondComponent;
import org.springframework.stereotype.Service;

@Service
@DiamondComponent(configClass = DarkSkyConfig.class, name = "DarkSky Weather", category = ComponentTypes.WEATHER, version = "1.0", description = "Get weather information")
@ScheduledComponent(seconds = 10)
public class DarkSkyComponent extends AbstractDiamondComponent<DarkSkyConfig> {

    private DsClient dsClient;
    private DsForecastRequest request;

    public DarkSkyComponent() {
        setPoll(true);
    }

    @Override
    public DarkSkyConfig getDefaultConfig() {
        return new DarkSkyConfig();
    }

    @Override
    public void initConfig(DarkSkyConfig config) {
        super.initConfig(config);
        dsClient = new DsClient(config.getApiKey());
    }

    @Override
    public boolean start() {
        request = DsForecastRequest.builder()
                .latitude("46.93011019")
                .longitude("7.5635394")
                .unit(DsUnit.SI)
                .build();

        return true;
    }

    @Override
    public boolean stop() {
        return false;
    }

    @Override
    public ComponentPollResult<?> poll() throws Exception {
        logger.info("Checking weather");
        var response = dsClient.sendForecastRequest(request);
        response.currently().apparentTemperature();
        var entity = new DarkSkyEntity();
        entity.setCurrentTemperature(response.currently().temperature());
        return ComponentPollResult.builder().data(entity).status(ComponentPollResultType.SUCCESS).build();
    }
}
