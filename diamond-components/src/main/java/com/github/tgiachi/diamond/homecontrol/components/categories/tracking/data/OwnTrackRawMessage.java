package com.github.tgiachi.diamond.homecontrol.components.categories.tracking.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OwnTrackRawMessage implements Serializable {

    @JsonProperty("_type")
    private String type;

    @JsonProperty("batt")
    private double battery;

    @JsonProperty("alt")
    private double altitude;

    @JsonProperty("lat")
    private double latitude;

    @JsonProperty("lon")
    private double longitude;

    @JsonProperty("vel")
    private double speed;


}
