package com.github.tgiachi.diamond.homecontrol.api.data;

import com.github.tgiachi.diamond.homecontrol.api.interfaces.components.IDiamondComponent;
import lombok.Data;

@Data
public class ComponentInfo {
    private String name;

    private String description;

    private String version;

    private String category;

    private Class<?> classz;

    private boolean enabled;

    private IDiamondComponent component;
}
