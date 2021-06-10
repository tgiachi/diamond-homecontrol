package com.github.tgiachi.diamond.homecontrol.components.categories.weather;

import ch.rasc.darksky.DsClient;
import ch.rasc.darksky.model.DsForecastRequest;
import ch.rasc.darksky.model.DsUnit;
import com.github.tgiachi.diamond.homecontrol.api.annotations.DiamondComponent;
import com.github.tgiachi.diamond.homecontrol.api.annotations.ScheduledComponent;
import com.github.tgiachi.diamond.homecontrol.api.data.ComponentPollResult;
import com.github.tgiachi.diamond.homecontrol.api.data.ComponentPollResultType;
import com.github.tgiachi.diamond.homecontrol.api.data.ComponentTypes;
import com.github.tgiachi.diamond.homecontrol.api.data.config.DiamondConfig;
import com.github.tgiachi.diamond.homecontrol.api.impl.components.AbstractDiamondComponent;
import org.springframework.stereotype.Service;

@Service
@DiamondComponent(configClass = DarkSkyConfig.class, name = "DarkSky Weather", category = ComponentTypes.WEATHER, version = "1.0", description = "Get weather information")
@ScheduledComponent(seconds = 300)
public class DarkSkyComponent extends AbstractDiamondComponent<DarkSkyConfig> {

    private final DiamondConfig config;

    private DsClient dsClient;
    private DsForecastRequest request;

    public DarkSkyComponent(DiamondConfig config) {
        this.config = config;
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
                .latitude(Double.toString(config.getHomeLatitude()))
                .longitude(Double.toString(config.getHomeLatitude()))
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

        var response = dsClient.sendForecastRequest(request);
        response.currently().apparentTemperature();
        var entity = new DarkSkyEntity();
        entity.setCurrentTemperature(response.currently().temperature());
        response.currently().sunriseTime();
        var pollResult = new ComponentPollResult<DarkSkyEntity>();
        pollResult.setEntityClass(DarkSkyEntity.class);
        pollResult.setData(entity);
        pollResult.setStatus(ComponentPollResultType.SUCCESS);

        logger.info("Checking weather: {}", entity.getCurrentTemperature());

        return pollResult;
    }
}
