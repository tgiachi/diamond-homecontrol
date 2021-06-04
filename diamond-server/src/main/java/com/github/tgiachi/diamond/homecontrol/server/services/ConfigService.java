package com.github.tgiachi.diamond.homecontrol.server.services;

import com.github.tgiachi.diamond.homecontrol.api.annotations.DiamondComponent;
import com.github.tgiachi.diamond.homecontrol.api.impl.services.AbstractDiamondService;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.components.IDiamondComponent;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.config.IDiamondComponentConfig;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.services.IConfigService;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.services.IFileSystemService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;

@Service
public class ConfigService extends AbstractDiamondService implements IConfigService {

    private final IFileSystemService fileSystemService;
    private String configDirectory;

    public ConfigService(IFileSystemService fileSystemService) {
        this.fileSystemService = fileSystemService;
    }

    @Override
    public void onStart() {
        configDirectory = fileSystemService.buildPath("configs");
        super.onStart();
    }

    @Override
    public <TConfig extends IDiamondComponentConfig> TConfig getConfigForComponent(IDiamondComponent<TConfig> component) {
        var annotation = component.getClass().getAnnotation(DiamondComponent.class);
        TConfig config = null;
        if (annotation != null) {
            if (!new File(buildConfigFullFilename(component)).exists()) {
                try {
                    logger.warn("Default config for component {} not found, creating default", annotation.name());
                    config = component.getDefaultConfig();
                    fileSystemService.writeToFileYaml(buildConfigFilename(component), config);
                } catch (Exception ex) {
                    logger.error("Error during creating default config for {}", annotation.name(), ex);
                }
            } else {
                try {

                    config = (TConfig) fileSystemService.readFileFromYaml(buildConfigFilename(component), component.getDefaultConfig().getClass());

                    component.initConfig((TConfig) config);

                } catch (Exception ex) {
                    logger.error("Error during load config for {}", annotation.name(), ex);
                }
            }
        }
        return config;
    }

    @Override
    public <TConfig extends IDiamondComponentConfig> void saveConfigForComponent(IDiamondComponent<TConfig> diamondComponent) {
        try {
            fileSystemService.writeToFileYaml(buildConfigFilename(diamondComponent), diamondComponent.getConfig());
        } catch (Exception ex) {
            logger.error("Error during save config for {}, ", diamondComponent.getClass().getSimpleName(), ex);
        }
    }

    private String buildConfigFullFilename(IDiamondComponent<?> component) {
        return fileSystemService.buildPath("configs", String.format("%s.yaml", component.getClass().getSimpleName()));
    }

    private String buildConfigFilename(IDiamondComponent<?> component) {
        return Paths.get("configs", String.format("%s.yaml", component.getClass().getSimpleName())).toString();
    }

}
